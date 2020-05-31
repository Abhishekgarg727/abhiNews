package com.abhishek.news.api;

import android.content.Context;
import android.os.NetworkOnMainThreadException;

import com.abhishek.news.MainApplication;
import com.abhishek.news.R;
import com.abhishek.news.utils.ClassUtility;
import com.abhishek.news.utils.LoggerUtils;
import com.abhishek.news.utils.RetrofitUtils;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.CancellationException;

import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

public class ApiHandler<T> {
    private Context context;
    private Handle<T> handle;
    private Call<T> call;
    private StartCallback startCallback;
    private SuccessCallback<T> successCallback;
    private FailCallback failCallback;


    public ApiHandler() {
        this.context = MainApplication.getAppContext();
        this.startCallback = () -> {
        };
    }

    public ApiHandler doApiCall(Call<T> call) {
        this.call = call;
        return this;
    }

    public ApiHandler onStart(StartCallback callback) {
        this.startCallback = callback;
        return this;
    }

    public ApiHandler addHandler(Handle<T> handle) {
        this.handle = handle;
        return this;
    }


    public ApiHandler build() {
        if (handle == null && successCallback != null || failCallback != null) {
            handle = new Handle<T>() {
                @Override
                public void onSuccess(T object) {
                    if (successCallback != null) {
                        successCallback.onSuccess(object);
                    }
                }

                @Override
                public void onFail(Error object) {
                    if (failCallback != null) {
                        failCallback.onFail(object);
                    }
                }
            };
        }
        startCallback.onStart();
        call.enqueue(retrofitCallback(handle));
        return this;
    }

    // success
    private void success(Handle<T> handle, Response<T> response) {

        switch (response.code()) {
            case 200:
                handle.onSuccess(response.body());
                break;
            case 426:
                ClassUtility.shortToast("Developer accounts are limited to a max of 100 results");
                handle.onFail(new Error());
                break;
            default:
                Error parseError = RetrofitUtils.parseError(response, new Error());

                if (parseError == null || parseError.getMessage() == null) {
                    parseError = new Error();
                    parseError.setMessage("Something went wrong at our side, " + response.code());
                    parseError.setStatus(response.code());
                    parseError.setSuccess(false);
                }

                final Error error = parseError;
                ClassUtility.shortToast("Oops!" + error.getMessage());
                handle.onFail(error);
        }
    }

    // failure
    private void failure(Handle handle, Throwable t) {

        String message = context.getResources().getString(R.string.unable_to_perform_action);
        String log;

        if (t instanceof NoInternetConnectionException) {
            message = t.getMessage();
            log = message;
        } else if (t instanceof JSONException) {
            log = "JSON Exception";
        } else if (t instanceof CancellationException) {
            log = "Cancellation Exception";
        } else if (t instanceof HttpException) {
            log = "Http Exception";
        } else if (t instanceof NetworkOnMainThreadException) {
            log = "Network on Main thread Exception";
        } else if (t instanceof RuntimeException) {
            log = "Runtime Exception";
        } else if (t instanceof IOException) {
            log = "IO Exception";
        } else {
            log = "default case Exception";
        }

        log = t != null && t.getMessage() != null ? log + "  --> message : " + t.getMessage() : log;
        LoggerUtils.error("API_FAILURE", log);

        Error error = new Error();
        error.setMessage(message);
        error.setStatus(0);
        error.setSuccess(false);

        //  CustomDialog.singleBtnDialog(context, "Info", error.getMessage(), () -> handle.onFail(error));
    }

    // retrofit call back
    private retrofit2.Callback<T> retrofitCallback(Handle<T> handle) {
        return new retrofit2.Callback<T>() {
            @Override
            public void onResponse(@NotNull Call<T> call, @NotNull Response<T> response) {
                success(handle, response);
            }

            @Override
            public void onFailure(@NotNull Call<T> call, @NotNull Throwable t) {
                failure(handle, t);
            }
        };
    }

    public interface StartCallback {
        void onStart();
    }

    public interface SuccessCallback<T> {
        void onSuccess(T object);
    }

    public interface FailCallback {
        void onFail(Error object);
    }

    public interface Handle<T> {

        void onSuccess(T object);

        void onFail(Error object);
    }

    // Error Object
    public static class Error {
        @SerializedName("status")
        private Integer status;
        @SerializedName("success")
        private Boolean success;
        @SerializedName("message")
        private String message;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

    }

}
