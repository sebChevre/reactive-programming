package ch.sebooom.reactive.rx.mongodb.dao;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.List;



public class PeopleDAO {

private final MongoCollection<Document> peopleCollection;
    
    public PeopleDAO(final MongoDatabase rxDatabase) {
    	peopleCollection = rxDatabase.getCollection("people");
    }

	public boolean addPeoples(List<Document> personnsSamplesDatas) {
		 peopleCollection.insertMany(personnsSamplesDatas);
		 
		 return true;
		
	}

	public FindIterable<Document> findPeopleByUserId(String uid) {
		return peopleCollection.find(new Document("user_id",uid));
	}

	public FindIterable<Document> findByNameAndSurname(String name, String surname) {
		return peopleCollection.find(new Document()
				.append("name", name)
				.append("surname", surname));
		
		
	}
    
    
}
