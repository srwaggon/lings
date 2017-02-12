package tech.srwaggon.lings.entity;

public abstract class Entity {

  int x = 0;
  int y = 0;

  public Entity() {
  }

  public Entity(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public abstract String getSymbol();

  public abstract String getString();
}
