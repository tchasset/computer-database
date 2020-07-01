package com.excilys.tchasset.dto;

public class PaginationDTO {
	
	private String addSuccess;
	private String editSuccess;
	private String orderByName;
	private String orderByCompany;
	private String search="";
	private String currentPage="1";
	private String size="10";
	
	public String getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getAddSuccess() {
		return addSuccess;
	}
	public void setAddSuccess(String addSuccess) {
		this.addSuccess = addSuccess;
	}
	public String getEditSuccess() {
		return editSuccess;
	}
	public void setEditSuccess(String editSuccess) {
		this.editSuccess = editSuccess;
	}
	public String getOrderByName() {
		return orderByName;
	}
	public void setOrderByName(String orderByName) {
		this.orderByName = orderByName;
	}
	public String getOrderByCompany() {
		return orderByCompany;
	}
	public void setOrderByCompany(String orderByCompany) {
		this.orderByCompany = orderByCompany;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	
	
}
