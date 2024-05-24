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

    @PostMapping
    public ResponseEntity<?> saveCollection(@RequestBody Collections collection) {
        try {
            Optional<Collections> existingCollection = collectionsRepository
                    .findByCollectionnameAndUser(collection.getCollectionname(), collection.getUser());

            if (existingCollection.isPresent()) {
                return new ResponseEntity<String>("La colección con el mismo nombre ya existe para este usuario",
                        HttpStatus.CONFLICT);
            }

            Collections collectionsaved = collectionsRepository.save(collection);
            return new ResponseEntity<Collections>(collectionsaved, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAllCollections() {
        try {
            List<Collections> collections = collectionsRepository.findAll();
            return new ResponseEntity<List<Collections>>(collections, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/user/{user}")
    public ResponseEntity<?> findDecksByUser(@PathVariable("user") String user) {
        try {
            List<Collections> usercollections = collectionsRepository.findByUser(user);
            return new ResponseEntity<List<Collections>>(usercollections, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
	
    @GetMapping("/cards")
    public List<ScryfallCard> findCardsInCollection(@RequestParam String user, @RequestParam String collectionName) {
        try {
            Optional<Collections> collection = collectionsRepository.findByCollectionnameAndUser(collectionName, user);
            if (collection.isPresent()) {
                List<String> cards = collection.get().getCollectionlist();
				return scryfallService.getCardList(cards);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @PutMapping("/addCard")
    public ResponseEntity<?> addCardToDeck(@RequestBody AddRemoveCardRequest addCardRequest) {
        try {
            Optional<Collections> _collection = collectionsRepository
                    .findByCollectionnameAndUser(addCardRequest.getDeckname(), addCardRequest.getUser());
            if (_collection.isPresent()) {
                Collections collection = _collection.get();
                collection.getCollectionlist().add(addCardRequest.getCardName());
                collectionsRepository.save(collection);
                return new ResponseEntity<Collections>(collection, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("No se encontró la colección para el usuario especificado",
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/removeCard")
    public ResponseEntity<?> removeCardFromDeck(@RequestBody AddRemoveCardRequest removeCardRequest) {
        try {
            Optional<Collections> _collection = collectionsRepository
                    .findByCollectionnameAndUser(removeCardRequest.getDeckname(), removeCardRequest.getUser());
            if (_collection.isPresent()) {
                Collections collection = _collection.get();
                boolean removed = collection.getCollectionlist().remove(removeCardRequest.getCardName());
                if (removed) {
                    collectionsRepository.save(collection);
                    return new ResponseEntity<Collections>(collection, HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("La carta no existe en la colección especificada",
                            HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<String>("No se encontró la colección para el usuario especificado",
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findDeck(@PathVariable("id") String id) {
        try {
            ObjectId collectionId = new ObjectId(id);
            Optional<Collections> _collection = collectionsRepository.findById(collectionId);
            if (_collection.isPresent()) {
                Collections collection = _collection.get();
                return new ResponseEntity<Collections>(collection, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("No existe la Colección", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCollection(@RequestBody AddRemoveCardRequest toBeDeleted) {
        try {
            Optional<Collections> collectionOpt = collectionsRepository
                    .findByCollectionnameAndUser(toBeDeleted.getDeckname(), toBeDeleted.getUser());
            if (collectionOpt.isPresent()) {
                Collections collection = collectionOpt.get();
                collectionsRepository.delete(collection);
                return new ResponseEntity<>(collection, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No se encontró la colección para el usuario especificado",
                        HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateCollection(@PathVariable("id") String id, @RequestBody Collections newCollection) {
        try {
            ObjectId collectionId = new ObjectId(id);
            Optional<Collections> _collection = collectionsRepository.findById(collectionId);
            if (_collection.isPresent()) {
                Collections collection = _collection.get();
                collection.setUser(newCollection.getUser());
                collection.setCollectionname(newCollection.getCollectionname());
                collection.setCollectionlist(newCollection.getCollectionlist());
                collectionsRepository.save(collection);
                return new ResponseEntity<Collections>(collection, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>("No existe la Colección", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
