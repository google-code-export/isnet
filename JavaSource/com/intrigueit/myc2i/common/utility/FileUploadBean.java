/**
 * 
 */
package com.intrigueit.myc2i.common.utility;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.richfaces.event.UploadEvent;
import org.richfaces.model.UploadItem;

/**
 * @author Ilya Shaikovsky
 *
 */
public class FileUploadBean implements Serializable {  
  private static final long serialVersionUID = 3653666287711916144L;  
  protected static final Logger logger = Logger.getLogger( FileUploadBean.class );
  
  public static final String DEFAULT_FILE_LOCATION = "/images/upload/";
	private ArrayList<UFile> files = new ArrayList<UFile>();
	private int uploadsAvailable = 5;
	private boolean autoUpload = false;
	private boolean useFlash = false;
	private Map<String,String> imageUrls;
	private boolean keepOpen = true;
	
	/**
   * @return the keepOpen
   */
  public boolean isKeepOpen() {
    return keepOpen;
  }

  /**
   * @param keepOpen the keepOpen to set
   */
  public void setKeepOpen(boolean keepOpen) {
    this.keepOpen = keepOpen;
  }

  /**
   * @return the fileData
   */
  public UFile getUploadFile() {
    return this.getFiles().get(0);
  }

  public int getSize() {
		if (getFiles().size()>0){
			return getFiles().size();
		}else 
		{
			return 0;
		}
	}

	public FileUploadBean() {
		imageUrls = new HashMap<String, String>();
	}

	public synchronized void paint(OutputStream stream, Object object) throws IOException {
		stream.write(getFiles().get((Integer)object).getData());
	}
	public synchronized void listener(UploadEvent event) throws Exception{
	  logger.debug(" Image upload listener called ");  
	  try{
	    UploadItem item = event.getUploadItem();
	    UFile file = new UFile();
	    file.setLength(item.getData().length);
	    file.setName(item.getFileName());
	    file.setData(item.getData());
	    files.add(file);
	    uploadsAvailable--;
	    logger.debug(" Image uploaded: "+ getUploadFile());
    }
    catch(Exception ex){
    	ex.printStackTrace();
    }
	}
	
	public String clearUploadData() {
		System.out.println("clear ...");
	  files.clear();
		setUploadsAvailable(5);
		imageUrls.clear();
		return null;
	}
	
	public long getTimeStamp(){
		return System.currentTimeMillis();
	}
	
	public ArrayList<UFile> getFiles() {
		return files;
	}

	public void setFiles(ArrayList<UFile> files) { 
		this.files = files;
	}

	public int getUploadsAvailable() {
		return uploadsAvailable;
	}

	public void setUploadsAvailable(int uploadsAvailable) {
		this.uploadsAvailable = uploadsAvailable;
	}

	public boolean isAutoUpload() {
		return autoUpload;
	}

	public void setAutoUpload(boolean autoUpload) {
		this.autoUpload = autoUpload;
	}

	public boolean isUseFlash() {
		return useFlash;
	}

	public void setUseFlash(boolean useFlash) {
		this.useFlash = useFlash;
	}
	public  void writeToFile(String fileName, byte data[])	throws IOException {
		String me = "FileUtils.WriteToFile";
		if (fileName == null) {
			throw new IOException(me + ": filename is null");
		}
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String imagePath = servletContext.getRealPath("");
		String filePath = imagePath +""+DEFAULT_FILE_LOCATION+fileName;
		System.out.println(filePath);
		File theFile = new File(filePath);
		if(!this.imageUrls.containsKey(DEFAULT_FILE_LOCATION+fileName)){
			this.imageUrls.put(fileName, DEFAULT_FILE_LOCATION+fileName);
		}
		// Check if a file exists.
		if (theFile.exists()) {
			String msg = theFile.isDirectory() ? "directory" : (!theFile.canWrite() ? "not writable" : null);
			if (msg != null) {
				throw new IOException(me + ": file '" + fileName + "' is " + msg);
			}
		}

		// Create directory for the file, if requested.
		if (theFile.getParentFile() != null) {
			theFile.getParentFile().mkdirs();
		}

		BufferedOutputStream fOut = null;
		try	{
			fOut = new BufferedOutputStream(new FileOutputStream(theFile));
			fOut.write(data);
		} catch (Exception e) {
			throw new IOException(me + " failed, got: " + e.toString());
		} finally {
			if (fOut != null )
		  fOut.close();
		}
	}

	/**
	 * @return the imageUrls
	 */
	public Map<String, String> getImageUrls() {
		return imageUrls;
	}

	/**
	 * @param imageUrls the imageUrls to set
	 */
	public void setImageUrls(Map<String, String> imageUrls) {
		this.imageUrls = imageUrls;
	}
	
}
