package com.ocean.oceans4.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.ocean.oceans4.R;

import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public abstract class BaseFragment<E extends Event, M extends UIModel> extends Fragment {

	private BasePresenter<E, M> presenter;
	private CompositeDisposable garbage = new CompositeDisposable();

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		garbage.add(presenter.getObservable()
			.subscribe(new Consumer<M>() {
				@Override
				public void accept(M m) throws Exception {
					setCurrentState(m);
				}
			}));
	}

	public void sendEvent(E event) {
		presenter.getEventEmitter()
			.onNext(event);
	}

	public String onError(Throwable throwable) {
		if (isNetworkException(throwable)) {
			return getString(R.string.errorNetwork);
		} else {
			return getString(R.string.errorServerUnavailable);
		}
	}

	@Override
	public void onDestroyView() {
		garbage.clear();
		super.onDestroyView();
	}

	public abstract void setCurrentState(M model);

	public static boolean isNetworkException(Throwable throwable) {
		return throwable instanceof ConnectException || throwable instanceof UnknownHostException
			|| throwable instanceof SocketTimeoutException
			|| throwable instanceof SocketException;
	}

	/*
		Requires method to connect your presenter to fragment. Too bad implementation, has to be refactored. Alternative is to use dagger, but do not have enough time.
	*/
	public void setPresenter(BasePresenter<E, M> presenter) {
		this.presenter = presenter;
	}
}
