package com.gofar.wanandroid.c.home;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.IComponent;

/**
 * @author lcf
 * @date 2018/6/5 16:35
 * @since 1.0
 */
public class ComponentHome implements IComponent {
    @Override
    public String getName() {
        return "ComponentHome";
    }

    @Override
    public boolean onCall(CC cc) {
        return false;
    }
}
