// MongoDB repository interface for Users documents.
package com.magicvault.repository;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.magicvault.documents.Users;

public interface UsersRepository extends MongoRepository<Users, ObjectId> {
    // Method to find a user by username and password.
    Users findByUsernameAndPass(String username, String pass);
    
    // Method to find a user by username.
    Optional<UserDetails> findByUsername(String username);
}
