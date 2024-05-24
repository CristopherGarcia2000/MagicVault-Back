// Class representing a request to add or remove a card from a deck.
package com.magicvault.requests;

public class AddRemoveCardRequest {
    private String deckname; // Name of the deck
    private String cardName; // Name of the card
    private String user; // User associated with the deck

    // Getter and setter for deckname
    public String getDeckname() {
        return deckname;
    }

    public void setDeckname(String deckname) {
        this.deckname = deckname;
    }

    // Getter and setter for cardName
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    // Getter and setter for user
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}