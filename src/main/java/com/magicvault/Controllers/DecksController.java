package com.magicvault.Controllers;

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
import com.magicvault.Documents.Decks;
import com.magicvault.Repositories.DecksRepository;
import com.magicvault.Requests.AddRemoveCardRequest;

@RestController
@RequestMapping("/decks")
@CrossOrigin(origins = "*")
public class DecksController {

	@Autowired
	private DecksRepository deckRepository;

	@GetMapping
	public ResponseEntity<?> findAllDecks() {
		try {
			List<Decks> decks = deckRepository.findAll();
			return new ResponseEntity<List<Decks>>(decks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/user/{user}")
	public ResponseEntity<?> findDecksByUser(@PathVariable("user") String user) {
		try {
			List<Decks> userDecks = deckRepository.findByUser(user);
			return new ResponseEntity<List<Decks>>(userDecks, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/addCard")
	public ResponseEntity<?> addCardToDeck(@RequestBody AddRemoveCardRequest addCardRequest) {
		try {
			Optional<Decks> optionalDeck = deckRepository.findByDecknameAndUser(addCardRequest.getDeckname(),
					addCardRequest.getUser());
			if (optionalDeck.isPresent()) {
				Decks deck = optionalDeck.get();
				deck.getDecklist().add(addCardRequest.getCardName());
				deckRepository.save(deck);
				return new ResponseEntity<Decks>(deck, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No se encontró el mazo para el usuario especificado",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/removeCard")
	public ResponseEntity<?> removeCardFromDeck(@RequestBody AddRemoveCardRequest removeCardRequest) {
		try {
			Optional<Decks> optionalDeck = deckRepository.findByDecknameAndUser(removeCardRequest.getDeckname(),
					removeCardRequest.getUser());
			if (optionalDeck.isPresent()) {
				Decks deck = optionalDeck.get();
				boolean removed = deck.getDecklist().remove(removeCardRequest.getCardName());
				if (removed) {
					deckRepository.save(deck);
					return new ResponseEntity<Decks>(deck, HttpStatus.OK);
				} else {
					return new ResponseEntity<String>("La carta no existe en el mazo especificado",
							HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<String>("No se encontró el mazo para el usuario especificado",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<?> findDeck(@PathVariable("id") String id) {
		try {
			ObjectId deckId = new ObjectId(id);
			Optional<Decks> _deck = deckRepository.findById(deckId);
			if (_deck.isPresent()) {
				Decks deck = _deck.get();
				return new ResponseEntity<Decks>(deck, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No existe el mazo", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> deleteDeck(@RequestBody AddRemoveCardRequest toBeDeleted) {
		try {
			Optional<Decks> deckOpt = deckRepository.findByDecknameAndUser(toBeDeleted.getDeckname(),
					toBeDeleted.getUser());
			if (deckOpt.isPresent()) {
				Decks deck = deckOpt.get();
				deckRepository.delete(deck);
				return new ResponseEntity<>(deck, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("No se encontró el mazo para el usuario especificado",
						HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Error interno del servidor: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@PutMapping(value = "/{id}")
	public ResponseEntity<?> updateDeck(@PathVariable("id") String id, @RequestBody Decks newDeck) {
		try {
			ObjectId deckId = new ObjectId(id);
			Optional<Decks> _deck = deckRepository.findById(deckId);
			if (_deck.isPresent()) {
				Decks deck = _deck.get();
				deck.setUser(newDeck.getUser());
				deck.setDeckname(newDeck.getDeckname());
				deck.setDecklist(newDeck.getDecklist());
				deckRepository.save(deck);
				return new ResponseEntity<Decks>(deck, HttpStatus.OK);
			} else {
				return new ResponseEntity<String>("No existe el mazo", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getCause().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
