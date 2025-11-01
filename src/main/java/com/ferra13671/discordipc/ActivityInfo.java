package com.ferra13671.discordipc;

import com.google.gson.JsonObject;

public record ActivityInfo(String details, String state, String largeImage, String largeText, String smallImage, String smallText, Long startTime, Party party) {

    public ActivityInfo setDetails(String details) {
        return new ActivityInfo(details, this.state, this.largeImage, this.largeText, this.smallImage, this.smallText, this.startTime, this.party);
    }

    public ActivityInfo setState(String state) {
        return new ActivityInfo(this.details, state, this.largeImage, this.largeText, this.smallImage, this.smallText, this.startTime, this.party);
    }

    public ActivityInfo setLargeImage(String largeImage) {
        return new ActivityInfo(this.details, this.state, largeImage, this.largeText, this.smallImage, this.smallText, this.startTime, this.party);
    }

    public ActivityInfo setLargeText(String largeText) {
        return new ActivityInfo(this.details, this.state, this.largeImage, largeText, this.smallImage, this.smallText, this.startTime, this.party);
    }

    public ActivityInfo setSmallImage(String smallImage) {
        return new ActivityInfo(this.details, this.state, this.largeImage, this.largeText, smallImage, this.smallText, this.startTime, this.party);
    }

    public ActivityInfo setSmallText(String smallText) {
        return new ActivityInfo(this.details, this.state, this.largeImage, this.largeText, this.smallImage, smallText, this.startTime, this.party);
    }

    public ActivityInfo setStartTime(Long startTime) {
        return new ActivityInfo(this.details, this.state, this.largeImage, this.largeText, this.smallImage, this.smallText, startTime, this.party);
    }

    public ActivityInfo setParty(Party party) {
        return new ActivityInfo(this.details, this.state, this.largeImage, this.largeText, this.smallImage, this.smallText, this.startTime, party);
    }

    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();

        if (this.details != null)
            jsonObject.addProperty("details", this.details);
        if (this.state != null)
            jsonObject.addProperty("state", this.state);

        JsonObject assetsObject = new JsonObject();

        if (this.largeImage != null)
            assetsObject.addProperty("large_image", this.largeImage);
        if (this.largeText != null)
            assetsObject.addProperty("large_text", this.largeText);
        if (this.smallImage != null)
            assetsObject.addProperty("small_image", this.smallImage);
        if (this.smallText != null)
            assetsObject.addProperty("small_text", this.smallText);

        jsonObject.add("assets", assetsObject);

        JsonObject timestampsObject = new JsonObject();

        if (this.startTime != null)
            timestampsObject.addProperty("start", this.startTime);

        jsonObject.add("timestamps", timestampsObject);

        if (this.party != null)
            jsonObject.add("party", this.party.toJson());

        return jsonObject;
    }
}
