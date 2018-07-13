package com.gofar.component.basiclib.base;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gofar.component.basiclib.R;
import com.luojilab.router.facade.annotation.RouteNode;

/**
 * @author lcf
 * @date 13/7/2018 下午 2:29
 * @since 1.0
 */
@RouteNode(path = "/web",desc = "网页")
public class WebActivity extends BaseCompatActivity {
    private WebView mWebView;

    private String mUrl;

    @Override
    protected int getContentLayoutId() {
        return R.layout.base_web;
    }

    @Override
    protected void initContentView(View content) {
        mWebView = content.findViewById(R.id.web_view);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setSupportMultipleWindows(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

        mWebView.setWebViewClient(mWebViewClient);
        mWebView.setWebChromeClient(mWebChromeClient);
    }

    @Override
    protected void initialize() {
        mUrl = getIntent().getStringExtra("url");
    }

    @Override
    protected void initData() {
        mWebView.loadUrl(mUrl);
    }

    private WebViewClient mWebViewClient = new WebViewClient();
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            mToolbar.setCenterTitle(title);
        }
    };

}
