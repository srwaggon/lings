package tech.srwaggon.lings.net.message;

import org.codehaus.jackson.annotate.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tech.srwaggon.lings.net.message.action.AbstractActionMessage;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeName("action")
public class ActionMessage extends Message {
  private final String type = "action";
  private AbstractActionMessage action;
}
