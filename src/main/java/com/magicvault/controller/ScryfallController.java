package com.magicvault.controller;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.magicvault.documents.Collections;
import com.magicvault.documents.Decks;
import com.magicvault.repository.CollectionsRepository;
import com.magicvault.repository.DecksRepository;
import com.magicvault.requests.CardListRequests;
import com.magicvault.requests.CardSearchFilter;
import com.magicvault.requests.CreatureTypesRequest;
import com.magicvault.requests.SetsDTO;
import com.magicvault.services.ScryfallService;


@RestController
@CrossOrigin(origins = "*",allowedHeaders="*")
public class ScryfallController {
	@Autowired
    private ScryfallService scryfallService;
	@Autowired
	private DecksRepository deckRepository;
	@Autowired
	private CollectionsRepository collectionsRepository;


    @GetMapping("/random-commander")
    public Object getRandomCommander() {
        return scryfallService.getRandomCommander();
    }


    @GetMapping("/all-cards")
    public Object getAllCards() {
    	return scryfallService.getAllCards();
    }
    @GetMapping(value="/deck/{id}")
	public Object getDeckList(@PathVariable String id) {
    	ObjectId deckId = new ObjectId(id);
		Optional<Decks> _deck = deckRepository.findById(deckId);
		if(_deck.isPresent()) 
		{
			Decks deck = _deck.get();
			return scryfallService.getCardList(deck.getDecklist());	
		}
		return null; 
    }
    @GetMapping(value="/collection/{id}")
	public Object getCollectionList(@PathVariable String id) {
    	ObjectId collectionId = new ObjectId(id);
		Optional<Collections> _collection = collectionsRepository.findById(collectionId);
		if(_collection.isPresent()) 
		{
			Collections collection = _collection.get();
			return scryfallService.getCardList(collection.getCollectionlist());	
		}
		return null; 
    }
    @GetMapping("/creature-types")
    public CreatureTypesRequest getCreatureTypes() {
        return scryfallService.getCreatureTypes();
    }

    @GetMapping("/sets")
    public SetsDTO getAllSets() {
        return scryfallService.getAllSets();
    }
    @PostMapping("/search-cards")
	public CardListRequests searchCards(@RequestBody CardSearchFilter filter) {
    	return scryfallService.searchCards(filter);
	}
}