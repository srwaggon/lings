package tech.srwaggon.lings.net.message;

import org.codehaus.jackson.annotate.JsonTypeName;

import lombok.Data;

@Data
@JsonTypeName("map")
public class MapMessage extends Message {
  private final String type = "map";
}
