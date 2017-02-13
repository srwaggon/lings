package tech.srwaggon.lings.net.message.action;

import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonTypeInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.srwaggon.lings.Game;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = EatMessage.class, name = "eat"),
    @JsonSubTypes.Type(value = MoveMessage.class, name = "move")
})
public abstract class AbstractActionMessage {
  int id;

  public abstract void perform(Game game);
}
