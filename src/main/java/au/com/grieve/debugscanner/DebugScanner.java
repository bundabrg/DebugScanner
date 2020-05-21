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

import au.com.grieve.bcf.BukkitCommandManager;
import au.com.grieve.debugscanner.commands.MainCommand;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public final class DebugScanner extends JavaPlugin {

    @Getter
    private static DebugScanner instance;

    @Getter
    private BukkitCommandManager bcf;

    private BukkitRunnable runnable;

    public DebugScanner() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Setup Command Manager
        bcf = new BukkitCommandManager(this);

        // Register Commands
        bcf.registerCommand(MainCommand.class);
    }

    // Start operation in a thread
    public void start(int start, int period, float pitch, float yaw) throws DebugScannerException {
        if (runnable != null) {
            stop();
        }

        // Set everyone into spectator mode
        for (Player player : getServer().getOnlinePlayers()) {
            player.setGameMode(GameMode.SPECTATOR);
        }

        runnable = new BukkitRunnable() {
            final double y = 70.0;
            double x = ((double) (start / 106) * 2) + 1.0;
            double z = ((double) (start - ((start / 106) * 106)) * 2) + 1.0;
            int upto = start;

            @Override
            public void run() {
                Location location = new Location(getServer().getWorld("world"), x, y, z);

                if (location.getBlock().getType() == Material.AIR) {
                    x += 2.0;
                    z = 1.0;
                    location = new Location(getServer().getWorld("world"), x, y, z);

                    // Are we done?
                    if (location.getBlock().getType() == Material.AIR) {
                        cancel();
                        return;
                    }
                }

                BaseComponent[] blockData = new ComponentBuilder(String.valueOf(upto)).color(ChatColor.RED)
                        .append(": ").color(ChatColor.YELLOW)
                        .append(location.getBlock().getBlockData().getAsString()).color(ChatColor.WHITE)
                        .create();

                Location l = new Location(getServer().getWorld("world"), x, y, z, yaw, pitch)
                        .add(new Vector(0.5, 0.5, -1.5));
                for (Player player : getServer().getOnlinePlayers()) {
                    player.teleport(l);
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, blockData);
                }

                z += 2.0;
                upto++;
            }
        };

        runnable.runTaskTimer(DebugScanner.getInstance(), 0, period);
    }

    // Stop any existing operations
    public void stop() throws DebugScannerException {
        if (runnable == null) {
            throw new DebugScannerException("Operation is already stopped");
        }

        runnable.cancel();
        runnable = null;
    }

    public static class DebugScannerException extends Exception {
        public DebugScannerException(String message) {
            super(message);
        }
    }
}
