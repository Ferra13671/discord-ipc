package com.ferra13671.discordipc;

import com.ferra13671.discordipc.connection.Connection;
import com.google.gson.JsonObject;

import java.lang.management.ManagementFactory;
import java.util.function.BiConsumer;

public class DiscordIPC {

    private static Connection connection;
    private static Runnable onReady;
    private static BiConsumer<Integer, String> onError;

    private static RichPresence richPresence;
    private static boolean dispatch = false;

    private static IPCUser user;

    public static boolean start(long appId, Runnable onReady) {
        return start(appId, onReady, (code, message) -> System.err.println("Discord IPC error " + code + " with message: " + message));
    }

    public static boolean start(long appId, Runnable onReady, BiConsumer<Integer, String> onError) {
        // Open connection
        connection = Connection.open(DiscordIPC::onPacket);
        if (connection == null)
            return false;

        DiscordIPC.onReady = onReady;
        DiscordIPC.onError = onError;


        // Handshake
        JsonObject o = new JsonObject();
        o.addProperty("v", 1);
        o.addProperty("client_id", Long.toString(appId));
        connection.write(Opcode.Handshake, o);

        return true;
    }

    public static boolean isConnected() {
        return connection != null;
    }

    public static boolean isDispatch() {
        return dispatch;
    }

    public static IPCUser getUser() {
        return user;
    }

    public static void setRichPresence(RichPresence presence) {
        richPresence = presence;
        richPresence.trySendActivity();
    }

    public static void stop() {
        if (connection != null) {
            connection.close();

            dispatch = false;
            connection = null;
            onReady = null;
            user = null;
        }
    }

    public static void updateActivity() {
        if (richPresence != null && isDispatch()) {
            JsonObject args = new JsonObject();
            args.addProperty("pid", getPID());
            args.add("activity", richPresence.getActivityObject());

            JsonObject o = new JsonObject();
            o.addProperty("cmd", "SET_ACTIVITY");
            o.add("args", args);

            connection.write(Opcode.Frame, o);
        }
    }

    private static void onPacket(Packet packet) {
        // Close
        if (packet.opcode() == Opcode.Close) {
            if (onError != null) onError.accept(packet.data().get("code").getAsInt(), packet.data().get("message").getAsString());
            stop();
        }
        // Frame
        else if (packet.opcode() == Opcode.Frame) {
            // Error
            if (packet.data().has("evt") && packet.data().get("evt").getAsString().equals("ERROR")) {
                JsonObject d = packet.data().getAsJsonObject("data");
                if (onError != null)
                    onError.accept(d.get("code").getAsInt(), d.get("message").getAsString());
            }
            // Dispatch
            else if (packet.data().has("cmd") && packet.data().get("cmd").getAsString().equals("DISPATCH")) {
                dispatch = true;
                user = IPCUser.fromJson(packet.data().getAsJsonObject("data").getAsJsonObject("user"));

                if (onReady != null)
                    onReady.run();

                richPresence.trySendActivity();
            }
        }
    }

    private static int getPID() {
        String pr = ManagementFactory.getRuntimeMXBean().getName();
        return Integer.parseInt(pr.substring(0, pr.indexOf('@')));
    }
}
