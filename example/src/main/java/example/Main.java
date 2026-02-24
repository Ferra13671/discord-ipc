package example;

import com.ferra13671.discordipc.AvatarType;
import com.ferra13671.discordipc.UserAvatar;
import com.ferra13671.discordipc.activity.Button;
import com.ferra13671.discordipc.DiscordIPC;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Discord IPC");

        DiscordIPC discordIPC = new DiscordIPC();
        discordIPC.setOnReady(() -> System.out.println("Logged in account: " + discordIPC.getUser().username()));

        //Starting discord ipc
        if (!discordIPC.start(932987954815696957L)) {
            System.out.println("Failed to start Discord IPC");
            return;
        }

        //Setup activity
        discordIPC.updateActivity(activityInfo ->
                activityInfo
                        .withDetails("Monkey!!!")
                        .withState("ABC")
                        .withLargeImage("a")
                        .withLargeText("Large image")
                        .withSmallImage("b")
                        .withSmallText("Small Image")
                        //.withParty(new Party("party", 1, 4))
                        .withButton1(new Button("Nah", "https://www.youtube.com/watch?v=dQw4w9WgXcQ"))
        );

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        UserAvatar userAvatar = discordIPC.getUser().getAvatarImage();
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
        discordIPC.stop();
    }
}
