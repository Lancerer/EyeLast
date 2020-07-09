package com.lancer.eyelast.network


import android.os.Build
import com.lancer.eyelast.extension.screenPixel
import com.lancer.eyelast.utils.AppUtils
import com.lancer.eyelast.utils.GlobalUtil
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * @author lancer
 * @des
 * @Date 2020/7/3 8:40
 */
class HttpControl {

    private constructor(baseUrl: String, okHttpClient: OkHttpClient) {
        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    companion object {

        //默认读取超时时间
        const val DEFAULT_READ_TIME_OUT_SECOND = 60

        //默认连接超时时间
        const val DEFAULT_CONNECT_TIME_OUT_SECOND = 30

        const val BASE_URL = "http://baobab.kaiyanapp.com/"

        fun getInstance(baseUrl: String): HttpControl {
            return HttpControl(baseUrl, getOkHttpClient())
        }

        fun getInstance(baseUrl: String, okHttpClient: OkHttpClient): HttpControl {
            return HttpControl(baseUrl, okHttpClient)
        }

        /**
         * 获取一个 OkHttpClient 对象
         *
         * @return OkHttpClient
         */
        private fun getOkHttpClient(): OkHttpClient {
            val okHttpBuilder = OkHttpClient.Builder()

            //设置网络连接超时时间及数据读取超时时间
            okHttpBuilder.readTimeout(DEFAULT_READ_TIME_OUT_SECOND.toLong(), TimeUnit.SECONDS)
            okHttpBuilder.connectTimeout(DEFAULT_CONNECT_TIME_OUT_SECOND.toLong(), TimeUnit.SECONDS)
            // 设置失败重连
            okHttpBuilder.retryOnConnectionFailure(false)
            // 设置 Log
            val logging = HttpLoggingInterceptor()
            // 设置 Log 级别
            logging.level = HttpLoggingInterceptor.Level.HEADERS
            okHttpBuilder.addInterceptor(logging)
            //公共请求头
            okHttpBuilder.addInterceptor(HeaderInterceptor())
            //query
            okHttpBuilder.addInterceptor(QueryParameterInterceptor())
            return okHttpBuilder.build()
        }

        /**
         * 获取一个 OkHttpClient 对象
         * @param interceptor 添加拦截器
         * @return OkHttpClient
         */
        fun getOkHttpClient(interceptor: Interceptor?): OkHttpClient? {
            return getOkHttpClient().newBuilder().addInterceptor(interceptor).build()
        }
    }

    private var retrofit: Retrofit? = null


    fun <T> create(serviceClass: Class<T>): T = retrofit!!.create(serviceClass)

    /**
     * 设置公共参数
     */
    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val request = original.newBuilder().apply {
                header("model", "Android")
                header("If-Modified-Since", "${Date()}")
                header("User-Agent", System.getProperty("http.agent") ?: "unknown")
            }.build()
            return chain.proceed(request)
        }
    }

    class QueryParameterInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            val originalHttpUrl = original.url()
            val url = originalHttpUrl.newBuilder().apply {
                addQueryParameter("vc", GlobalUtil.eyepetizerVersionCode.toString())
                addQueryParameter("vn", GlobalUtil.eyepetizerVersionName)
                addQueryParameter("size", screenPixel())
                addQueryParameter("deviceModel", GlobalUtil.deviceModel)
                addQueryParameter("first_channel", GlobalUtil.deviceBrand)
                addQueryParameter("last_channel", GlobalUtil.deviceBrand)
                addQueryParameter("system_version_code", "${Build.VERSION.SDK_INT}")
            }.build()
            val request =
                original.newBuilder().url(url).method(original.method(), original.body()).build()
            return chain.proceed(request)
        }

    }

}