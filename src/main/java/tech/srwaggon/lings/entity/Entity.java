package tech.srwaggon.lings.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Entity {

  private static int ids = 0;
  private final int id;

  int x = 0;
  int y = 0;

  public Entity() {
    this.id = ids++;
  }

  public Entity(int id) {
    this.id = id;
  }

  public boolean fillsTile() {
    return false;
  }
}
