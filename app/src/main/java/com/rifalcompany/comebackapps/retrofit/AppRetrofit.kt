package com.rifalcompany.comebackapps.retrofit

import com.rifalcompany.comebackapps.features.home.data.service.HomeService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object AppRetrofit {
        private const val BASE_URL = "https://api.npoint.io/99c279bb173a6e28359c/"

        private val retrofitClient: Retrofit.Builder by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client: OkHttpClient = OkHttpClient.Builder()
                .readTimeout(300, TimeUnit.SECONDS)
                .connectTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val original: Request = chain.request()
                    val request: Request =
                        original
                            .newBuilder()
//                            .header(
//                                "Authorization", "Bearer " +
//                                        Pref.loadString(
//                                            PrefConst.LOGIN_STATUS,
//                                            ""
//                                        )
//                            )
                            .method(original.method, original.body)
                            .build()
                    chain.proceed(request)
                })
                .addInterceptor(logging)
                .build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
        }

    val homeService: HomeService by lazy {
        retrofitClient
            .build()
            .create(HomeService::class.java)
    }
}