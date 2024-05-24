// Class representing a filter for searching cards.
package com.magicvault.requests;

import java.util.List;

public class CardSearchFilter {
    private List<String> colors; // List of colors
    private String type; // Type of the card
    private String expansion; // Expansion of the card
    private String name; // Name of the card

    // Getter and setter for the list of colors
    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    // Getter and setter for the card type
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Getter and setter for the expansion
    public String getExpansion() {
        return expansion;
    }

    public void setExpansion(String expansion) {
        this.expansion = expansion;
    }

    // Getter and setter for the card name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
