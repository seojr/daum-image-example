package com.orangecaw.android.simage.data.source;

import com.orangecaw.android.simage.data.Result;
import com.orangecaw.android.simage.data.source.local.LocalSource;
import com.orangecaw.android.simage.data.source.remote.DaumService;
import com.orangecaw.android.simage.util.ArchiveUtil;
import com.orangecaw.android.simage.util.FileDateDescComparator;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@EBean(scope = EBean.Scope.Singleton)
public class Repository {

    private static final String API_BASE_URL = "https://apis.daum.net/";

    DaumService remote;

    @Bean
    LocalSource local;

    @Bean
    ArchiveUtil archiveUtil;

    @AfterInject
    void init() {
        OkHttpClient client = createClient();
        remote = createService(client, DaumService.class);
    }

    private OkHttpClient createClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    private <T> T createService(OkHttpClient client, Class<T> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        return retrofit.create(service);
    }

    public Observable<Result> searchImage(String query) {
        return remote.searchImage(query);
    }

    public Observable<File> download(String url) {
        return remote.download(url)
                .flatMap(response ->
                        Observable.create(subscriber -> {
                            try {
                                File file = new File(archiveUtil.makeNewImagePath());
                                BufferedSink sink = Okio.buffer(Okio.sink(file));
                                sink.writeAll(response.body().source());
                                sink.close();
                                subscriber.onNext(file);
                                subscriber.onComplete();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                subscriber.onError(e);
                            } catch (IOException e) {
                                e.printStackTrace();
                                subscriber.onError(e);
                            }
                        })
                );
    }

    public Observable<List<File>> getStorageFiles() {
        return Observable.just(local.getStorageFiles())
                .flatMap(files -> Observable.fromIterable(files))
                .toSortedList(new FileDateDescComparator())
                .toObservable();
    }

}
