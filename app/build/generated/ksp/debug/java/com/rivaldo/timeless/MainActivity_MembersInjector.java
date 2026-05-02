package com.rivaldo.timeless;

import com.rivaldo.timeless.domain.usecase.ScheduleReminderUseCase;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MainActivity_MembersInjector implements MembersInjector<MainActivity> {
  private final Provider<ScheduleReminderUseCase> scheduleReminderUseCaseProvider;

  public MainActivity_MembersInjector(
      Provider<ScheduleReminderUseCase> scheduleReminderUseCaseProvider) {
    this.scheduleReminderUseCaseProvider = scheduleReminderUseCaseProvider;
  }

  public static MembersInjector<MainActivity> create(
      Provider<ScheduleReminderUseCase> scheduleReminderUseCaseProvider) {
    return new MainActivity_MembersInjector(scheduleReminderUseCaseProvider);
  }

  @Override
  public void injectMembers(MainActivity instance) {
    injectScheduleReminderUseCase(instance, scheduleReminderUseCaseProvider.get());
  }

  @InjectedFieldSignature("com.rivaldo.timeless.MainActivity.scheduleReminderUseCase")
  public static void injectScheduleReminderUseCase(MainActivity instance,
      ScheduleReminderUseCase scheduleReminderUseCase) {
    instance.scheduleReminderUseCase = scheduleReminderUseCase;
  }
}
