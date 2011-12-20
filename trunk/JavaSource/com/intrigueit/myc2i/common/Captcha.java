package com.intrigueit.myc2i.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.io.Serializable;

import com.intrigueit.myc2i.common.utility.PassPhrase;

public class Captcha implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String text;
	private Style captchaStyle;
	private int fontSize;
	public void generateCaptcha(){
		
		String cText = PassPhrase.getNext();
		this.text = cText;
		this.captchaStyle = new Style(cText, Color.BLUE.getAlpha(), 1.0f);
	}
    public void paint(Graphics2D g2d, Object obj) {
        
    	Style data = this.captchaStyle;
       // data.text = this.text;
        int testLenght = data.text.length();
        fontSize = testLenght < 8? 40 : 40 - (testLenght - 8);
         if (fontSize < 12)fontSize = 12;
         Font font = new Font("Serif", Font.HANGING_BASELINE, fontSize);
         g2d.setFont(font);
        
         int x = 10;
         int y = 40;//fontSize*5/2;
         //System.out.println(x + ".............."+ y + "..........."+ data.text);
         this.text = data.text;
         g2d.translate(x, y);
         Color color = new Color(data.color );
         
         g2d.setPaint(new Color(color.getRed(),color.getGreen(), color.getBlue(), 30));
         AffineTransform origTransform = g2d.getTransform();
         g2d.shear(-0.5*data.scale, 0);
         g2d.scale(1, data.scale);
         g2d.drawString(data.text, 0, 0);
         
         g2d.setTransform(origTransform);
         g2d.setPaint(color);
         g2d.drawString(data.text, 0, 0);
    }
	public String getText(){
		return text;
	}
	public Style getStyle(){
		return this.captchaStyle;
	}
	
}
