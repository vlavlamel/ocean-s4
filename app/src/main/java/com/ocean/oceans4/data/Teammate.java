package com.ocean.oceans4.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Teammate implements Serializable {
	public String name;
	public String post;
	public String photo;
	public String about;
	public String group;
}
