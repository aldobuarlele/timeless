package com.rivaldo.timeless.domain.usecase;

import com.rivaldo.timeless.domain.repository.DailyLogRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class SaveDailyLogUseCase_Factory implements Factory<SaveDailyLogUseCase> {
  private final Provider<DailyLogRepository> repositoryProvider;

  public SaveDailyLogUseCase_Factory(Provider<DailyLogRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SaveDailyLogUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SaveDailyLogUseCase_Factory create(
      Provider<DailyLogRepository> repositoryProvider) {
    return new SaveDailyLogUseCase_Factory(repositoryProvider);
  }

  public static SaveDailyLogUseCase newInstance(DailyLogRepository repository) {
    return new SaveDailyLogUseCase(repository);
  }
}
