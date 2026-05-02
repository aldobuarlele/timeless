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
public final class ExtractPhotoMetadataUseCase_Factory implements Factory<ExtractPhotoMetadataUseCase> {
  private final Provider<DailyLogRepository> repositoryProvider;

  public ExtractPhotoMetadataUseCase_Factory(Provider<DailyLogRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ExtractPhotoMetadataUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static ExtractPhotoMetadataUseCase_Factory create(
      Provider<DailyLogRepository> repositoryProvider) {
    return new ExtractPhotoMetadataUseCase_Factory(repositoryProvider);
  }

  public static ExtractPhotoMetadataUseCase newInstance(DailyLogRepository repository) {
    return new ExtractPhotoMetadataUseCase(repository);
  }
}
