package com.gmail.cactuscata.pcmapi.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import com.gmail.cactuscata.pcmapi.sql.BAT_staff;

public class DisplayTag {

	private final BAT_staff batstaff;
	private static HashMap<Player, StaffList> rankMap = new HashMap<>();

	public DisplayTag() {
		this.batstaff = new BAT_staff();
	}

	public void setGradeConnect(Player player, Connection connection) throws SQLException {

		boolean b = false;

		StaffList staff = StaffList.NULL;

		if (batstaff.isStaff(player, connection)) {

			staff = StaffList.getStaff(batstaff.getStaff(player, connection));
			System.out.println(batstaff.getStaff(player, connection));
			b = true;

		}

		if (!b || staff == StaffList.NULL)
			staff = StaffList.AUCUN;

		createTeamSystem(player, staff);

	}

	public void createBasicRank(Player player, String prefix, String suffix) {

		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();

		createTeam(player.getName(), board);
		joinTeam("", player.getName(), board);
		Team team = board.getTeam(player.getName());

		team.setPrefix(prefix);
		team.setSuffix(suffix);

	}

	public void changeRank(Player player, String rank, Connection connection) throws SQLException {

		removeTeam(player);
		createTeamSystem(player, StaffList.getStaff(rank));
		PreparedStatement ps = connection.prepareStatement(
				"UPDATE BAT_staff SET grade = '" + rank.toLowerCase() + "' WHERE player='" + player.getName() + "'");
		ps.executeUpdate();

	}

	public void removeTeam(Player player) {

		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();

		Team team = board.getTeam(rankMap.get(player).getPower() + player.getName());

		if (team != null)
			team.unregister();

		rankMap.remove(player);

	}

	private void createTeamSystem(Player player, StaffList staff) {

		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();

		createTeam(staff.getPower() + player.getName(), board);
		joinTeam(staff.getPower() + "", player.getName(), board);
		Team team = board.getTeam(staff.getPower() + player.getName());

		new Runnable() {

			@Override
			public void run() {
				team.setPrefix(staff.getPrefix() + "caca");
				team.setPrefix(staff.getSuffix());

			}
		};

		System.out.println(staff.getPrefix());
		System.out.println(staff.getSuffix());
		rankMap.put(player, staff);

	}

	private void createTeam(String NameOfPlayer, Scoreboard board) {
		if (board.getTeam(NameOfPlayer) == null)
			board.registerNewTeam(NameOfPlayer);
	}

	private void joinTeam(String power, String NameOfPlayer, Scoreboard board) {

		Team team = board.getTeam(power + NameOfPlayer);
		Player player = Bukkit.getPlayerExact(NameOfPlayer);
		team.addEntry(player.getName());

	}

	public static HashMap<Player, StaffList> getMap() {
		return rankMap;
	}

}
