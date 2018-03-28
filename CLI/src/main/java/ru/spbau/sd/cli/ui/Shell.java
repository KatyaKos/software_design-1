package ru.spbau.sd.cli.ui;

import ru.spbau.sd.cli.interpreter.InterpreterSession;

import java.util.Scanner;

public class Shell {
    private static final String PROMPT = ">";

    public static void main(String[] args) {
        InterpreterSession session = new InterpreterSession();
        Scanner inputScanner = new Scanner(System.in);
        while (!session.isTerminated()) {
            System.out.print(PROMPT);
            String cmd = inputScanner.nextLine();
            String output = session.runCommand(cmd);
            if (output != null) {
                System.out.println(output);
            }
        }
    }
}