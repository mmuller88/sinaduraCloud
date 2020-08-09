package net.zylk.sinadura.cloud.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.zylk.sinadura.cloud.dao.DaoTransactionFactory;
import net.zylk.sinadura.cloud.exceptions.DaoCoreException;


public class DaoCheckerServlet extends HttpServlet {

	private static final Logger _LOG = Logger.getLogger(DaoCheckerServlet.class.getName());
	
	private static final long serialVersionUID = 1L;

	
	public DaoCheckerServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			request.setAttribute("transactions", DaoTransactionFactory.getInstance().getAll());
			request.getRequestDispatcher("/jsp/active-transactions.jsp").forward(request, response);
			
		} catch (DaoCoreException e) {
			throw new ServletException(e);
		}
		
	}
	
}
