package dev.onecheetah.zeta.handler.util;

import dev.onecheetah.zeta.handler.ContextMenuInteractionHandler;
import dev.onecheetah.zeta.handler.SelectMenuInteractionHandler;
import dev.onecheetah.zeta.model.impl.button.Button;
import dev.onecheetah.zeta.model.impl.commands.ApplicationCommand;
import dev.onecheetah.zeta.model.impl.context.ContextMenu;
import dev.onecheetah.zeta.model.impl.modal.Modal;
import dev.onecheetah.zeta.model.impl.select.SelectMenu;
import dev.onecheetah.zeta.util.Reflections;
import java.lang.reflect.Constructor;
import java.util.Map;

public final class Registry {

  private Map<String, Constructor<? extends ApplicationCommand>> applicationCommands;
  private Map<String, Constructor<? extends Button>> buttons;
  private Map<String, Constructor<? extends ContextMenu>> contexts;
  private Map<String, Constructor<? extends Modal>> modals;
  private Map<String, Constructor<? extends SelectMenu>> selectMenus;

  public Registry setApplicationCommands(
      Map<String, Constructor<? extends ApplicationCommand>> applicationCommands) {
    this.applicationCommands = applicationCommands;
    return this;
  }

  public Registry setButtons(
      Map<String, Constructor<? extends Button>> buttons) {
    this.buttons = buttons;
    return this;
  }

  public Registry setContexts(
      Map<String, Constructor<? extends ContextMenu>> contexts) {
    this.contexts = contexts;
    return this;
  }

  public Registry setModals(
      Map<String, Constructor<? extends Modal>> modals) {
    this.modals = modals;
    return this;
  }

  public Registry setSelectMenus(
      Map<String, Constructor<? extends SelectMenu>> selectMenus) {
    this.selectMenus = selectMenus;
    return this;
  }

  public ApplicationCommand getApplicationCommand(String id) {
    Constructor<? extends ApplicationCommand> constructor = applicationCommands.get(id);
    if (constructor == null) {
      throw new NullPointerException("ApplicationCommand not found: " + id);
    }
    return Reflections.noArgsNewInstance(constructor);
  }

  public Button getButton(String id) {
    Constructor<? extends Button> constructor = buttons.get(id);
    if (constructor == null) {
      throw new NullPointerException("Button not found: " + id);
    }
    return Reflections.noArgsNewInstance(constructor);
  }

  public ContextMenu getContext(String id) {
    Constructor<? extends ContextMenu> constructor = contexts.get(id);
    if (constructor == null) {
      if (id.startsWith(ContextMenuInteractionHandler.USER_MAP_PREFIX)) {
        throw new NullPointerException("User context not found: " + id.substring(
            ContextMenuInteractionHandler.USER_MAP_PREFIX.length()));
      } else {
        throw new NullPointerException("Message context not found: " + id.substring(
            ContextMenuInteractionHandler.MESSAGE_MAP_PREFIX.length()));
      }
    }
    return Reflections.noArgsNewInstance(constructor);
  }

  public Modal getModal(String id) {
    Constructor<? extends Modal> constructor = modals.get(id);
    if (constructor == null) {
      throw new NullPointerException("Button not found: " + id);
    }
    return Reflections.noArgsNewInstance(constructor);
  }

  public SelectMenu getSelectMenu(String id) {
    Constructor<? extends SelectMenu> constructor = selectMenus.get(id);
    if (constructor == null) {
      if (id.startsWith(SelectMenuInteractionHandler.ENTITY_MAP_PREFIX)) {
        throw new NullPointerException("Entity select menu not found: " + id.substring(
            SelectMenuInteractionHandler.ENTITY_MAP_PREFIX.length()));
      } else {
        throw new NullPointerException("String select menu not found: " + id.substring(
            SelectMenuInteractionHandler.STRING_MAP_PREFIX.length()));
      }
    }
    return Reflections.noArgsNewInstance(constructor);

  }

}
