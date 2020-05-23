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
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

@Getter
public class Scanner {
    private final Player player;
    private final DebugScanner plugin;
    private final int start;
    private final int interval;
    private final float pitch;
    private final float yaw;
    private final float x_offset;
    private final float y_offset;
    private final float z_offset;
    private final Direction direction;
    private BukkitRunnable runnable;

    public Scanner(DebugScanner plugin, Player player, int start, int interval, float pitch, float yaw, float x_offset, float y_offset, float z_offset, Direction direction) {
        this.plugin = plugin;
        this.player = player;
        this.start = start;
        this.interval = interval;
        this.pitch = pitch;
        this.yaw = yaw;
        this.x_offset = x_offset;
        this.y_offset = y_offset;
        this.z_offset = z_offset;
        this.direction = direction;
    }

    public void start() {
        player.setGameMode(GameMode.CREATIVE); // Workaround for Bedrock
//        player.setGameMode(GameMode.SPECTATOR);


        runnable = new BukkitRunnable() {
            final double y = 70.0;
            double x = ((double) (start / 106) * 2) + 1.0;
            double z = ((double) (start - ((start / 106) * 106)) * 2) + 1.0;
            int direction = Scanner.this.direction==Direction.ALL?0:Scanner.this.direction.ordinal();
            int upto = start;

            @Override
            public void run() {
                Location location = new Location(plugin.getServer().getWorld("world"), x, y, z);

                if (location.getBlock().getType() == Material.AIR) {
                    x += 2.0;
                    z = 1.0;
                    location = new Location(plugin.getServer().getWorld("world"), x, y, z);

                    // Are we done?
                    if (location.getBlock().getType() == Material.AIR) {
                        cancel();
                    }
                }

                BaseComponent[] blockData = new ComponentBuilder(String.valueOf(upto)).color(ChatColor.RED)
                        .append(": ").color(ChatColor.YELLOW)
                        .append(location.getBlock().getBlockData().getAsString()).color(ChatColor.DARK_GRAY)
                        .create();

                Location l = new Location(plugin.getServer().getWorld("world"), x + 0.5, y + 0.5, z + 0.5, yaw - (-90 * direction), pitch);

                // Rotate around direction if required
                l.add(Utils.rotate(new Vector(x_offset, y_offset, z_offset), new Vector(0, 1, 0), -90 * direction));

                player.teleport(l);
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, blockData);

                if (Scanner.this.direction == Direction.ALL) {
                    if (direction < 3) {
                        direction++;
                    } else {
                        direction = 0;
                        z += 2.0;
                        upto++;
                    }
                } else {
                    z += 2.0;
                    upto++;
                }
            }

        };

        runnable.runTaskTimer(DebugScanner.getInstance(), 0, interval);

    }

    public void stop() {
        if (runnable != null) {
            runnable.cancel();
        }
        runnable = null;
    }

    public enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST,
        ALL
    }
}
