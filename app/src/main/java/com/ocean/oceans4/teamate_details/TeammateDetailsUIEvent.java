package com.ocean.oceans4.teamate_details;

import com.ocean.oceans4.base.Event;

public class TeammateDetailsUIEvent extends Event {
	private String data;
	private int id;

	public String getData() {
		return data;
	}

	public int getId() {
		return id;
	}

	public TeammateDetailsUIEvent(int id, String data) {
		this.data = data;
		this.id = id;
	}

	public static class ChangeNameEvent extends TeammateDetailsUIEvent {

		public ChangeNameEvent(int id, String data) {
			super(id, data);
		}
	}

	public static class ChangeGroupEvent extends TeammateDetailsUIEvent {

		public ChangeGroupEvent(int id, String data) {
			super(id, data);
		}
	}

	public static class ChangeAboutEvent extends TeammateDetailsUIEvent {

		public ChangeAboutEvent(int id, String data) {
			super(id, data);
		}
	}

	public static class ChangePostEvent extends TeammateDetailsUIEvent {

		public ChangePostEvent(int id, String data) {
			super(id, data);
		}
	}
}
