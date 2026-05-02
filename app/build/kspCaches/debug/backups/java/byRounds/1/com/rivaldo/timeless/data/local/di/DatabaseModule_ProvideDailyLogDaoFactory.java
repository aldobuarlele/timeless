package com.rivaldo.timeless.data.local.di;

import com.rivaldo.timeless.data.local.dao.DailyLogDao;
import com.rivaldo.timeless.data.local.database.TimelessDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DatabaseModule_ProvideDailyLogDaoFactory implements Factory<DailyLogDao> {
  private final Provider<TimelessDatabase> databaseProvider;

  public DatabaseModule_ProvideDailyLogDaoFactory(Provider<TimelessDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public DailyLogDao get() {
    return provideDailyLogDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideDailyLogDaoFactory create(
      Provider<TimelessDatabase> databaseProvider) {
    return new DatabaseModule_ProvideDailyLogDaoFactory(databaseProvider);
  }

  public static DailyLogDao provideDailyLogDao(TimelessDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideDailyLogDao(database));
  }
}
