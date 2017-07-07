package com.college.student.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.college.student.bean.StudentBean;
import com.college.student.bean.SubjectBean;
import com.college.student.dao.StudentDao;


import com.college.student.bean.StudentBean;
import com.college.student.service.CalculateGPA;
import com.college.student.util.DBUtil;
/**
 * Servlet implementation class Operations_Servlet
 */
@WebServlet("/Operations_Servlet")

public class Operations_Servlet extends HttpServlet {
	public static  int credits[] = new int[100];
	static String regno;
	static int sem;
	static String subjects[] = new String[6];
	private static final long serialVersionUID = 1L;
	static String cgpa;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Operations_Servlet() {
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
		 PrintWriter pw = response.getWriter();
		 response.setContentType("text/html");
		String opr = request.getParameter("operation");
		String s= new String();
		System.out.println("opr-"+opr);
		SubjectBean subb = new SubjectBean();
		
		//Logging out
		if(opr.equalsIgnoreCase("LogOut")){
			flushTable(request,response);
		}
		
		//View the student records based on the register number
		else if(opr.equalsIgnoreCase("viewStudent")){
			System.out.println("cond-"+opr);
			 RequestDispatcher rd = request.getRequestDispatcher("Sort_Servlet");
			 rd.forward(request, response);
		}
		
		//Add the subjects for each semester
		else if(opr.equalsIgnoreCase("AddSubjects")){
			 try {
				 addsubjects(request,response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				 RequestDispatcher rd = request.getRequestDispatcher("Student_Details.html");
				 rd.forward(request, response);
			 }
		
		//Calculates the GPA
		else if(opr.equalsIgnoreCase("CalculateGPA")){
			subb.setCredit1(credits[0]);
			subb.setCredit2(credits[1]);
			subb.setCredit3(credits[2]);
			subb.setCredit4(credits[3]);
			subb.setCredit5(credits[4]);
			subb.setCredit6(credits[5]);
			System.out.println("subj-"+subb.getSubject1());
			 try {
				s = addstudents(request,credits,subjects);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 System.out.println("s="+s);
			 pw.println(s);
		}
		
		//Calculates the CGPA by fetching the previous GPAs and manipulating
		else if(opr.equalsIgnoreCase("CalculateCGPA")){
			pw.println(cgpa);
			 }
		
		//Deletes the student record permanently
		else if(opr.equalsIgnoreCase("DeleteStudent")){
			System.out.println("func");
			deleteStudent(request,response);
		}
		
		//Displays the student and the grades of each subject obtained in the current semester
		else if(opr.equalsIgnoreCase("Student & Subject Details")){
			Connection con;
			PreparedStatement pstmt;
			ResultSet rset;
			try{
				con = DBUtil.getDBConnection();
				pstmt = con.prepareStatement("Select * from Subject_TBL");
				ResultSet rs = pstmt.executeQuery();
				pw.println("<html>  <link rel = \"stylesheet\" type = \"text/css\" href = \"CSS_File.css\"> <body>");
				pw.println("<h1> STUDENT DETAILS </h1><table border = \"3\" align=\"center\" bordercolor = \"black\">");
				pw.println("<tr>");
				pw.println("<th>"+"Name"+"</td>");
				pw.println("<th>"+"Regno."+"</td>");
				pw.println("<th>"+subjects[0]+"</td>");
				pw.println("<th>"+subjects[1]+"</td>");
				pw.println("<th>"+subjects[2]+"</td>");
				pw.println("<th>"+subjects[3]+"</td>");
				pw.println("<th>"+subjects[4]+"</td>");
				pw.println("<th>"+subjects[5]+"</td>");
				pw.println("</tr><tr>");
				while(rs.next()){
					pw.println("<td>"+rs.getString("name")+"</td>");
					pw.println("<td>"+rs.getString("regno")+"</td>");
					if(rs.getString("grade1").equalsIgnoreCase("U"))
						pw.println("<td style = \"color:red\">"+rs.getString("grade1")+"</td>");
						else
						pw.println("<td style = \"color:green\">"+rs.getString("grade1")+"</td>");
					if(rs.getString("grade2").equalsIgnoreCase("U"))
						pw.println("<td style = \"color:red\">"+rs.getString("grade2")+"</td>");
						else
						pw.println("<td style = \"color:green\">"+rs.getString("grade2")+"</td>");
					if(rs.getString("grade3").equalsIgnoreCase("U"))
						pw.println("<td style = \"color:red\">"+rs.getString("grade3")+"</td>");
						else
						pw.println("<td style = \"color:green\">"+rs.getString("grade3")+"</td>");
					if(rs.getString("grade4").equalsIgnoreCase("U"))
						pw.println("<td style = \"color:red\">"+rs.getString("grade4")+"</td>");
						else
						pw.println("<td style = \"color:green\">"+rs.getString("grade4")+"</td>");
					if(rs.getString("grade5").equalsIgnoreCase("U"))
						pw.println("<td style = \"color:red\">"+rs.getString("grade5")+"</td>");
						else
						pw.println("<td style = \"color:green\">"+rs.getString("grade5")+"</td>");
					if(rs.getString("grade6").equalsIgnoreCase("U"))
						pw.println("<td style = \"color:red\">"+rs.getString("grade6")+"</td>");
						else
						pw.println("<td style = \"color:green\">"+rs.getString("grade6")+"</td>");
					pw.println("</tr>");
				}
				pw.println("<form action = \"Filters.html\"><tr><td><input type = \"submit\" value = \"<<Back\"></form>");
				pw.println("<form action = \"Student_Details.html\">");
				pw.println("<input type = \"submit\" value = \"AddStudent\"></form>");
				pw.println("<form method = \"post\" action = \"http://localhost:8080/GPA_Calculator/Operations_Servlet\">");
				pw.println("<input type = \"text\" name = \"regno\">");
				pw.println("<input type = \"submit\" name = \"operation\" value = \"DeleteStudent\"></td><tr></form>");
				pw.println("</table> </body> </html>");
			}
			catch(SQLException e){
				System.out.println("SQL Exception!!");
				e.printStackTrace();
			}
		}
		pw.close();
		}
	
	
	void addsubjects(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException, SQLException{
		Connection con;
		PreparedStatement pstmt;
		ResultSet rset;
		SubjectBean subb = new SubjectBean();
		int  p = 0;
		sem = Integer.parseInt(request.getParameter("semester"));	
		con = DBUtil.getDBConnection();
		
		//To check whether the redundant semester number is trying to be added
		pstmt = con.prepareStatement("select count(*) from SEM where semester = ?");
		pstmt.setInt(1,sem);
		rset = pstmt.executeQuery();
		int n = 0;
		while(rset.next()){
			n = rset.getInt(1);
		}
		System.out.println(n);
		if(n > 0){
			PrintWriter pw = response.getWriter();
			 response.setContentType("text/html");
			pw.print("<script>alert('semester "+sem+" marks are already exist!!'); window.location = \"Subject_Details.html\";</script>");
			pw.close();
		}
		else{
			pstmt = con.prepareStatement("Insert into SEM values(?)");
			pstmt.setInt(1,sem);
			pstmt.executeUpdate();
		}
		subjects[0] = request.getParameter("subj1");
		subjects[1] = request.getParameter("subj2");
		subjects[2] = request.getParameter("subj3");
		subjects[3] = request.getParameter("subj4");
		subjects[4] = request.getParameter("subj5");
		subjects[5] = request.getParameter("subj6");
		 credits[0] = Integer.parseInt(request.getParameter("cred1"));
		 credits[1] = Integer.parseInt(request.getParameter("cred2"));
		 credits[2] = Integer.parseInt(request.getParameter("cred3"));
		 credits[3] = Integer.parseInt(request.getParameter("cred4"));
		 credits[4] = Integer.parseInt(request.getParameter("cred5"));
		 credits[5] = Integer.parseInt(request.getParameter("cred6"));
	}
	
	
	String addstudents(HttpServletRequest request,int[] credits,String[] subjects) throws SQLException{
		StudentBean stub = new StudentBean();
		String s[] = new String[2];
		stub.setName(request.getParameter("name"));
		stub.setRegno(request.getParameter("regno"));
		stub.setGrad1(request.getParameter("g1").charAt(0));
		stub.setGrad2(request.getParameter("g2").charAt(0));
		stub.setGrad3(request.getParameter("g3").charAt(0));
		stub.setGrad4(request.getParameter("g4").charAt(0));
		stub.setGrad5(request.getParameter("g5").charAt(0));
		stub.setGrad6(request.getParameter("g6").charAt(0));
		regno = stub.getRegno();
		s = StudentDao.addstudents(stub,credits,subjects,sem);
		cgpa = s[1];
		System.out.println("cgpa="+cgpa);
		return s[0];		
	}
	
	//Deleting the student whose record is already present, if so success else shows failure
	void deleteStudent(HttpServletRequest request,HttpServletResponse response) throws IOException{
		PrintWriter pw = response.getWriter();
		Connection con;
		PreparedStatement pstmt;
		ResultSet rset;
		int n = 0;
		String regno = request.getParameter("regno");
		try{
		con = DBUtil.getDBConnection();
		pstmt = con.prepareStatement("delete SUBJECT_TBL where regno = ?");
		pstmt.setString(1,regno);
		n = pstmt.executeUpdate();
		System.out.println("**");
		if(n > 0){
			pw.print("<script>alert('Record Deleted Successfully!!'); window.location = \"Filters.html\";</script>");
		}
		else{
			pw.print("<script>alert('Record not Found!!'); window.location = \"Filters.html\";</script>");
		}
		pw.close();
	}
		catch(SQLException e){
			System.out.println("SQL Exception!!");
			e.printStackTrace();
		}
	}
	
	//After log out the current semester subject table will be truncated
	void flushTable(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
		PrintWriter pw = response.getWriter();
		Connection con;
		PreparedStatement pstmt;
		pw.println("<script>alert('Thank you!! Logged out Successfully!!');</script>");
		try{
			con = DBUtil.getDBConnection();
			pstmt = con.prepareStatement("Truncate table SUBJECT_TBL");
			pstmt.executeUpdate();
		}
		catch(SQLException e){
			System.out.println("SQL Exception!!");
			e.printStackTrace();
		}
		 RequestDispatcher rd = request.getRequestDispatcher("Welcome.html");
		 rd.forward(request, response);
	}
	
}
	