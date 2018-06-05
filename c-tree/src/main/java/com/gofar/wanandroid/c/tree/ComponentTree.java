package com.gofar.wanandroid.c.tree;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.IComponent;

/**
 * @author lcf
 * @date 2018/6/5 16:38
 * @since 1.0
 */
public class ComponentTree implements IComponent{
    @Override
    public String getName() {
        return "ComponentTree";
    }

    @Override
    public boolean onCall(CC cc) {
        return false;
    }
}
