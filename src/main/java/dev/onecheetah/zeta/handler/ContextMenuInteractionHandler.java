package dev.onecheetah.zeta.handler;

import dev.onecheetah.zeta.model.impl.context.ContextMenu;
import dev.onecheetah.zeta.model.impl.context.MessageContextMenu;
import dev.onecheetah.zeta.model.impl.context.UserContextMenu;
import dev.onecheetah.zeta.util.Reflections;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

@Slf4j
public final class ContextMenuInteractionHandler extends AbstractInteractionHandler<ContextMenu> {

  public static final String USER_MAP_PREFIX = "!!user@";
  public static final String MESSAGE_MAP_PREFIX = "!!message@";

  public ContextMenuInteractionHandler(final String... PACKAGE_NAME) {
    super(ContextMenu.class, PACKAGE_NAME);
  }

  @Override
  public Map<String, Constructor<? extends ContextMenu>> process() {
    final Map<String, Constructor<? extends ContextMenu>> DATA = new HashMap<>();
    for (final var CONSTRUCTOR : extract()) {
      final ContextMenu INSTANCE = Reflections.noArgsNewInstance(CONSTRUCTOR);
      final ContextMenu.Property PROPERTY = INSTANCE.getProperty();

      if (INSTANCE instanceof UserContextMenu) {
        REGISTRY.add(
            Commands.user(PROPERTY.getName())
                .setDefaultPermissions(PROPERTY.getPermissions())
                .setGuildOnly(PROPERTY.isGuildOnly())
                .setNameLocalizations(PROPERTY.getNameLocalizations())
                .setNSFW(PROPERTY.isNSFW())
        );
        DATA.put(USER_MAP_PREFIX + PROPERTY.getName(), CONSTRUCTOR);
        log.info("User Context Menu registered with name of '{}'", PROPERTY.getName());
      } else if (INSTANCE instanceof MessageContextMenu) {
        REGISTRY.add(
            Commands.message(PROPERTY.getName())
                .setDefaultPermissions(PROPERTY.getPermissions())
                .setGuildOnly(PROPERTY.isGuildOnly())
                .setNameLocalizations(PROPERTY.getNameLocalizations())
                .setNSFW(PROPERTY.isNSFW())
        );
        DATA.put(MESSAGE_MAP_PREFIX + PROPERTY.getName(), CONSTRUCTOR);
        log.info("Message Context Menu registered with name of '{}'", PROPERTY.getName());
      } else {
        throw new IllegalArgumentException(
            "Unknown context menu type: " + INSTANCE.getClass().getName());
      }
    }
    return DATA;
  }
}
