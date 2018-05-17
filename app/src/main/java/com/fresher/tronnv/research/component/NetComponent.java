package com.fresher.tronnv.research.component;

import com.fresher.tronnv.research.data.DataManager;
import com.fresher.tronnv.research.mudule.NetModule;

import javax.inject.Singleton;

import dagger.Component;
@Singleton
@Component(modules = {NetModule.class})
public interface NetComponent {
    DataManager getDataManager();
}
