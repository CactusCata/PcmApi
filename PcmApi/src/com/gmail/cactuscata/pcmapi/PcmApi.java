package com.gmail.cactuscata.pcmapi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.cactuscata.pcmapi.bungee.Bungee;
import com.gmail.cactuscata.pcmapi.command.bungee.TpOtherServer;
import com.gmail.cactuscata.pcmapi.command.other.Settag;
import com.gmail.cactuscata.pcmapi.command.other.TitleApi;
import com.gmail.cactuscata.pcmapi.enums.PrefixMessage;
import com.gmail.cactuscata.pcmapi.listeners.JoinEvent;
import com.gmail.cactuscata.pcmapi.listeners.Other;
import com.gmail.cactuscata.pcmapi.listeners.QuitEvent;
import com.gmail.cactuscata.pcmapi.sql.BDD;
import com.gmail.cactuscata.pcmapi.utils.FileRestor;

public class PcmApi extends JavaPlugin {

	private static PcmApi plugin;
	private static Connection connexion;
	public HashMap<Player, Runnable> confirm_success = new HashMap<>();
	public HashMap<Player, Runnable> confirm_cancel = new HashMap<>();

	public void onEnable() {

		plugin = this;
		saveDefaultConfig();

		PluginManager pm = this.getServer().getPluginManager();

		new Bungee(this).initialize();
		new FileRestor(this).initialize();

		pm.registerEvents(new JoinEvent(), this);
		pm.registerEvents(new Other(), this);
		pm.registerEvents(new QuitEvent(), this);

		getCommand("tpotherserver").setExecutor(new TpOtherServer());
		getCommand("settag").setExecutor(new Settag());
		getCommand("titleapi").setExecutor(new TitleApi());

		try {
			Class.forName("com.mysql.jdbc.Driver");
			connexion = DriverManager.getConnection("jdbc:mysql://2.pcm.ovh/cactus", "cactus", "HrvvMv3z");
		} catch (Exception e) {
			e.printStackTrace();
		}

		timeToWaitBeforeWhitelist();

	}

	public static PcmApi getInstance() {
		return plugin;
	}

	private void timeToWaitBeforeWhitelist() {

		final int i = plugin.getConfig().getInt("TimeToDisableWhitelist");

		if (i == -1)
			return;

		getServer().setWhitelist(true);
		Bukkit.broadcastMessage(PrefixMessage.PREFIX + "La whitelist est activé !");

		new BukkitRunnable() {

			@Override
			public void run() {

				Bukkit.broadcastMessage(PrefixMessage.PREFIX + "La whitelist est désactivé !");
				plugin.getServer().setWhitelist(false);

			}
		}.runTaskLater(plugin, i * 20L);

	}

	public static Connection getConnexion() {
		return connexion;
	}

	public BDD create_connection(String host, String dbname, String user, String password) {
		return new BDD(host, dbname, user, password);
	}

	public static abstract interface Callback<T> {
		public abstract void onSuccess(T paramT);

		public abstract void onFailure(Throwable paramThrowable);
	}

}
