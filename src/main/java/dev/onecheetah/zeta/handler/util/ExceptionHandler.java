package dev.onecheetah.zeta.handler.util;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public interface ExceptionHandler {

  default void handle(SlashCommandInteractionEvent event, Exception e) {

  }

  default void handle(CommandAutoCompleteInteractionEvent event, Exception e) {

  }

  default void handle(ButtonInteractionEvent event, Exception e) {

  }

  default void handle(MessageContextInteractionEvent event, Exception e) {

  }

  default void handle(UserContextInteractionEvent event, Exception e) {
  }

  default void handle(ModalInteractionEvent event, Exception e) {
  }

  default void handle(EntitySelectInteractionEvent event, Exception e) {
  }

  default void handle(StringSelectInteractionEvent event, Exception e) {
  }
}
