package com.intrigueit.myc2i.message.view;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.intrigueit.myc2i.common.utility.CryptographicUtility;

/**
 * Example servlet to generate a PDF from a servlet.
 * <br/>
 * Servlet param is:
 * <ul>
 *   <li>fo: the path to a XSL-FO file to render
 * </ul>
 * or
 * <ul>
 *   <li>xml: the path to an XML file to render</li>
 *   <li>xslt: the path to an XSLT file that can transform the above XML to XSL-FO</li>
 * </ul>
 * <br/>
 * Example URL: http://servername/fop/servlet/FopServlet?fo=readme.fo
 * <br/>
 * Example URL: http://servername/fop/servlet/FopServlet?xml=data.xml&xslt=format.xsl
 * <br/>
 * For this to work with Internet Explorer, you might need to append "&ext=.pdf"
 * to the URL.
 *
 * @author <a href="mailto:fop-dev@xmlgraphics.apache.org">Apache FOP Development Team</a>
 * @version $Id$
 * (todo) Ev. add caching mechanism for Templates objects
 */
public class ExportServlet extends HttpServlet {


    /**
     * {@inheritDoc}
     */
    public void init() throws ServletException {

    }
    
    /**
     * This method is called right after the FopFactory is instantiated and can be overridden
     * by subclasses to perform additional configuration.
     */
    protected void configureFopFactory() {
        //Subclass and override this method to perform additional configuration
    }

    /**
     * {@inheritDoc} 
     */
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException {
        try {
        	//Get parameters

            String xmlParam = request.getParameter("name");

            if ((xmlParam != null)) {

                ServletOutputStream stream = null;
                BufferedInputStream buf = null;
                try {    

            		String realPath = getServletContext().getRealPath("");
            		String filePath = realPath+AttachmentUploadHandler.DEFAULT_FILE_LOCATION;
            		String fullPath = ""+ filePath+xmlParam;
            		
        	        File xmlfile = new File( fullPath);
                

                   stream = response.getOutputStream();

                   //response.setContentType("text/xml");
                   response.addHeader("Content-Disposition", "attachment; filename="+ xmlParam);

                  response.setContentLength((int) xmlfile.length());
                  FileInputStream input = new FileInputStream(xmlfile);
                  buf = new BufferedInputStream(input);
                  int readBytes = 0;
                  while ((readBytes = buf.read()) != -1)
                    stream.write(readBytes);
                } catch (IOException ioe) {
                  throw new ServletException(ioe.getMessage());
                } finally {
                  if (stream != null)
                    stream.close();
                  if (buf != null)
                    buf.close();
                }
            	
            	
            } else {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><head><title>Error</title></head>\n"
                          + "<body><h1>FopServlet Error</h1><h3>No 'fo' "
                          + "request param given.</body></html>");
            }
        } catch (Exception ex) {
            throw new ServletException(ex);
        }
    }

 
 
 


}