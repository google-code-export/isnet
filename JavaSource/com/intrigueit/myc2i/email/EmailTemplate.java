package com.intrigueit.myc2i.email;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.intrigueit.myc2i.utility.ContextInfo;

public class EmailTemplate {

	private String name;
	
	private Map data;
	
	/** Initialize the Logger */
	protected static final Logger logger = Logger.getLogger( EmailTemplate.class );
	
	public EmailTemplate(final String templateName,final Map data){
		this.name = templateName;
		this.data = data;
	}
	public String generate() throws Exception{
		


		FacesContext facesContext = FacesContext.getCurrentInstance();
		//String contextPath = (String) facesContext.getExternalContext().getRequestContextPath();
		ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext(); 
		
		String realPath = servletContext.getRealPath("/");
		String path = realPath +  "WEB-INF"+ System.getProperty("file.separator") +"e_template"+ System.getProperty("file.separator");
		String velocityLog = realPath +  "WEB-INF"+ System.getProperty("file.separator") +"logs"+ System.getProperty("file.separator");
		logger.debug("Generating velocity content for file:" + path +" : "+ this.name);
		Properties p = new Properties();

		p.setProperty( "resource.loader", "file" );
		p.setProperty("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.FileResourceLoader" );
		p.setProperty("file.resource.loader.path",path);
		p.setProperty("runtime.log",velocityLog+ "velocity.log");
		
		/*  first, get and initialize an engine  */
        VelocityEngine ve = new VelocityEngine();
        ve.init(p);
        
        /*  next, get the Template  */
       
        Template t = ve.getTemplate( this.name );
        /*  create a context and add data */
        VelocityContext context = new VelocityContext();
       
        Iterator<Map.Entry<String,String>> it =this.data.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry<String,String> pair = it.next();
            context.put(pair.getKey(), pair.getValue());
        }

        /* now render the template into a StringWriter */
        StringWriter writer = new StringWriter();
        t.merge( context, writer );
        
        /* show the World */
        return writer.toString();
	}
	
}
