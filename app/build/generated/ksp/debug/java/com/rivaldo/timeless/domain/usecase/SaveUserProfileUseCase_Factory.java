package com.rivaldo.timeless.domain.usecase;

import com.rivaldo.timeless.domain.repository.UserProfileRepository;
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
public final class SaveUserProfileUseCase_Factory implements Factory<SaveUserProfileUseCase> {
  private final Provider<UserProfileRepository> repositoryProvider;

  public SaveUserProfileUseCase_Factory(Provider<UserProfileRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public SaveUserProfileUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static SaveUserProfileUseCase_Factory create(
      Provider<UserProfileRepository> repositoryProvider) {
    return new SaveUserProfileUseCase_Factory(repositoryProvider);
  }

  public static SaveUserProfileUseCase newInstance(UserProfileRepository repository) {
    return new SaveUserProfileUseCase(repository);
  }
}
