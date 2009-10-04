package com.intrigueit.myc2i.security;

public class Menu {

	public Boolean mentorTopMenu;
	
	public Boolean guestTopMenu;
	
	private Boolean adminTopMenu;
	
	private Boolean publicTopMenu;
	
	public Menu() {
		this.mentorTopMenu = false;
		this.guestTopMenu = false;
		this.adminTopMenu = false;
	}

	public Boolean getMentorTopMenu() {
		return mentorTopMenu;
	}

	public void setMentorTopMenu(Boolean mentorTopMenu) {
		this.mentorTopMenu = mentorTopMenu;
	}

	public Boolean getGuestTopMenu() {
		return guestTopMenu;
	}

	public void setGuestTopMenu(Boolean guestTopMenu) {
		this.guestTopMenu = guestTopMenu;
	}

	public Boolean getAdminTopMenu() {
		return adminTopMenu;
	}

	public void setAdminTopMenu(Boolean adminTopMenu) {
		this.adminTopMenu = adminTopMenu;
	}

	public Boolean getPublicTopMenu() {
		this.publicTopMenu = !this.adminTopMenu & !this.mentorTopMenu & !this.guestTopMenu;
		return publicTopMenu;
	}

	public void setPublicTopMenu(Boolean publicTopMenu) {
		this.publicTopMenu = publicTopMenu;
	}
	
}
