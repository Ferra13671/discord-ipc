package com.ferra13671.discordipc;

import com.ferra13671.discordipc.activity.RichPresence;
import com.ferra13671.discordipc.connection.Connection;
import com.ferra13671.discordipc.connection.packet.S2CPacket;
import com.ferra13671.discordipc.connection.packet.impl.c2s.HandsnakePacket;
import com.ferra13671.discordipc.connection.packet.impl.c2s.SetActivityPacket;
import com.ferra13671.discordipc.connection.packet.impl.s2c.CloseConnectionPacket;
import com.ferra13671.discordipc.connection.packet.impl.s2c.DispatchPacket;
import com.ferra13671.discordipc.connection.packet.impl.s2c.ErrorPacket;
import lombok.Getter;

import java.lang.management.ManagementFactory;
import java.util.function.BiConsumer;

/**
 * Discord IPC Main Class
 */
public class DiscordIPC {
    private static Connection connection;
    private static Runnable onReady;
    private static BiConsumer<Integer, String> onError;

    private static RichPresence richPresence;
    /**
     * Whether it is currently possible to send activity information or not.
     */
    @Getter
    private static boolean dispatch = false;

    /**
     * -- GETTER --
     *  Returns information about the Discord account or null if there is no connection to Discord at the moment.
     */
    @Getter
    private static IPCUser user;

    /**
     * Starts an RPC with the specified application ID.
     *
     * @param appId application ID.
     * @return whether a connection was created with the local Discord application or not.
     */
    public static boolean start(long appId) {
        return start(appId, () -> {});
    }

    /**
     * Starts an RPC with the specified application ID and action to call when ready.
     *
     * @param appId application ID.
     * @param onReady action to call when ready.
     * @return whether a connection was created with the local Discord application or not.
     */
    public static boolean start(long appId, Runnable onReady) {
        return start(appId, onReady, (code, message) -> System.err.println("Discord IPC error " + code + " with message: " + message));
    }

    /**
     * Starts an RPC with the specified application ID, action to call when ready and the action called in case of an error..
     *
     * @param appId application ID.
     * @param onReady action to call when ready.
     * @param onError error action.
     * @return whether a connection was created with the local Discord application or not.
     */
    public static boolean start(long appId, Runnable onReady, BiConsumer<Integer, String> onError) {
        connection = Connection.open(DiscordIPC::onPacket);
        if (connection == null)
            return false;

        DiscordIPC.onReady = onReady;
        DiscordIPC.onError = onError;

        connection.write(new HandsnakePacket(appId, 1));

        return true;
    }

    /**
     * Returns whether the connection to the local discord is valid or not.
     *
     * @return whether the connection to the local discord is valid or not.
     */
    public static boolean isConnected() {
        return connection != null;
    }

    /**
     * Sets RichPresence for Discord IPC.
     *
     * @param presence RichPresence.
     */
    public static void setRichPresence(RichPresence presence) {
        richPresence = presence;
        updateActivity();
    }

    /**
     * Stops RPC.
     */
    public static void stop() {
        if (connection != null) {
            connection.close();

            dispatch = false;
            connection = null;
            onReady = null;
            user = null;
        }
    }

    /**
     * Sends activity information to the local Discord.
     * Called automatically when activity information changes.
     */
    public static void updateActivity() {
        if (richPresence != null && isDispatch())
            connection.write(new SetActivityPacket(getPID(), richPresence.getActivityInfo()));
    }

    private static void onPacket(S2CPacket p) {
        if (p instanceof CloseConnectionPacket packet)
            onCloseConnection(packet);

        if (p instanceof ErrorPacket packet)
            onErrorPacket(packet);

        if (p instanceof DispatchPacket packet)
            onDispatch(packet);
    }

    private static void onCloseConnection(CloseConnectionPacket packet) {
        if (onError != null)
            onError.accept(packet.code(), packet.message());

        stop();
    }

    private static void onErrorPacket(ErrorPacket packet) {
        if (onError != null)
            onError.accept(packet.code(), packet.message());
    }

    private static void onDispatch(DispatchPacket packet) {
        dispatch = true;
        user = packet.discordUser();

        if (onReady != null)
            onReady.run();

        updateActivity();
    }

    /**
     * Returns the PID of the program.
     *
     * @return PID of the program.
     */
    private static int getPID() {
        String pr = ManagementFactory.getRuntimeMXBean().getName();
        return Integer.parseInt(pr.substring(0, pr.indexOf('@')));
    }
}
