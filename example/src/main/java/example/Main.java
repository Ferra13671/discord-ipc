package example;

import com.ferra13671.discordipc.activity.Button;
import com.ferra13671.discordipc.DiscordIPC;
import com.ferra13671.discordipc.activity.RichPresence;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Discord IPC");

        //Starting discord ipc
        if (!DiscordIPC.start(932987954815696957L, () -> System.out.println("Logged in account: " + DiscordIPC.getUser().username()))) {
            System.out.println("Failed to start Discord IPC");
            return;
        }

        //Setup activity
        RichPresence presence = new RichPresence();
        presence.update(activityInfo ->
                activityInfo
                        .setDetails("Monkey!!!")
                        .setState("ABC")
                        .setLargeImage("a")
                        .setLargeText("Large image")
                        .setSmallImage("b")
                        .setSmallText("Small Image")
                        //.setParty(new Party("party", 1, 4))
                        .setButtons(new Button("Nah", "https://www.youtube.com/watch?v=dQw4w9WgXcQ"))
        );
        DiscordIPC.setRichPresence(presence);

        //Sleep
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //Stopping discord ipc
        System.out.println("Stopping Discord IPC");
        DiscordIPC.stop();
    }
}
