package example;

import com.ferra13671.discordipc.DiscordIPC;
import com.ferra13671.discordipc.RichPresence;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Discord IPC");

        if (!DiscordIPC.start(932987954815696957L, () -> System.out.println("Logged in account: " + DiscordIPC.getUser().username()))) {
            System.out.println("Failed to start Discord IPC");
            return;
        }

        RichPresence presence = new RichPresence();
        presence.update(activityInfo ->
                activityInfo
                        .setDetails("Monkey!!!")
                        .setState("ABC")
                        .setLargeImage("a")
                        .setLargeText("Large image")
                        .setSmallImage("b")
                        .setSmallText("Small Image")
        );
        DiscordIPC.setRichPresence(presence);

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Stopping Discord IPC");
        DiscordIPC.stop();
    }
}
