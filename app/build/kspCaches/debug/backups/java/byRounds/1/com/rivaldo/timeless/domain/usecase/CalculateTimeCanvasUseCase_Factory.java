package com.rivaldo.timeless.domain.usecase;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

@ScopeMetadata
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
public final class CalculateTimeCanvasUseCase_Factory implements Factory<CalculateTimeCanvasUseCase> {
  @Override
  public CalculateTimeCanvasUseCase get() {
    return newInstance();
  }

  public static CalculateTimeCanvasUseCase_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CalculateTimeCanvasUseCase newInstance() {
    return new CalculateTimeCanvasUseCase();
  }

  private static final class InstanceHolder {
    private static final CalculateTimeCanvasUseCase_Factory INSTANCE = new CalculateTimeCanvasUseCase_Factory();
  }
}
