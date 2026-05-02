package com.rivaldo.timeless.presentation.home;

import com.rivaldo.timeless.domain.usecase.CalculateTimeCanvasUseCase;
import com.rivaldo.timeless.domain.usecase.GetLogForDateUseCase;
import com.rivaldo.timeless.domain.usecase.GetUserProfileUseCase;
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
public final class HomeViewModel_Factory implements Factory<HomeViewModel> {
  private final Provider<GetUserProfileUseCase> getUserProfileUseCaseProvider;

  private final Provider<CalculateTimeCanvasUseCase> calculateTimeCanvasUseCaseProvider;

  private final Provider<GetLogForDateUseCase> getLogForDateUseCaseProvider;

  public HomeViewModel_Factory(Provider<GetUserProfileUseCase> getUserProfileUseCaseProvider,
      Provider<CalculateTimeCanvasUseCase> calculateTimeCanvasUseCaseProvider,
      Provider<GetLogForDateUseCase> getLogForDateUseCaseProvider) {
    this.getUserProfileUseCaseProvider = getUserProfileUseCaseProvider;
    this.calculateTimeCanvasUseCaseProvider = calculateTimeCanvasUseCaseProvider;
    this.getLogForDateUseCaseProvider = getLogForDateUseCaseProvider;
  }

  @Override
  public HomeViewModel get() {
    return newInstance(getUserProfileUseCaseProvider.get(), calculateTimeCanvasUseCaseProvider.get(), getLogForDateUseCaseProvider.get());
  }

  public static HomeViewModel_Factory create(
      Provider<GetUserProfileUseCase> getUserProfileUseCaseProvider,
      Provider<CalculateTimeCanvasUseCase> calculateTimeCanvasUseCaseProvider,
      Provider<GetLogForDateUseCase> getLogForDateUseCaseProvider) {
    return new HomeViewModel_Factory(getUserProfileUseCaseProvider, calculateTimeCanvasUseCaseProvider, getLogForDateUseCaseProvider);
  }

  public static HomeViewModel newInstance(GetUserProfileUseCase getUserProfileUseCase,
      CalculateTimeCanvasUseCase calculateTimeCanvasUseCase,
      GetLogForDateUseCase getLogForDateUseCase) {
    return new HomeViewModel(getUserProfileUseCase, calculateTimeCanvasUseCase, getLogForDateUseCase);
  }
}
