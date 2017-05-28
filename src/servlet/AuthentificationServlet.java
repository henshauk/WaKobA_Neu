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
	/*protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet sollte nicht verwendet werden zur Authentifizierung (Passwort würde in der URL stehen)
	}*/

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");

		//Authentifi.deleteFile(getServletConfig().getServletContext().getRealPath("/WEB-INF"));
		Authentifi.setFile(getServletConfig().getServletContext().getRealPath("/WEB-INF"));
		
		//Authentifi.rmAllUser();
		//System.out.println(Authentifi.newUser("admin", "admin"));
		
		System.out.println();System.out.println("Login.txt:");
		Authentifi.userAusgeben();
		System.out.println("Login.txt Ende");System.out.println();
		
		
		String user = request.getParameter("user");
		String pass = request.getParameter("pass");
		boolean login = Authentifi.valid(user, pass);
		

		//PrintWriter out = response.getWriter();
		
		
		
		//Authentifi.userAusgeben();
        
        if(login==true){
            System.out.println("Welcome user");
            response.sendRedirect("upload.html");
        }
        else if(login == false){
        	response.sendRedirect("login.html");
        	System.out.println("Benutzername oder Passwort Falsch!");
        }
        
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

}
