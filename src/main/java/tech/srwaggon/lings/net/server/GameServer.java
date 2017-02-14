package tech.srwaggon.lings.net.server;

import com.google.common.eventbus.EventBus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import tech.srwaggon.lings.Game;
import tech.srwaggon.lings.net.Connection;

@Component
public class GameServer {

  @Inject
  private ExecutorService executorService;
  @Inject
  private EventBus eventBus;
  @Inject
  private Game game;
  @Inject
  private ServerSocket serverSocket;

  private Logger logger = LoggerFactory.getLogger(GameServer.class);

  public void runOnline() {
    addFoodEvery5sec();

    game.getAgentManager().newAgent(0);
    try {
      while (true) {
        logger.info("Waiting for connection...");
        Socket accept = serverSocket.accept();
        handleConnection(accept);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void addFoodEvery5sec() {
    executorService.submit(() -> {
      while (true) {
        game.getWorld().getTile(getNum(), getNum()).addFood();
        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
  }

  private void handleConnection(Socket socket) throws IOException {
    logger.info("Client connected: " + socket.getInetAddress().getHostName());
    ClientHandler clientHandler = new ClientHandler(new Connection(socket), game);
    eventBus.register(clientHandler);
    executorService.submit(clientHandler);
  }

  private int getNum() {
    return (int) (Math.random() * game.getWorld().getNumColumns());
  }
}
