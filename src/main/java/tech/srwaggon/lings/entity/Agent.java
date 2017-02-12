package tech.srwaggon.lings.entity;

import com.google.common.eventbus.EventBus;

import tech.srwaggon.lings.Game;
import tech.srwaggon.lings.net.message.MoveMessage;
import tech.srwaggon.lings.world.Tile;

public class Agent extends Entity {

  private static int ids = 0;
  private final int id;
  private final Game game;
  private EventBus eventBus;

  public Agent(EventBus eventBus, Game game) {
    this.eventBus = eventBus;
    this.game = game;
    this.id = ids++;
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
    return this.game.world().tile(x, y);
  }

  public void move(int x, int y) {
    getTile().occupy(null);
    this.game.world().tile(x, y).occupy(this);
    MoveMessage moveMessage = new MoveMessage(this.id, this.x, this.y);
    eventBus.post(moveMessage);
  }

  @Override
  public String getString() {
    return String.format("agent id %d x %d y %d", id, x, y);
  }

  public int getId() {
    return id;
  }
}
