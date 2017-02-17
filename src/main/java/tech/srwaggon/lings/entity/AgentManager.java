package tech.srwaggon.lings.entity;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import lombok.Getter;
import tech.srwaggon.lings.game.Game;

@Component
public class AgentManager {

  @Inject
  private EventBus eventBus;

  @Getter
  private Map<Integer, Agent> agents = new HashMap<>();

  @Inject
  private Game game;

  public Agent newAgent() {
    Agent agent = new Agent();
    addAgent(agent);
    return agent;
  }

  public Agent newAgent(int id) {
    Agent agent = new Agent(id);
    addAgent(agent);
    return agent;
  }

  public void addAgent(Agent agent) {
    agent.setEventBus(eventBus);
    agent.setGame(game);
    agent.getTile().occupy(agent);
    agents.put(agent.getId(), agent);
  }

  public void remove(Agent agent) {
    agents.remove(agent.getId());
  }
}
