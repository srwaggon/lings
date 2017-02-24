package tech.srwaggon.lings.world.tile;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Data;
import tech.srwaggon.lings.entity.Entity;
import tech.srwaggon.lings.world.tile.tiletype.TileType;

@Data
public class Tile {

  private List<Entity> occupants = Lists.newArrayList();

  private final int x;
  private final int y;
  private TileType type = TileType.Normal;

  public Tile(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public String getSymbol() {
    return "" + type.getChar();
  }

  public void occupy(Entity entity) {
    occupants.add(entity);
  }

  public boolean isFilled() {
    return occupants.stream().anyMatch(Entity::fillsTile);
  }

  public List<Entity> getFillers() {
    return occupants.stream().filter(Entity::fillsTile).collect(Collectors.toList());
  }
}
