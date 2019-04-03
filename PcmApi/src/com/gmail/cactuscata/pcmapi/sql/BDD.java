package com.gmail.cactuscata.pcmapi.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.bukkit.Bukkit;

import com.gmail.cactuscata.pcmapi.PcmApi;

public class BDD {
	public Connection laConnection;
	public String host;
	public String dbname;
	public String user;
	public String password;

	public BDD(String host, String dbname, String user, String password) {
		this.host = host;
		this.dbname = dbname;
		this.user = user;
		this.password = password;

		connect();
	}

	public boolean connect() {
		try {
			if ((this.laConnection == null) || (this.laConnection.isClosed())) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
					this.laConnection = DriverManager.getConnection(
							"jdbc:mysql://" + this.host + ":3306/" + this.dbname + "?autoReconnect=true", this.user,
							this.password);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			return true;
		} catch (Exception e) {
			System.out.print("Une erreur est survenue ! #1 : " + e.getMessage());
		}
		return false;
	}

	public ResultSet request(String query) {
		if (connect()) {
			try {
				Statement transmission = this.laConnection.createStatement();
				return transmission.executeQuery(query);
			} catch (Exception e) {
				System.out.print("Une erreur est survenue ! #2 : " + e.getMessage());
				return null;
			}
		}
		return null;
	}

	public boolean transmission(String query) {
		if (connect()) {
			try {
				PreparedStatement updateemp = this.laConnection.prepareStatement(query);
				updateemp.executeUpdate();
				return true;
			} catch (Exception e) {
				System.out.print("Une erreur est survenue ! #3 : " + e.getLocalizedMessage());
				return false;
			}
		}
		return false;
	}

	public void AsyncRequest(final String query, final PcmApi.Callback<ResultSet> callback) {
		if (connect()) {
			Bukkit.getScheduler().runTaskAsynchronously(PcmApi.getInstance(), new Runnable() {
				public void run() {
					try {
						Statement transmission = BDD.this.laConnection.createStatement();
						final ResultSet req = transmission.executeQuery(query);
						Bukkit.getScheduler().runTask(PcmApi.getInstance(), new Runnable() {
							public void run() {
								callback.onSuccess(req);
							}
						});
					} catch (Exception e) {
						Bukkit.getScheduler().runTask(PcmApi.getInstance(), new Runnable() {
							public void run() {
								callback.onFailure(e);
							}
						});
					}
				}
			});
		}
	}

	public void AsyncTransmission(final String query, final PcmApi.Callback<Integer> callback) {
		if (connect()) {
			Bukkit.getScheduler().runTaskAsynchronously(PcmApi.getInstance(), new Runnable() {
				public void run() {
					try {
						PreparedStatement updateemp = BDD.this.laConnection.prepareStatement(query);
						final Integer result = Integer.valueOf(updateemp.executeUpdate());
						Bukkit.getScheduler().runTask(PcmApi.getInstance(), new Runnable() {
							public void run() {
								callback.onSuccess(result);
							}
						});
					} catch (Exception e) {
						Bukkit.getScheduler().runTask(PcmApi.getInstance(), new Runnable() {
							public void run() {
								callback.onFailure(e);
							}
						});
					}
				}
			});
		}
	}
}
