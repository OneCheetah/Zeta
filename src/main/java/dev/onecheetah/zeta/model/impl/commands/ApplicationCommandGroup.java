package dev.onecheetah.zeta.model.impl.commands;

import java.util.Map;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import net.dv8tion.jda.api.interactions.DiscordLocale;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class ApplicationCommandGroup {

  private final ApplicationCommandGroup.Property property;

  @EqualsAndHashCode(onlyExplicitlyIncluded = true)
  @RequiredArgsConstructor(staticName = "of")
  @Accessors(chain = true, makeFinal = true)
  @Data
  public static final class Property {

    @EqualsAndHashCode.Include
    private final String name;
    @EqualsAndHashCode.Include
    private final String description;

    private Map<DiscordLocale, String> localeNames = Map.of();
    private Map<DiscordLocale, String> localeDescriptions = Map.of();

  }
}
