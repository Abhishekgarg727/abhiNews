package com.abhishek.news.api;

import com.abhishek.news.MainApplication;
import com.abhishek.news.utils.LoggerUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkConnectionInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        LoggerUtils.debug("networkInter", "is connected : " + MainApplication.isConnectedToNetwork());
        if (!MainApplication.isConnectedToNetwork()) {
            throw new NoInternetConnectionException();
            // Throwing our custom exception 'NoConnectivityException'
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

}