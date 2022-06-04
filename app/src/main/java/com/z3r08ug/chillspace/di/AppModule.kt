package com.z3r08ug.chillspace.di

import com.z3r0_8ug.ui_common.framework.util.Dispatchers
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class AppModule {

  @Provides
  @Reusable
  fun provideDispatchers(): Dispatchers {
    return Dispatchers()
  }
}