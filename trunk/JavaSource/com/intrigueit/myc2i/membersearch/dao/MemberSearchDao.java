package com.intrigueit.myc2i.membersearch.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MemberSearchDao {

	private DataSource dataSource;
	protected final Log log = LogFactory.getLog(getClass());
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public List<String> fetchZipCodes(String zipcodex, Double distance){
		/** declare variables */

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;

	    
		List<String> codes = new ArrayList<String>();
		//String SQL = "select zipcode from zipcodedata where   "+ distance +" >= distance("+ latt +","+ lonn +",LATITUDE,LONGITUDE) order by zipcode";
		String SQL = "CALL GetNearbyZipCodes( \'"+ zipcodex +"\' , "+ distance +") ";
		try {

			conn = dataSource.getConnection();
			log.debug(SQL);
			stmt = conn.prepareStatement( SQL );
			//stmt.setMaxRows(200);
			rs = stmt.executeQuery();
			String zip = null;
			while(rs.next()) {
				zip = rs.getString("zipcode");
				codes.add(zip);
				log.debug(zip);
			}
			rs.close();
			stmt.close();
		}
		catch (Exception _e) {
			_e.printStackTrace();
			log.error(_e.getMessage());
		}
		finally{
			try{
				conn.close();
			}
			catch(Exception ex){}
		}
		log.debug("Loading end...."+codes.size());
		return codes;
	}	
	public List<String> fetchZipCodexxxxxxxx(Double lat, Double lon, Double distance){
		/** declare variables */

		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
	    int decimalPlace =5;
	    BigDecimal bd = new BigDecimal(lat);
	    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
	    double latt = bd.doubleValue();

	    decimalPlace =4;
	    bd = new BigDecimal(lon);
	    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
	    double lonn = bd.doubleValue();
	    
		List<String> codes = new ArrayList<String>();
		//String SQL = "select zipcode from zipcodedata where   "+ distance +" >= distance("+ latt +","+ lonn +",LATITUDE,LONGITUDE) order by zipcode";
		String SQL = "select zipcode from zipcodedata where   "+ distance +" >= distance("+ latt +","+ lonn +",LATITUDE,LONGITUDE) order by zipcode";
		try {

			conn = dataSource.getConnection();
			log.debug(SQL);
			stmt = conn.prepareStatement( SQL );
			stmt.setMaxRows(200);
			rs = stmt.executeQuery();
			String zip = null;
			while(rs.next()) {
				zip = rs.getString("zipcode");
				codes.add(zip);
				log.debug(zip);
			}
			rs.close();
			stmt.close();
		}
		catch (Exception _e) {
			_e.printStackTrace();
			log.error(_e.getMessage());
		}
		finally{
			try{
				conn.close();
			}
			catch(Exception ex){}
		}
		log.debug("Loading end...."+codes.size());
		return codes;
	}
}
