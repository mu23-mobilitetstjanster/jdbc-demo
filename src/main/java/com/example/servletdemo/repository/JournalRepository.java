package com.example.servletdemo.repository;

import com.example.servletdemo.model.JournalEntry;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Integration mot databas
public class JournalRepository {
  private Connection conn;

  public JournalRepository() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/journaldb", "root", "");
    } catch (ClassNotFoundException | SQLException e) {
      throw new RuntimeException(e);
    }
  }

  public List<JournalEntry> getAll() {
    List<JournalEntry> entries = new ArrayList<>();
    String sql = "SELECT title, content FROM journals";

    try {
      PreparedStatement pstmt = conn.prepareStatement(sql);
      ResultSet rs = pstmt.executeQuery();

      while(rs.next()) {
        JournalEntry entry = new JournalEntry();

        entry.setTitle(rs.getString("title"));
        entry.setContent(rs.getString("content"));

        entries.add(entry);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return entries;
  }

  public List<JournalEntry> getAll(String owner) {
    List<JournalEntry> entries = new ArrayList<>();
    String sql = "SELECT title, content FROM journals WHERE owner=?";

    try {
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, owner);
      ResultSet rs = pstmt.executeQuery();

      while(rs.next()) {
        JournalEntry entry = new JournalEntry();

        entry.setTitle(rs.getString("title"));
        entry.setContent(rs.getString("content"));

        entries.add(entry);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return entries;
  }

  public JournalEntry get(int id) {
    return null;
  }

  public void create(String title, String content) {
    String sql =
            "INSERT INTO journals (title, content)" +
            "VALUES (?, ?)"; // parametriserad fråga (query)

    try {
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, title);
      pstmt.setString(2, content);

      pstmt.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  }

  public boolean delete(String owner, String title) {
    String sql = "DELETE FROM journals WHERE title=? AND owner=?";

    try {
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, title);
      pstmt.setString(2, owner);

      pstmt.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return true;
  }

  public JournalEntry update(JournalEntry entry) {
    return null;
  }
}
