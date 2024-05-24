// Class representing a DTO (Data Transfer Object) for sets.
package com.magicvault.requests;

import java.util.List;

public class SetsDTO {
    private List<SetsRequest> data; // List of SetsRequest objects

    // Getter for data
    public List<SetsRequest> getData() {
        return data;
    }

    // Setter for data
    public void setData(List<SetsRequest> data) {
        this.data = data;
    }
}
