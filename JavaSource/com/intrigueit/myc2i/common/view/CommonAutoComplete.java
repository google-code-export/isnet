package com.intrigueit.myc2i.common.autocomplete;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.richfaces.renderkit.html.SuggestionBoxRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.ServiceConstants;
import com.intrigueit.myc2i.utility.ContextInfo;

@Component("commonAutoComplete")
@Scope("request") 
public class CommonAutoComplete extends ContextInfo implements Serializable {
	private static final long serialVersionUID = 3126855679065836286L;
	/** Serialize version no */
	private static final Logger log = Logger.getLogger(CommonAutoComplete.class);
    /** Suggestion control attributes */
    private String rows;
    private String first;
    private String cellspacing;
    private String cellpadding;
    private String minchars;
    private String frequency;
    private String rules;
    private boolean check;
    private String shadowDepth = Integer.toString(SuggestionBoxRenderer.SHADOW_DEPTH);
    private String border = "1";
    private String width = "200";
    private String height = "150";
    private String shadowOpacity = "4";
    private String noRecordMsg = "No record found for the search text.";
    private static String SEARCH_TABLE_CODE="searchTableCode";
    private static String EXT_CONDITION="extConds";
    private String emptyMsg = ServiceConstants.NO_RECORD_FOUND;
    /** Initialize the Logger */
    protected static final Logger logger = Logger.getLogger( CommonAutoComplete.class );
    StringBuffer sessionXML;
    private CommonSearchDao commonSearchDao;
    
    @Autowired
    public CommonAutoComplete(CommonSearchDao commonSearchDao) {
      this.commonSearchDao = commonSearchDao;
      this.rows = "0";
      this.first = "0";
      this.cellspacing = "2";
      this.cellpadding = "2";
      this.minchars = "1";
      this.frequency = "1";
      this.rules = "none";
    }

    public ArrayList<CommonSearchDataTmp> processRequest(Object suggest) {
    	String tableCode = this.getParameter(SEARCH_TABLE_CODE);
    	String extConds = this.getParameter(EXT_CONDITION);
    	ArrayList<CommonSearchDataTmp> resultList = new ArrayList<CommonSearchDataTmp>();
  		if(suggest!=null && tableCode!=null) {
  			String searchText = (String)suggest;
    		if(searchText!=null && !searchText.isEmpty()) {
    			try {
      			resultList = commonSearchDao.requestProcess(tableCode,searchText,extConds);
      			if(resultList == null || resultList.isEmpty()) {
      				resultList = new ArrayList<CommonSearchDataTmp>();
      			}
    			} catch (Exception e) {
  	  			e.printStackTrace();
  	  		} 
    		} else {
    			System.out.println("please enter search text..........................");
    			//Please enter search text
    		}
  		}
  		return resultList;
    }

    public String getCellpadding() {
        return cellpadding;
    }

    public void setCellpadding(String cellpadding) {
        this.cellpadding = cellpadding;
    }

    public String getCellspacing() {
        return cellspacing;
    }

    public void setCellspacing(String cellspacing) {
        this.cellspacing = cellspacing;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getFirst() {
        return first;
    }

    public int getIntFirst() {
        return Integer.parseInt(getFirst());
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getFrequency() {
        return frequency;
    }

    public double getDoubleFrequency() {
        return Double.parseDouble(getFrequency());
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getMinchars() {
        return minchars;
    }

    public void setMinchars(String minchars) {
        this.minchars = minchars;
    }

    public String getRows() {
        return rows;
    }

    public int getIntRows() {
        return Integer.parseInt(getRows());
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public void OnSelect() {
        System.out.print("Onselect works!!!");

    }

    public String getShadowDepth() {
        return shadowDepth;
    }

    public void setShadowDepth(String shadowDepth) {
        this.shadowDepth = shadowDepth;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getShadowOpacity() {
        return shadowOpacity;
    }

    public void setShadowOpacity(String shadowOpacity) {
        this.shadowOpacity = shadowOpacity;
    }
    
		public String getNoRecordMsg() {
			return noRecordMsg;
		}

		public void setNoRecordMsg(String noRecordMsg) {
			this.noRecordMsg = noRecordMsg;
		}

		/**
		 * @return the emptyMsg
		 */
		public String getEmptyMsg() {
			return emptyMsg;
		}

		/**
		 * @param emptyMsg the emptyMsg to set
		 */
		public void setEmptyMsg(String emptyMsg) {
			this.emptyMsg = emptyMsg;
		}
}
