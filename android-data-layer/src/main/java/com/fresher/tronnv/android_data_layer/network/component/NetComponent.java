package com.fresher.tronnv.android_data_layer.network.component;


/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 * <p>
 * A Component is a mapping between one or more modules and one or more classes that will use them
 * In this particular case, I have a single module, NetModule, that is being used by a single class, WelcomeActivity.
 */


import com.fresher.tronnv.android_data_layer.DataManager;
import com.fresher.tronnv.android_data_layer.network.module.NetModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A Component is a mapping between one or more modules and one or more classes that will use them
 * In this particular case, I have a single module, NetModule, that is being used by a single class, WelcomeActivity.
 */
@Singleton
@Component(modules = {NetModule.class})
public interface NetComponent {
    DataManager getDataManager();
}
