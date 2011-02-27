package se.eloff.fudge.client.bean;

import java.io.Serializable;

public class Forum implements Serializable{

	private static final long serialVersionUID = 5671127441683003571L;
	
	private int id;
	private String name;
	private String description;
	private int nrOfTopics;
	
	public Forum() {
	}
	
	public Forum(String name, String description){
		this.setName(name);
		this.setDescription(description);
	}

	public void setNrOfTopics(int nrOfTopics) {
		this.nrOfTopics = nrOfTopics;
	}

	public int getNrOfTopics() {
		return nrOfTopics;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}
}
