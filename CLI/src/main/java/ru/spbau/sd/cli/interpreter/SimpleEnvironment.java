package ru.spbau.sd.cli.interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of the Environment interface based on a hash table.
 */
public class SimpleEnvironment implements Environment {
    private Map<String, String> vars = new HashMap<>();

    public SimpleEnvironment() {
        vars = new HashMap<>();
        vars.put("HOME", System.getProperty("user.home"));
        vars.put("PWD", System.getProperty("user.dir"));
    }

    public void set(String name, String val) {
        vars.put(name, val);
    }

    public String get(String name) {
        return vars.getOrDefault(name, "");
    }
}
