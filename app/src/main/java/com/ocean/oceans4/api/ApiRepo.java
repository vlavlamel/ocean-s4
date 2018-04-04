package com.ocean.oceans4.api;

import io.reactivex.ObservableSource;
import io.reactivex.subjects.PublishSubject;

public class ApiRepo {

	private static OceanApi oceanApi;

	private static PublishSubject<String> subject;

	public static Api getOceanApi() {
		if (oceanApi == null) oceanApi = new OceanApi();
		return oceanApi.getApi();
	}

	public static PublishSubject<String> getSubject() {
		if (subject == null) subject = PublishSubject.create();
		return subject;
	}
}
