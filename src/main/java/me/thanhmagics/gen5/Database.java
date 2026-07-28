package me.thanhmagics.gen5;

import java.sql.*;
import java.util.*;

public class Database implements AutoCloseable {

    private final Connection conn;

    public Database(String dbFile) {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try (Statement st = conn.createStatement()) {
            st.execute("PRAGMA journal_mode = WAL");
            st.executeUpdate("""
                CREATE TABLE IF NOT EXISTS words (
                  origin TEXT    PRIMARY KEY,
                  level  INTEGER NOT NULL DEFAULT 0
                )""");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addWord(String origin) {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO words(origin) VALUES(?)")) {
            ps.setString(1, origin);
            ps.executeUpdate();
        } catch (SQLException ignored) {}
    }

    public boolean deleteWord(String origin) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM words WHERE origin=?")) {
            ps.setString(1, origin);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Integer find(String origin) {
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM words WHERE origin=?")) {
            ps.setString(1, origin);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("level") : 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void shift(String origin, int delta) {
        try (PreparedStatement ps = conn.prepareStatement(
                "UPDATE words SET level = ? WHERE origin = ?")) {
            ps.setInt(1, find(origin) + delta);
            ps.setString(2, origin);
            if (ps.executeUpdate() == 0) System.out.println("Word not found: " + origin);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> toList() {
        List<String> list = new ArrayList<>();
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM words ORDER BY origin")) {
            while (rs.next()) list.add(rs.getString("origin"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Map<String,Integer> toMap() {
        Map<String,Integer> rs = new HashMap<>();
        try {
            Statement st = conn.createStatement();
            ResultSet resultSet = st.executeQuery("SELECT * FROM words");
            while (resultSet.next()) {
                rs.put(resultSet.getString("origin"), resultSet.getInt("level"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rs;
    }


    @Override
    public void close() throws Exception {
        if (conn != null && !conn.isClosed()) conn.close();
    }

    public static void main(String[] args) {
        Database database = new Database("database.db");
        for (String s : database.toList()) {
            database.shift(s, -10);
            System.out.println(s + " : " + database.find(s));
        }
    }
}
