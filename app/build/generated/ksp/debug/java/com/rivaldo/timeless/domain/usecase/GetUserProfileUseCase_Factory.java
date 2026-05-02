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
public final class GetUserProfileUseCase_Factory implements Factory<GetUserProfileUseCase> {
  private final Provider<UserProfileRepository> repositoryProvider;

  public GetUserProfileUseCase_Factory(Provider<UserProfileRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetUserProfileUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetUserProfileUseCase_Factory create(
      Provider<UserProfileRepository> repositoryProvider) {
    return new GetUserProfileUseCase_Factory(repositoryProvider);
  }

  public static GetUserProfileUseCase newInstance(UserProfileRepository repository) {
    return new GetUserProfileUseCase(repository);
  }
}
