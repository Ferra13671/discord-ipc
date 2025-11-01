package com.ferra13671.discordipc;

import java.util.function.Consumer;

public class OnChangeHolder<T> {
    private T value;
    private final Consumer<T> onChangeConsumer;

    public OnChangeHolder(Consumer<T> onChangeConsumer) {
        this(null, onChangeConsumer);
    }

    public OnChangeHolder(T value, Consumer<T> onChangeConsumer) {
        this.value = value;
        this.onChangeConsumer = onChangeConsumer;
    }

    public void setValue(T value) {
        if (this.value != value && !this.value.equals(value)) {
            this.value = value;
            this.onChangeConsumer.accept(value);
        }
    }

    public T getValue() {
        return this.value;
    }
}
