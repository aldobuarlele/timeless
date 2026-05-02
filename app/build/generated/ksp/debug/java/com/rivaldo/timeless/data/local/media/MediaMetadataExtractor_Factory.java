package com.rivaldo.timeless.data.local.media;

import android.content.Context;
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
public final class MediaMetadataExtractor_Factory implements Factory<MediaMetadataExtractor> {
  private final Provider<Context> contextProvider;

  public MediaMetadataExtractor_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public MediaMetadataExtractor get() {
    return newInstance(contextProvider.get());
  }

  public static MediaMetadataExtractor_Factory create(Provider<Context> contextProvider) {
    return new MediaMetadataExtractor_Factory(contextProvider);
  }

  public static MediaMetadataExtractor newInstance(Context context) {
    return new MediaMetadataExtractor(context);
  }
}
