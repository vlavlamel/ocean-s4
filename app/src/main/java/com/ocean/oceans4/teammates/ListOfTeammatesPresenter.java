package com.ocean.oceans4.teammates;

import com.ocean.oceans4.api.ApiRepo;
import com.ocean.oceans4.base.BasePresenterImplementation;
import com.ocean.oceans4.base.FragmentState;
import com.ocean.oceans4.data.Teammate;

import java.util.ArrayList;
import java.util.Map;

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
				return upstream.publish(new Function<Observable<ListOfTeammatesEvent>, ObservableSource<ListOfTeammatesUIModel>>() {
					@Override
					public ObservableSource<ListOfTeammatesUIModel> apply(Observable<ListOfTeammatesEvent> listOfTeammatesEventObservable) throws Exception {
						return Observable.merge(
							listOfTeammatesEventObservable.ofType(ListOfTeammatesEvent.GetTeammates.class)
								.compose(getTeammates()),
							listOfTeammatesEventObservable.ofType(ListOfTeammatesEvent.AddTeammate.class)
								.compose(addTeammate()));
					}
				});
			}
		};
	}

	public ObservableTransformer<ListOfTeammatesEvent.GetTeammates, ListOfTeammatesUIModel> getTeammates() {
		return new ObservableTransformer<ListOfTeammatesEvent.GetTeammates, ListOfTeammatesUIModel>() {
			@Override
			public ObservableSource<ListOfTeammatesUIModel> apply(Observable<ListOfTeammatesEvent.GetTeammates> upstream) {
				return upstream.flatMap(new Function<ListOfTeammatesEvent.GetTeammates, ObservableSource<ListOfTeammatesUIModel>>() {
					@Override
					public ObservableSource<ListOfTeammatesUIModel> apply(ListOfTeammatesEvent.GetTeammates getTeammates) throws Exception {
						return oceanApi
							.teammates()
							.observeOn(AndroidSchedulers.mainThread())
							.subscribeOn(Schedulers.io())
							.map(new Function<Map<String, Teammate>, ListOfTeammatesUIModel>() {
								@Override
								public ListOfTeammatesUIModel apply(Map<String, Teammate> teammates) throws Exception {
									return new ListOfTeammatesUIModel(FragmentState.SUCCESS).setTeammates(teammates);
								}
							})
							.startWith(new ListOfTeammatesUIModel(FragmentState.IN_PROGRESS))
							.onErrorReturn(new Function<Throwable, ListOfTeammatesUIModel>() {
								@Override
								public ListOfTeammatesUIModel apply(Throwable throwable) throws Exception {
									return new ListOfTeammatesUIModel(throwable);
								}
							})
							.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
								@Override
								public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
									objectObservable.subscribe();
									return ApiRepo.getSubject();
								}
							});
					}
				});
			}
		};
	}

	public ObservableTransformer<ListOfTeammatesEvent.AddTeammate, ListOfTeammatesUIModel> addTeammate() {
		return new ObservableTransformer<ListOfTeammatesEvent.AddTeammate, ListOfTeammatesUIModel>() {
			@Override
			public ObservableSource<ListOfTeammatesUIModel> apply(Observable<ListOfTeammatesEvent.AddTeammate> upstream) {
				return upstream.flatMap(new Function<ListOfTeammatesEvent.AddTeammate, ObservableSource<ListOfTeammatesUIModel>>() {
					@Override
					public ObservableSource<ListOfTeammatesUIModel> apply(ListOfTeammatesEvent.AddTeammate addTeammate) throws Exception {
						return oceanApi
							.addTeammate(addTeammate.getRequest())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribeOn(Schedulers.io())
							.map(new Function<Map<String, Teammate>, ListOfTeammatesUIModel>() {
								@Override
								public ListOfTeammatesUIModel apply(Map<String, Teammate> addTeammateRequest) throws Exception {
									Teammate teammate = new ArrayList<Teammate>(addTeammateRequest.values()).get(0);
									return new ListOfTeammatesUIModel(FragmentState.SUCCESS).setTeammate(teammate);
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