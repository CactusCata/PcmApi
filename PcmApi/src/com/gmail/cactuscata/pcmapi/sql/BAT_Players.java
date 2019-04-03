package com.gmail.cactuscata.pcmapi.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.entity.Player;

public class BAT_Players {

	private final Connection connection;

	public BAT_Players(Connection connection) {
		this.connection = connection;
	}

	public void addPlayer(Player player) throws SQLException {

		String query = "INSERT INTO `cactus`.`BAT_players` (`BAT_player` ,`UUID` ,`lastip` ,`firstlogin` ,`lastlogin` ,`grade` ,`nbr_votes` ,`nbr_vote_total` ,`vote_time`)VALUES ('"
				+ player.getName() + "', '" + player.getUniqueId().toString().replace("-", "") + "', '"
				+ player.getAddress().getHostName()
				+ "', NOW( ) ,CURRENT_TIMESTAMP , 'null', '0', '0', '0000-00-00 00:00:00');";
		PreparedStatement preparedStmt = connection.prepareStatement(query);
		preparedStmt.execute();

	}

	public void updateLastConnection(Player player) throws SQLException {

		PreparedStatement ps = connection.prepareStatement(
				"UPDATE BAT_players SET lastlogin = CURRENT_TIMESTAMP WHERE BAT_player='" + player.getName() + "'");
		ps.executeUpdate();

	}

	public void updateNewPlayerName(Player player) throws SQLException {

		PreparedStatement ps = connection.prepareStatement("UPDATE BAT_players SET BAT_player = '" + player.getName()
				+ "' WHERE UUID='" + player.getUniqueId().toString().replace("-", "") + "'");
		ps.executeUpdate();

	}

}
