//package com.fresher.tronnv.research;
//
//import android.app.Application;
//
//import com.squareup.leakcanary.AndroidExcludedRefs;
//import com.squareup.leakcanary.ExcludedRefs;
//import com.squareup.leakcanary.InstrumentationLeakDetector;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;
//
//public class InstrumentationTestExampleApplication extends InstrumentationTestExampleApplication.DebugExampleApplication {
//    @Override protected void installLeakCanary() {
//        InstrumentationLeakDetector.instrumentationRefWatcher(this)
//                .buildAndInstall();
//    }
//    public class DebugExampleApplication extends ExampleApplication {
//        @Override protected void installLeakCanary() {
//            ExcludedRefs excludedRefs = AndroidExcludedRefs.createAppDefaults()
//                    .instanceField("com.example.ExampleClass", "exampleField")
//                    .build();
//            RefWatcher refWatcher = LeakCanary.refWatcher(this)
//                    .excludedRefs(excludedRefs)
//                    .buildAndInstall();
//        }
//    }
//    public class ExampleApplication extends Application {
//
//        @Override public void onCreate() {
//            super.onCreate();
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                // This process is dedicated to LeakCanary for heap analysis.
//                // You should not init your app in this process.
//                return;
//            }
//            installLeakCanary();
//        }
//
//        protected void installLeakCanary() {
//            // no-op, LeakCanary is disabled in production.
//        }
//    }
//}