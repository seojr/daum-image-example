package com.orangecaw.android.simage.view.ui.search;

import android.content.Context;
import android.support.v7.widget.SearchView;

import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.orangecaw.android.simage.R;
import com.orangecaw.android.simage.data.Image;
import com.orangecaw.android.simage.data.source.Repository;
import com.orangecaw.android.simage.util.RxEvent;
import com.orangecaw.android.simage.util.MediaUtil;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

@EBean
public class SearchPresenter implements SearchContract.Presenter {

    private CompositeDisposable compositeDisposable;

    private Image waitImage;

    SearchContract.View view;

    @Bean
    Repository repository;

    @RootContext
    Context context;

    @AfterInject
    void setUp() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void setView(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void watchSearchView(SearchView searchView) {
        compositeDisposable.add(
                RxSearchView.queryTextChanges(searchView)
                .subscribeOn(AndroidSchedulers.mainThread())
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(charSequence -> {
                    if(charSequence.length() == 0) {
                        view.hideProgress();
                        return Observable.empty();
                    } else {
                        view.showProgress();
                        return Observable.just(charSequence);
                    }
                })
                .observeOn(Schedulers.io())
                .switchMap(query -> repository.searchImage(query.toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> {
                            view.hideProgress();
                            view.setImages(result==null ? new ArrayList<>() : result.getChannel().getItem());
                        },
                        error -> {
                            view.hideProgress();
                            view.showToast(R.string.fail_search_image);
                        }
                )
        );
    }

    @Override
    public void waitDownload(Image image) {
        waitImage = image;
    }

    @Override
    public void download() {
        if(waitImage != null) {
            download(waitImage);
        }
    }

    @Override
    public void download(Image image) {
        compositeDisposable.add(
                repository.download(image.getThumbnail())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(file -> {
                            MediaUtil.scanImage(context, file.getAbsolutePath());
                            RxEvent.sendEvent(file);
                            view.hideProgress();
                            view.showToast(R.string.complete_download);
                        }, error -> {
                            view.hideProgress();
                            view.showToast(R.string.fail_search_image);
                        })
        );
    }

}
