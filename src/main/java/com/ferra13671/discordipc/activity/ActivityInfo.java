package com.ferra13671.discordipc.activity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Information about activity displayed in Discord.
 *
 * @param details description of the activity.
 * @param state the state of the activity (for example, a user action, or the id of a command if you created one).
 * @param largeImage path to the large image (image id in the application assets/link to the image).
 * @param largeText text displayed when hovering over a large image.
 * @param smallImage path to the small image (image id in the application assets/link to the image).
 * @param smallText text displayed when hovering over a small image.
 * @param startTime activity start time.
 * @param party team.
 * @param buttons buttons displayed below the activity. A maximum of two buttons can be displayed below an activity. Also, only other users will see your activity buttons.
 */
public record ActivityInfo(String details, String state, String largeImage, String largeText, String smallImage, String smallText, Long startTime, Party party, Button... buttons) {

    public ActivityInfo setDetails(String details) {
        return new ActivityInfo(details, this.state, this.largeImage, this.largeText, this.smallImage, this.smallText, this.startTime, this.party, this.buttons);
    }

    public ActivityInfo setState(String state) {
        return new ActivityInfo(this.details, state, this.largeImage, this.largeText, this.smallImage, this.smallText, this.startTime, this.party, this.buttons);
    }

    public ActivityInfo setLargeImage(String largeImage) {
        return new ActivityInfo(this.details, this.state, largeImage, this.largeText, this.smallImage, this.smallText, this.startTime, this.party, this.buttons);
    }

    public ActivityInfo setLargeText(String largeText) {
        return new ActivityInfo(this.details, this.state, this.largeImage, largeText, this.smallImage, this.smallText, this.startTime, this.party, this.buttons);
    }

    public ActivityInfo setSmallImage(String smallImage) {
        return new ActivityInfo(this.details, this.state, this.largeImage, this.largeText, smallImage, this.smallText, this.startTime, this.party, this.buttons);
    }

    public ActivityInfo setSmallText(String smallText) {
        return new ActivityInfo(this.details, this.state, this.largeImage, this.largeText, this.smallImage, smallText, this.startTime, this.party, this.buttons);
    }

    public ActivityInfo setStartTime(Long startTime) {
        return new ActivityInfo(this.details, this.state, this.largeImage, this.largeText, this.smallImage, this.smallText, startTime, this.party, this.buttons);
    }

    public ActivityInfo setParty(Party party) {
        return new ActivityInfo(this.details, this.state, this.largeImage, this.largeText, this.smallImage, this.smallText, this.startTime, party, this.buttons);
    }

    public ActivityInfo setButtons(Button... buttons) {
        return new ActivityInfo(this.details, this.state, this.largeImage, this.largeText, this.smallImage, this.smallText, this.startTime, this.party, buttons);
    }

    /**
     * Converts activity information into a json object.
     *
     * @return json object storing activity information.
     */
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();

        if (this.details != null)
            jsonObject.addProperty("details", this.details);
        if (this.state != null)
            jsonObject.addProperty("state", this.state);

        jsonObject.add("assets", getAssetsObject());
        jsonObject.add("timestamps", getTimestampsObject());

        if (this.party != null)
            jsonObject.add("party", this.party.toJson());

        if (this.buttons != null && this.buttons.length > 0)
            jsonObject.add("buttons", getButtonsArray());

        jsonObject.addProperty("instance", false);

        return jsonObject;
    }

    private JsonObject getAssetsObject() {
        JsonObject jsonObject = new JsonObject();

        if (this.largeImage != null)
            jsonObject.addProperty("large_image", this.largeImage);
        if (this.largeText != null)
            jsonObject.addProperty("large_text", this.largeText);
        if (this.smallImage != null)
            jsonObject.addProperty("small_image", this.smallImage);
        if (this.smallText != null)
            jsonObject.addProperty("small_text", this.smallText);

        return jsonObject;
    }

    private JsonObject getTimestampsObject() {
        JsonObject jsonObject = new JsonObject();

        if (this.startTime != null)
            jsonObject.addProperty("start", this.startTime);

        return jsonObject;
    }

    private JsonArray getButtonsArray() {
        JsonArray jsonArray = new JsonArray();

        for (Button button : this.buttons)
            jsonArray.add(button.toJson());

        return jsonArray;
    }
}
