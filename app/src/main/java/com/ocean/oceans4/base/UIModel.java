package com.ocean.oceans4.base;

public class UIModel {

	private FragmentState state;
	private Throwable error;

	public UIModel(FragmentState state) {
		this.state = state;
	}

	public UIModel(Throwable error) {
		this.error = error;
		state = FragmentState.ERROR;
	}

	public FragmentState getState() {
		return state;
	}

	public Throwable getError() {
		return error;
	}
}
