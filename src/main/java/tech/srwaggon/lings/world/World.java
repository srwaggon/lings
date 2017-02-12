package tech.srwaggon.lings.world;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class World {
  private int numColumns;
  private int numRows;
  private Tile[][] world;

  public int getNumColumns() {
    return numColumns;
  }

  public int getNumRows() {
    return numRows;
  }

  public World() {
    numColumns = 5;
    numRows = 5;
    world = new Tile[numRows][numColumns];
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numColumns; j++) {
        world[i][j] = new Tile();
      }
    }
  }

  public void forEach(Consumer<Tile> tileOperator) {
    int numRows = world.length;
    int numColumns = world[0].length;
    for (int i = 0; i < numRows; i++) {
      for (int j = 0; j < numColumns; j++) {
        tileOperator.accept(world[i][j]);
      }
    }
  }

  public String getString() {
    String result = "";
    result += numColumns + " ";
    result += numRows + " ";
    for (Tile[] tileArray : world) {
      for (Tile tile : tileArray) {
        result += tile.getSymbol() + "";
      }
    }
    return result;
  }

  public Tile tile(int x, int y) {
    return world[y % getNumRows()][x % getNumColumns()];
  }
}
