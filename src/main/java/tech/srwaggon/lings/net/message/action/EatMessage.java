package tech.srwaggon.lings.net.message.action;

import org.codehaus.jackson.annotate.JsonTypeName;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.srwaggon.lings.game.Game;
import tech.srwaggon.lings.entity.Agent;

@Data
@NoArgsConstructor
@JsonTypeName("eat")
public class EatMessage extends AbstractActionMessage {

  @Builder
  public EatMessage(int id) {
    super(id);
  }

  @Override
  public void perform(Game game) {
    Agent agent = game.getAgentManager().getAgents().get(getId());
    agent.eat();
  }
}
