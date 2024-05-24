package com.magicvault.Repositories;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.magicvault.Documents.Users;

public interface UsersRepository extends MongoRepository<Users,ObjectId> {
	Users findByUsernameAndPass(String username, String pass);
    Optional<UserDetails> findByUsername(String username);
}
