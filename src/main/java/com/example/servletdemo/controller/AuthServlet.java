package com.example.servletdemo.controller;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//url: /auth/login, /auth/logout, /auth/register
@WebServlet("/auth/*")
public class AuthServlet extends HttpServlet {

  private Map<String, String> db = new HashMap<String, String>() {{
    put("bob", "123");
    put("Yves", "pass");
  }};

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    switch(req.getPathInfo()) {
      case "/login": login(req, resp); break;
      case "/logout": logout(req, resp); break;
    }
  }

  private void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    HttpSession session = req.getSession(true);

    session.setAttribute("username", null); // nollställ "state" för inloggade användare
    session.invalidate(); // nollställ session

    resp.sendRedirect("/login.jsp"); //dirigera om användaren till login sidan
  }

  private void login(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
    String username = req.getParameter("username");
    String password = req.getParameter("password");

    if(db.get(username) == null) { // användaren fanns inte i databasen
      req.getRequestDispatcher("/register.jsp").forward(req, resp);
    } else if(db.get(username).equals(password)) { // användaren fanns och rätt lössenord angavs
      HttpSession session = req.getSession(true);
      session.setAttribute("username", username);

      resp.sendRedirect("/journal");
    } else { // användaren fanns men fel lössenord
      resp.sendRedirect("/login.jsp?error=invalid%20login");
    }
  }
}
