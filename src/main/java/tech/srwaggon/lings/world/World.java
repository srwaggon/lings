package tech.srwaggon.lings.world;

import com.google.common.eventbus.EventBus;

import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

import javax.inject.Inject;

@Component
public class World {

  @Inject
  private EventBus eventBus;

  private int numColumns;
  private int numRows;
  private Tile[][] world;

  @JsonProperty(value = "columns")
  public int getNumColumns() {
    return numColumns;
  }

  @JsonProperty(value = "rows")
  public int getNumRows() {
    return numRows;
  }


  public World(EventBus eventBus) {
    numColumns = 40;
    numRows = 40;
    world = new Tile[numRows][numColumns];
    for (int y = 0; y < numRows; y++) {
      for (int x = 0; x < numColumns; x++) {
        world[y][x] = new Tile(x, y, eventBus);
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

        result.append(tile.isOccupied() ? tile.getOccupant().getId() + " " : tile.getSymbol() + " ");
      }
      result.append("\n");
    }
    System.out.println(result.toString());
  }
}
