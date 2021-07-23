package dev.dementisimus.mrs.commands;

import com.google.inject.Inject;
import dev.dementisimus.capi.core.annotations.bukkit.BukkitCommand;
import dev.dementisimus.capi.core.databases.DataManagement;
import dev.dementisimus.capi.core.translations.bukkit.BukkitTranslation;
import dev.dementisimus.mrs.MapRatingSystem;
import dev.dementisimus.mrs.api.MapRating;
import dev.dementisimus.mrs.api.RatingType;
import dev.dementisimus.mrs.data.Key;
import dev.dementisimus.mrs.strings.Prefixes;
import dev.dementisimus.mrs.translation.Translations;
import org.bson.Document;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
/**
 * Copyright (c) by dementisimus
 *
 * Class COMMAND_maprating @ MapRatingSystem
 *
 * @author dementisimus
 * @since 29.06.2020:19:39
 */
@BukkitCommand(name = "maprating", nameAliases = {"mr"}, additionalModulesToInject = {MapRatingSystem.class, MapRating.class})
public class COMMAND_maprating implements CommandExecutor {

    @Inject MapRatingSystem mapRatingSystem;

    @Inject MapRating mapRating;

    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {
        Player player = ((cs instanceof Player) ? (Player) cs : null);

        if(player != null) {
            if(args.length != 0) {
                if(args.length == 1 || args.length == 2) {
                    String map = args[0];
                    DataManagement dataManagement = this.mapRatingSystem.getCoreAPI().getDataManagement();
                    dataManagement.setRequirements("mapRatings", Key.MAP.value, map);
                    dataManagement.get(new String[]{Key.PLAYER_VOTES.value}, result -> {
                        if(result != null && !result.isEmpty()) {
                            Document votesByMapRatings = this.mapRating.getRatingData(result);
                            if(!votesByMapRatings.isEmpty()) {
                                this.mapRating.setPreconditionsForRatingDisplaying(player);
                                StringBuilder resu = new StringBuilder(Prefixes.MAPRATINGSYSTEM + "\n");
                                resu.append("§7§l§m--------------------").append("\n");
                                resu.append("§e").append(new BukkitTranslation(Translations.MAP).get(player)).append("§7: §6").append(map).append("\n");
                                resu.append("§e").append(new BukkitTranslation(Translations.RATING).get(player)).append("§7: ").append("\n");
                                if(args.length == 1) {
                                    for(RatingType ratingType : this.mapRating.getRatingTypes()) {
                                        resu.append(this.appendToStringBuilder(player, ratingType, votesByMapRatings));
                                    }
                                }else {
                                    RatingType selectedRatingType;
                                    try {
                                        selectedRatingType = RatingType.valueOf(args[1].toUpperCase());
                                    }catch(Exception ex) {
                                        selectedRatingType = null;
                                    }
                                    if(selectedRatingType == null) {
                                        RatingType ratingType = this.mapRating.getRatingDisplayTagByRatingString(args[1].toLowerCase());
                                        if(ratingType != null) {
                                            selectedRatingType = ratingType;
                                        }else {
                                            this.sendRatingTypeNotFoundError(player, args[1]);
                                            return;
                                        }
                                    }
                                    resu.append(this.appendToStringBuilder(player, selectedRatingType, votesByMapRatings));
                                }
                                resu.append("§7§l§m--------------------");
                                player.sendMessage(resu.toString());
                            }else {
                                this.sendMapNullMessage(player, map);
                            }
                        }else {
                            this.sendMapNullMessage(player, map);
                        }
                    });
                }else {
                    this.sendHelpMessage(player);
                }
            }else {
                this.sendHelpMessage(player);
            }
        }
        return false;
    }

    private String appendToStringBuilder(Player player, RatingType ratingType, Document votesByMapRatings) {
        String toAppendTo = "";
        ArrayList<String> votes = votesByMapRatings.get(ratingType.name(), ArrayList.class);
        if(votes != null) {
            String votesTranslation = ((votes.size() == 1) ? new BukkitTranslation(Translations.SG_VOTES).get(player) : new BukkitTranslation(Translations.VOTES).get(player));
            if(!votes.isEmpty()) {
                toAppendTo = "§7|   " + this.mapRating.getRatingDisplayTagByRatingEnum(ratingType) + " §7§l:§e " + votes.size() + " " + votesTranslation + "\n";
            }else {
                toAppendTo = "§7|   " + this.mapRating.getRatingDisplayTagByRatingEnum(ratingType) + " §7§l:§e 0 " + votesTranslation + "\n";
            }
        }
        return toAppendTo;
    }

    private void sendHelpMessage(Player player) {
        player.sendMessage(new BukkitTranslation(Translations.COMMAND_MAPRATING_HELP).get(player, "$prefix$", Prefixes.MAPRATINGSYSTEM));
    }

    private void sendRatingTypeNotFoundError(Player player, String ratingType) {
        player.sendMessage(new BukkitTranslation(Translations.COMMAND_MAPRATING_RATINGTYPE_NOT_FOUND).get(player, new String[]{"$prefix$", "$ratingType$"}, new String[]{Prefixes.MAPRATINGSYSTEM, ratingType}));
    }

    private void sendMapNullMessage(Player player, String map) {
        player.sendMessage(new BukkitTranslation(Translations.COMMAND_MAPRATING_MAP_NULL).get(player, new String[]{"$prefix$", "$map$"}, new String[]{Prefixes.MAPRATINGSYSTEM, map}));
    }
}
