package com.rivaldo.timeless;

import dagger.hilt.InstallIn;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedEntryPoint;

@OriginatingElement(
    topLevelClass = TimelessApp.class
)
@GeneratedEntryPoint
@InstallIn(SingletonComponent.class)
public interface TimelessApp_GeneratedInjector {
  void injectTimelessApp(TimelessApp timelessApp);
}
