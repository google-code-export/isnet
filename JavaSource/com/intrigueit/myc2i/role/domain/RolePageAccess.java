package com.intrigueit.myc2i.role.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ROLE_PAGE_ACCESS")
public class RolePageAccess implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -7938563988877485570L;

  @Id
	@Column(name="ROLE_PAGE_ACCESS_ID")
	private Long rolePageAccessId;
  
  @Column(name="MEMBER_ROLE_ID")
  private Long memberRoleId;

  @Column(name="MEMBER_TYPE_ID")	
  private String memberTypeId;

  @Column(name="PAGE_ID")
	private String pageId;

  /**
   * @return the rolePageAccessId
   */
  public Long getRolePageAccessId() {
    return rolePageAccessId;
  }

  /**
   * @param rolePageAccessId the rolePageAccessId to set
   */
  public void setRolePageAccessId(Long rolePageAccessId) {
    this.rolePageAccessId = rolePageAccessId;
  }

  /**
   * @return the memberRoleId
   */
  public Long getMemberRoleId() {
    return memberRoleId;
  }

  /**
   * @param memberRoleId the memberRoleId to set
   */
  public void setMemberRoleId(Long memberRoleId) {
    this.memberRoleId = memberRoleId;
  }

  /**
   * @return the memberTypeId
   */
  public String getMemberTypeId() {
    return memberTypeId;
  }

  /**
   * @param memberTypeId the memberTypeId to set
   */
  public void setMemberTypeId(String memberTypeId) {
    this.memberTypeId = memberTypeId;
  }

  /**
   * @return the pageId
   */
  public String getPageId() {
    return pageId;
  }

  /**
   * @param pageId the pageId to set
   */
  public void setPageId(String pageId) {
    this.pageId = pageId;
  }  
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "PAGE_ID", nullable = false, insertable = false, updatable = false)
  private ApplicationPages applicationPages;

  public ApplicationPages getApplicationPages() {
    return applicationPages;
  }

  public void setApplicationPages(ApplicationPages applicationPages) {
    this.applicationPages = applicationPages;
  }
  
}
