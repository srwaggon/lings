package tech.srwaggon.lings.net.message;

import lombok.Data;

@Data
public class MoveMessage {

  private final int id;
  private final int x;
  private final int y;

  public MoveMessage(int id, int x, int y) {
    this.id = id;
    this.x = x;
    this.y = y;
  }

  public String convert() {
    return String.format("move id %d x %d y %d", id, x, y);
  }

  public static MoveMessage parse(String string) {
    String[] coords = string.split(" ");
    int id = Integer.parseInt(coords[2]);
    int x = Integer.parseInt(coords[4]);
    int y = Integer.parseInt(coords[6]);

    return new MoveMessage(id, x, y);
  }
}
