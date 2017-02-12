package tech.srwaggon.lings;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import tech.srwaggon.lings.entity.Agent;
import tech.srwaggon.lings.entity.AgentManager;
import tech.srwaggon.lings.world.World;

@Component
public class Game {

  @Inject
  private AgentManager agentManager;

  @Inject
  private World world;

  @PostConstruct
  private void init() {
    world.tile(4, 0).addFood();
  }

  public World world() {
    return world;
  }

  public Map<Integer, Agent> getAgents() {
    return agentManager.getAll();
  }
}
