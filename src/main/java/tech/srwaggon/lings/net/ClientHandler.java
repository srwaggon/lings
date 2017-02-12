package tech.srwaggon.lings.net;

import java.io.IOException;
import java.net.Socket;

import tech.srwaggon.lings.Game;
import tech.srwaggon.lings.entity.Agent;

public class ClientHandler implements Runnable {

  private final Connection connection;
  private final Game game;

  public ClientHandler(Socket socket, Game game) throws IOException {
    this.connection = new Connection(socket);
    this.game = game;
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
    System.out.println(msg);
    moveAgent(msg);
    sendMap();
  }

  private void moveAgent(String msg) {
    String[] coords = msg.split(" ");
    int id = Integer.parseInt(coords[0]);
    int x = Integer.parseInt(coords[1]);
    int y = Integer.parseInt(coords[2]);

    Agent agent = game.getAgents().get(id);

    agent.move(x, y);
  }

  private void sendId() {
    connection.send("0");
  }

  private void sendMap() throws IOException {
    connection.send(game.getWorld().getString());
  }

  private void sendEntities() {
    for (Agent agent : game.getAgents()) {
      connection.send(agent.getString());
    }
  }
}
