package com.orangecaw.android.simage.data.source.remote;

import com.orangecaw.android.simage.BuildConfig;
import com.orangecaw.android.simage.data.Result;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface DaumService {

    @GET("search/image?output=json&result=20&apikey=" + BuildConfig.API_KEY)
    Observable<Result> searchImage(@Query("q") String search);

    @GET
    Observable<Response<ResponseBody>> download(@Url String url);

}
