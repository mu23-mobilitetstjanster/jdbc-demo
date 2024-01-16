package com.example.servletdemo.controller;

import com.example.servletdemo.model.JournalEntry;
import com.example.servletdemo.service.JournalService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@WebServlet("/journal/*")
public class JournalServlet extends HttpServlet {

  private JournalService journalService = new JournalService();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession(true);
    String username = (String) session.getAttribute("username");

    if(username == null) {
      resp.sendRedirect("/login.jsp");
    } else {
      req.getRequestDispatcher("/index.jsp").include(req, resp);

      List<JournalEntry> entries = journalService.getAll();

      PrintWriter out = resp.getWriter();
      for(JournalEntry entry : entries) {
        out.println("<br>Title:" + entry.getTitle() + "<br>Content:" + entry.getContent());
      }
    }
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    switch (req.getPathInfo()) {
      case "/": createEntry(req, resp); break;
      case "/delete": deleteEntry(req, resp); break;
    }
  }

  private void deleteEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession(true);
    String username = (String) session.getAttribute("username");

    String title = req.getParameter("title");

    if(username == null) {
      resp.sendRedirect("/login.jsp");
    } else {
      journalService.delete(username, title);
    }
  }

  private void createEntry(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    HttpSession session = req.getSession(true);
    String username = (String) session.getAttribute("username");

    if(username == null) {
      resp.sendRedirect("/login.jsp");
    } else {
      String title = req.getParameter("title");
      String content = req.getParameter("content");

      journalService.create(title, content);

      resp.sendRedirect(req.getServletPath());
    }
  }
}
