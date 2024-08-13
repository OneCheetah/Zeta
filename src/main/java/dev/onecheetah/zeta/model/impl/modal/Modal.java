package dev.onecheetah.zeta.model.impl.modal;

import dev.onecheetah.zeta.model.base.Interaction;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

@RequiredArgsConstructor()
@Data
public abstract class Modal implements Interaction<ModalInteractionEvent> {

  private final Property property;

  @RequiredArgsConstructor(staticName = "of")
  @Accessors(chain = true, makeFinal = true)
  @Data
  public static final class Property {

    private final String id;
  }

}
