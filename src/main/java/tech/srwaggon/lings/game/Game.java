package tech.srwaggon.lings.game;

import com.google.common.collect.Lists;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

import javax.inject.Inject;

import lombok.Data;
import tech.srwaggon.lings.entity.Agent;
import tech.srwaggon.lings.entity.AgentManager;
import tech.srwaggon.lings.world.World;
import tech.srwaggon.lings.world.WorldMap;

@Component
@Data
public class Game implements Runnable {

  public static final int MS_PER_SECOND = 1000;
  public static final int TICKS_PER_SECOND = 30;
  private final long startTime = System.currentTimeMillis();
  private long now;

  @Inject
  private AgentManager agentManager;

  private final World world = new World();

  private Collection<TimerTask> timerTasks = Lists.newLinkedList();

  public Map<Integer, Agent> getAgents() {
    return agentManager.getAgents();
  }

  @Override
  public void run() {
    long lastTime = System.currentTimeMillis();
    double unprocessed = 0;
    while (true) {
      now = System.currentTimeMillis();
      long timeDiff = now - lastTime;
      unprocessed += timeDiff / getMillisecondsPerTick();
      while (unprocessed >= 1) {
        tick();
        unprocessed--;
        lastTime = now;
      }
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  private void tick() {
    for (TimerTask timerTask : timerTasks) {
      timerTask.tick();
    }
    world.tick();
  }

  public int currentTimeTicks() {
    return (int) ((now - startTime) / getMillisecondsPerTick());
  }

  public void addTimerTask(TimerTask timerTask) {
    this.timerTasks.add(timerTask);
  }

  private double getMillisecondsPerTick() {
    return MS_PER_SECOND / TICKS_PER_SECOND;
  }
}
