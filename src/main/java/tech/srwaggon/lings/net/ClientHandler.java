package tech.srwaggon.lings.net;

import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import tech.srwaggon.lings.Game;
import tech.srwaggon.lings.entity.Agent;
import tech.srwaggon.lings.net.message.MoveMessage;

public class ClientHandler implements Runnable {

  private final Connection connection;
  private final Game game;

  private final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

  public ClientHandler(Connection connection, Game game) throws IOException {
    this.connection = connection;
    this.game = game;
  }

  @Subscribe
  public void handleMove(MoveMessage moveMessage) {
    connection.send(moveMessage.convert());
  }

  @Override
  public void run() {
    try {
      sendId();
      sendMap();
      sendEntities();
      while (true) {
        handleAnyInput();
        Thread.sleep(500);
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    logger.info("Connection Died");
  }

  private void handleAnyInput() throws IOException {
    if (connection.hasLine()) {
      handleMessage(connection.readLine());
    }
  }

  private void handleMessage(String msg) throws IOException {
    logger.debug("Handling: " + msg);
    moveAgent(msg);
  }

  private void moveAgent(String msg) {
    MoveMessage moveMessage = MoveMessage.parse(msg);
    Agent agent = game.getAgents().get(moveMessage.getId());
    agent.move(moveMessage.getX(), moveMessage.getY());
  }

  private void sendId() {
    connection.send("" + Agent.getIds());
  }

  private void sendMap() throws IOException {
    connection.send(game.world().getString());
  }

  private void sendEntities() {
    for (Agent agent : game.getAgents().values()) {
      connection.send(agent.getString());
    }
  }
}