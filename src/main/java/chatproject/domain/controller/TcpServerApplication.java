package chatproject.domain.controller;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

@SpringBootApplication
@PropertySource("classpath:application-server.properties")
public class TcpServerApplication implements CommandLineRunner {

    private static final int PORT = 8888;
    private static final int TIMEOUT = 30000; // 30초

    public static void main(String[] args) {
        SpringApplication.run(TcpServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        while (true) { // 서버를 계속 실행
            try (ServerSocket serverSocket = new ServerSocket(PORT)) {
                serverSocket.setSoTimeout(TIMEOUT);
                System.out.println("서버가 " + PORT + " 포트에서 " + (TIMEOUT / 1000) + " 초의 타임아웃으로 대기 중입니다.");

                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("새로운 클라이언트가 연결되었습니다");

                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String clientInput;
                    while ((clientInput = input.readLine()) != null) {
                        System.out.println("클라이언트로부터 수신: " + clientInput);
                    }
                } catch (java.net.SocketTimeoutException e) {
                    // 오류를 출력하지 않고 클라이언트를 계속 기다림
                }
            } catch (Exception e) {
                System.out.println("서버 실행 중 오류 발생: " + e.getMessage());
            }
        }
    }
}
