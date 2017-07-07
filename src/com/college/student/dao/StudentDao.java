package com.college.student.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.college.student.bean.StudentBean;
import com.college.student.service.CalculateGPA;
import com.college.student.util.DBUtil;

public class StudentDao {
	
	
	public static String[] addstudents(StudentBean stub,int credits[],String[] subjects,int sem){
		Connection con;
		PreparedStatement pstmt;
		ResultSet rset;
		int n = 0;
		try{
			//Inserting the student's subjects and grades details
			con = DBUtil.getDBConnection();
			pstmt = con.prepareStatement("insert into SUBJECT_TBL values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			pstmt.setString(1, stub.getName());
			pstmt.setString(2, stub.getRegno());
			pstmt.setString(3, subjects[0]);
			pstmt.setString(4, subjects[1]);
			pstmt.setString(5, subjects[2]);
			pstmt.setString(6, subjects[3]);
			pstmt.setString(7, subjects[4]);
			pstmt.setString(8, subjects[5]);
			pstmt.setString(9, stub.getGrad1()+"");
			pstmt.setString(10, stub.getGrad2()+"");
			pstmt.setString(11, stub.getGrad3()+"");
			pstmt.setString(12, stub.getGrad4()+"");
			pstmt.setString(13, stub.getGrad5()+"");
			pstmt.setString(14, stub.getGrad6()+"");
			n = pstmt.executeUpdate();
		}
		catch(SQLException e){
			System.out.println("SQL Exception occurred!!");
			e.printStackTrace();
		}
		String s[] = new String[3];
		if(n > 0){
			CalculateGPA cal = new CalculateGPA();
			s = cal.studentDetails(stub,credits,sem);
		}
		return s;
	}
}
