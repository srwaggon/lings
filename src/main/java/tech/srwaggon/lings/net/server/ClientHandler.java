package tech.srwaggon.lings.net.server;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Queue;

import tech.srwaggon.lings.game.Game;
import tech.srwaggon.lings.game.GameRunner;
import tech.srwaggon.lings.net.Connection;
import tech.srwaggon.lings.net.message.ActionMessage;
import tech.srwaggon.lings.net.message.FoodAppearedMessage;
import tech.srwaggon.lings.net.message.Message;
import tech.srwaggon.lings.net.message.action.AbstractActionMessage;

public class ClientHandler implements Runnable {

  private final Connection connection;
  private final GameRunner gameRunner;

  private final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

  public ClientHandler(Connection connection, GameRunner gameRunner) throws IOException {
    this.connection = connection;
    this.gameRunner = gameRunner;
  }

  @Subscribe
  public void handleAction(AbstractActionMessage actionMessage) throws IOException {
    connection.sendJson(new ActionMessage(actionMessage));
  }

  @Subscribe
  public void handleFood(FoodAppearedMessage message) {
    connection.sendJson(message);
  }

  @Override
  public void run() {
    try {
      while (connection.isConnected()) {
        processMessages(readAllMessages());
        Thread.sleep(10);
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    logger.info("Client disconnected: " + connection.toString());
  }

  private Queue<Message> readAllMessages() throws IOException {
    Queue<Message> messages = Lists.newLinkedList();
    if (connection.hasLine()) {
      messages.add(connection.readJson(Message.class));
    }
    return messages;
  }

  private void processMessages(Queue<Message> messages) throws IOException {
    Message inProcess;
    while ((inProcess = messages.poll()) != null) {
      String messageType = inProcess.getType();
      if (messageType.equals("action")) {
        gameRunner.handleAction((ActionMessage) inProcess);
      } else if (messageType.equals("map")) {
        gameRunner.sendMap(connection);
      } else if (messageType.equals("id")) {
        gameRunner.sendId(connection, 0);
      }
    }
  }
}