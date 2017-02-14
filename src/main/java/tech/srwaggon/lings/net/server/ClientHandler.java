package tech.srwaggon.lings.net.server;

import com.google.common.collect.Lists;
import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Queue;

import tech.srwaggon.lings.Game;
import tech.srwaggon.lings.entity.Agent;
import tech.srwaggon.lings.net.Connection;
import tech.srwaggon.lings.net.message.ActionMessage;
import tech.srwaggon.lings.net.message.FoodAppearedMessage;
import tech.srwaggon.lings.net.message.IdMessage;
import tech.srwaggon.lings.net.message.Message;
import tech.srwaggon.lings.net.message.action.AbstractActionMessage;

public class ClientHandler implements Runnable {

  private final Connection connection;
  private final Game game;

  private final Logger logger = LoggerFactory.getLogger(ClientHandler.class);
  private int id;

  public ClientHandler(Connection connection, Game game) throws IOException {
    this.connection = connection;
    this.game = game;
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
      id = game.getAgentManager().newAgent().getId();
      sendMap();
      sendEntities();
      sendId();
      while (connection.isConnected()) {
        handleAnyInput();
        Thread.sleep(10);
      }
    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
    logger.info("Client disconnected: " + connection.toString());
  }

  private void handleAnyInput() throws IOException {
    processMessages(readAllMessages());
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
        handleAction((ActionMessage) inProcess);
        game.getWorld().print();
      } else if (messageType.equals("map")) {
        sendMap();
      } else if (messageType.equals("id")) {
        sendId();
      }
    }
  }

  private void handleAction(ActionMessage actionMessage) throws IOException {
    actionMessage.getAction().perform(game);
  }

  private void sendMap() throws IOException {
    connection.sendJson(game.getWorld());
  }

  private void sendId() {
    connection.sendJson(new IdMessage(getId()));
  }

  private void sendEntities() throws IOException {
    for (Agent agent : game.getAgentManager().getAgents().values()) {
      connection.sendJson(agent);
    }
  }

  private int getId() {
    return id;
  }
}