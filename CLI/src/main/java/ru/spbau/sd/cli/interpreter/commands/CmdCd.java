package ru.spbau.sd.cli.interpreter.commands;

import ru.spbau.sd.cli.interpreter.Environment;
import ru.spbau.sd.cli.interpreter.ExecutionResult;
import ru.spbau.sd.cli.interpreter.SimpleEnvironment;
import ru.spbau.sd.cli.interpreter.io.InputStream;
import ru.spbau.sd.cli.interpreter.io.OutputStream;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CmdCd implements Command {
    private final Environment environment;

    public CmdCd(Environment environment) {
        this.environment = environment;
    }

    @Override
    public ExecutionResult run(List<String> arguments, InputStream inputStream, OutputStream outputStream) {
        if (arguments.isEmpty()) {
            String home = System.getProperty("user.home");
            outputStream.write(home + "\n");
            environment.set("PWD", home);
            return ExecutionResult.OK;
        }
        if (arguments.size() != 1) {
            outputStream.write("Error during \'cd\' command occurred. Please, provide some arguments.");
            return ExecutionResult.Error;
        }
        String name = arguments.get(0);
        File file = new File(name);
        if (!file.isAbsolute()) {
            file = new File(environment.get("PWD") + File.separator + name);
        }
        if (!file.exists() || !file.isDirectory()) {
            outputStream.write("No such directory " + name);
            return ExecutionResult.Error;
        }
        try {
            environment.set("PWD", file.getCanonicalPath());
            outputStream.write(file.getCanonicalPath() + "\n");
            return ExecutionResult.OK;
        } catch (IOException e) {
            outputStream.write(e.getMessage());
            return ExecutionResult.Error;
        }
    }

}
