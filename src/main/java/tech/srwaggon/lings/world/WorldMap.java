package tech.srwaggon.lings.world;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Scanner;

import javax.annotation.PostConstruct;

import tech.srwaggon.lings.world.tile.Tile;
import tech.srwaggon.lings.world.tile.tiletype.TileType;

public class WorldMap {

  private int numColumns;
  private int numRows;
  private Tile[][] world;
  private int id;

  public WorldMap(int numColumns, int numRows) {
    this.numColumns = numColumns;
    this.numRows = numRows;
    initTiles(numColumns, numRows);
  }

  private void initTiles(int numColumns, int numRows) {
    world = new Tile[numRows][numColumns];
    for (int y = 0; y < numRows; y++) {
      for (int x = 0; x < numColumns; x++) {
        world[y][x] = new Tile(x, y);
      }
    }
  }

  @JsonProperty(value = "columns")
  public int getNumColumns() {
    return numColumns;
  }

  @JsonProperty(value = "rows")
  public int getNumRows() {
    return numRows;
  }

  @PostConstruct
  public void init() {
  }

  public String getType() {
    return "map";
  }

  @JsonProperty(value = "map")
  public String getTilesAsString() {
    String result = "";
    for (Tile[] tileArray : world) {
      for (Tile tile : tileArray) {
        result += tile.getSymbol() + "";
      }
    }
    return result;
  }

  public Tile getTile(int x, int y) {
    return world[y % getNumRows()][x % getNumColumns()];
  }

  public void print() {
    StringBuilder result = new StringBuilder();
    result.append("====================================\n");
    for (int y = 0; y < numRows; y++) {
      for (int x = 0; x < numColumns; x++) {
        Tile tile = world[y][x];
        String symbol = tile.isFilled() ? "" + tile.getFillers().get(0).getId() : tile.getSymbol();
        result.append(String.format("%s ", symbol));
      }
      result.append("\n");
    }
    System.out.println(result.toString());
  }

  public void tick() {

  }

  public static WorldMap parse(String description) {
    // W H symbols
    Scanner mapReader = new Scanner(description);
    int w = Integer.parseInt(mapReader.next());
    int h = Integer.parseInt(mapReader.next());
    String symbols = mapReader.next();
    WorldMap newWorldMap = new WorldMap(w, h);
    for (int i = 0; i < symbols.length(); i++) {
      char tileSymbol = symbols.charAt(i);
      int x = i % w;
      int y = i / w;
      newWorldMap.getTile(x, y).setType(TileType.parse(tileSymbol));
    }
    return newWorldMap;
  }

  public void setId(int id) {
    this.id = id;
  }
}
