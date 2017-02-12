package tech.srwaggon.lings;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import tech.srwaggon.lings.entity.Agent;
import tech.srwaggon.lings.world.World;

@Component
public class Game {

  private World world = new World();
  private List<Agent> entities = new ArrayList<>();

  public Game() {
    entities.add(new Agent(world));
    world.get(4, 0).addFood();
  }

  public World getWorld() {
    return world;
  }

  public List<Agent> getAgents() {
    return entities;
  }
}
