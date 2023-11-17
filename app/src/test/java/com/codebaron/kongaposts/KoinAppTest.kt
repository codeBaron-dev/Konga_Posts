package com.codebaron.kongaposts

import android.content.Context
import com.codebaron.kongaposts.koin.KoinApp
import com.codebaron.kongaposts.remote.Endpoints
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit

@RunWith(MockitoJUnitRunner::class)
class KoinAppTest {

    private lateinit var koinApp: KoinApp

    @Before
    fun setup() {
        koinApp = KoinApp()
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun testOnCreate() {
        val context: Context = mockk(relaxed = true)

        // Mock Retrofit and OkHttpClient
        val retrofit = mockk<Retrofit>()
        `when`(retrofit.create(Endpoints::class.java)).thenReturn(mockk())
        val networkModule = module {
            single { retrofit }
        }

        // Start Koin with mocked modules and configuration.
        startKoin {
            androidLogger()
            androidContext(context)
            modules(networkModule)
        }

        koinApp.onCreate()

    }

    @Test
    fun testAllModules() {
        // Create a mock context and test the allModules() function
        val context: Context = mockk(relaxed = true)

        // Mock Retrofit and OkHttpClient
        val retrofit = mockk<Retrofit>()
        `when`(retrofit.create(Endpoints::class.java)).thenReturn(mockk())
        val networkModule = module {
            single { retrofit }
        }

        // Start Koin with mocked modules and configuration.
        startKoin {
            androidLogger()
            androidContext(context)
            modules(networkModule)
        }

        val modules = koinApp.allModules()
    }
}