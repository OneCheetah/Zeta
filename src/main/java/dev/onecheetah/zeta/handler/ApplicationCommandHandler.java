package dev.onecheetah.zeta.handler;

import dev.onecheetah.zeta.model.impl.commands.ApplicationCommand;
import dev.onecheetah.zeta.model.impl.commands.ApplicationCommandCategory;
import dev.onecheetah.zeta.model.impl.commands.ApplicationCommandGroup;
import dev.onecheetah.zeta.util.Reflections;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandGroupData;

@Slf4j
public final class ApplicationCommandHandler extends
    AbstractInteractionHandler<ApplicationCommand> {

  public ApplicationCommandHandler(final String... PACKAGE_NAME) {
    super(ApplicationCommand.class, PACKAGE_NAME);
  }

  @Override
  public Map<String, Constructor<? extends ApplicationCommand>> process() {
    final Map<ApplicationCommandCategory, Map<ApplicationCommandGroup, List<SubcommandData>>> COMMANDS = new HashMap<>(
        16);
    final Map<String, Constructor<? extends ApplicationCommand>> DATA = new HashMap<>(16);

    for (final var CONSTRUCTOR : extract()) {
      final ApplicationCommand INSTANCE = Reflections.noArgsNewInstance(CONSTRUCTOR);
      final ApplicationCommand.Property PROPERTY = INSTANCE.getProperty();

      if (PROPERTY.getCategory() != null) {
        COMMANDS.computeIfAbsent(PROPERTY.getCategory(), k -> new HashMap<>());
        COMMANDS.get(PROPERTY.getCategory())
            .computeIfAbsent(PROPERTY.getGroup(), k -> new ArrayList<>(16));
        COMMANDS.get(PROPERTY.getCategory()).get(PROPERTY.getGroup())
            .add(createSubApplicationData(PROPERTY));

        if (PROPERTY.getGroup() != null) {
          DATA.put(String.format("%s %s %s", PROPERTY.getCategory().getProperty().getName(),
              PROPERTY.getGroup().getProperty().getName(), PROPERTY.getName()), CONSTRUCTOR);
          log.info(
              "Application Command registered with category '{}', group '{}', and name '{}' (/{} {} {})",
              PROPERTY.getCategory().getProperty().getName(),
              PROPERTY.getGroup().getProperty().getName(),
              PROPERTY.getName(), PROPERTY.getCategory().getProperty().getName(),
              PROPERTY.getGroup().getProperty().getName(),
              PROPERTY.getName());
        } else {
          DATA.put(String.format("%s %s", PROPERTY.getCategory().getProperty().getName(),
              PROPERTY.getName()), CONSTRUCTOR);
          log.info("Application Command registered  with category '{}' and name '{}' (/{} {})",
              PROPERTY.getCategory().getProperty().getName(),
              PROPERTY.getName(), PROPERTY.getCategory().getProperty().getName(),
              PROPERTY.getName());
        }
      } else {
        if (PROPERTY.getGroup() != null) {
          log.error("An Application Command may not have a group without a category");
          throw new IllegalStateException();
        }
        log.info("Application Command registered: '{}' (/{})", PROPERTY.getName(),
            PROPERTY.getName());
        DATA.put(PROPERTY.getName(), CONSTRUCTOR);
        REGISTRY.add(createApplicationCommandData(PROPERTY));
      }
    }
    for (final var CATEGORY : COMMANDS.entrySet()) {
      var category = createCategoryApplicationCommandData(CATEGORY.getKey().getProperty());
      for (final var groups : CATEGORY.getValue().entrySet()) {
        if (groups.getKey() != null) {
          var group = createApplicationGroupData(groups.getKey().getProperty());
          group.addSubcommands(groups.getValue());
          category.addSubcommandGroups(group);
        } else {
          category.addSubcommands(groups.getValue());
        }
      }
      REGISTRY.add(category);
    }
    return DATA;
  }

  private SlashCommandData createApplicationCommandData(
      final ApplicationCommand.Property property) {
    return Commands.slash(property.getName(), property.getDescription())
        .setDefaultPermissions(property.getPermissions())
        .setGuildOnly(property.isGuildOnly())
        .setNSFW(property.isNSFW())
        .addOptions(property.getOptions())
        .setNameLocalizations(property.getLocaleNames())
        .setDescriptionLocalizations(property.getLocaleDescriptions());
  }

  private SubcommandData createSubApplicationData(
      final ApplicationCommand.Property property) {
    return new SubcommandData(property.getName(), property.getDescription()).setNameLocalizations(
            property.getLocaleNames()).setDescriptionLocalizations(property.getLocaleDescriptions())
        .addOptions(property.getOptions());
  }

  private SlashCommandData createCategoryApplicationCommandData(
      final ApplicationCommandCategory.Property property) {
    return Commands.slash(property.getName(), property.getDescription())
        .setDefaultPermissions(property.getPermissions())
        .setNSFW(property.isNSFW())
        .setGuildOnly(property.isGuildOnly());
  }

  private SubcommandGroupData createApplicationGroupData(
      final ApplicationCommandGroup.Property property) {
    return new SubcommandGroupData(property.getName(),
        property.getDescription()).setDescriptionLocalizations(property.getLocaleDescriptions())
        .setDescriptionLocalizations(property.getLocaleDescriptions());
  }
}