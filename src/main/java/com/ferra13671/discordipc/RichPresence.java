package com.ferra13671.discordipc;

import com.google.gson.JsonObject;

import java.util.function.Function;

public class RichPresence {
    private JsonObject activityObject = null;
    private final OnChangeHolder<ActivityInfo> activityInfo = new OnChangeHolder<>(new ActivityInfo(null, null, null, null, null, null, System.currentTimeMillis()),
            info -> {
                this.activityObject = info.toJson();
                trySendActivity();
            }
    );

    public void update(Function<ActivityInfo, ActivityInfo> updateConsumer) {
        this.activityInfo.setValue(updateConsumer.apply(this.activityInfo.getValue()));
    }

    public void trySendActivity() {
        if (this.activityObject != null)
            DiscordIPC.updateActivity();
    }

    public JsonObject getActivityObject() {
        return activityObject;
    }
}
