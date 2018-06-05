package com.gofar.wanandroid.c.navi;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.IComponent;

/**
 * @author lcf
 * @date 2018/6/5 16:36
 * @since 1.0
 */
public class ComponentNavi implements IComponent{
    @Override
    public String getName() {
        return "ComponentNavi";
    }

    @Override
    public boolean onCall(CC cc) {
        return false;
    }
}
