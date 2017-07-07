package com.college.student.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.college.student.bean.StudentBean;
import com.college.student.bean.StudentBean;
import com.college.student.service.CalculateGPA;
import com.college.student.util.DBUtil;
public class CalculateGPA {
	static int credits[] = new int[100];
	
	
	 public String[] studentDetails(StudentBean stub, int[] creds,int sem){
		 double gradeval = 0.0;
		 String porf = new String("PASS");
		 double sum = 0.0;
			 credits[0] = creds[0];
			 credits[1] = creds[1];
			 credits[2] = creds[2];
			 credits[3] = creds[3];
			 credits[4] = creds[4];
			 credits[5] = creds[5];
		String grade[] = new String[6];
		int credit[] = new int[6];
		grade[0] = stub.getGrad1()+"";
		grade[1] = stub.getGrad2()+"";
		grade[2] = stub.getGrad3()+"";
		grade[3] = stub.getGrad4()+"";
		grade[4] = stub.getGrad5()+"";
		grade[5] = stub.getGrad6()+"";
		double credsum = 0.0;
		double tot = 0.0;
		//assigning values for the grades
		for(int i = 0; i < 6; i++){
			gradeval = 0.0;
			if(grade[i].equalsIgnoreCase("S"))
			 gradeval = 10.0;
			else if(grade[i].equalsIgnoreCase("A"))
			 gradeval = 9.0;
			else if(grade[i].equalsIgnoreCase("B"))
			 gradeval = 8.0;
			else if(grade[i].equalsIgnoreCase("C"))
		     gradeval= 7.0;
			else if(grade[i].equalsIgnoreCase("D"))
		     gradeval= 6.0;
			else if(grade[i].equalsIgnoreCase("E"))
			 gradeval= 5.0;
			else if(grade[i].equalsIgnoreCase("U"))
				porf = "FAIL";
			sum += ((double)credits[i] * gradeval);
			credsum += credits[i];
		}
		//Calculation part for GPA
		String strarr[] = new String[2];
		double gpa = (sum / credsum);
		gpa = Math.round(gpa*100.0)/100.0;
		String c = Double.toString(gpa);
		Connection con;
		PreparedStatement pstmt;
		ResultSet rset;
		double gpaval = 0.0;
		int cnt = 0;
		double cgpa = 0.0;
		String d = new String();
		try{
			con = DBUtil.getDBConnection();
			
			//Checks if the student's record is inserted for the first time(1st semester)
			pstmt = con.prepareStatement("select count(*) from CGPA_TBL where regno = ?");
			pstmt.setString(1, stub.getRegno());
			rset = pstmt.executeQuery();
			if(rset.next()){
				cnt = rset.getInt(1);
			}
			if(cnt == 0){
				
			//If it is first semester, enter the details into the table
			pstmt = con.prepareStatement("insert into CGPA_TBL(name,regno,cgpa,status,total)values(?,?,?,?,?)");
			pstmt.setString(1, stub.getName());
			pstmt.setString(2, stub.getRegno());
			pstmt.setDouble(3, gpa);
			pstmt.setString(4, porf);
			pstmt.setDouble(5, gpa);
			pstmt.executeUpdate();
			d = Double.toString(gpa);
			tot = gpa;
			}
			else{
			//If it is not the 1st semester fetch the previous CGPA/GPA from the table for calculating CGPA till the current semester
			pstmt = con.prepareStatement("select cgpa,total from CGPA_TBL where regno = ?");
			pstmt.setString(1, stub.getRegno());
			rset = pstmt.executeQuery();
			if(rset.next()){
				gpaval = rset.getDouble(1);
				tot = rset.getDouble(2);
			}
			tot = tot + gpa;
			double upd = tot / sem;
			//Now update the table with the calculated CGPA along with the status (Pass/Fail)
			pstmt = con.prepareStatement("update CGPA_TBL set cgpa = ?, status = ?, total = ? where regno = ?");
			pstmt.setDouble(1, upd);
			pstmt.setString(2, porf);
			pstmt.setDouble(3, tot);
			pstmt.setString(4, stub.getRegno());
			pstmt.executeUpdate();
			
			//Select the current CGPA and return
			 pstmt = con.prepareStatement("select cgpa from CGPA_TBL where regno = ?");
			 pstmt.setString(1, stub.getRegno());
			 rset = pstmt.executeQuery();
			 if(rset.next()){
				 cgpa = rset.getDouble(1);
				 d = Double.toString(cgpa);
			 }
	
			}
		}
		catch(SQLException e){
			System.out.println("SQL Exception");
			e.printStackTrace();
		}
		//return both GPA and CGPA
			strarr[0] = c;
			strarr[1] = d;
		    return strarr;
		
	}
}
