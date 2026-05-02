package com.rivaldo.timeless.domain.usecase;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class CheckContextUseCase_Factory implements Factory<CheckContextUseCase> {
  private final Provider<Context> contextProvider;

  public CheckContextUseCase_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public CheckContextUseCase get() {
    return newInstance(contextProvider.get());
  }

  public static CheckContextUseCase_Factory create(Provider<Context> contextProvider) {
    return new CheckContextUseCase_Factory(contextProvider);
  }

  public static CheckContextUseCase newInstance(Context context) {
    return new CheckContextUseCase(context);
  }
}
