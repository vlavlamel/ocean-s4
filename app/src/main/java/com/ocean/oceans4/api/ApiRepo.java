package com.ocean.oceans4.api;

public class ApiRepo {

	private static OceanApi oceanApi;

	public static Api getOceanApi() {
		if (oceanApi == null) oceanApi = new OceanApi();
		return oceanApi.getApi();
	}

}
