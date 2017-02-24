package tech.srwaggon.lings.entity;

import com.google.common.eventbus.EventBus;

import org.codehaus.jackson.annotate.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import tech.srwaggon.lings.game.Game;
import tech.srwaggon.lings.net.message.action.EatMessage;
import tech.srwaggon.lings.net.message.action.MoveMessage;
import tech.srwaggon.lings.world.tile.Tile;

@Data
public class Agent extends Entity {

  @Setter
  private Game game;
  @Setter
  private EventBus eventBus;
  @Getter
  @Setter
  private int energy;

  public Agent() {
    super();
  }

  public Agent(int id) {
    super(id);
  }

  public void eat() {
    eventBus.post(EatMessage.builder().id(getId()).build());
  }

  @JsonIgnore
  public Tile getTile() {
    return this.game.getWorld().get(0).getTile(x, y);
  }

  public void move(int x, int y) {
    if (isValidMove(x, y)) {
    int xx = x % game.getWorld().get(0).getNumColumns();
    int yy = y % game.getWorld().get(0).getNumRows();
      actuallyMove(xx, yy);
    }
  }

  private boolean isValidMove(int x, int y) {
    int dx = Math.abs(this.x - x);
    int dy = Math.abs(this.y - y);
    return dx + dy <= 1;
  }

  private void actuallyMove(int x, int y) {
    getTile().occupy(null);
    setX(x);
    setY(y);
    getTile().occupy(this);
    eventBus.post(MoveMessage.builder().id(getId()).x(x).y(y).build());
  }

  public String getType() {
    return "agent";
  }

  @Override
  public boolean fillsTile() {
    return true;
  }
}
