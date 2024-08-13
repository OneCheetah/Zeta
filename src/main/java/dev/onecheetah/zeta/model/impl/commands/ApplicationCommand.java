package dev.onecheetah.zeta.model.impl.commands;

import dev.onecheetah.zeta.model.base.Interaction;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor()
@Data
public abstract class ApplicationCommand implements Interaction<SlashCommandInteractionEvent> {

  private final Property property;

  @EqualsAndHashCode(onlyExplicitlyIncluded = true)
  @RequiredArgsConstructor(staticName = "of")
  @Accessors(chain = true, makeFinal = true)
  @Data
  public static final class Property {

    @EqualsAndHashCode.Include
    @NotNull
    private final String name;
    @EqualsAndHashCode.Include
    @NotNull
    private final String description;
    @NotNull
    private List<OptionData> options = List.of();
    @NotNull
    private DefaultMemberPermissions permissions = DefaultMemberPermissions.ENABLED;
    @NotNull
    private Map<DiscordLocale, String> localeNames = Map.of();
    @NotNull
    private Map<DiscordLocale, String> localeDescriptions = Map.of();
    private boolean NSFW = false;
    private boolean guildOnly = true;

    private ApplicationCommandCategory category;
    private ApplicationCommandGroup group;
  }

  public void onAutoComplete(CommandAutoCompleteInteractionEvent event) {
  }
}
