package com.intrigueit.myc2i.media;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;


public class MediaBean {
	
	private Clip sClip;
	
	private String pageContent;


	/**
	 * 
	 */
	public MediaBean() {
	}
	/**
	 * 
	 */
	public MediaBean(String file)throws Exception{
		try{
			AudioInputStream stream = AudioSystem.getAudioInputStream(new File(file));
			AudioFormat format = stream.getFormat();
			//System.out.println(format);
			if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
				format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), format.getSampleSizeInBits() * 2, format.getChannels(), format.getFrameSize() * 2, format.getFrameRate(),true); // big endian
				stream = AudioSystem.getAudioInputStream(format, stream);
           }
           DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat(),
               ((int) stream.getFrameLength() * format.getFrameSize()));
            sClip = (Clip) AudioSystem.getLine(info);
            sClip.open(stream);

        }catch(Exception ex){
        	ex.printStackTrace();
        	throw ex;
        } 
	}
	public MediaBean(byte data[])throws Exception{
		if(data == null){
			sClip = null;
			return;
		}
		try{
			InputStream inStream = new ByteArrayInputStream(data);
			
			AudioInputStream stream = AudioSystem.getAudioInputStream(inStream);
			AudioFormat format = stream.getFormat();
			//System.out.println(format);
			if (format.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
				format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, format.getSampleRate(), format.getSampleSizeInBits() * 2, format.getChannels(), format.getFrameSize() * 2, format.getFrameRate(),true); // big endian
				stream = AudioSystem.getAudioInputStream(format, stream);
           }
           DataLine.Info info = new DataLine.Info(Clip.class, stream.getFormat(),
               ((int) stream.getFrameLength() * format.getFrameSize()));
            sClip = (Clip) AudioSystem.getLine(info);
            sClip.open(stream);

        }catch(Exception ex){
        	ex.printStackTrace();
        	throw ex;
        } 
	}
	
	public void play(){
		try{
			if(sClip == null){
				return;
			}
			sClip.start();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void pause(){
    try{
      if(sClip == null){
        return;
      }
      sClip.stop();
    }
    catch(Exception ex){
      ex.printStackTrace();
    }
  }
	
	public void dispose(){
		try{
			if(sClip == null){
				return;
			}			
			sClip.stop();
			sClip.close();

		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void stop(){
		try{
			if(sClip == null){
				return;
			}			
			sClip.stop();
			sClip.close();

		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public String getPageContent() {
		return pageContent;
	}
	public void setPageContent(String pageContent) {
		this.pageContent = pageContent;
	}
	
}
