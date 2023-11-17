package com.codebaron.kongaposts.koin

import android.app.Application
import com.codebaron.kongaposts.internet.handler.InternetConfigViewModel
import com.codebaron.kongaposts.mvvm.UserPostsRepository
import com.codebaron.kongaposts.mvvm.UserPostsViewModel
import com.codebaron.kongaposts.remote.Endpoints
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author Anyanwu Nicholas(codeBaron)
 * @since 16-11-2023
 */

/**
 * Custom Application class for initializing Koin dependency injection.
 */
class KoinApp : Application() {

    /**
     * Called when the application is starting. Responsible for initializing Koin.
     */
    override fun onCreate() {
        super.onCreate()
        // Start Koin with specified modules and configuration.
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@KoinApp)
            modules(allModules())
        }
    }

    /**
     * Provides a list of Koin modules used for dependency injection.
     *
     * @return List of Koin modules.
     */
    fun allModules(): List<Module> {
        // Network module providing Retrofit instance for API calls.
        val networkModule = module {
            single {
                val okHttpClient = OkHttpClient.Builder().apply {
                    readTimeout(30, TimeUnit.SECONDS)
                    connectTimeout(30, TimeUnit.SECONDS)
                }.build()

                // App module providing repositories and view models.
                Retrofit.Builder()
                    .baseUrl("https://engineering.league.dev/challenge/api/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(Endpoints::class.java)
            }
        }

        val appModule = module {
            single { UserPostsRepository(get()) }
            viewModel { UserPostsViewModel(get()) }
            viewModel { InternetConfigViewModel() }
        }

        return listOf(networkModule, appModule)
    }
}