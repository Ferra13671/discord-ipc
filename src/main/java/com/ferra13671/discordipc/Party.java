package com.ferra13671.discordipc;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public record Party(String name, int minSize, int maxSize) {

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("id", this.name);
        JsonArray sizeArray = new JsonArray();
        sizeArray.add(this.minSize);
        sizeArray.add(this.maxSize);
        jsonObject.add("size", sizeArray);

        return jsonObject;
    }
}
