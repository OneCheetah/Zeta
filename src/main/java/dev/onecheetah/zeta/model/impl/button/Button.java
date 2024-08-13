package dev.onecheetah.zeta.model.impl.button;

import dev.onecheetah.zeta.model.base.Interaction;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

@RequiredArgsConstructor()
@Data
public abstract class Button implements Interaction<ButtonInteractionEvent> {

  private final Property property;

  @RequiredArgsConstructor(staticName = "of")
  @Accessors(chain = true, makeFinal = true)
  @Data
  public static final class Property {

    private final String id;
  }
}
