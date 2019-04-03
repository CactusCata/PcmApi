package com.gmail.cactuscata.pcmapi.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BAT_staff {

	public boolean isStaff(Player player, Connection connection) throws SQLException {

		PreparedStatement ps = connection.prepareStatement(
				"SELECT * FROM `BAT_staff` WHERE `UUID` = '" + player.getUniqueId().toString().replace("-", "") + "'");
		ResultSet rs = ps.executeQuery();

		if (!rs.next() || rs.getString("grade").equals("null"))
			// n'a pas de grade
			return false;
		return true;
	}

	public String getStaff(Player player, Connection connection) throws SQLException {

		PreparedStatement ps = connection.prepareStatement(
				"SELECT * FROM `BAT_staff` WHERE `UUID` = '" + player.getUniqueId().toString().replace("-", "") + "'");
		ResultSet rs = ps.executeQuery();
		System.out.println(rs.getString("grade"));
		return rs.getString("grade");

	}

	public void updateNewPlayerName(Player player, Connection connection) throws SQLException {

		PreparedStatement ps = connection.prepareStatement("UPDATE BAT_staff SET player = '" + player.getName()
				+ "' WHERE UUID='" + player.getUniqueId().toString().replace("-", "") + "'");
		ps.executeUpdate();

	}

	public void setNewRank(Player player, String rank, Connection connection) throws SQLException {

		if (isStaff(player, connection)) {
			PreparedStatement ps = connection.prepareStatement("UPDATE BAT_staff SET grade = '" + player.getName()
					+ "' WHERE UUID='" + player.getUniqueId().toString().replace("-", "") + "'");
			ps.executeUpdate();
		} else {

			String query = "INSERT INTO `cactus`.`BAT_staff` (`uuid` ,`player` ,`grade` ,`specificite` ,`commentaire` ,`date` ) VALUES ('"
					+ player.getUniqueId().toString().replace("-", "") + "', '" + player.getName() + "', '" + rank
					+ "', '' ,'' , CURRENT_TIMESTAMP);";
			PreparedStatement preparedStmt = connection.prepareStatement(query);
			preparedStmt.execute();

		}

	}

}
