package dev.dementisimus.mrs.commands;

import com.google.inject.Inject;
import dev.dementisimus.capi.core.injection.annotations.bukkit.BukkitCommand;
import dev.dementisimus.mrs.rating.CustomMapRating;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
/**
 * Copyright (c) by dementisimus,
 * licensed under Attribution-NonCommercial-NoDerivatives 4.0 International
 *
 * Class MapRatingCommand @ MapRatingSystem
 *
 * @author dementisimus
 * @since 25.09.2021:18:20
 */
@BukkitCommand(isOptional = false, name = "maprating", nameAliases = {"mr"})
public class MapRatingCommand implements CommandExecutor {

    @Inject CustomMapRating customMapRating;

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String l, @NotNull String[] args) {
        if(cs instanceof Player player) {
            this.customMapRating.openRatedMapsInventory(player);
        }
        return false;
    }
}
