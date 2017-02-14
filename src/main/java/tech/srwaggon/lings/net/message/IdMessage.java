package tech.srwaggon.lings.net.message;

import org.codehaus.jackson.annotate.JsonTypeName;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@JsonTypeName("id")
@AllArgsConstructor
public class IdMessage extends Message {
  private final String type = "id";
  private int id;
}
