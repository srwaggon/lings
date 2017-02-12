package tech.srwaggon.lings.net;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import tech.srwaggon.lings.Game;

@Component
public class GameServer {

  private ExecutorService executorService = Executors.newFixedThreadPool(4);
  @Inject
  private Game game;
  @Inject
  private ServerSocket serverSocket;

  public void runOnline() {
    try {
      while (true) {
        System.out.println("Waiting for connection...");
        handleConnection(serverSocket.accept());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void handleConnection(Socket socket) throws IOException {
    System.out.println("Client connected: " + socket.getInetAddress().getHostName());
    executorService.submit(new ClientHandler(socket, game));
  }
}
