package com.fresher.tronnv.research.component;

import android.content.Context;

import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.mudule.NetModule;

import javax.inject.Singleton;
/**
 * Created by NGUYEN VAN TRON on 05/16/18.
 */

/**
 * A Component is a mapping between one or more modules and one or more classes that will use them
 * In this particular case, I have a single module, NetModule, that is being used by a single class, WelcomeActivity.
 */
import dagger.Component;
@Singleton
@Component(modules = {NetModule.class})
public interface NetComponent {
    DataManager getDataManager();
}
