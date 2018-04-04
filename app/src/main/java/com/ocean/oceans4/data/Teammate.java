package com.ocean.oceans4.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Teammate implements Serializable {
	public String name;
	public String post;
	public String photo;
	public String about;
	public String group;
	public int id;

	@JsonIgnore
	public boolean isEmpty() {
		return name == null && post == null && photo == null && about == null && group == null;
	}

	public Teammate() {
		//hardcode for jackson
	}

	public Teammate(String name, String post, String about, String group, int id) {
		this.name = name;
		this.post = post;
		this.photo = photo;
		this.about = about;
		this.group = group;
		this.id = id;
	}
}
