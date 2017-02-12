package tech.srwaggon.lings.entity;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import tech.srwaggon.lings.Game;

@Component
public class AgentManager {

  @Inject
  private EventBus eventBus;

  private Map<Integer, Agent> agents = new HashMap<>();

  @Inject
  private Game game;

  public Agent newAgent() {
    Agent agent = new Agent(eventBus, game);
    agents.put(agent.getId(), agent);
    return agent;
  }

  public void remove(Agent agent) {
    agents.remove(agent.getId());
  }

  public Map<Integer, Agent> getAll() {
    return agents;
  }
}
