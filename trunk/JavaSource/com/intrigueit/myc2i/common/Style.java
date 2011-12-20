package com.intrigueit.myc2i.common;

import java.io.Serializable;

public class Style  implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String text;
    int color;
    float scale;
    
    
    public Style(String text, int color, float scale) {
		super();
		this.text = text;
		this.color = color;
		this.scale = scale;
	}
	public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public float getScale() {
        return scale;
    }
    public void setScale(float scale) {
        this.scale = scale;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
