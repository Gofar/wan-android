package com.gofar.component.basiclib.base;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.gofar.component.basiclib.R;
import com.gofar.titlebar.TitleBar;
import com.just.agentweb.AgentWeb;
import com.luojilab.router.facade.annotation.RouteNode;

/**
 * @author lcf
 * @date 13/7/2018 下午 2:29
 * @since 1.0
 */
@RouteNode(path = "/web", desc = "网页")
public class BaseWebActivity extends BaseCompatActivity {
    protected WebView mWebView;

    private String mUrl;
    private String mTitle;
    private AgentWeb mAgentWeb;

    @Override
    protected int getContentLayoutId() {
        return R.layout.base_web;
    }

    @Override
    protected void initContentView(View content) {
        LinearLayout webContent = content.findViewById(R.id.ll_web);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(webContent, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setWebViewClient(mWebViewClient)
                .setWebChromeClient(mWebChromeClient)
                .setMainFrameErrorView(R.layout.webview_error_view, -1)
                .createAgentWeb()
                .ready()
                .go(mUrl);
        mWebView = mAgentWeb.getWebCreator().getWebView();
    }

    @Override
    protected void initialize() {
        mUrl = getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("title");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initToolBar(TitleBar toolbar) {
        super.initToolBar(toolbar);
        toolbar.setCenterTitle(mTitle);
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            return super.shouldOverrideUrlLoading(view, request);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    };

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mAgentWeb.handleKeyEvent(keyCode, event) || super.onKeyDown(keyCode, event);
    }
}
