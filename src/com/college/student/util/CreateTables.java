package com.college.student.util;

import java.sql.*;;

public class CreateTables {

	public static void main(String[] args) {
		Connection con;
		Statement stmt;
		ResultSet rset;
		try{
		con = DBUtil.getDBConnection();
		stmt = con.createStatement();
		//stmt.executeUpdate("drop table CGPA_TBL");
		//stmt.executeUpdate("create table SUBJECT_TBL(name varchar2(20),regno varchar2(20),subject1 varchar2(20) not null,subject2 varchar2(20) not null,subject3 varchar2(20) not null,subject4 varchar2(20) not null,subject5 varchar2(20) not null,subject6 varchar2(20) not null,grade1 varchar2(3),grade2 varchar2(3),grade3 varchar2(3),grade4 varchar2(3),grade5 varchar2(3),grade6 varchar2(3))");
		//stmt.executeUpdate("create table STUDENT_TBL(name varchar2(20) not null,regno varchar2(20) ,grade1 varchar2(2) not null,grade2 varchar2(2) not null,grade3 varchar2(2) not null,grade4 varchar2(2) not null,grade5 varchar2(2) not null,grade6 varchar2(2) not null)");
		//stmt.executeUpdate("create table CGPA_TBL(name varchar2(20),regno varchar2(20), cgpa decimal(3,2) default 0.00,status varchar2(20),total decimal(5,2) default 000.00)");
		//stmt.executeUpdate("create table SEM(semester number not null)");
		System.out.println("Tables Created Successfully!!");
		}
		catch(SQLException e){
			e.printStackTrace();
			System.out.println("SQL Exception occurred!!");
		}
	}
}
