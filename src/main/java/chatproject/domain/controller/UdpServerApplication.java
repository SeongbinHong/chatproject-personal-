package chatproject.domain.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

@SpringBootApplication
@PropertySource("classpath:application-server.properties")
public class UdpServerApplication implements CommandLineRunner {

    private static final int PORT = 8084; // 서버 포트 설정

    public static void main(String[] args) {
        SpringApplication.run(UdpServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try (DatagramSocket serverSocket = new DatagramSocket(PORT)) {
            System.out.println("UDP 서버가 포트 " + PORT + "에서 수신 대기 중입니다.");

            byte[] receiveBuffer = new byte[1024]; // 수신 버퍼
            DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

            while (true) {
                // 클라이언트로부터 패킷을 수신
                serverSocket.receive(receivePacket);
                String receivedMessage = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("클라이언트로부터 받은 메시지: " + receivedMessage);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
