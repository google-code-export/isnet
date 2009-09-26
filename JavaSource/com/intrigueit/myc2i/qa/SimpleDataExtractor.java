package com.intrigueit.myc2i.qa;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;


public class SimpleDataExtractor {

	private String URL;

	public List<IslamCityQuestion> extract() throws Exception{
		if(URL == null || URL.equals("")){
			throw new Exception("Invalid URL location ");
		}
		List<IslamCityQuestion> list = new ArrayList<IslamCityQuestion>();
		
		final WebClient webClient = new WebClient();
		final HtmlPage page = webClient.getPage(URL);
		String tablePath= "/html/body/table[2]";
		final List<HtmlElement> tables = page.getElementsByTagName("table");
		for(final HtmlElement ele: tables){

			if(ele.getCanonicalXPath().equals(tablePath)){
				final HtmlTable table= (HtmlTable) ele;
				int rowCount = 0;
				for (final HtmlTableRow row : table.getRows()) {
					if (rowCount > 0){
						HtmlAnchor link = (HtmlAnchor) row.getFirstChild().getFirstChild();
						DomNodeList<DomNode> childList = row.getFirstChild().getChildNodes();
						IslamCityQuestion question = new IslamCityQuestion();
						question.setDetailsQnALink(link.getHrefAttribute());
						question.setQuestionNo(link.asText());
						question.setQuestionReference(childList.get(2).asText());
						question.setQuestionDetails(childList.get(4).asText());
						list.add(question);

						
					}
					rowCount = rowCount + 1;						
				}
			}

		}
		
		return  list;
	}
	/**
	 * @param url
	 */
	public SimpleDataExtractor(String url) {
		URL = url;
	}

	public String getURL() {
		return URL;
	}

	public void setURL(String url) {
		URL = url;
	}
	
	
}
