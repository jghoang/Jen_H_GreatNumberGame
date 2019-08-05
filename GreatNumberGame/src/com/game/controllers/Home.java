package com.game.controllers;

import java.io.IOException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String message = "";
		String win = "hide";
		String play = "show";
		HttpSession session = request.getSession();
		if(session.getAttribute("message") != null) {
			message = (String)session.getAttribute("message");
		}
		if(session.getAttribute("win") != null) {
			win = (String)session.getAttribute("win");
		}
		if(session.getAttribute("play") != null) {
			play = (String)session.getAttribute("play");
		}
		request.setAttribute("message", message);
		request.setAttribute("win", win);
		request.setAttribute("play", play);
		RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/index.jsp");
		view.forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession sesh = request.getSession();
		
		if(sesh.getAttribute("answer") == null) {
			Random randomNum = new Random();
			int r = randomNum.nextInt(100) + 1;
			sesh.setAttribute("answer", r);
		}
		int guess = Integer.parseInt(request.getParameter("guess"));
		int answer = (int)sesh.getAttribute("answer");	
		String message = "";
		String messages = "";
		if(guess < answer) {
			message = "Too Low!!!";
			messages = "red";
		}
		else if(guess > answer) {
			message = "Too High!!!";
			messages = "red";
		}
		else {
			message = guess + " was the number!";
			messages = "green";
			sesh.setAttribute("win", "show");
			sesh.setAttribute("play", "hide");
		}
		sesh.setAttribute("message", message);
		sesh.setAttribute("messages", messages);
		response.sendRedirect("/GreatNumberGame/Home");
	}
}