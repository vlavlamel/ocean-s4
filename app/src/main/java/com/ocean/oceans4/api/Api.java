package com.ocean.oceans4.api;

import com.ocean.oceans4.data.Teammate;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

	@GET("teammate.json")
	Observable<Map<String, Teammate>> teammates();

	@PUT("teammate/{id}/name.json")
	Observable<String> changeName(@Path("id") int id, @Body String name);

	@PUT("teammate/{id}/group.json")
	Observable<String> changeGroup(@Path("id") int id, @Body String group);

	@PUT("teammate/{id}/post.json")
	Observable<String> changePost(@Path("id") int id, @Body String post);

	@PUT("teammate/{id}/about.json")
	Observable<String> changeAbout(@Path("id") int id, @Body String about);

	@PUT("teammate/{id}/rating.json")
	Observable<Float> changeRating(@Path("id") int id, @Body Float rating);

	@PATCH("teammate.json")
	Observable<Map<String, Teammate>> addTeammate(@Body Map<String, Teammate> addTeammate);
}
