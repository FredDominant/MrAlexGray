package com.freddominant.myapplication.di

import com.freddominant.myapplication.data.entities.Repository
import com.freddominant.myapplication.data.entities.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class MrAlexGrayModule {

    @Binds
    @ViewModelScoped
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}