package com.gmail.cactuscata.pcmapi.bungee;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.gmail.cactuscata.pcmapi.PcmApi;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class Bungee implements PluginMessageListener {

	private static PcmApi plugin;
	private static final HashMap<String, Integer> serverPlayersNumber = new HashMap<>();

	public Bungee(final PcmApi plugin) {
		Bungee.plugin = plugin;
	}

	public final void initialize() {
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
		plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);
		plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BUtils", this);
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		// if (!channel.equals("BUtils")) {
		// return;
		// }

		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();

		if (channel.equals("BUtils")) {
			if (subchannel.equals("Connect")) {

				if ("teleportTo".equals(subchannel)) {
					Player target = Bukkit.getPlayer(in.readUTF());
					if (target != null) {
						player.teleport(target);
						return;
					}

				}
			}
		}

		if (subchannel.equals("PlayerCount")) {
			String server = in.readUTF();
			int playerCount = in.readInt();
			serverPlayersNumber.put(server, playerCount);
		}

	}

	public static void teleport(Player player, String server) {

		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);

		try {
			out.writeUTF("Connect");
			out.writeUTF(server);
		} catch (IOException e) {
			e.printStackTrace();
		}

		player.sendPluginMessage(PcmApi.getInstance(), "BungeeCord", b.toByteArray());

	}

	private static void updateCount(Player player, String server) {

		if (server == null)
			server = "ALL";

		ByteArrayDataOutput out = ByteStreams.newDataOutput();

		out.writeUTF("PlayerCount");
		out.writeUTF(server);

		player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());

	}

	public static int getCount(Player player, String server) {
		updateCount(player, server);
		return serverPlayersNumber.get(server);
	}

}
