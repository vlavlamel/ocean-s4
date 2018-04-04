package com.ocean.oceans4.teammates;

import com.ocean.oceans4.base.Event;
import com.ocean.oceans4.data.Teammate;

import java.util.Map;

public class ListOfTeammatesEvent extends Event{

	public static class GetTeammates extends ListOfTeammatesEvent{

	}

	public static class AddTeammate extends ListOfTeammatesEvent{
		private Map<String, Teammate> request;

		public AddTeammate(Map<String, Teammate> request) {
			this.request = request;
		}

		public Map<String, Teammate> getRequest() {
			return request;
		}
	}
}
