// MongoDB repository interface for Collections documents.
package com.magicvault.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.magicvault.documents.Collections;

public interface CollectionsRepository extends MongoRepository<Collections, ObjectId> {
    // Method to find collections by user.
    List<Collections> findByUser(String user);
    
    // Method to find collections by collection name and user.
    Optional<Collections> findByCollectionnameAndUser(String collectionname, String user);
}

