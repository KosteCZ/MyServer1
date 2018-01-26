package cz.koscak.jan.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class ApplicationInitializationServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		
		try {
			System.out.println("Initializing MyServer...");
			Application.initialize();
		} catch (IOException e) {
			System.err.println("ERROR: During initializing application: " + e.getMessage());
			e.printStackTrace();
			throw new ServletException(e);
		}
		
	}

}
