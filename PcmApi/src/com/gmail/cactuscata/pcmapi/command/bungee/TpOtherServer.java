package com.gmail.cactuscata.pcmapi.command.bungee;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.cactuscata.pcmapi.bungee.Bungee;
import com.gmail.cactuscata.pcmapi.enums.PrefixMessage;

public class TpOtherServer implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (args.length < 1) {
			sender.sendMessage(PrefixMessage.ERROR + "Veuilez préciser le nom du joueur !");
			return true;
		}

		Player player = Bukkit.getPlayerExact(args[0]);
		if (player == null || !player.isOnline()) {
			sender.sendMessage(PrefixMessage.ERROR + "Le joueur " + args[0] + " n'est pas en ligne !");
			return true;
		}

		if (args.length < 2) {
			sender.sendMessage(PrefixMessage.ERROR + "Veuillez préciser le server !");
			return true;
		}

		Bungee.teleport(player, args[1]);

		return true;

	}

}
