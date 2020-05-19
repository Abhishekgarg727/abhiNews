package com.abhishek.news.utils;

import com.abhishek.news.api.RetrofitInstance;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class RetrofitUtils {

    public static <T> T parseError(Response<?> response, T wrapper) {
        Converter<ResponseBody, T> converter = RetrofitInstance.retrofit()
                .responseBodyConverter(wrapper.getClass(), new Annotation[0]);

        T error;

        try {
            error = converter.convert(response.errorBody());
        } catch (Exception e) {
            return wrapper;
        }

        return error;
    }


}
