package com.ocean.oceans4.base;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public abstract class BasePresenterImplementation<E extends Event, M extends UIModel> implements BasePresenter<E, M> {

	private E viewEvent;
	private M viewModel;

	private PublishSubject<E> viewSubject = PublishSubject.create();
	private Observable<M> viewObservable = viewSubject.share()
		.compose(getPresenterTransformer());

	@Override
	public abstract ObservableTransformer<E, M> getPresenterTransformer();

	@Override
	public PublishSubject<E> getEventEmitter() {
		return viewSubject;
	}

	@Override
	public Observable<M> getObservable() {
		return viewObservable;
	}
}
