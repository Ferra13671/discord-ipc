package com.ferra13671.discordipc;

import com.google.gson.JsonObject;

public record Packet(Opcode opcode, JsonObject data) {
}
