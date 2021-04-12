package kr.ac.ggu.pdfreader.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Cmd {
    public String execCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec("cmd.exe /c " + command);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = null;
            StringBuilder readBuffer = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                readBuffer.append(line);
                readBuffer.append("\n");
            }

            return readBuffer.toString();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }

    private static class LazyHolder {
        public static final Cmd INSTANCE = new Cmd();
    }

    public static Cmd getInstance() {
        return Cmd.LazyHolder.INSTANCE;
    }
}