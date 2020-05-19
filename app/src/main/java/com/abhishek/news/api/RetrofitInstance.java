package com.abhishek.news.api;

import android.content.Context;

import com.abhishek.news.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static Retrofit loggedUserAPIService = null;
    private static Retrofit newUserAPIService = null;
    private static OkHttpClient newUserHttpClient = null;
    private String userID = null;
    private String accessToken = null;

    // for users who are not signed in yet
    public static RestApiService getApiService(Context context) {
        if (newUserAPIService == null) {
            newUserAPIService = new Retrofit
                    .Builder()
                    .client(getHttpClient(context))
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return newUserAPIService.create(RestApiService.class);
    }


    private static OkHttpClient getHttpClient(Context context) {
        if (newUserHttpClient == null) {
            newUserHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(new NetworkConnectionInterceptor(context))
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request();
                            Response response = chain.proceed(request);
                            return response;
                        }
                    })
                    //here we can add Interceptor for dynamical adding headers
                    /*  .addNetworkInterceptor(new Interceptor() {
                          @Override
                          public Response intercept(Chain chain) throws IOException {
                              Request request = chain.request().newBuilder()
                                      .addHeader(ApiConstants.Headers.X_SECREAT_TOKEN, ApiConstants.Headers.SECRET_TOKEN_VALUE)
                                      .build();
                              return chain.proceed(request);
                          }
                      })*/
                    //here we adding Interceptor for full level logging
                    .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();
        }
        return newUserHttpClient;
    }


    public static Retrofit retrofit() {
        return newUserAPIService;
    }
}
