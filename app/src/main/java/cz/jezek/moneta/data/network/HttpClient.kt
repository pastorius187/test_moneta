package cz.jezek.moneta.data.network

import cz.jezek.moneta.core.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Definice HTTP klienta pro cteni
 *
 * Zabezpecuje autorizaci, timeouty a poskytuje retrofit
 */
@Singleton
class HttpClient {

    companion object {
        private const val BASE_URL = Constants.BASE_URL

        // Interceptor pro přidání API klíče do každého requestu
        private val authInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Authorization", Constants.API_KEY)
                .build()
            chain.proceed(request)
        }

        // OkHttpClient s interceptor a timeouty
        private val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        // Retrofit instance
        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }
    }
}
