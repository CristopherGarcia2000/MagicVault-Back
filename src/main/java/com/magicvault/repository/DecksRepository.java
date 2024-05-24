// MongoDB repository interface for Decks documents.
package com.magicvault.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.magicvault.documents.Decks;

public interface DecksRepository extends MongoRepository<Decks, ObjectId> {
    // Method to find decks by user.
    List<Decks> findByUser(String user);
    
    // Method to find decks by deck name and user.
    Optional<Decks> findByDecknameAndUser(String deckname, String user);
}
