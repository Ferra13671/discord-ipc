package com.ferra13671.discordipc.connection.packet.impl.s2c;

import com.ferra13671.discordipc.connection.packet.S2CPacket;
import com.ferra13671.discordipc.connection.packet.opcode.Opcode;
import com.google.gson.JsonObject;

public class ErrorPacket implements S2CPacket {
    private final int code;
    private final String message;

    public ErrorPacket(JsonObject jsonObject) {
        JsonObject dataObject = jsonObject.getAsJsonObject("data");

        this.code = dataObject.get("code").getAsInt();
        this.message = dataObject.get("message").getAsString();
    }

    public ErrorPacket(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public Opcode getOpcode() {
        return Opcode.Frame;
    }

    public static boolean is(JsonObject jsonObject) {
        return jsonObject.has("evt") && jsonObject.get("evt").getAsString().equals("ERROR");
    }
}
