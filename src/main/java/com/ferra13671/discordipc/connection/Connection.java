package com.ferra13671.discordipc.connection;

import com.ferra13671.discordipc.connection.impl.UnixConnection;
import com.ferra13671.discordipc.connection.impl.WinConnection;
import com.ferra13671.discordipc.connection.packet.C2SPacket;
import com.ferra13671.discordipc.connection.packet.S2CPacket;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.UUID;
import java.util.function.Consumer;

public abstract class Connection {
    private final static String[] UNIX_TEMP_PATHS = { "XDG_RUNTIME_DIR", "TMPDIR", "TMP", "TEMP" };

    public static Connection open(Consumer<S2CPacket> callback) {
        String os = System.getProperty("os.name").toLowerCase();

        // Windows
        if (os.contains("win")) {
            for (int i = 0; i < 10; i++) {
                try {
                    return new WinConnection("\\\\?\\pipe\\discord-ipc-" + i, callback);
                } catch (IOException ignored) {}
            }
        }
        // Unix
        else {
            String name = null;

            for (String tempPath : UNIX_TEMP_PATHS) {
                name = System.getenv(tempPath);
                if (name != null) break;
            }

            if (name == null) name = "/tmp";
            name += "/discord-ipc-";

            for (int i = 0; i < 10; i++) {
                try {
                    return new UnixConnection(name + i, callback);
                } catch (IOException ignored) {}
            }
        }

        return null;
    }

    public void write(C2SPacket packet) {
        JsonObject packetObject = packet.toJson();

        packetObject.addProperty("nonce", UUID.randomUUID().toString());

        byte[] d = packetObject.toString().getBytes();
        ByteBuffer packetBuf = ByteBuffer.allocate(d.length + 8);
        packetBuf.putInt(Integer.reverseBytes(packet.getOpcode().ordinal()));
        packetBuf.putInt(Integer.reverseBytes(d.length));
        packetBuf.put(d);

        packetBuf.rewind();
        write(packetBuf);
    }

    protected abstract void write(ByteBuffer buffer);

    public abstract void close();
}
