package com.ocean.oceans4.teamate_details;

import com.ocean.oceans4.base.FragmentState;
import com.ocean.oceans4.base.UIModel;

public class TeammateDetailsUIModel extends UIModel {

	private String data;

	public TeammateDetailsUIModel(FragmentState state) {
		super(state);
	}

	public TeammateDetailsUIModel(Throwable error) {
		super(error);
	}

	public String getData() {
		return data;
	}

	public TeammateDetailsUIModel setData(String data) {
		this.data = data;
		return this;
	}
}
