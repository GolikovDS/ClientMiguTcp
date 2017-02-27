package ru.artsok;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class TestApp {

    public static void main(String[] args) throws IOException, URISyntaxException {
//        Properties properties = new Properties();
        try {
//            properties.load(TestApp.class.getResourceAsStream("resources/properties/panel_size.properties"));
//            System.out.println(properties.getProperty("btnRs"));
//            System.out.println(properties.getProperty("ddd"));
            System.out.println("12");
            File file = new File("P:\\Разное\\Программирование\\Spring\\ClientMiguTcp.V2\\src\\ru\\artsok\\tester.txt");
            System.out.println(file.toString());
            if(file.createNewFile())
                System.out.println("Ok");
//            properties.setProperty("ddd", String.valueOf(System.nanoTime()));
//            properties.store(new FileOutputStream("src/ru/artsok/resources/properties/panel_size.properties"), null);
//            properties.store(new FileOutputStream(TestApp.class.getResourceAsStream("resources/properties/panel_size.properties").toString()), null);

//            System.out.println(Main.class.getResource("resources/properties/panel_size.properties").getFile());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
//P:\Разное\Программирование\Spring\ClientMiguTcp.V2\src\ru\artsok\resources\properties\panel_size.properties