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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.magicvault.card.ScryfallCard;
import com.magicvault.documents.Decks;
import com.magicvault.repository.DecksRepository;
import com.magicvault.requests.AddRemoveCardRequest;
import com.magicvault.services.ScryfallService;

@RestController
@RequestMapping("/decks")
@CrossOrigin(origins = "*")
public class DecksController {

	@Autowired
	private DecksRepository deckRepository;

	@Autowired
	private ScryfallService scryfallService;

	// Endpoint to retrieve all decks
	@GetMapping
	public ResponseEntity<?> findAllDecks() {
		try {
			// Retrieve all decks from the repository
			List<Decks> decks = deckRepository.findAll();
			return new ResponseEntity<>(decks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Endpoint to find decks by user
	@GetMapping(value = "/user/{user}")
	public ResponseEntity<?> findDecksByUser(@PathVariable("user") String user) {
		try {
			// Find decks by user from the repository
			List<Decks> userDecks = deckRepository.findByUser(user);
			return new ResponseEntity<>(userDecks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Endpoint to find cards in a deck
	@GetMapping("/cards")
	public List<ScryfallCard> findCardsInDeck(@RequestParam String user, @RequestParam String deckName) {
		try {
			// Find the deck by name and user
			Optional<Decks> deck = deckRepository.findByDecknameAndUser(deckName, user);
			if (deck.isPresent()) {
				// Get the list of cards from the deck
				List<String> cards = deck.get().getDecklist();
				// Use Scryfall service to get card details
				return scryfallService.getCardList(cards);
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}

	// Endpoint to add a card to a deck
	@PutMapping("/addCard")
	public ResponseEntity<?> addCardToDeck(@RequestBody AddRemoveCardRequest addCardRequest) {
		try {
			// Find the deck by name and user
			Optional<Decks> optionalDeck = deckRepository.findByDecknameAndUser(addCardRequest.getDeckname(),
					addCardRequest.getUser());
			if (optionalDeck.isPresent()) {
				Decks deck = optionalDeck.get();
				// Add the card to the deck
				deck.getDecklist().add(addCardRequest.getCardName());
				// Save the updated deck
				deckRepository.save(deck);
				return new ResponseEntity<>(deck, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Deck not found for the specified user", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Endpoint to remove a card from a deck
	@DeleteMapping("/removeCard")
	public ResponseEntity<?> removeCardFromDeck(@RequestBody AddRemoveCardRequest removeCardRequest) {
		try {
			// Find the deck by name and user
			Optional<Decks> optionalDeck = deckRepository.findByDecknameAndUser(removeCardRequest.getDeckname(),
					removeCardRequest.getUser());
			if (optionalDeck.isPresent()) {
				Decks deck = optionalDeck.get();
				// Remove the card from the deck
				boolean removed = deck.getDecklist().remove(removeCardRequest.getCardName());
				if (removed) {
					// Save the updated deck
					deckRepository.save(deck);
					return new ResponseEntity<>(deck, HttpStatus.OK);
				} else {
					return new ResponseEntity<>("The card does not exist in the specified deck", HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<>("Deck not found for the specified user", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Endpoint to find a deck by ID
	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findDeck(@PathVariable("id") String id) {
		try {
			// Find the deck by ID
			ObjectId deckId = new ObjectId(id);
			Optional<Decks> _deck = deckRepository.findById(deckId);
			if (_deck.isPresent()) {
				Decks deck = _deck.get();
				return new ResponseEntity<>(deck, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Deck not found", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Endpoint to delete a deck
	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteDeck(@RequestBody AddRemoveCardRequest toBeDeleted) {
		try {
			// Find the deck by name and user
			Optional<Decks> deckOpt = deckRepository.findByDecknameAndUser(toBeDeleted.getDeckname(),
					toBeDeleted.getUser());
			if (deckOpt.isPresent()) {
				Decks deck = deckOpt.get();
				// Delete the deck
				deckRepository.delete(deck);
				return new ResponseEntity<>(deck, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Deck not found for the specified user", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Internal Server Error: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Endpoint to save a deck
	@PostMapping
	public ResponseEntity<?> saveDeck(@RequestBody Decks deck) {
		try {
			// Get commander card details
			ScryfallCard commanderCard = scryfallService.getCommanderByName(deck.getCommander());
			if (commanderCard == null) {
				return new ResponseEntity<>("Commander not found", HttpStatus.NOT_FOUND);
			}

			deck.setCommander(commanderCard.getName());
			deck.setColorIdentity(commanderCard.getColorIdentity());
			// Save the new deck
			Decks deckSaved = deckRepository.save(deck);
			return new ResponseEntity<>(deckSaved, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Endpoint to update a deck
	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateDeck(@PathVariable("id") String id, @RequestBody Decks newDeck) {
		try {
			// Find the deck by ID
			ObjectId deckId = new ObjectId(id);
			Optional<Decks> _deck = deckRepository.findById(deckId);
			if (_deck.isPresent()) {
				Decks deck = _deck.get();
				// Update deck details
				deck.setUser(newDeck.getUser());
				deck.setDeckname(newDeck.getDeckname());
				deck.setDecklist(newDeck.getDecklist());
				// Save the updated deck
				deckRepository.save(deck);
				return new ResponseEntity<>(deck, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Deck not found", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}