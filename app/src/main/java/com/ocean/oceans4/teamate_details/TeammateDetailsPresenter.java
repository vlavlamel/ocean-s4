package com.ocean.oceans4.teamate_details;

import com.ocean.oceans4.base.BasePresenterImplementation;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

public class TeammateDetailsPresenter extends BasePresenterImplementation<TeammateDetailsUIEvent, TeammateDetailsUIModel> {
	@Override
	public ObservableTransformer<TeammateDetailsUIEvent, TeammateDetailsUIModel> getPresenterTransformer() {
		return new ObservableTransformer<TeammateDetailsUIEvent, TeammateDetailsUIModel>() {
			@Override
			public ObservableSource<TeammateDetailsUIModel> apply(Observable<TeammateDetailsUIEvent> upstream) {
				return upstream.flatMap(new Function<TeammateDetailsUIEvent, ObservableSource<TeammateDetailsUIModel>>() {
					@Override
					public ObservableSource<TeammateDetailsUIModel> apply(TeammateDetailsUIEvent teammateDetailsUIEvent) throws Exception {
						return Observable.empty();
					}
				});
			}
		};
	}
}
