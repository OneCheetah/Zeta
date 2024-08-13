package dev.onecheetah.zeta.model.base;

import net.dv8tion.jda.api.events.interaction.GenericInteractionCreateEvent;
import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

public interface Interaction<T extends GenericInteractionCreateEvent & IReplyCallback> {

  void execute(T event) throws Exception;
}
