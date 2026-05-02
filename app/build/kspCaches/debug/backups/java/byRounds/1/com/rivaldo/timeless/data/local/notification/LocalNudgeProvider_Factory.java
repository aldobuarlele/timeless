package com.rivaldo.timeless.data.local.notification;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation",
    "nullness:initialization.field.uninitialized"
})
public final class LocalNudgeProvider_Factory implements Factory<LocalNudgeProvider> {
  @Override
  public LocalNudgeProvider get() {
    return newInstance();
  }

  public static LocalNudgeProvider_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static LocalNudgeProvider newInstance() {
    return new LocalNudgeProvider();
  }

  private static final class InstanceHolder {
    private static final LocalNudgeProvider_Factory INSTANCE = new LocalNudgeProvider_Factory();
  }
}
