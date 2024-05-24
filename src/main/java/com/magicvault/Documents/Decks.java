package com.magicvault.documents;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Decks")
public class Decks {
	
	@Id
	private ObjectId id;
	
	private String user;
	
	private String deckname;
	
	private List<String> decklist;

	private String commander;

	private String color;

	public Decks(ObjectId id, String user, String deckname, List<String> decklist,String commander,String color) {
		super();
		this.id = id;
		this.user = user;
		this.deckname = deckname;
		this.decklist = decklist;
		this.commander = commander;
		this.color = color;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getDeckname() {
		return deckname;
	}

	public void setDeckname(String deckname) {
		this.deckname = deckname;
	}

	public List<String> getDecklist() {
		return decklist;
	}

	public void setDecklist(List<String> decklist) {
		this.decklist = decklist;
	}

	public String getCommander() {
		return commander;
	}

	public void setCommander(String commander) {
		this.commander = commander;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setColorIdentity(List<String> colorIdentity) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'setColorIdentity'");
	}
	
}
