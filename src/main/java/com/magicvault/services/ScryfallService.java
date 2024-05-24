// Service class for interacting with the Scryfall API.
package com.magicvault.services;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import com.magicvault.card.ScryfallCard;
import com.magicvault.requests.CardListRequests;
import com.magicvault.requests.CardSearchFilter;
import com.magicvault.requests.CreatureTypesRequest;
import com.magicvault.requests.SetsDTO;

@Service
public class ScryfallService {

    // Endpoint URL for the Scryfall API
    private static final String SCRYFALL_ENDPOINT = "https://api.scryfall.com/cards/";

    // RestTemplate for making HTTP requests
    // RestTemplate is a Spring framework class for making HTTP requests to RESTful services.
    // It simplifies the interaction with RESTful web services by abstracting the complexities of 
    //RESTful operations like GET, POST, PUT, and DELETE requests.
    // It handles the creation of HTTP requests, the processing of HTTP responses, 
    //and the marshalling and unmarshalling of data between Java objects and JSON/XML representations.
    private final RestTemplate restTemplate;

    // Constructor injecting RestTemplate dependency
    public ScryfallService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Method to retrieve a random commander card from Scryfall
    public ScryfallCard getRandomCommander() {
        try {
            return restTemplate.getForObject(SCRYFALL_ENDPOINT.concat("random?q=type:legendary+type:creature"), ScryfallCard.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error fetching commander: " + ex.getMessage());
            return null;
        }
    }

    // Method to retrieve a commander card by name from Scryfall
    public ScryfallCard getCommanderByName(String name) {
        try {
            String query = String.format("q=name:\"%s\" type:legendary type:creature", name);
            CardListRequests response = restTemplate.getForObject(SCRYFALL_ENDPOINT.concat("search?" + query), CardListRequests.class);
            if (response != null && response.getData() != null && response.getData().length > 0) {
                return response.getData()[0];
            }
            return null;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error fetching commander by name: " + ex.getMessage());
            return null;
        }
    }

    // Method to retrieve all cards from Scryfall
    public ScryfallCard[] getAllCards() {
        try {
            CardListRequests response = restTemplate.getForObject(SCRYFALL_ENDPOINT.concat("search?q=o:a+or+o:e+or+o:i+or+o:o+or+o:u"), CardListRequests.class);
            return response.getData();
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error fetching commander: " + ex.getMessage());
            return null;
        }
    }

    // Method to retrieve a list of cards by their names from Scryfall
    public List<ScryfallCard> getCardList(List<String> cardlist) {
        try {
            List<ScryfallCard> cards = new ArrayList<>();
            for (String cardname : cardlist) {
                String url = SCRYFALL_ENDPOINT + "named?fuzzy=" + cardname;
                ScryfallCard card = restTemplate.getForObject(url, ScryfallCard.class);
                if (card != null) {
                    cards.add(card);
                }
            }
            return cards;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error fetching commander: " + ex.getMessage());
            return null;
        }
    }

    // Method to retrieve creature types from Scryfall
    public CreatureTypesRequest getCreatureTypes() {
        String url = "https://api.scryfall.com/catalog/creature-types";
        return restTemplate.getForObject(url, CreatureTypesRequest.class);
    }

    // Method to retrieve all sets from Scryfall
    public SetsDTO getAllSets() {
        String url = "https://api.scryfall.com/sets";
        return restTemplate.getForObject(url, SetsDTO.class);
    }

    // Method to search for cards based on filter criteria
    public CardListRequests searchCards(CardSearchFilter filter) {
        try {
            StringBuilder queryStringBuilder = new StringBuilder();

            // Append filter parameters to the query string
            if (filter.getColors() != null && !filter.getColors().isEmpty()) {
                queryStringBuilder.append("c=");
                queryStringBuilder.append(String.join("", filter.getColors()));
                queryStringBuilder.append("+");
            }
            if (filter.getType() != null) {
                queryStringBuilder.append("t=");
                queryStringBuilder.append(filter.getType());
                queryStringBuilder.append("+");
            }
            if (filter.getExpansion() != null) {
                queryStringBuilder.append("e=");
                queryStringBuilder.append(filter.getExpansion());
                queryStringBuilder.append("+");
            }

            if (filter.getName() != null) {
                queryStringBuilder.append("name=");
                queryStringBuilder.append(filter.getName());
                queryStringBuilder.append("+");
            }

            // Remove the trailing '&' if exists
            String queryString = queryStringBuilder.toString();
            if (!queryString.isEmpty() && queryString.endsWith("&")) {
                queryString = queryString.substring(0, queryString.length() - 1);
            }

            // Construct the complete URL
            String url = SCRYFALL_ENDPOINT + "search?q=" + queryString;
            System.out.println("Url : " + url);

            // Make the request to the Scryfall API endpoint
            return restTemplate.getForObject(url, CardListRequests.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error searching cards: " + ex.getMessage());
            return null;
        }
    }
}
