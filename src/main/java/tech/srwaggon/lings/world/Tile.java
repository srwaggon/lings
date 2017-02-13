package tech.srwaggon.lings.world;

import com.google.common.eventbus.EventBus;

import tech.srwaggon.lings.entity.Agent;
import tech.srwaggon.lings.net.message.FoodAppearedMessage;

public class Tile {

  private final int x;
  private final int y;
  private final EventBus eventBus;
  private Agent occupant;
  private boolean hasFood;

  public Tile(int x, int y, EventBus eventBus) {
    this.x = x;
    this.y = y;
    this.eventBus = eventBus;
  }

  public String getSymbol() {
    return this.hasFood ? "F" : ".";
  }

  public void occupy(Agent agent) {
    this.occupant = agent;
  }

  public boolean isOccupied() {
    return occupant != null;
  }

  public Agent getOccupant() {
    return occupant;
  }

  public void addFood() {
    this.hasFood = true;
    eventBus.post(FoodAppearedMessage.builder().x(x).y(y).build());
  }

  public boolean consume() {
    boolean result = this.hasFood;
    this.hasFood = false;
    return result;
  }
}
