package tech.srwaggon.lings.net;

import com.google.common.eventbus.Subscribe;

import java.io.IOException;

import tech.srwaggon.lings.Game;
import tech.srwaggon.lings.entity.Agent;
import tech.srwaggon.lings.net.message.MoveMessage;

public class ClientHandler implements Runnable {

  private final Connection connection;
  private final Game game;

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
    System.out.println("Connection Died");
  }

  private void handleAnyInput() throws IOException {
    if (connection.hasLine()) {
      handleMessage(connection.readLine());
    }
  }

  private void handleMessage(String msg) throws IOException {
    System.out.println("Handling: " + msg);
    moveAgent(msg);
  }

  private void moveAgent(String msg) {
    MoveMessage moveMessage = MoveMessage.parse(msg);
    Agent agent = game.getAgents().get(moveMessage.getId());
    agent.move(moveMessage.getX(), moveMessage.getY());
  }

  private void sendId() {
    connection.send("0");
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
