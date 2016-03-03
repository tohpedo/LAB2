package servlets;


import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EchoServlet
 */
@WebServlet(name = "TodoServlet",
description = "To do servlet",
urlPatterns = {"/Todo","/TodoServlet"})
public class TodoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ConcurrentHashMap<Integer, String> data;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	private String message;
	
    public TodoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init() throws ServletException
    {
        System.out.println("in init");
        data = new ConcurrentHashMap<Integer, String>() ;
        message = "Hello World";
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		if(request.getParameter("id").equals(null)){
			Enumeration<Integer> iter = data.keys();
			while(iter.hasMoreElements()){
				 out.println("<h1>" + data.get(iter.nextElement()) + "</h1>");
				}
			}
		int my_id = Integer.parseInt(request.getParameter("id")); 
		String message = data.get(my_id);
		response.setContentType("text/html");
	    out.println("<h1>" + message + "</h1>");
	}

	
	protected void doDelete(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
		int my_id = Integer.parseInt(request.getParameter("id"));
		data.remove(my_id);
	}
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("in Post");
		String my_message = request.getParameter("message");
		int my_id = Integer.parseInt(request.getParameter("id"));
		this.data.put(my_id, my_message);
		
		
		
		
	}

}
