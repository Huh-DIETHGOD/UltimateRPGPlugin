package Ultimate.huh.core.commands.impl;

import Ultimate.huh.core.UltimateRPGPlugin;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

public abstract class URPGCommandsFactory {
    private final @NotNull String label;
    private final @NotNull Set<String> alias;
    private @Nullable String permission;
    private @Nullable String description;


    protected URPGCommandsFactory(@NotNull String label, String description , String... alias) {
        this.label = label;
        this.description = description;
        this.alias = Sets.newHashSet(alias);
        this.setPermission("urpg." + label);
    }

    public static @NotNull Stream<URPGCommandsFactory> filterByPermission(@NotNull CommandSender sender, @NotNull Stream<URPGCommandsFactory> commands) {
        return commands.filter((target) -> {
            return target.getPermission() == null || sender.hasPermission(target.getPermission());
        });
    }

    public static void suggestByParameter(@NotNull Stream<String> possible, @NotNull List<String> suggestions, @Nullable String parameter) {
        if (parameter == null) {
            possible.forEach(suggestions::add);
        } else {
            possible.filter((suggestion) -> {
                return suggestion.toLowerCase(Locale.ROOT).startsWith(parameter.toLowerCase(Locale.ROOT));
            }).forEach(suggestions::add);
        }

    }

    public final @NotNull String getLabel() {
        return this.label;
    }

    public final @NotNull @Unmodifiable Set<String> getAlias() {
        return ImmutableSet.copyOf(this.alias);
    }

    public final @NotNull @Unmodifiable Set<String> getLabels() {
        return ImmutableSet.<String>builder().add(this.label).addAll(this.alias).build();
    }

    public final @Nullable String getPermission() {
        return this.permission;
    }

    public void setPermission(@NotNull String permission) {
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void URPGCommand(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params) {
    }

    public void complete(@NotNull UltimateRPGPlugin plugin, @NotNull CommandSender sender, @NotNull String alias, @NotNull @Unmodifiable List<String> params, @NotNull List<String> suggestions) {
    }
}
