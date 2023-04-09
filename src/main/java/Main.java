import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {

        //получаем категории из файла TSV
        TSVtoCateg tsVtoJSON = new TSVtoCateg();
        File file = new File("purchase.tsv");
        ArrayList<String> category = tsVtoJSON.convert(file);

        ArrayList<String> list = tsVtoJSON.getFields();
        //заполняем json категориями
        JSONObject fileCat = new JSONObject();
        JSONObject other = new JSONObject();
        other.put("category", "другое");
        other.put("sum", 0);
        fileCat.put("другое", other);
        for (String el : category) {
            JSONObject objCategory = new JSONObject();
            objCategory.put("category", el);
            objCategory.put("sum", 0);
            fileCat.put(el, objCategory);
        }

        //загрузить data.json при наличии
        File fileJson = new File("data.json");
        if (fileJson.exists()) {
            try (InputStream inputStream = new FileInputStream(fileJson)) {
                JSONParser parser = new JSONParser();
                //JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(inputStream));
            }
        } else {
            //создание Json файла
            try {
                fileJson.createNewFile();
                FileOutputStream outputStream = new FileOutputStream(fileJson);
                outputStream.write(fileCat.toString().getBytes());
                outputStream.close();
                System.out.println("JSON файл успешно создан!");
            } catch (IOException ex) {
                System.out.println("Ошибка при создании файла.");
            }
        }

        try (ServerSocket serverSocket = new ServerSocket(8989);) { // стартуем сервер один(!) раз
            while (true) { // в цикле(!) принимаем подключения
                try (Socket socket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter out = new PrintWriter(socket.getOutputStream());) {

                    JSONParser parser = new JSONParser();
                    String line = in.readLine();
                    JSONObject readJson = (JSONObject) parser.parse(line);

                    MaxPurchace max = new MaxPurchace();
                    System.out.println(max.calcMax(readJson, list));
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
