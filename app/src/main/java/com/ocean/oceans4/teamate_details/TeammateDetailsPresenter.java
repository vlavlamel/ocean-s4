package com.ocean.oceans4.teamate_details;

import com.ocean.oceans4.api.ApiRepo;
import com.ocean.oceans4.base.BasePresenterImplementation;
import com.ocean.oceans4.base.FragmentState;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TeammateDetailsPresenter extends BasePresenterImplementation<TeammateDetailsUIEvent, TeammateDetailsUIModel> {
	@Override
	public ObservableTransformer<TeammateDetailsUIEvent, TeammateDetailsUIModel> getPresenterTransformer() {
		return new ObservableTransformer<TeammateDetailsUIEvent, TeammateDetailsUIModel>() {
			@Override
			public ObservableSource<TeammateDetailsUIModel> apply(Observable<TeammateDetailsUIEvent> upstream) {
				return upstream.publish(new Function<Observable<TeammateDetailsUIEvent>, ObservableSource<TeammateDetailsUIModel>>() {
					@Override
					public ObservableSource<TeammateDetailsUIModel> apply(Observable<TeammateDetailsUIEvent> teammateDetailsUIEventObservable) throws Exception {
						return Observable.merge(teammateDetailsUIEventObservable.ofType(TeammateDetailsUIEvent.ChangeNameEvent.class)
								.compose(changeName()),
							teammateDetailsUIEventObservable.ofType(TeammateDetailsUIEvent.ChangeGroupEvent.class)
								.compose(changeGroup()),
							teammateDetailsUIEventObservable.ofType(TeammateDetailsUIEvent.ChangePostEvent.class)
								.compose(changePost()),
							teammateDetailsUIEventObservable.ofType(TeammateDetailsUIEvent.ChangeAboutEvent.class)
								.compose(changeAbout()));
					}
				});
			}
		};
	}

	public ObservableTransformer<TeammateDetailsUIEvent.ChangeNameEvent, TeammateDetailsUIModel> changeName() {
		return new ObservableTransformer<TeammateDetailsUIEvent.ChangeNameEvent, TeammateDetailsUIModel>() {
			@Override
			public ObservableSource<TeammateDetailsUIModel> apply(Observable<TeammateDetailsUIEvent.ChangeNameEvent> upstream) {
				return upstream.flatMap(new Function<TeammateDetailsUIEvent.ChangeNameEvent, ObservableSource<TeammateDetailsUIModel>>() {
					@Override
					public ObservableSource<TeammateDetailsUIModel> apply(TeammateDetailsUIEvent.ChangeNameEvent changeNameEvent) throws Exception {
						return oceanApi.changeName(changeNameEvent.getId(), changeNameEvent.getData())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribeOn(Schedulers.io())
							.map(new Function<String, TeammateDetailsUIModel>() {
								@Override
								public TeammateDetailsUIModel apply(String s) throws Exception {
									ApiRepo.getSubject().onNext(s);
									return new TeammateDetailsUIModel(FragmentState.SUCCESS).setData(s);
								}
							})
							.startWith(new TeammateDetailsUIModel(FragmentState.IN_PROGRESS))
							.onErrorReturn(new Function<Throwable, TeammateDetailsUIModel>() {
								@Override
								public TeammateDetailsUIModel apply(Throwable throwable) throws Exception {
									return new TeammateDetailsUIModel(throwable);
								}
							});
					}
				});
			}
		};
	}

	public ObservableTransformer<TeammateDetailsUIEvent.ChangeGroupEvent, TeammateDetailsUIModel> changeGroup() {
		return new ObservableTransformer<TeammateDetailsUIEvent.ChangeGroupEvent, TeammateDetailsUIModel>() {
			@Override
			public ObservableSource<TeammateDetailsUIModel> apply(Observable<TeammateDetailsUIEvent.ChangeGroupEvent> upstream) {
				return upstream.flatMap(new Function<TeammateDetailsUIEvent.ChangeGroupEvent, ObservableSource<TeammateDetailsUIModel>>() {
					@Override
					public ObservableSource<TeammateDetailsUIModel> apply(TeammateDetailsUIEvent.ChangeGroupEvent changeGroupEvent) throws Exception {
						return oceanApi.changeGroup(changeGroupEvent.getId(), changeGroupEvent.getData())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribeOn(Schedulers.io())
							.map(new Function<String, TeammateDetailsUIModel>() {
								@Override
								public TeammateDetailsUIModel apply(String s) throws Exception {
									ApiRepo.getSubject().onNext(s);
									return new TeammateDetailsUIModel(FragmentState.SUCCESS).setData(s);
								}
							})
							.startWith(new TeammateDetailsUIModel(FragmentState.IN_PROGRESS))
							.onErrorReturn(new Function<Throwable, TeammateDetailsUIModel>() {
								@Override
								public TeammateDetailsUIModel apply(Throwable throwable) throws Exception {
									return new TeammateDetailsUIModel(throwable);
								}
							});
					}
				});
			}
		};
	}

	public ObservableTransformer<TeammateDetailsUIEvent.ChangePostEvent, TeammateDetailsUIModel> changePost() {
		return new ObservableTransformer<TeammateDetailsUIEvent.ChangePostEvent, TeammateDetailsUIModel>() {
			@Override
			public ObservableSource<TeammateDetailsUIModel> apply(Observable<TeammateDetailsUIEvent.ChangePostEvent> upstream) {
				return upstream.flatMap(new Function<TeammateDetailsUIEvent.ChangePostEvent, ObservableSource<TeammateDetailsUIModel>>() {
					@Override
					public ObservableSource<TeammateDetailsUIModel> apply(TeammateDetailsUIEvent.ChangePostEvent changePostEvent) throws Exception {
						return oceanApi.changePost(changePostEvent.getId(), changePostEvent.getData())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribeOn(Schedulers.io())
							.map(new Function<String, TeammateDetailsUIModel>() {
								@Override
								public TeammateDetailsUIModel apply(String s) throws Exception {
									ApiRepo.getSubject().onNext(s);
									return new TeammateDetailsUIModel(FragmentState.SUCCESS).setData(s);
								}
							})
							.startWith(new TeammateDetailsUIModel(FragmentState.IN_PROGRESS))
							.onErrorReturn(new Function<Throwable, TeammateDetailsUIModel>() {
								@Override
								public TeammateDetailsUIModel apply(Throwable throwable) throws Exception {
									return new TeammateDetailsUIModel(throwable);
								}
							});
					}
				});
			}
		};
	}

	public ObservableTransformer<TeammateDetailsUIEvent.ChangeAboutEvent, TeammateDetailsUIModel> changeAbout() {
		return new ObservableTransformer<TeammateDetailsUIEvent.ChangeAboutEvent, TeammateDetailsUIModel>() {
			@Override
			public ObservableSource<TeammateDetailsUIModel> apply(Observable<TeammateDetailsUIEvent.ChangeAboutEvent> upstream) {
				return upstream.flatMap(new Function<TeammateDetailsUIEvent.ChangeAboutEvent, ObservableSource<TeammateDetailsUIModel>>() {
					@Override
					public ObservableSource<TeammateDetailsUIModel> apply(TeammateDetailsUIEvent.ChangeAboutEvent changeAboutEvent) throws Exception {
						return oceanApi.changeAbout(changeAboutEvent.getId(), changeAboutEvent.getData())
							.observeOn(AndroidSchedulers.mainThread())
							.subscribeOn(Schedulers.io())
							.map(new Function<String, TeammateDetailsUIModel>() {
								@Override
								public TeammateDetailsUIModel apply(String s) throws Exception {
									ApiRepo.getSubject().onNext(s);
									return new TeammateDetailsUIModel(FragmentState.SUCCESS).setData(s);
								}
							})
							.startWith(new TeammateDetailsUIModel(FragmentState.IN_PROGRESS))
							.onErrorReturn(new Function<Throwable, TeammateDetailsUIModel>() {
								@Override
								public TeammateDetailsUIModel apply(Throwable throwable) throws Exception {
									return new TeammateDetailsUIModel(throwable);
								}
							});
					}
				});
			}
		};
	}
}