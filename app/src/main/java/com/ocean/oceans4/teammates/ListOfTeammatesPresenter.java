package com.ocean.oceans4.teammates;

import com.ocean.oceans4.api.ApiRepo;
import com.ocean.oceans4.base.BasePresenterImplementation;
import com.ocean.oceans4.base.FragmentState;
import com.ocean.oceans4.data.Teammate;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class ListOfTeammatesPresenter extends BasePresenterImplementation<ListOfTeammatesEvent, ListOfTeammatesUIModel> {

	@Override
	public ObservableTransformer<ListOfTeammatesEvent, ListOfTeammatesUIModel> getPresenterTransformer() {
		return new ObservableTransformer<ListOfTeammatesEvent, ListOfTeammatesUIModel>() {
			@Override
			public ObservableSource<ListOfTeammatesUIModel> apply(Observable<ListOfTeammatesEvent> upstream) {
				return upstream.flatMap(new Function<ListOfTeammatesEvent, ObservableSource<ListOfTeammatesUIModel>>() {
					@Override
					public ObservableSource<ListOfTeammatesUIModel> apply(ListOfTeammatesEvent listOfTeammatesEvent) throws Exception {
						return ApiRepo.getOceanApi()
							.teammates()
							.observeOn(AndroidSchedulers.mainThread())
							.subscribeOn(Schedulers.io())
							.map(new Function<List<Teammate>, ListOfTeammatesUIModel>() {
								@Override
								public ListOfTeammatesUIModel apply(List<Teammate> teammates) throws Exception {
									return new ListOfTeammatesUIModel(FragmentState.SUCCESS).setTeammates(teammates);
								}
							})
							.startWith(new ListOfTeammatesUIModel(FragmentState.IN_PROGRESS))
							.onErrorReturn(new Function<Throwable, ListOfTeammatesUIModel>() {
								@Override
								public ListOfTeammatesUIModel apply(Throwable throwable) throws Exception {
									return new ListOfTeammatesUIModel(throwable);
								}
							});
					}
				});
			}
		};
	}
}
