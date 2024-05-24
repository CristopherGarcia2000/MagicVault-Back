package com.magicvault.requests;

import java.util.List;

public class CardSearchFilter {
    private List<String> colors;
    private String type;
    private String expansion;
    private String name;

    public List<String> getColors() {
        return colors;
    }
    public void setColors(List<String> colors) {
        this.colors = colors;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getExpansion() {
        return expansion;
    }
    public void setExpansion(String expansion) {
        this.expansion = expansion;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    
    

    
}
