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

package au.com.grieve.debugscanner.commands;

import au.com.grieve.bcf.BukkitCommand;
import au.com.grieve.bcf.annotations.Arg;
import au.com.grieve.bcf.annotations.Command;
import au.com.grieve.debugscanner.DebugScanner;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@Command("debugscanner|ds")
public class MainCommand extends BukkitCommand {

    @Arg({
            "auto start",
            "@int(switch=interval|i,min=1,default=10)",
            "@float(switch=pitch|p,default=45)",
            "@float(switch=yaw|y,default=0)",
            "@int(switch=start,min=1,default=1)",
            "@float(switch=x_offset|x,default=0.0)",
            "@float(switch=y_offset|y,default=0.5)",
            "@float(switch=z_offset|z,default=-2.0)",
            "@player(mode=online,default=%self)"
    })
    public void onAutoStart(
            CommandSender sender,
            Integer ticks,
            Float pitch,
            Float yaw,
            Integer start,
            Float x_offset,
            Float y_offset,
            Float z_offset,
            Player player
    ) {
        DebugScanner.getInstance().start(player, start, ticks, pitch, yaw, x_offset, y_offset, z_offset);
        // Don't send any response since we don't want to obscure the screen with text
//        sender.spigot().sendMessage(
//                new ComponentBuilder("Started").color(ChatColor.YELLOW).create()
//        );
    }

    @Arg("auto stop @player(mode=online,default=%self)")
    public void onAutoStop(CommandSender sender, Player player) {
        DebugScanner.getInstance().stop(player);

        sender.spigot().sendMessage(
                new ComponentBuilder("Successfully Stopped").color(ChatColor.YELLOW).create()
        );
    }

    @Arg("detect start @player(mode=online,default=%self)")
    public void onDetectStart(CommandSender sender, Player player) {
        DebugScanner.getInstance().startDetect(player);

        sender.spigot().sendMessage(
                new ComponentBuilder("Successfully Started Detect").color(ChatColor.YELLOW).create()
        );
    }

    @Arg("detect stop @player(mode=online,default=%self)")
    public void onDetectStop(CommandSender sender, Player player) {
        DebugScanner.getInstance().stopDetect(player);

        sender.spigot().sendMessage(
                new ComponentBuilder("Successfully Stopped Detect").color(ChatColor.YELLOW).create()
        );
    }

}
