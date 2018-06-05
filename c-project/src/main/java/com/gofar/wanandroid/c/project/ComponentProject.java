package com.gofar.wanandroid.c.project;

import com.billy.cc.core.component.CC;
import com.billy.cc.core.component.IComponent;

/**
 * @author lcf
 * @date 2018/6/5 16:37
 * @since 1.0
 */
public class ComponentProject implements IComponent{
    @Override
    public String getName() {
        return "ComponentProject";
    }

    @Override
    public boolean onCall(CC cc) {
        return false;
    }
}
