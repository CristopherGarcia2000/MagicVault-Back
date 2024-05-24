package com.magicvault.card;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ScryfallCard {

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

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("type_line")
    public String getTypeLine() {
        return typeLine;
    }

    public void setTypeLine(String typeLine) {
        this.typeLine = typeLine;
    }

    @JsonProperty("oracle_text")
    public String getOracleText() {
        return oracleText;
    }

    public void setOracleText(String oracleText) {
        this.oracleText = oracleText;
    }

    @JsonProperty("image_uris")
    public ImageUris getImageUris() {
        return imageUris;
    }

    public void setImageUris(ImageUris imageUris) {
        this.imageUris = imageUris;
    }
    public static class ImageUris {
        private String png;

        @JsonProperty("png")
        public String getPng() {
            return png;
        }

        public void setPng(String png) {
            this.png = png;
        }
    }
    @JsonProperty("mana_cost")
    public String getManaCost() {
        return manaCost;
    }

    public void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    @JsonProperty("power")
    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    @JsonProperty("toughness")
    public String getToughness() {
        return toughness;
    }

    public void setToughness(String toughness) {
        this.toughness = toughness;
    }

    @JsonProperty("colors")
    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    @JsonProperty("color_identity")
    public List<String> getColorIdentity() {
        return colorIdentity;
    }

    public void setColorIdentity(List<String> colorIdentity) {
        this.colorIdentity = colorIdentity;
    }
    @JsonProperty("prices")
    public Prices getPrices() {
    	return prices;
    }
    public void setPrices(Prices prices) {
    	this.prices = prices;
    }
    
    public static class Prices {
        private String eur;
        private String eur_foil;

        @JsonProperty("eur")
        public String getEur() {
            return eur;
        }

        public void setEur(String eur) {
            this.eur = eur;
        }
        @JsonProperty("eur_foil")
        public String getEurFoil() {
        	return eur_foil;
        }
        public void setEurFoil(String eur_foil) {
        	this.eur_foil = eur_foil;
        }
    }
}