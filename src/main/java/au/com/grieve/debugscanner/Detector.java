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

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

@Getter
public class Detector implements Listener {

    private DebugScanner plugin;
    private Player player;

    public Detector(DebugScanner plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    public void start() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public void stop() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getPlayer() != player) {
            return;
        }

        Block block = event.getPlayer().getTargetBlock(null, 10);
        if (block.getLocation().getY() != 70 || block.getType() == Material.AIR) {
            return;
        }

        // Work out block number
        Location blockLocation = block.getLocation();
        int blockNumber = ((blockLocation.getBlockX() - 1) / 2) * 106;
        blockNumber += (blockLocation.getBlockZ() - 1) / 2;

        BaseComponent[] blockData = new ComponentBuilder(String.valueOf(blockNumber)).color(ChatColor.RED)
                .append(": ").color(ChatColor.YELLOW)
                .append(block.getBlockData().getAsString()).color(ChatColor.WHITE)
                .create();

        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, blockData);
    }
}
