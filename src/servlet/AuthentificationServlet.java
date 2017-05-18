package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import data.Authentifi;
/**
 * Servlet implementation class AuthentificationServlet
 */
@WebServlet("/AuthentificationServlet")
public class AuthentificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AuthentificationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		
		System.out.println(Authentifi.newUser("user", "pass"));
		Authentifi.newUser("1", "pass1");
		Authentifi.newUser("1", "pass1");
		Authentifi.newUser("1", "pass1");
		Authentifi.newUser("4", "pass4");
		Authentifi.newUser("5", "pass5");
		
		
		// System.out.println(this.getServletContext().getRealPath("/"));
		
		Authentifi.rmUser("1");
		Authentifi.rmUser("2");
		Authentifi.rmUser("5");
		System.out.println(Authentifi.rmUser("2"));
		PrintWriter out = response.getWriter();
		
		boolean login = Authentifi.valid(user, pass);
		
		Authentifi.userAusgeben();
        
        if(login==true){
            out.println("Welcome user");
        }
        else if(login == false){
        	out.println("Benutzername oder Passwort Falsch!");
        }
        
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
