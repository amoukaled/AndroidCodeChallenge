package com.amoukaled.androidcodechallenge.di

import android.content.Context
import androidx.room.Room
import com.amoukaled.androidcodechallenge.api.TodoApi
import com.amoukaled.androidcodechallenge.data.dao.UserDao
import com.amoukaled.androidcodechallenge.data.dataStore.LoginSessionDataStore
import com.amoukaled.androidcodechallenge.data.dataStore.LoginSessionSharedPref
import com.amoukaled.androidcodechallenge.data.dataStore.TimerDataStore
import com.amoukaled.androidcodechallenge.data.dataStore.TimerSharedPref
import com.amoukaled.androidcodechallenge.data.databases.AppDatabase
import com.amoukaled.androidcodechallenge.models.DispatcherProvider
import com.amoukaled.androidcodechallenge.repositories.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * The app module containing DI.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDispatchers() = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

    @Provides
    @Singleton
    fun providesRoomDB(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME).build()

    @Provides
    @Singleton
    fun providesUserDao(database: AppDatabase): UserDao = database.userDao()


    @Singleton
    @Provides
    fun providesLoginSessionDataStore(@ApplicationContext context: Context): LoginSessionDataStore = LoginSessionSharedPref(context)


    @Singleton
    @Provides
    fun providesTimerDataStore(@ApplicationContext context: Context): TimerDataStore = TimerSharedPref(context)

    @Singleton
    @Provides
    fun providesAuthRepository(
        loginSessionDataStore: LoginSessionDataStore,
        userDao: UserDao,
        @ApplicationContext context: Context,
        timerDataStore: TimerDataStore
    ): AuthRepository = AuthRepositoryImpl(loginSessionDataStore, userDao, context, timerDataStore)

    @Singleton
    @Provides
    fun providesTodoApi(): TodoApi {
        return Retrofit.Builder()
            .baseUrl(TodoApi.JSON_PLACE_HOLDER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApi::class.java)
    }

    @Provides
    @Singleton
    fun providesTodoRepository(@ApplicationContext context: Context, todoApi: TodoApi): TodoRepository = TodoRepositoryImpl(context, todoApi)

    @Provides
    @Singleton
    fun providesTimerRepository(timerDataStore: TimerDataStore): TimerRepository = TimerRepositoryImpl(timerDataStore)

}