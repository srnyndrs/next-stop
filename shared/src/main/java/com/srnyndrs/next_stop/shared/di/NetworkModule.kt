package com.srnyndrs.next_stop.shared.di

import com.srnyndrs.next_stop.shared.data.remote.TransportService
import com.srnyndrs.next_stop.shared.data.remote.TransportServiceImpl
import com.srnyndrs.next_stop.shared.data.repository.TransportRepositoryImpl
import com.srnyndrs.next_stop.shared.domain.repository.TransportRepository
import com.srnyndrs.next_stop.shared.data.remote.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    @OptIn(ExperimentalSerializationApi::class)
    fun provideHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        encodeDefaults = true
                        explicitNulls = false
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging) {
                this.level = LogLevel.BODY
                this.logger = Logger.Companion.SIMPLE
            }
            defaultRequest {
                url(Constants.BASE_URL)
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }
    }

    @Singleton
    @Provides
    fun provideTransportService(httpClient: HttpClient): TransportService {
        return TransportServiceImpl(httpClient)
    }

    @Singleton
    @Provides
    fun provideTransportRepository(transportService: TransportService): TransportRepository {
        return TransportRepositoryImpl(transportService)
    }
}