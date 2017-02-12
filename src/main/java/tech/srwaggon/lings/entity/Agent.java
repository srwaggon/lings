package tech.srwaggon.lings.entity;

import tech.srwaggon.lings.world.Tile;
import tech.srwaggon.lings.world.World;

public class Agent extends Entity {

  private static int ids = 0;
  private final int id;
  private final World world;

  public Agent(World world) {
    this.id = ids++;
    this.world = world;
  }

  public String getSymbol() {
    return "A";
  }

  private void eatAnyFood() {
    Tile tile = getTile();
    if (tile.hasFood()) {
      tile.consume();
    }
  }

  private Tile getTile() {
    return world.get(x, y);
  }

  public void move(int x, int y) {
    getTile().occupy(null);
    world.get(x, y).occupy(this);
  }

  @Override
  public String getString() {
    return String.format("agent id %d x %d y %d", id, x, y);
  }
}
