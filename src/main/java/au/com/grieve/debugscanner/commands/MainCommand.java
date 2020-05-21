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


@Command("debugscanner|ds")
public class MainCommand extends BukkitCommand {

    @Arg("start @int(switch=interval|i,min=1,default=2) @float(switch=pitch|p,default=45) @float(switch=yaw|y,default=0) @int(switch=start,min=1,default=1)")
    public void onStart(CommandSender sender, Integer ticks, Float pitch, Float yaw, Integer start) {
        try {
            DebugScanner.getInstance().start(start, ticks, pitch, yaw);
        } catch (DebugScanner.DebugScannerException e) {
            sender.spigot().sendMessage(
                    new ComponentBuilder(e.getMessage()).color(ChatColor.RED).create()
            );
        }
        // Don't send any response since we don't want to obscure the screen with text
//        sender.spigot().sendMessage(
//                new ComponentBuilder("Started").color(ChatColor.YELLOW).create()
//        );
    }

    @Arg("stop")
    public void onStop(CommandSender sender) {
        try {
            DebugScanner.getInstance().stop();
        } catch (DebugScanner.DebugScannerException e) {
            sender.spigot().sendMessage(
                    new ComponentBuilder(e.getMessage()).color(ChatColor.RED).create()
            );
            return;
        }
        sender.spigot().sendMessage(
                new ComponentBuilder("Successfully Stopped").color(ChatColor.YELLOW).create()
        );
    }

}
