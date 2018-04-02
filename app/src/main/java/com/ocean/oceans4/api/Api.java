package com.ocean.oceans4.api;

import com.ocean.oceans4.data.Teammate;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface Api {

	@GET("teammate.json")
	Observable<List<Teammate>> teammates();
}
