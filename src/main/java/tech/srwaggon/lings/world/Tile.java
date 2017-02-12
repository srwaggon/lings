package tech.srwaggon.lings.world;

import tech.srwaggon.lings.entity.Entity;

public class Tile {

  private Entity occupant;
  private boolean hasFood;

  public String getSymbol() {
    return occupant != null ? occupant.getSymbol() : this.hasFood ? "F" : ".";
  }

  public void occupy(Entity entity) {
    this.occupant = entity;
  }

  public void addFood() {
    this.hasFood = true;
  }

  public boolean hasFood() {
    return hasFood;
  }

  public void consume() {
    this.hasFood = false;
  }
}
