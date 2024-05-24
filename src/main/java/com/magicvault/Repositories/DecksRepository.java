package com.magicvault.Repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.magicvault.Documents.Decks;

public interface DecksRepository extends MongoRepository<Decks,ObjectId> {
	List<Decks> findByUser(String user);
	Optional<Decks> findByDecknameAndUser(String deckname,String user);
}
