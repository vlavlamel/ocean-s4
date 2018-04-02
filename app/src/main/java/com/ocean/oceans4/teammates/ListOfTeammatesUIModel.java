package com.ocean.oceans4.teammates;

import com.ocean.oceans4.base.FragmentState;
import com.ocean.oceans4.base.UIModel;
import com.ocean.oceans4.data.Teammate;

import java.util.List;

public class ListOfTeammatesUIModel extends UIModel {

	private List<Teammate> teammates;

	public ListOfTeammatesUIModel(FragmentState state) {
		super(state);
	}

	public ListOfTeammatesUIModel(Throwable error) {
		super(error);
	}

	public List<Teammate> getTeammates() {
		return teammates;
	}

	public ListOfTeammatesUIModel setTeammates(List<Teammate> teammates) {
		this.teammates = teammates;
		return this;
	}
}
