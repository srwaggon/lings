package tech.srwaggon.lings.game;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import tech.srwaggon.lings.entity.Agent;
import tech.srwaggon.lings.net.Connection;
import tech.srwaggon.lings.net.PlayerJoinedEvent;
import tech.srwaggon.lings.net.message.ActionMessage;
import tech.srwaggon.lings.net.message.IdMessage;

@Component
public class GameRunner implements Runnable {

  @Inject
  private Game game;

  @Inject
  private EventBus eventBus;

  @Inject
  private ExecutorService executorService;

  @PostConstruct
  public void init() {
    executorService.submit(this);
  }

  @Override
  public void run() {
    eventBus.register(this);
    game.getAgentManager().newAgent(0);
    game.addTimerTask(addFoodIntervalTask());
    game.addTimerTask(addPrintWorldIntervalTask());
    game.run();
  }

  @Subscribe
  public void handlePlayerJoined(PlayerJoinedEvent event) throws IOException {
    Connection connection = event.getConnection();
    int id = game.getAgentManager().newAgent().getId();
    sendMap(connection);
    sendEntities(connection);
    sendId(connection, id);
  }

  public void sendMap(Connection connection) throws IOException {
    connection.sendJson(game.getWorld());
  }

  public void sendId(Connection connection, int id) {
    connection.sendJson(new IdMessage(id));
  }

  public void sendEntities(Connection connection) throws IOException {
    for (Agent agent : game.getAgentManager().getAgents().values()) {
      connection.sendJson(agent);
    }
  }

  private TimerTask addPrintWorldIntervalTask() {
    return new TimerTask() {
      private long lastExecution = System.currentTimeMillis();
      @Override
      public void tick() {
        long now = System.currentTimeMillis();
        if (now - lastExecution > 3000) {
          System.out.print(game.currentTimeTicks() + "\t");
          game.getWorld().print();
          lastExecution = now;
        }
      }
    };
  }

  private TimerTask addFoodIntervalTask() {
    return new TimerTask() {
      private long lastExecution = System.currentTimeMillis();
      @Override
      public void tick() {
        long now = System.currentTimeMillis();
        if (now - lastExecution > 20000) {
          game.getWorld().getTile(getNum(), getNum()).addFood();
          lastExecution = now;
        }
      }

      private int getNum() {
        return (int) (Math.random() * game.getWorld().getNumColumns());
      }
    };
  }

  public void handleAction(ActionMessage actionMessage) throws IOException {
    actionMessage.getAction().perform(game);
  }
}
