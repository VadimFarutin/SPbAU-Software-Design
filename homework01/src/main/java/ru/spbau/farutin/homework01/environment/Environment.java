package ru.spbau.farutin.homework01.environment;

import java.util.List;
import java.util.Optional;

/**
 * Environment stores and provides access to variable values.
 */
public interface Environment {
    /**
     * Sets new value for given key.
     * @param key key to update value for
     * @param value new value
     */
    void set(String key, String value);

    /**
     * Getter for variable value.
     * @param key variable name
     * @return variable value
     */
    Optional<String> get(String key);

    /**
     * Getter for all stored variables.
     * @return list with all environment entries
     */
    List<Environment.Entry> entryList();

    /**
     * Entry represents stored key-value pair.
     */
    interface Entry {
        String getKey();
        String getValue();
    }
}
