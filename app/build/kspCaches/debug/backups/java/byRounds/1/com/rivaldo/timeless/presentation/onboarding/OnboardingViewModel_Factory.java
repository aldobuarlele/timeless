package com.rivaldo.timeless.presentation.onboarding;

import com.rivaldo.timeless.domain.usecase.GetUserProfileUseCase;
import com.rivaldo.timeless.domain.usecase.SaveUserProfileUseCase;
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
public final class OnboardingViewModel_Factory implements Factory<OnboardingViewModel> {
  private final Provider<SaveUserProfileUseCase> saveUserProfileUseCaseProvider;

  private final Provider<GetUserProfileUseCase> getUserProfileUseCaseProvider;

  public OnboardingViewModel_Factory(
      Provider<SaveUserProfileUseCase> saveUserProfileUseCaseProvider,
      Provider<GetUserProfileUseCase> getUserProfileUseCaseProvider) {
    this.saveUserProfileUseCaseProvider = saveUserProfileUseCaseProvider;
    this.getUserProfileUseCaseProvider = getUserProfileUseCaseProvider;
  }

  @Override
  public OnboardingViewModel get() {
    return newInstance(saveUserProfileUseCaseProvider.get(), getUserProfileUseCaseProvider.get());
  }

  public static OnboardingViewModel_Factory create(
      Provider<SaveUserProfileUseCase> saveUserProfileUseCaseProvider,
      Provider<GetUserProfileUseCase> getUserProfileUseCaseProvider) {
    return new OnboardingViewModel_Factory(saveUserProfileUseCaseProvider, getUserProfileUseCaseProvider);
  }

  public static OnboardingViewModel newInstance(SaveUserProfileUseCase saveUserProfileUseCase,
      GetUserProfileUseCase getUserProfileUseCase) {
    return new OnboardingViewModel(saveUserProfileUseCase, getUserProfileUseCase);
  }
}
