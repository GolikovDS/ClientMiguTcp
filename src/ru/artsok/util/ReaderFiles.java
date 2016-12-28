package ru.artsok.util;


import java.io.*;

public class ReaderFiles {
    public String read(String path) {
        StringBuilder sb = new StringBuilder();

        try {
            try (BufferedReader in = new BufferedReader(new FileReader(new File(path).getAbsoluteFile()))) {
                String s;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                    sb.append("\n");
                }
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
