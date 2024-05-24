package com.magicvault.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ScryfallCard {

    // Private fields representing the properties of a Scryfall card
    private String name;
    private String typeLine;
    private String oracleText;
    private ImageUris imageUris;
    private String manaCost;
    private String power;
    private String toughness;
    private List<String> colors;
    private List<String> colorIdentity;
    private Prices prices;

    // Getter for the 'name' property, with JSON property annotation
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    // Setter for the 'name' property
    public void setName(String name) {
        this.name = name;
    }

    // Getter for the 'type_line' property, with JSON property annotation
    @JsonProperty("type_line")
    public String getTypeLine() {
        return typeLine;
    }

    // Setter for the 'type_line' property
    public void setTypeLine(String typeLine) {
        this.typeLine = typeLine;
    }

    // Getter for the 'oracle_text' property, with JSON property annotation
    @JsonProperty("oracle_text")
    public String getOracleText() {
        return oracleText;
    }

    // Setter for the 'oracle_text' property
    public void setOracleText(String oracleText) {
        this.oracleText = oracleText;
    }

    // Getter for the 'image_uris' property, with JSON property annotation
    @JsonProperty("image_uris")
    public ImageUris getImageUris() {
        return imageUris;
    }

    // Setter for the 'image_uris' property
    public void setImageUris(ImageUris imageUris) {
        this.imageUris = imageUris;
    }

    // Static inner class representing image URIs
    public static class ImageUris {
        private String png;

        // Getter for the 'png' property, with JSON property annotation
        @JsonProperty("png")
        public String getPng() {
            return png;
        }

        // Setter for the 'png' property
        public void setPng(String png) {
            this.png = png;
        }
    }

    // Getter for the 'mana_cost' property, with JSON property annotation
    @JsonProperty("mana_cost")
    public String getManaCost() {
        return manaCost;
    }

    // Setter for the 'mana_cost' property
    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    // Getter for the 'power' property, with JSON property annotation
    @JsonProperty("power")
    public String getPower() {
        return power;
    }

    // Setter for the 'power' property
    public void setPower(String power) {
        this.power = power;
    }

    // Getter for the 'toughness' property, with JSON property annotation
    @JsonProperty("toughness")
    public String getToughness() {
        return toughness;
    }

    // Setter for the 'toughness' property
    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    // Getter for the 'colors' property, with JSON property annotation
    @JsonProperty("colors")
    public List<String> getColors() {
        return colors;
    }

    // Setter for the 'colors' property
    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    // Getter for the 'color_identity' property, with JSON property annotation
    @JsonProperty("color_identity")
    public List<String> getColorIdentity() {
        return colorIdentity;
    }

    // Setter for the 'color_identity' property
    public void setColorIdentity(List<String> colorIdentity) {
        this.colorIdentity = colorIdentity;
    }

    // Getter for the 'prices' property, with JSON property annotation
    @JsonProperty("prices")
    public Prices getPrices() {
        return prices;
    }

    // Setter for the 'prices' property
    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    // Static inner class representing the prices
    public static class Prices {
        private String eur;
        private String eur_foil;

        // Getter for the 'eur' property, with JSON property annotation
        @JsonProperty("eur")
        public String getEur() {
            return eur;
        }

        // Setter for the 'eur' property
        public void setEur(String eur) {
            this.eur = eur;
        }

        // Getter for the 'eur_foil' property, with JSON property annotation
        @JsonProperty("eur_foil")
        public String getEurFoil() {
            return eur_foil;
        }

        // Setter for the 'eur_foil' property
        public void setEurFoil(String eur_foil) {
            this.eur_foil = eur_foil;
        }
    }
}
