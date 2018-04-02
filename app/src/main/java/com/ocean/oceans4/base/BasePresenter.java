package com.ocean.oceans4.base;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.subjects.PublishSubject;

public interface BasePresenter<E extends Event, M extends UIModel> {
	PublishSubject<E> getEventEmitter();
	ObservableTransformer<E, M> getPresenterTransformer();
	Observable<M> getObservable();
}
