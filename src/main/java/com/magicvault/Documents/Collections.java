package com.magicvault.documents;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Collections")
public class Collections {
	@Id
	private ObjectId id;
	
	private String user;
	
	private String collectionname;
	
	private List<String> collectionlist;

	private String color;

	public Collections(ObjectId id, String user, String collectionname, List<String> collectionlist,String color) {
		super();
		this.id = id;
		this.user = user;
		this.collectionname = collectionname;
		this.collectionlist = collectionlist;
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

	public String getCollectionname() {
		return collectionname;
	}

	public void setCollectionname(String collectionname) {
		this.collectionname = collectionname;
	}

	public List<String> getCollectionlist() {
		return collectionlist;
	}

	public void setCollectionlist(List<String> collectionlist) {
		this.collectionlist = collectionlist;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

}
