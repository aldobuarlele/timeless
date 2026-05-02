package com.rivaldo.timeless.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.rivaldo.timeless.data.local.notification.LocalNudgeProvider;
import com.rivaldo.timeless.domain.usecase.CheckContextUseCase;
import com.rivaldo.timeless.domain.usecase.GetUserProfileUseCase;
import dagger.internal.DaggerGenerated;
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
public final class TimelessReminderWorker_Factory {
  private final Provider<CheckContextUseCase> checkContextUseCaseProvider;

  private final Provider<LocalNudgeProvider> localNudgeProvider;

  private final Provider<GetUserProfileUseCase> getUserProfileUseCaseProvider;

  public TimelessReminderWorker_Factory(Provider<CheckContextUseCase> checkContextUseCaseProvider,
      Provider<LocalNudgeProvider> localNudgeProvider,
      Provider<GetUserProfileUseCase> getUserProfileUseCaseProvider) {
    this.checkContextUseCaseProvider = checkContextUseCaseProvider;
    this.localNudgeProvider = localNudgeProvider;
    this.getUserProfileUseCaseProvider = getUserProfileUseCaseProvider;
  }

  public TimelessReminderWorker get(Context appContext, WorkerParameters workerParams) {
    return newInstance(appContext, workerParams, checkContextUseCaseProvider.get(), localNudgeProvider.get(), getUserProfileUseCaseProvider.get());
  }

  public static TimelessReminderWorker_Factory create(
      Provider<CheckContextUseCase> checkContextUseCaseProvider,
      Provider<LocalNudgeProvider> localNudgeProvider,
      Provider<GetUserProfileUseCase> getUserProfileUseCaseProvider) {
    return new TimelessReminderWorker_Factory(checkContextUseCaseProvider, localNudgeProvider, getUserProfileUseCaseProvider);
  }

  public static TimelessReminderWorker newInstance(Context appContext,
      WorkerParameters workerParams, CheckContextUseCase checkContextUseCase,
      LocalNudgeProvider localNudgeProvider, GetUserProfileUseCase getUserProfileUseCase) {
    return new TimelessReminderWorker(appContext, workerParams, checkContextUseCase, localNudgeProvider, getUserProfileUseCase);
  }
}
