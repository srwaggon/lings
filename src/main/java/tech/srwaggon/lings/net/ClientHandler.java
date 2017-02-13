package tech.srwaggon.lings.net;

import com.google.common.eventbus.Subscribe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import tech.srwaggon.lings.Game;
import tech.srwaggon.lings.entity.Agent;
import tech.srwaggon.lings.net.message.ActionMessage;
import tech.srwaggon.lings.net.message.FoodAppearedMessage;
import tech.srwaggon.lings.net.message.Message;
import tech.srwaggon.lings.net.message.action.AbstractActionMessage;
import tech.srwaggon.lings.net.message.action.MoveMessage;

public class ClientHandler implements Runnable {

  private final Connection connection;
  private final Game game;

  private final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

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
      sendMap();
      sendEntities();
      connection.sendJson(new ActionMessage(new MoveMessage(game.getAgents().get(0))));
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
    if (connection.hasLine()) {
      Message message = connection.readJson(Message.class);

      String messageType = message.getType();
      if (messageType.equals("action")) {
        handleAction();
      } else if (messageType.equals("map")) {
        sendMap();
      }
      game.getWorld().print();
    }
  }

  private void handleAction() throws IOException {
    ActionMessage actionMessage = connection.readJson(ActionMessage.class);
    actionMessage.getAction().perform(game);
  }

  private void sendMap() throws IOException {
    connection.sendJson(game.getWorld());
  }

  private void sendEntities() throws IOException {
    for (Agent agent : game.getAgentManager().getAgents().values()) {
      connection.sendJson(agent);
    }
  }
}