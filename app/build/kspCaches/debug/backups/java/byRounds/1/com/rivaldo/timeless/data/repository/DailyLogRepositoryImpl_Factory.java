package com.rivaldo.timeless.data.repository;

import android.content.Context;
import com.rivaldo.timeless.data.local.dao.DailyLogDao;
import com.rivaldo.timeless.data.local.media.MediaMetadataExtractor;
import com.rivaldo.timeless.data.local.media.MicroThumbnailGenerator;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class DailyLogRepositoryImpl_Factory implements Factory<DailyLogRepositoryImpl> {
  private final Provider<DailyLogDao> daoProvider;

  private final Provider<MicroThumbnailGenerator> thumbnailGeneratorProvider;

  private final Provider<MediaMetadataExtractor> metadataExtractorProvider;

  private final Provider<Context> contextProvider;

  public DailyLogRepositoryImpl_Factory(Provider<DailyLogDao> daoProvider,
      Provider<MicroThumbnailGenerator> thumbnailGeneratorProvider,
      Provider<MediaMetadataExtractor> metadataExtractorProvider,
      Provider<Context> contextProvider) {
    this.daoProvider = daoProvider;
    this.thumbnailGeneratorProvider = thumbnailGeneratorProvider;
    this.metadataExtractorProvider = metadataExtractorProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public DailyLogRepositoryImpl get() {
    return newInstance(daoProvider.get(), thumbnailGeneratorProvider.get(), metadataExtractorProvider.get(), contextProvider.get());
  }

  public static DailyLogRepositoryImpl_Factory create(Provider<DailyLogDao> daoProvider,
      Provider<MicroThumbnailGenerator> thumbnailGeneratorProvider,
      Provider<MediaMetadataExtractor> metadataExtractorProvider,
      Provider<Context> contextProvider) {
    return new DailyLogRepositoryImpl_Factory(daoProvider, thumbnailGeneratorProvider, metadataExtractorProvider, contextProvider);
  }

  public static DailyLogRepositoryImpl newInstance(DailyLogDao dao,
      MicroThumbnailGenerator thumbnailGenerator, MediaMetadataExtractor metadataExtractor,
      Context context) {
    return new DailyLogRepositoryImpl(dao, thumbnailGenerator, metadataExtractor, context);
  }
}
