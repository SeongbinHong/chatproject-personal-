package chatproject.domain.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

@SpringBootApplication
@PropertySource("classpath:application-client.properties")
public class TcpClientApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TcpClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try (Socket socket = new Socket("localhost", 8888);
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("서버에 연결되었습니다. 메시지를 입력하세요:");
            while (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                out.println(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}