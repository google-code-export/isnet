package com.intrigueit.myc2i.resource.view;

import java.io.OutputStream;
import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.intrigueit.myc2i.common.view.BasePage;
import com.intrigueit.myc2i.trainingitem.domain.TrainingItem;
import com.intrigueit.myc2i.trainingitem.service.TrainingItemService;

@Component("resourceViewHandler")
@Scope("session")	
public class ResourceViewHandler extends BasePage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5036946145319075988L;
	private TrainingItemService itemsService;
	private List<TrainingItem> books;
	
	
	/**
	 * @param trainingItemService
	 */
	@Autowired
	public ResourceViewHandler(TrainingItemService trainingItemService) {
		this.itemsService = trainingItemService;
	}
	public void renderImage(OutputStream out, Object data){
		try{
			byte dts[] = (byte[]) data;
			out.write(dts);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	private void loadBooks(){
		try{
			this.books = this.itemsService.getMentorBooks();
		}
		catch(Exception ex){
			log.error(ex.getMessage());
		}
	}
	public List<TrainingItem> getBooks() {
		this.loadBooks();
		return books;
	}


	public void setBooks(List<TrainingItem> books) {
		this.books = books;
	}
	
	
}
