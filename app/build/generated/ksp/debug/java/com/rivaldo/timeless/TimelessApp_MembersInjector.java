package com.rivaldo.timeless;

import androidx.hilt.work.HiltWorkerFactory;
import com.rivaldo.timeless.data.local.notification.NotificationChannelManager;
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
public final class TimelessApp_MembersInjector implements MembersInjector<TimelessApp> {
  private final Provider<NotificationChannelManager> notificationChannelManagerProvider;

  private final Provider<HiltWorkerFactory> hiltWorkerFactoryProvider;

  public TimelessApp_MembersInjector(
      Provider<NotificationChannelManager> notificationChannelManagerProvider,
      Provider<HiltWorkerFactory> hiltWorkerFactoryProvider) {
    this.notificationChannelManagerProvider = notificationChannelManagerProvider;
    this.hiltWorkerFactoryProvider = hiltWorkerFactoryProvider;
  }

  public static MembersInjector<TimelessApp> create(
      Provider<NotificationChannelManager> notificationChannelManagerProvider,
      Provider<HiltWorkerFactory> hiltWorkerFactoryProvider) {
    return new TimelessApp_MembersInjector(notificationChannelManagerProvider, hiltWorkerFactoryProvider);
  }

  @Override
  public void injectMembers(TimelessApp instance) {
    injectNotificationChannelManager(instance, notificationChannelManagerProvider.get());
    injectHiltWorkerFactory(instance, hiltWorkerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.rivaldo.timeless.TimelessApp.notificationChannelManager")
  public static void injectNotificationChannelManager(TimelessApp instance,
      NotificationChannelManager notificationChannelManager) {
    instance.notificationChannelManager = notificationChannelManager;
  }

  @InjectedFieldSignature("com.rivaldo.timeless.TimelessApp.hiltWorkerFactory")
  public static void injectHiltWorkerFactory(TimelessApp instance,
      HiltWorkerFactory hiltWorkerFactory) {
    instance.hiltWorkerFactory = hiltWorkerFactory;
  }
}
