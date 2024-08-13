package dev.onecheetah.zeta.model.impl.select;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Data
public abstract class SelectMenu {

  private final Property property;

  @RequiredArgsConstructor(staticName = "of")
  @Accessors(chain = true, makeFinal = true)
  @Data
  public static final class Property {

    private final String componentId;
  }
}
