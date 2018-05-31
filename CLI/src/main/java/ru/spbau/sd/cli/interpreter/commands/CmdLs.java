package ru.spbau.sd.cli.interpreter.commands;

import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.ExecutionResult;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CmdLs implements Command {
    private final Environment environment;

    public CmdLs(Environment environment) {
        this.environment = environment;
    }

    @Override
    public ExecutionResult run(List<String> arguments, InputStream inputStream, OutputStream outputStream) {
        File dir;
        String name = "";
        if (arguments.size() <= 1) {
            dir = arguments.isEmpty() ? new File(environment.get("PWD")) : new File(arguments.get(0));
            name = arguments.isEmpty() ? environment.get("PWD") : arguments.get(0);
        } else {
            outputStream.write("Error during \'ls\' command occurred. Please, provide one or zero arguments.");
            return ExecutionResult.Error;
        }
        if (!dir.exists()) {
            outputStream.write("No such directory " + name);
            return ExecutionResult.OK;
        }
        File[] entries = dir.listFiles(file -> !file.isHidden());
        if (entries == null) {
            outputStream.write(name + "\n");
            return ExecutionResult.OK;
        }
        Arrays.sort(entries, Comparator.comparing(File::getName));
        for (File entry: entries) {
            outputStream.write(entry.getName() + "\n");
        }
        return ExecutionResult.OK;
    }
}