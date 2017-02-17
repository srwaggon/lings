package tech.srwaggon.lings.net.server;

import com.google.common.eventbus.EventBus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import tech.srwaggon.lings.game.GameRunner;
import tech.srwaggon.lings.net.Connection;
import tech.srwaggon.lings.net.PlayerJoinedEvent;

@Component
public class GameServer implements Runnable {

  @Inject
  private ExecutorService executorService;
  @Inject
  private EventBus eventBus;
  @Inject
  private GameRunner gameRunner;
  @Inject
  private ServerSocket serverSocket;

  private Logger logger = LoggerFactory.getLogger(GameServer.class);

  @PostConstruct
  public void init() {
    executorService.submit(this);
  }

  public void run() {
    try {
      while (true) {
        logger.info("Waiting for connection...");
        Socket accept = serverSocket.accept();
        handleConnection(accept);
        Thread.sleep(100);
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void handleConnection(Socket socket) throws IOException {
    Connection connection = new Connection(socket);
    logger.info("Client connected: " + connection.toString());
    ClientHandler clientHandler = new ClientHandler(connection, gameRunner);
    eventBus.post(new PlayerJoinedEvent(connection));
    eventBus.register(clientHandler);
    executorService.submit(clientHandler);
  }
}
