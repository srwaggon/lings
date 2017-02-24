package tech.srwaggon.lings.world.tile.tiletype;

public interface TileType {
  char getChar();

  boolean onEnter();

  boolean onExit();

  boolean onStand();

  TileType Normal = new NormalTileType();
  TileType Blocked = new BlockedTileType();

  static TileType parse(char symbol) {
    return Normal;
  }
}
