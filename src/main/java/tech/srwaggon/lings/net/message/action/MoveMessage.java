package tech.srwaggon.lings.net.message.action;

import org.codehaus.jackson.annotate.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.srwaggon.lings.game.Game;
import tech.srwaggon.lings.entity.Agent;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("move")
public class MoveMessage extends AbstractActionMessage {
  private int x;
  private int y;

  @Builder
  public MoveMessage(int id, int x, int y) {
    super(id);
    this.x = x;
    this.y = y;
  }

  public MoveMessage(Agent agent) {
    super(agent.getId());
    this.x = agent.getX();
    this.y = agent.getY();
  }

  @Override
  public void perform(Game game) {
    game.getAgents().get(getId()).move(x, y);
  }
}
