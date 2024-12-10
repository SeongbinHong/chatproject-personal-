package chatproject.domain.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

@SpringBootApplication
@PropertySource("classpath:application-client.properties")
public class UdpClientApplication implements CommandLineRunner {

    private static final int SERVER_PORT = 8084; // 서버가 수신 대기 중인 포트

    public static void main(String[] args) {
        SpringApplication.run(UdpClientApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try (DatagramSocket clientSocket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            InetAddress serverAddress = InetAddress.getByName("localhost"); // 서버 주소 설정

            System.out.println("UDP 서버로 보낼 메시지를 입력하세요:");
            while (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                byte[] sendBuffer = message.getBytes();

                // 서버로 패킷 전송
                DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, serverAddress, SERVER_PORT);
                clientSocket.send(sendPacket);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
