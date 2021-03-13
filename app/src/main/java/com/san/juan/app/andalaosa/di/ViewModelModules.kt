package com.san.juan.app.andalaosa.di

import android.content.Context
import com.san.juan.app.andalaosa.data.DataRepository
import com.san.juan.app.andalaosa.data.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * @author Dario Carrizo on 13/3/2021
 **/
@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModules {

    @Provides
    @ViewModelScoped
    fun provideLoginRepository(@ApplicationContext appContext: Context): LoginRepository = LoginRepository(appContext)

    @Provides
    @ViewModelScoped
    fun provideDataRepository(@ApplicationContext appContext: Context): DataRepository = DataRepository(appContext)
}