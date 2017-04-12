package com.orangecaw.android.simage.view.ui.archive;

import com.orangecaw.android.simage.data.source.Repository;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import io.reactivex.disposables.CompositeDisposable;

@EBean
public class ArchivePresenter implements ArchiveContract.Presenter {

    private CompositeDisposable compositeDisposable;

    ArchiveContract.View view;

    @Bean
    Repository repository;

    @AfterInject
    void setUp() {
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void setView(ArchiveContract.View view) {
        this.view = view;
    }

    @Override
    public void unSubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void loadFiles() {
        compositeDisposable.add(
                repository.getStorageFiles()
                        .doOnSubscribe(disposable -> view.showProgress())
                        .subscribe(files -> {
                            view.hideProgress();
                            view.setFiles(files);
                        }, error -> {
                            view.hideProgress();
                            // TODO 에러 알림
                        })
        );
    }

}
