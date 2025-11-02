package com.ferra13671.discordipc.connection.packet.impl.s2c;

import com.ferra13671.discordipc.IPCUser;
import com.ferra13671.discordipc.connection.packet.S2CPacket;
import com.ferra13671.discordipc.connection.packet.opcode.Opcode;
import com.google.gson.JsonObject;

public class DispatchPacket implements S2CPacket {
    private final IPCUser discordUser;

    public DispatchPacket(JsonObject jsonObject) {
        this.discordUser = IPCUser.fromJson(jsonObject.getAsJsonObject("data").getAsJsonObject("user"));
    }

    public DispatchPacket(IPCUser discordUser) {
        this.discordUser = discordUser;
    }

    public IPCUser getDiscordUser() {
        return discordUser;
    }

    @Override
    public Opcode getOpcode() {
        return Opcode.Frame;
    }

    public static boolean is(JsonObject jsonObject) {
        return jsonObject.has("cmd") && jsonObject.get("cmd").getAsString().equals("DISPATCH");
    }
}
