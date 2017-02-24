package tech.srwaggon.lings.world.tile.tiletype;

public abstract class SimpleTileType implements TileType {
  @Override
  public boolean onEnter() {
    return false;
  }

  @Override
  public boolean onExit() {
    return false;
  }

  @Override
  public boolean onStand() {
    return false;
  }
}
