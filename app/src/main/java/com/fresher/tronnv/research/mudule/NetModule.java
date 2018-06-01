package com.fresher.tronnv.research.mudule;

import com.fresher.tronnv.research.network.RetrofitClient;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * A Module defines one or more injectable classes (as denoted by the Provides annotation)
 */
/**Dagger 2 use the following annotation
 * @Module and @Provides: define classes and methods which provide dependencies
 * @Inject: request dependencies. Can be used on a constructor, a field, or a method
 * @Component: enable selected modules and used for performing dependency injection
 *
 */
/** ---------------------------------------------------------
 * Annotation---Usage-------------------
 * @Module      Used on classes which contains methods annotated with @Provides.
 * @Provides    Can be used on methods in classes annotated with @Module and is used for methods which provides objects for dependencies injection.
 * @Singleton   Single instance of this provided object is created and shared.
 * @Component   Used on an interface. This interface is used by Dagger 2 to generate code which uses the modules to fulfill the requested dependencies.
 */
@Module
public class NetModule {
    private RetrofitClient retrofitClient;
    public NetModule(RetrofitClient retrofitClient){
        this.retrofitClient = retrofitClient;
    }
    @Provides
    @Singleton
    public RetrofitClient provideRetro(){
        return retrofitClient;
    }
}
