// Class representing a request for creature types.
package com.magicvault.requests;

import java.util.List;

public class CreatureTypesRequest {
    private List<String> data; // List of creature types

    // Getter and setter for the list of creature types
    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
