/*
 * DebugScanner - Debug World Scanner
 * Copyright (C) 2020 DebugScanner Developers
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package au.com.grieve.debugscanner;

import au.com.grieve.debugscanner.utils.Utils;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Watcher implements Listener {

    private final DebugScanner plugin;
    private final Player player;

    private final List<Player> watchers = new ArrayList<>();

    public Watcher(DebugScanner plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void start() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void stop() {
        HandlerList.unregisterAll(this);
    }

    public void addWatcher(Player player) {
        if (watchers.contains(player)) {
            return;
        }

        this.player.hidePlayer(plugin, player);
        player.hidePlayer(plugin, this.player);

        for (Player watcher : watchers) {
            watcher.hidePlayer(plugin, player);
            player.hidePlayer(plugin, watcher);

        }

        player.spigot().sendMessage(
                new ComponentBuilder("You are now watching what " + this.player.getName() + " watches.")
                    .color(ChatColor.YELLOW).create()
        );

        player.setGameMode(GameMode.CREATIVE); // Bedrock spectator doesn't really work
        watchers.add(player);
    }

    public void removeWatcher(Player player) {
        if (!watchers.contains(player)) {
            return;
        }

        watchers.remove(player);

        this.player.showPlayer(plugin, player);
        player.showPlayer(plugin, this.player);

        for (Player watcher : watchers) {
            watcher.showPlayer(plugin, player);
            player.showPlayer(plugin, watcher);
        }

        player.spigot().sendMessage(
                new ComponentBuilder("You have stopped watching what " + this.player.getName() + " watches.")
                        .color(ChatColor.YELLOW).create()
        );

    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer() != player || event.getTo() == null) {
            return;
        }

        for (Player watcher : watchers) {
            watcher.teleport(event.getTo());
        }
    }

    @EventHandler
    public void OnPlayerTeleport(PlayerTeleportEvent event) {
        if (event.getPlayer() != player || event.getTo() == null) {
            return;
        }

        for (Player watcher : watchers) {
            watcher.teleport(event.getTo());
        }
    }

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent event) {
        if (watchers.contains(event.getPlayer())) {
            removeWatcher(event.getPlayer());
        }
    }


}
