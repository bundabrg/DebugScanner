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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class DebugScanner extends JavaPlugin implements Listener {

    @Getter
    private static DebugScanner instance;

    @Getter
    private BukkitCommandManager bcf;

    @Getter
    private final Map<Player, Scanner> scanners = new HashMap<>();

    @Getter
    private final Map<Player, Detector> detectors = new HashMap<>();

    public DebugScanner() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Setup Command Manager
        bcf = new BukkitCommandManager(this);

        // Register Commands
        bcf.registerCommand(MainCommand.class);

        // Register Listeners
        getServer().getPluginManager().registerEvents(this, this);
    }

    // Start operation in a thread
    public void start(Player player, int start, int period, float pitch, float yaw, float x_offset, float y_offset, float z_offset) {
        if (scanners.containsKey(player)) {
            stop(player);
        }

        Scanner scanner = new Scanner(this, player, start, period, pitch, yaw, x_offset, y_offset, z_offset);
        scanners.put(player, scanner);
        scanner.start();
    }

    // Stop existing operations
    public void stop(Player player) {
        if (!scanners.containsKey(player)) {
            return;
        }

        Scanner scanner = scanners.remove(player);
        scanner.stop();
    }

    // Start Detect mode for player
    public void startDetect(Player player) {
        if (detectors.containsKey(player)) {
            stopDetect(player);
        }

        Detector detector = new Detector(this, player);
        detectors.put(player, detector);
        detector.start();
    }

    // Stop Detect mode for player
    public void stopDetect(Player player) {
        if (!detectors.containsKey(player)) {
            return;
        }

        Detector detector = detectors.remove(player);
        detector.stop();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (scanners.containsKey(event.getPlayer())) {
            Scanner scanner = scanners.remove(event.getPlayer());
            scanner.stop();
        }

        if (detectors.containsKey(event.getPlayer())) {
            Detector detector = detectors.remove(event.getPlayer());
            detector.stop();
        }
    }

}
