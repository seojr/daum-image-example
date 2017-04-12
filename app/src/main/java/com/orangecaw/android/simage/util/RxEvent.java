package com.orangecaw.android.simage.util;

import java.io.File;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

public class RxEvent {

    private static final PublishSubject<File> bus = PublishSubject.create();

    public static void sendEvent(File file) {
        bus.onNext(file);
    }

    public static Disposable subscribe(Consumer<File> consumer) {
        return bus.subscribe(consumer);
    }

}
