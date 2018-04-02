package com.ocean.oceans4.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class OceanApi {

	private Retrofit retrofit;
	public static final String BASE_URL = "https://ocean-s4.firebaseio.com/";

	public OceanApi() {
		retrofit = new Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(JacksonConverterFactory.create())
			.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
			.client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
				.build())
			.build();
	}

	public Api getApi() {
		return retrofit.create(Api.class);
	}
}
