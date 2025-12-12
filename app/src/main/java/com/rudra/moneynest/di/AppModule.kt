package com.rudra.moneynest.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.rudra.moneynest.data.Repository
import com.rudra.moneynest.data.RepositoryImpl
import com.rudra.moneynest.data.UserPreferencesRepository
import com.rudra.moneynest.data.local.LocalDataSource
import com.rudra.moneynest.data.local.LocalDataSourceImpl
import com.rudra.moneynest.data.remote.RemoteDataSource
import com.rudra.moneynest.data.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun bindRepository(repositoryImpl: RepositoryImpl): Repository

    @Binds
    @Singleton
    abstract fun bindLocalDataSource(localDataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    companion object {

        @Provides
        @Singleton
        fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> {
            return appContext.dataStore
        }

        @Provides
        @Singleton
        fun provideUserPreferencesRepository(dataStore: DataStore<Preferences>): UserPreferencesRepository {
            return UserPreferencesRepository(dataStore)
        }
    }
}
