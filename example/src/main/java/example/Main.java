package example;

import com.ferra13671.discordipc.AvatarType;
import com.ferra13671.discordipc.UserAvatar;
import com.ferra13671.discordipc.activity.Button;
import com.ferra13671.discordipc.DiscordIPC;
import com.ferra13671.discordipc.activity.RichPresence;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UserAvatar userAvatar = DiscordIPC.getUser().getAvatarImage();
        if (userAvatar != null) {
            Path path = Paths.get("Avatar." + (userAvatar.avatarType() == AvatarType.Image ? "png" : "gif"));
            try (OutputStream outputStream = Files.newOutputStream(path)) {
                outputStream.write(userAvatar.inputStream().readAllBytes());
                userAvatar.inputStream().close();
                System.out.printf("Avatar saved in '%s'", path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

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
