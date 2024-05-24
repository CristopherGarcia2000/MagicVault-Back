package com.magicvault.controller;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.magicvault.card.ScryfallCard;
import com.magicvault.documents.Collections;
import com.magicvault.repository.CollectionsRepository;
import com.magicvault.requests.AddRemoveCardRequest;
import com.magicvault.services.ScryfallService;

@RestController
@RequestMapping("/collections")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CollectionsController {

    @Autowired
    public CollectionsRepository collectionsRepository;

    @Autowired
    public ScryfallService scryfallService;

    // Endpoint to save a collection
    @PostMapping
    public ResponseEntity<?> saveCollection(@RequestBody Collections collection) {
        try {
            // Check if a collection with the same name already exists for this user
            Optional<Collections> existingCollection = collectionsRepository.findByCollectionnameAndUser(collection.getCollectionname(), collection.getUser());

            if (existingCollection.isPresent()) {
                return new ResponseEntity<>("A collection with the same name already exists for this user", HttpStatus.CONFLICT);
            }

            // Save the new collection
            Collections collectionsaved = collectionsRepository.save(collection);
            return new ResponseEntity<>(collectionsaved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to retrieve all collections
    @GetMapping
    public ResponseEntity<?> findAllCollections() {
        try {
            // Retrieve all collections from the repository
            List<Collections> collections = collectionsRepository.findAll();
            return new ResponseEntity<>(collections, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to find collections by user
    @GetMapping(value = "/user/{user}")
    public ResponseEntity<?> findDecksByUser(@PathVariable("user") String user) {
        try {
            // Find collections by user from the repository
            List<Collections> usercollections = collectionsRepository.findByUser(user);
            return new ResponseEntity<>(usercollections, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to find cards in a collection
    @GetMapping("/cards")
    public List<ScryfallCard> findCardsInCollection(@RequestParam String user, @RequestParam String collectionName) {
        try {
            // Find the collection by name and user
            Optional<Collections> collection = collectionsRepository.findByCollectionnameAndUser(collectionName, user);
            if (collection.isPresent()) {
                // Get the list of cards from the collection
                List<String> cards = collection.get().getCollectionlist();
                // Use Scryfall service to get card details
                return scryfallService.getCardList(cards);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    // Endpoint to add a card to a collection
    @PutMapping("/addCard")
    public ResponseEntity<?> addCardToDeck(@RequestBody AddRemoveCardRequest addCardRequest) {
        try {
            // Find the collection by name and user
            Optional<Collections> _collection = collectionsRepository.findByCollectionnameAndUser(addCardRequest.getDeckname(), addCardRequest.getUser());
            if (_collection.isPresent()) {
                Collections collection = _collection.get();
                // Add the card to the collection
                collection.getCollectionlist().add(addCardRequest.getCardName());
                // Save the updated collection
                collectionsRepository.save(collection);
                return new ResponseEntity<>(collection, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Collection not found for the specified user", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to remove a card from a collection
    @DeleteMapping("/removeCard")
    public ResponseEntity<?> removeCardFromDeck(@RequestBody AddRemoveCardRequest removeCardRequest) {
        try {
            // Find the collection by name and user
            Optional<Collections> _collection = collectionsRepository.findByCollectionnameAndUser(removeCardRequest.getDeckname(), removeCardRequest.getUser());
            if (_collection.isPresent()) {
                Collections collection = _collection.get();
                // Remove the card from the collection
                boolean removed = collection.getCollectionlist().remove(removeCardRequest.getCardName());
                if (removed) {
                    // Save the updated collection
                    collectionsRepository.save(collection);
                    return new ResponseEntity<>(collection, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("The card does not exist in the specified collection", HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>("Collection not found for the specified user", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to find a collection by ID
    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findDeck(@PathVariable("id") String id) {
        try {
            // Find the collection by ID
            ObjectId collectionId = new ObjectId(id);
            Optional<Collections> _collection = collectionsRepository.findById(collectionId);
            if (_collection.isPresent()) {
                Collections collection = _collection.get();
                return new ResponseEntity<>(collection, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Collection not found", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to delete a collection
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCollection(@RequestBody AddRemoveCardRequest toBeDeleted) {
        try {
            // Find the collection by name and user
            Optional<Collections> collectionOpt = collectionsRepository.findByCollectionnameAndUser(toBeDeleted.getDeckname(), toBeDeleted.getUser());
            if (collectionOpt.isPresent()) {
                Collections collection = collectionOpt.get();
                // Delete the collection
                collectionsRepository.delete(collection);
                return new ResponseEntity<>(collection, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Collection not found for the specified user", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to update a collection
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCollection(@PathVariable("id") String id, @RequestBody Collections newCollection) {
        try {
            // Find the collection by ID
            ObjectId collectionId = new ObjectId(id);
            Optional<Collections> _collection = collectionsRepository.findById(collectionId);
            if (_collection.isPresent()) {
                Collections collection = _collection.get();
                // Update collection details
                collection.setUser(newCollection.getUser());
                collection.setCollectionname(newCollection.getCollectionname());
                collection.setCollectionlist(newCollection.getCollectionlist());
                // Save the updated collection
                collectionsRepository.save(collection);
                return new ResponseEntity<>(collection, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Collection not found", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}