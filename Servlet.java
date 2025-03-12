package org.jspiders.emplApp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class FirstServlet extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//Fetch the UI/Form Data
		String sid=req.getParameter("i");
		int id=Integer.parseInt(sid);
		String name=req.getParameter("nm");
		String dept=req.getParameter("dp");
		String sperc=req.getParameter("pr");
		double perc=Double.parseDouble(sperc);
		
		//Presentation Logic //Servlet Technology
		PrintWriter out=resp.getWriter();
		out.println("<html><body bgcolor='orange'>"
				+ "<h1> Student name is "
				 + name + " from "+ dept + " </h1>"+"</body></html>");
		out.close();
		
		//Persistence Logic //JDBC Technology
		Connection con=null;
		PreparedStatement pstmt=null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=admin");
			pstmt=con.prepareStatement("insert into btm.student values (?,?,?,?)");//DML
			//Set the data
			pstmt.setInt(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3,dept);
			pstmt.setDouble(4, perc);
			
			//Execute
			pstmt.executeUpdate();
			System.out.println("Record saved successfully");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
