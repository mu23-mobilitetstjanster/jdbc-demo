package com.example.servletdemo.service;

import com.example.servletdemo.model.JournalEntry;
import com.example.servletdemo.repository.JournalRepository;

import java.util.List;

// Inne i service hanteras vanligen undantag och fel i den in eller ut data som skickas eller tas emot
public class JournalService {

  private JournalRepository journalRepository = new JournalRepository();

  public List<JournalEntry> getAll() {
    return journalRepository.getAll();
  }

  public JournalEntry get(int id) {
    return null;
  }

  public void create(String title, String content) {
    journalRepository.create(title, content);
  }

  public boolean delete() {
    return false;
  }

  public JournalEntry update(JournalEntry entry) {
    return null;
  }
}
