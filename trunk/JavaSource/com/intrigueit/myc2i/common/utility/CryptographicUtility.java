 /**
 * @(#)MailgateEncryption.java
 *
 * Copyright (c)  Ltd 2007
 * 
 */
package com.intrigueit.myc2i.common.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

/**
 * The CryptographicUtility class is responsible for 
 * encrypt and decrypt the string value.
 *
 * @version 	1.00	December 10,2007
 * @author 		Shamim Ahmmed
 */
public class CryptographicUtility {
	private String fileName = "/WEB-INF/key.tmp";
	
    public SecretKey generateKey() throws Exception {
        try {
            KeyGenerator kg = KeyGenerator.getInstance("DES");
            kg.init(56);
            SecretKey sk = kg.generateKey();
            
            return sk;
        } catch (NoSuchAlgorithmException nsae) {
            throw new Exception(nsae);
        }
    }
    
    public void writeKey(File f) throws Exception {
        SecretKey sk = generateKey();
        
        try {
            FileOutputStream fos = new FileOutputStream(f);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(sk);
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            throw new Exception(ioe);
        }
    }
    
    public SecretKey readKey(File f) throws Exception {
        if (!f.canRead()) {
            throw new Exception("cannot read key file: " + f);
        }
        FileInputStream fis;        
        try {
           fis = new FileInputStream(f);
        } catch (FileNotFoundException fnfe) {
            throw new Exception(fnfe);
        }
        
        return readKey(fis);
    }
   private SecretKey readKeys( InputStream in) throws Exception{
	   return readKey(in);
   }
    
    public SecretKey readKey(InputStream is) throws Exception {
        try {
            ObjectInputStream ois = new ObjectInputStream(is);
            SecretKey sk = (SecretKey)ois.readObject();
            ois.close();
            
            return sk;
        } catch (ClassNotFoundException cnfe) {
            throw new Exception(cnfe);
        } catch (IOException ioe) {
            throw new Exception(ioe);            
        }
    }
    
    public String decrypt(String s, SecretKey sk) throws Exception {
        try {
            s =  new String(Base64.decodeBase64(s.getBytes()), "ISO-8859-1");
            //s = URLDecoder.decode(s, "UTF-8");
            
            Cipher dciph = Cipher.getInstance("DES");
            dciph.init(Cipher.DECRYPT_MODE, sk);
            
            byte[] sb = s.getBytes("ISO-8859-1");
            byte[] db = dciph.doFinal(sb);
            String pw = new String(db, "ISO-8859-1");
            
            return pw;
        } catch (NoSuchAlgorithmException nsae) {
            throw new Exception(nsae);
        } catch (NoSuchPaddingException nspe) {
            throw new Exception(nspe);
        } catch (InvalidKeyException ike) {
            throw new Exception(ike);
        } catch (UnsupportedEncodingException uee) {
            throw new Exception(uee);
        } catch (IllegalBlockSizeException ibse) {
            throw new Exception(ibse);
        } catch (BadPaddingException bpe) {
            throw new Exception(bpe);
        }
    }
    
    public String encrypt(String s, SecretKey sk) throws Exception {
        try {
            Cipher eciph = Cipher.getInstance("DES");
            eciph.init(Cipher.ENCRYPT_MODE, sk);
            
            byte[] sb = s.getBytes("ISO-8859-1");
            byte[] eb = eciph.doFinal(sb);
            String epw = new String(Base64.encodeBase64(eb));
            //String epw = URLEncoder.encode(new String(eb, "ISO-8859-1"), "UTF-8");
            
            return epw;
        } catch (NoSuchAlgorithmException nsae) {
            throw new Exception(nsae);
        } catch (NoSuchPaddingException nspe) {
            throw new Exception(nspe);
        } catch (InvalidKeyException ike) {
            throw new Exception(ike);
        } catch (UnsupportedEncodingException uee) {
            throw new Exception(uee);
        } catch (IllegalBlockSizeException ibse) {
            throw new Exception(ibse);
        } catch (BadPaddingException bpe) {
            throw new Exception(bpe);
        }
    }

    public String getEncryptedText(String text) throws Exception{
    	String encryptedText = "";
    	CryptographicUtility p = new CryptographicUtility();
    	try{
    		 InputStream in = null;        
    	       try {
    	    	   in = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(fileName);
    	       } catch (Exception fnfe) {
    	    	   writeKey();
    	    	   in = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(fileName);
    	       }
    	       if(in == null){
    	    	   writeKey();
    	    	   in = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(fileName);
    	       }
    	       
        	SecretKey sk = p.readKeys(in);
        	encryptedText = p.encrypt(text, sk);
    	}
    	catch(Exception ex){
    		throw ex;
    	}
    	return encryptedText;
    	
    }
    public String getDeccryptedText(String text) throws Exception{
    	String decryptedText = "";
    	try{

   		 InputStream in = null;        
	       try {
	    	   in = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(fileName);
	       } catch (Exception fnfe) {
	           throw new Exception(fnfe);
	       }
            SecretKey sk = readKeys(in);
            decryptedText = decrypt(text, sk);
    	}
    	catch(Exception ex){
    		throw ex;
    	}
    	return decryptedText;
    	
    }
    public void writeKey(){
    	CryptographicUtility password = new CryptographicUtility();
    	HttpServletRequest httpReq = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
    	String path = httpReq.getRealPath("")+""+fileName;
    	try{
            File keyFile = new File(path);
            password.writeKey(keyFile);
    	}
    	catch(Exception ex){

    	}

            
    }

}