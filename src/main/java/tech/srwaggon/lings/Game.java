package tech.srwaggon.lings;

import org.springframework.stereotype.Component;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import lombok.Data;
import tech.srwaggon.lings.entity.Agent;
import tech.srwaggon.lings.entity.AgentManager;
import tech.srwaggon.lings.world.World;

@Component
@Data
public class Game {

  @Inject
  private AgentManager agentManager;

  @Inject
  private World world;

  public Map<Integer, Agent> getAgents() {
    return agentManager.getAgents();
  }
}
