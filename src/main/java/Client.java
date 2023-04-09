

import org.json.simple.JSONObject;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws InterruptedException, IOException {

        // запускаем подключение сокета по известным координатам и нициализируем приём сообщений с консоли клиента
        try (Socket socket = new Socket("localhost", 8989);
             BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             DataOutputStream oos = new DataOutputStream(socket.getOutputStream());
             DataInputStream ois = new DataInputStream(socket.getInputStream())) {

            System.out.println("Client connected to socket.");
            JSONObject sender = new JSONObject();

            Scanner scanner = new Scanner(System.in); //заполнение отправки json данных
            System.out.println("Введите наименование покупки:");
            String purchase = scanner.nextLine();
            LocalDate date = LocalDate.now();
            System.out.println("Введите траты на эту покупку:");
            Long cost = scanner.nextLong();
            sender.put("date", date.toString());
            sender.put("cost", cost);
            sender.put("title", purchase);
            oos.write(sender.toString().getBytes());
        }
    }
}