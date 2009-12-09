package com.intrigueit.myc2i.membersearch.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class MemberSearchDao {

	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<String> fetchZipCode(Double lat, Double lon, Double distance){
		/** declare variables */
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<String> codes = new ArrayList<String>();
		String SQL = "select zipcode from zipcodedata where   "+ distance +" > distance("+ lat +","+ lon +",LATITUDE,LONGITUDE) ";
		try {

			conn = dataSource.getConnection();
			
			stmt = conn.prepareStatement( SQL );
			rs = stmt.executeQuery();
			String zip = null;
			while(rs.next()) {
				zip = rs.getString("zipcode");
				codes.add(zip);
			}
			rs.close();
			stmt.close();
		}
		catch (Exception _e) {
			_e.getStackTrace();
		}
		finally{
			try{
				conn.close();
			}
			catch(Exception ex){}
		}
		return codes;
	}
}
