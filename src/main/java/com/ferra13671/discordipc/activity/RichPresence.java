package com.ferra13671.discordipc.activity;

import com.ferra13671.discordipc.DiscordIPC;
import com.ferra13671.discordipc.OnChangeHolder;

import java.util.function.Function;

/**
 * An object that controls the change of activity information.
 */
public class RichPresence {
    private final OnChangeHolder<ActivityInfo> activityInfo = new OnChangeHolder<>(
            new ActivityInfo(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    System.currentTimeMillis() / 1000,
                    null
            ),
            info -> DiscordIPC.updateActivity()
    );

    /**
     * Invokes update activity information.
     *
     * @param updateConsumer the action called when activity information is updated.
     */
    public void update(Function<ActivityInfo, ActivityInfo> updateConsumer) {
        this.activityInfo.setValue(updateConsumer.apply(this.activityInfo.getValue()));
    }

    /**
     * Returns activity information for this RichPresence.
     *
     * @return activity information for this RichPresence.
     */
    public ActivityInfo getActivityInfo() {
        return this.activityInfo.getValue();
    }
}
