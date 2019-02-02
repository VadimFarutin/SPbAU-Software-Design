package ru.spbau.farutin.homework01.environment;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EnvironmentImpl implements Environment {
    private HashMap<String, String> hashMap = new HashMap<>();

    @Override
    public void set(@NotNull String key, @NotNull String value) {
        hashMap.put(key, value);
    }

    @Override
    public @NotNull Optional<String> get(@NotNull String key) {
        String value = hashMap.get(key);
        return Optional.ofNullable(value);
    }

    @Override
    public @NotNull List<Environment.Entry> entryList() {
        List<Environment.Entry> entryList = new LinkedList<>();
        Set<Map.Entry<String, String>> entrySet = hashMap.entrySet();

        for (Map.Entry<String, String> mapEntry : entrySet) {
            Entry entry = new Entry(mapEntry.getKey(), mapEntry.getValue());
            entryList.add(entry);
        }

        return entryList;
    }

    class Entry implements Environment.Entry {
        private String key;
        private String value;

        Entry(@NotNull String key, @NotNull String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public @NotNull String getKey() {
            return key;
        }

        @Override
        public @NotNull String getValue() {
            return value;
        }
    }
}
