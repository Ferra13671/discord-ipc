package com.ferra13671.discordipc;

import com.google.gson.JsonObject;

public record IPCUser(String id, String username, String discriminator, String avatar, boolean bot, String flags, int premium_type) {

    public static IPCUser fromJson(JsonObject jsonObject) {
        return new IPCUser(
                jsonObject.get("id").getAsString(),
                jsonObject.get("username").getAsString(),
                jsonObject.get("discriminator").getAsString(),
                jsonObject.get("avatar").getAsString(),
                jsonObject.get("bot").getAsBoolean(),
                jsonObject.get("flags").getAsString(),
                jsonObject.get("premium_type").getAsInt()
        );
    }
}
