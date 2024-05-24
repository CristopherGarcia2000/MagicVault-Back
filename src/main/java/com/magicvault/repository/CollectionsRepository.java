package com.magicvault.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.magicvault.documents.Collections;

public interface CollectionsRepository extends MongoRepository<Collections,ObjectId> {
	List<Collections> findByUser(String user);
	Optional<Collections> findByCollectionnameAndUser(String collectionname,String user);
}
