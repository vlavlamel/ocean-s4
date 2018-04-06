package com.ocean.oceans4.teamate_details;

import com.ocean.oceans4.base.Event;

import io.reactivex.Flowable;

public class TeammateDetailsUIEvent extends Event {
	private String data;
	protected int id;

	public String getData() {
		return data;
	}

	public int getId() {
		return id;
	}

	public TeammateDetailsUIEvent() {

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

	public static class RatingEvent extends TeammateDetailsUIEvent {

		private Float rating;

		public RatingEvent(int id, Float rating) {
			this.rating = rating;
			this.id = id;
		}

		public Float getRating() {
			return rating;
		}
	}
}
