// Class representing a request containing a list of Scryfall cards.
package com.magicvault.requests;

import com.magicvault.card.ScryfallCard;

public class CardListRequests {
    private ScryfallCard[] data; // Array of ScryfallCard objects

    // Getter and setter for the array of ScryfallCard objects
    public ScryfallCard[] getData() {
        return data;
    }

    public void setData(ScryfallCard[] data) {
        this.data = data;
    }
}
