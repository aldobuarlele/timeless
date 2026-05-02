package com.rivaldo.timeless.presentation.diary;

import com.rivaldo.timeless.domain.usecase.GetLogForDateUseCase;
import com.rivaldo.timeless.domain.usecase.SaveDailyLogUseCase;
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
public final class DiaryViewModel_Factory implements Factory<DiaryViewModel> {
  private final Provider<SaveDailyLogUseCase> saveDailyLogUseCaseProvider;

  private final Provider<GetLogForDateUseCase> getLogForDateUseCaseProvider;

  public DiaryViewModel_Factory(Provider<SaveDailyLogUseCase> saveDailyLogUseCaseProvider,
      Provider<GetLogForDateUseCase> getLogForDateUseCaseProvider) {
    this.saveDailyLogUseCaseProvider = saveDailyLogUseCaseProvider;
    this.getLogForDateUseCaseProvider = getLogForDateUseCaseProvider;
  }

  @Override
  public DiaryViewModel get() {
    return newInstance(saveDailyLogUseCaseProvider.get(), getLogForDateUseCaseProvider.get());
  }

  public static DiaryViewModel_Factory create(
      Provider<SaveDailyLogUseCase> saveDailyLogUseCaseProvider,
      Provider<GetLogForDateUseCase> getLogForDateUseCaseProvider) {
    return new DiaryViewModel_Factory(saveDailyLogUseCaseProvider, getLogForDateUseCaseProvider);
  }

  public static DiaryViewModel newInstance(SaveDailyLogUseCase saveDailyLogUseCase,
      GetLogForDateUseCase getLogForDateUseCase) {
    return new DiaryViewModel(saveDailyLogUseCase, getLogForDateUseCase);
  }
}
