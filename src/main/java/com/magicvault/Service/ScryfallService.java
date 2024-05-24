package com.magicvault.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.magicvault.Card.ScryfallCard;
import com.magicvault.Requests.CardListRequests;
import com.magicvault.Requests.CardSearchFilter;
import com.magicvault.Requests.CreatureTypesRequest;
import com.magicvault.Requests.SetsDTO;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@Service
public class ScryfallService {

    private static final String SCRYFALL_ENDPOINT = "https://api.scryfall.com/cards/";
    
    private final RestTemplate restTemplate;

    public ScryfallService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ScryfallCard getRandomCommander() {
        try {
            return restTemplate.getForObject(SCRYFALL_ENDPOINT.concat("random?q=type:legendary+type:creature"), ScryfallCard.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error fetching commander: " + ex.getMessage());
            return null;
        }
    }

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



    public ScryfallCard[] getAllCards() {
    	try {
    		CardListRequests response = restTemplate.getForObject(SCRYFALL_ENDPOINT.concat("search?q=o:a+or+o:e+or+o:i+or+o:o+or+o:u"), CardListRequests.class);
    		ScryfallCard[] cards = response.getData();
    		return cards;
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error fetching commander: " + ex.getMessage());
            return null;
        }
    }
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
    public CreatureTypesRequest getCreatureTypes() {
        String url = "https://api.scryfall.com/catalog/creature-types";
        return restTemplate.getForObject(url, CreatureTypesRequest.class);
    }

    public SetsDTO getAllSets() {
        String url = "https://api.scryfall.com/sets";
        return restTemplate.getForObject(url, SetsDTO.class);
    }
    public CardListRequests searchCards(CardSearchFilter filter) {
        try {
            StringBuilder queryStringBuilder = new StringBuilder();
    
            // Agregar los parámetros del filtro a la cadena de consulta
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
    
            // Eliminar el último '&' si existe
            String queryString = queryStringBuilder.toString();
            if (!queryString.isEmpty() && queryString.endsWith("&")) {
                queryString = queryString.substring(0, queryString.length() - 1);
            }
    
            // Construir la URL completa
            String url = SCRYFALL_ENDPOINT + "search?q=" + queryString;
            System.out.println("Url : " +  url);
    
            // Realizar la solicitud al endpoint de la API de Scryfall
            return restTemplate.getForObject(url, CardListRequests.class);
        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            System.err.println("Error searching cards: " + ex.getMessage());
            return null;
        }
    }

}