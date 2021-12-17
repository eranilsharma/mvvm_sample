package com.mvvm.mvvmandroid.network


import API_LOGIN
import BASE_URL
import HEADER_AUTHORIZATION
import android.content.Context
import com.mvvm.mvvmandroid.BuildConfig
import com.mvvm.mvvmandroid.core.BaseApp
import com.mvvm.mvvmandroid.model.response.BaseRes
import com.mvvm.mvvmandroid.model.response.Data
import com.mvvm.mvvmandroid.utils.accessToken
import com.google.gson.Gson
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit


@JvmSuppressWildcards
interface ApiService {


    @POST(API_LOGIN)
    suspend fun login(@Body body: Map<String, String>): Response<BaseRes>


    //REST CLIENT IMPLEMENTATION in COMPANION OBJECT
    companion object Factory {

        val api: ApiService
        val picasso: Picasso
        val gson = Gson()


        init {
            api = apis(
                retrofit(
                    okHttpClient(
                        httpLoggingInterceptor(),
                        cache(file(BaseApp.INSTANCE))
                    ),
                    gsonConverterFactory(gson)
                )
            )
            picasso = picasso(
                BaseApp.INSTANCE, okHttp3Downloader(
                    okHttpClient(
                        httpLoggingInterceptor(), cache(
                            file(
                                BaseApp.INSTANCE
                            )
                        )
                    )
                )
            )
        }


        private fun apis(retrofit: Retrofit) = retrofit.create(ApiService::class.java)


        private fun retrofit(
            okHttpClient: OkHttpClient,
            gsonConverterFactory: GsonConverterFactory
        ) = Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            addConverterFactory(gsonConverterFactory)
            client(okHttpClient)
        }.build()

        private fun gsonConverterFactory(gson: Gson) = GsonConverterFactory.create(gson)


        private fun picasso(context: Context, okHttp3Downloader: OkHttp3Downloader): Picasso {
            return Picasso.Builder(context)
                .downloader(okHttp3Downloader)
                .loggingEnabled(true)
                .build()
        }

        private fun okHttp3Downloader(okHttpClient: OkHttpClient) = OkHttp3Downloader(okHttpClient)

        private fun okHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
            cache: Cache
        ) = OkHttpClient.Builder().apply {

            if (BuildConfig.DEBUG) addInterceptor(httpLoggingInterceptor)

            addInterceptor(ChuckInterceptor(BaseApp.INSTANCE))

            addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                    var request = chain.request()
                    val url = request.url
                        .newBuilder()
                        .build()
                    request = request.newBuilder()
                        .addHeader(
                            HEADER_AUTHORIZATION,
                            "Bearer ${accessToken ?: ""}"
                        )
                        .url(url).build()
                    val response = chain.proceed(request)
                    return response

                }
            }).cache(cache)
                .connectTimeout(5, TimeUnit.MINUTES)
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
        }.build()


        private fun file(context: Context) = File(context.cacheDir, "HttpCache").apply {
            if (exists().not()) this.mkdirs()
        }


        private fun cache(file: File) = Cache(file, 10 * 1000 * 1000)


        private fun httpLoggingInterceptor() =
            HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }


        fun multipartFromFile(
            path: String,
            mimeType: String,
            paramName: String
        ): MultipartBody.Part {
            val file = File(path)
            return MultipartBody.Part.createFormData(
                paramName, file.name, file
                    .asRequestBody(mimeType.toMediaTypeOrNull())
            )
        }


        /**
         * Extension fxn in RETROFIT's RESPONSE class
         */
        fun <T> Response<T>.toData() = if (isSuccessful) {
            Data.SUCCESS(body()!!, "Success")
        } else {
            val error = errorBody()?.string()
            val stringBuilder = StringBuilder("Error ${code()}: ")
            error?.let {
                try {
                    stringBuilder.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            Data.ERROR(Throwable(stringBuilder.toString()))
        }


        /**
         * Fxn to handle api's Response with SEALED CLASS
         */
        fun <T> processApi(
            response: Response<T>,
            block: (res: T?, err: String?) -> Unit
        ) {
            try {
                response.toData().apply {
                    when (this) {
                        is Data.SUCCESS -> {
                            block(data, null)
                        }
                        is Data.ERROR -> {
                            block(null, throwable.localizedMessage)
                        }
                    }
                }
            } catch (t: Throwable) {
                Timber.e(t)
                block(null, t.localizedMessage)
            }
        }


    }
}