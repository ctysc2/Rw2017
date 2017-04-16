package com.home.rw.mvp.ui.activitys.rongCloud;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

/**
 * Created by cty on 2017/2/17.
 */

public class MyExtensionModule extends DefaultExtensionModule {
    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules =  super.getPluginModules(conversationType);
        List<IPluginModule> newPluginModules = new ArrayList<>();
        newPluginModules.add(pluginModules.get(0));
        newPluginModules.add(pluginModules.get(1));
        return newPluginModules;
    }
}
