package dev.onecheetah.zeta.event;

import dev.onecheetah.zeta.handler.ContextMenuInteractionHandler;
import dev.onecheetah.zeta.handler.SelectMenuInteractionHandler;
import dev.onecheetah.zeta.handler.util.ExceptionHandler;
import dev.onecheetah.zeta.handler.util.Registry;
import dev.onecheetah.zeta.model.impl.context.MessageContextMenu;
import dev.onecheetah.zeta.model.impl.context.UserContextMenu;
import dev.onecheetah.zeta.model.impl.select.EntitySelectMenu;
import dev.onecheetah.zeta.model.impl.select.StringSelectMenu;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.MessageContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.UserContextInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.EntitySelectInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public final class ZetaEventListener extends ListenerAdapter {

  private final Registry REGISTRY;
  private final ExceptionHandler EXCEPTION_HANDLER;

  public ZetaEventListener(Registry registry, ExceptionHandler exceptionHandler) {
    this.REGISTRY = registry;
    this.EXCEPTION_HANDLER = exceptionHandler;
  }

  @Override
  public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
    try {
      REGISTRY.getApplicationCommand(event.getFullCommandName()).execute(event);
    } catch (Exception e) {
      EXCEPTION_HANDLER.handle(event, e);
    }
  }

  @Override
  public void onCommandAutoCompleteInteraction(@NotNull CommandAutoCompleteInteractionEvent event) {
    try {
      REGISTRY.getApplicationCommand(event.getFullCommandName()).onAutoComplete(event);
    } catch (Exception e) {
      EXCEPTION_HANDLER.handle(event, e);
    }
  }

  @Override
  public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
    try {
      REGISTRY.getButton(event.getComponentId()).execute(event);
    } catch (Exception e) {
      EXCEPTION_HANDLER.handle(event, e);
    }
  }

  @Override
  public void onModalInteraction(@NotNull ModalInteractionEvent event) {
    try {
      REGISTRY.getModal(event.getModalId()).execute(event);
    } catch (Exception e) {
      EXCEPTION_HANDLER.handle(event, e);
    }
  }

  @Override
  public void onUserContextInteraction(@NotNull UserContextInteractionEvent event) {
    try {
      ((UserContextMenu) REGISTRY.getContext(
          ContextMenuInteractionHandler.USER_MAP_PREFIX + event.getName())).execute(event);
    } catch (Exception e) {
      EXCEPTION_HANDLER.handle(event, e);
    }
  }

  @Override
  public void onMessageContextInteraction(@NotNull MessageContextInteractionEvent event) {
    try {
      ((MessageContextMenu) REGISTRY.getContext(
          ContextMenuInteractionHandler.MESSAGE_MAP_PREFIX + event.getName())).execute(event);
    } catch (Exception e) {
      EXCEPTION_HANDLER.handle(event, e);
    }
  }

  @Override
  public void onEntitySelectInteraction(@NotNull EntitySelectInteractionEvent event) {
    try {
      ((EntitySelectMenu) REGISTRY.getSelectMenu(
          SelectMenuInteractionHandler.ENTITY_MAP_PREFIX + event.getComponentId()))
          .execute(event);
    } catch (Exception e) {
      EXCEPTION_HANDLER.handle(event, e);
    }
  }

  @Override
  public void onStringSelectInteraction(@NotNull StringSelectInteractionEvent event) {
    try {
      ((StringSelectMenu) REGISTRY.getSelectMenu(
          SelectMenuInteractionHandler.STRING_MAP_PREFIX + event.getComponentId()))
          .execute(event);
    } catch (Exception e) {
      EXCEPTION_HANDLER.handle(event, e);
    }
  }
}


