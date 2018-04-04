package com.ocean.oceans4.teammates;

import com.ocean.oceans4.base.FragmentState;
import com.ocean.oceans4.base.UIModel;
import com.ocean.oceans4.data.Teammate;

import java.util.List;
import java.util.Map;

public class ListOfTeammatesUIModel extends UIModel {

	private Map<String, Teammate> teammates;

	private Teammate teammate;

	public ListOfTeammatesUIModel(FragmentState state) {
		super(state);
	}

	public ListOfTeammatesUIModel(Throwable error) {
		super(error);
	}

	public Map<String, Teammate> getTeammates() {
		return teammates;
	}

	public ListOfTeammatesUIModel setTeammates(Map<String, Teammate> teammates) {
		this.teammates = teammates;
		return this;
	}

	public Teammate getTeammate() {
		return teammate;
	}

	public ListOfTeammatesUIModel setTeammate(Teammate teammate) {
		this.teammate = teammate;
		return this;
	}
}
