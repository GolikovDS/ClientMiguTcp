package ru.artsok;


import java.io.*;
import java.net.URISyntaxException;

public class TestApp {

    public static void main(String[] args) throws IOException, URISyntaxException {

    }

    public String pathhh(){
        return getClass().getResource("resources/properties/panel_size.properties").toString();
    }

    private String loadText() {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream fileInputStream = (FileInputStream) getClass().getResourceAsStream("resources/properties/panel_size.properties");
            InputStream is = getClass().getResourceAsStream("resources/properties/panel_size.properties");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "Cp1251"));
            while (true) {
                String line = br.readLine();
                if (line == null)
                    break;
                sb.append(line).append("\n");
            }
        } catch (IOException ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            pw.flush();
            pw.close();
            sb.append("Error while loading text: ").append("\n\n");
            sb.append(sw.getBuffer().toString());
        }
        return sb.toString();
    }
}
//P:\Разное\Программирование\Spring\ClientMiguTcp.V2\src\ru\artsok\resources\properties\panel_size.properties