package com.ferra13671.discordipc.connection.packet;

import com.google.gson.JsonObject;

public interface C2SPacket extends Packet {

    JsonObject toJson();
}
