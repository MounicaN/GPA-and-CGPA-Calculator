package com.college.student.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.college.student.bean.StudentBean;
import com.college.student.service.CalculateGPA;
import com.college.student.util.DBUtil;
/**
 * Servlet implementation class Sort_Servlet
 */
@WebServlet("/Sort_Servlet")
public class Sort_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sort_Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String s = new String();
		boolean flag = true;
		String view = request.getParameter("operation");
		System.out.println("operation-"+view);
		
		//Sort based on Register number
		 if(view.equalsIgnoreCase("regno")){
			sortbyregno(request,response,flag);
		}
		 
		//Sort based on CGPA
		else if(view.equalsIgnoreCase("cgpa")){
			sortbycgpa(request,response);
		}
		 
		//Sort based on Name
		else if(view.equalsIgnoreCase("name")){
				sortbyname(request,response);
		}
		 
		//View student details based on the register number
		else if(view.equalsIgnoreCase("viewStudent")){
			flag = false;
			sortbyregno(request,response,flag);
		}
	}
	
	//Sort by name
	void sortbyname(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try{	
			PrintWriter pw = response.getWriter();
			Connection con = DBUtil.getDBConnection(); 
			PreparedStatement pstmt=con.prepareStatement ("select * from CGPA_TBL order by lower(name)");
			ResultSet rs = pstmt.executeQuery();
			pw.println("<html> <link rel = \"stylesheet\" type = \"text/css\" href = \"CSS_File.css\"><style>td.red{color:red;} td.green{color:green;}</style><body>");
			pw.println("<h1> SORTED DATA BY NAME </h1><table border = \"3\" align=\"center\" bordercolor = \"black\">");
			pw.println("<tr>");
			pw.println("<td>"+"Name"+"</td>");
			pw.println("<td>"+"Register No."+"</td>");
			pw.println("<td>"+"GPA"+"</td>");
			pw.println("<td>"+"P/F"+"</td>");
			pw.println("</tr>");
			while(rs.next()){
				pw.println("<tr>");
				pw.println("<td>"+rs.getString("name")+"</td>");
				pw.println("<td>"+rs.getString("regno")+"</td>");
				pw.println("<td>"+rs.getDouble("cgpa")+"</td>");
				String pf = rs.getString("status");
				if(pf.equalsIgnoreCase("PASS"))
				pw.println("<td class = \"green\">"+rs.getString("status")+"</td>");
				else
				pw.println("<td  class = \"red\">"+rs.getString("status")+"</td>");
				pw.println("</tr>");
			}
			pw.println("<form action = \"Filters.html\"><tr><td></td><td><input type = \"submit\" value = \"<<Back\"></form></td></tr>");
			pw.println("</table> </body> </html>");
	}
		catch(SQLException e){
			System.out.println("SQL Exception occurred!!");
			e.printStackTrace();
		}
		
	}
	
	//Sort by register number
		void sortbyregno(HttpServletRequest request,HttpServletResponse response,boolean flag) throws IOException{
			try{	
				PrintWriter pw = response.getWriter();
				Connection con = DBUtil.getDBConnection(); 
				PreparedStatement pstmt=con.prepareStatement ("select * from CGPA_TBL order by regno");
				ResultSet rs = pstmt.executeQuery();
				pw.println("<html>  <link rel = \"stylesheet\" type = \"text/css\" href = \"CSS_File.css\"><style>td.red{color:red;} td.green{color:green;}</style><body>");
				pw.println("<h1> SORTED DATA BY Regno </h1><table border = \"3\" align=\"center\" bordercolor = \"black\">");
				pw.println("<tr>");
				pw.println("<td>"+"Name"+"</td>");
				pw.println("<td>"+"Register No."+"</td>");
				pw.println("<td>"+"GPA"+"</td>");
				pw.println("<td>"+"P/F"+"</td>");
				pw.println("</tr>");
				while(rs.next()){
					pw.println("<tr>");
					pw.println("<td>"+rs.getString("name")+"</td>");
					pw.println("<td>"+rs.getString("regno")+"</td>");
					pw.println("<td>"+rs.getDouble("cgpa")+"</td>");
					String pf = rs.getString("status");
					if(pf.equalsIgnoreCase("PASS"))
					pw.println("<td class = \"green\">"+rs.getString("status")+"</td>");
					else
					pw.println("<td  class = \"red\">"+rs.getString("status")+"</td>");
					pw.println("</tr>");
				}
				if(flag == true)
				pw.println("<form action = \"Filters.html\"><tr><td></td><td><input type = \"submit\" value = \"<<Back\"></form></td></tr>");
				else
					pw.println("<form action = \"menu.html\"><tr><td></td><td><input type = \"submit\" value = \"<<Back\"></form></td></tr>");
				pw.println("</table> </body> </html>");
		}
			catch(SQLException e){
				System.out.println("SQL Exception occurred!!");
				e.printStackTrace();
			}
			
		}
		
		
		//Sort by CGPA
		void sortbycgpa(HttpServletRequest request,HttpServletResponse response) throws IOException{
			try{	
				PrintWriter pw = response.getWriter();
				Connection con = DBUtil.getDBConnection(); 
				PreparedStatement pstmt=con.prepareStatement ("select * from CGPA_TBL order by gpa desc");
				ResultSet rs = pstmt.executeQuery();
				pw.println("<html>  <link rel = \"stylesheet\" type = \"text/css\" href = \"CSS_File.css\"> <style>td.red{color:red;} td.green{color:green;}</style><body>");
				pw.println("<h1> SORTED DATA BY CGPA </h1><table border = \"3\" align=\"center\" bordercolor = \"black\">");
				pw.println("<tr>");
				pw.println("<td>"+"name"+"</td>");
				pw.println("<td>"+"Register No."+"</td>");
				pw.println("<td>"+"GPA"+"</td>");
				pw.println("<td>"+"P/F"+"</td>");
				pw.println("</tr>");
				while(rs.next()){
					pw.println("<tr>");
					pw.println("<td>"+rs.getString("name")+"</td>");
					pw.println("<td>"+rs.getString("regno")+"</td>");
					pw.println("<td>"+rs.getDouble("cgpa")+"</td>");
					String pf = rs.getString("status");
					if(pf.equalsIgnoreCase("PASS"))
					pw.println("<td class = \"green\">"+rs.getString("status")+"</td>");
					else
					pw.println("<td  class = \"red\">"+rs.getString("status")+"</td>");
					pw.println("</tr>");
				}
				pw.println("<form action = \"Filters.html\"><tr><td></td><td><input type = \"submit\" value = \"<<Back\"></form></td></tr>");
				pw.println("</table> </body> </html>");
		}
			catch(SQLException e){
				System.out.println("SQL Exception occurred!!");
				e.printStackTrace();
			}
			
		}
}
