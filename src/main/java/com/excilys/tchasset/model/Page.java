package com.excilys.tchasset.model;

import org.springframework.stereotype.Component;

@Component
public class Page {
	private int currentPage;
	private int sizePage;
	private int nbPages;
	
	public static class Builder{
		private int currentPage;
		private int sizePage;
		
		public Builder setCurrentPage(int currentPage) {
			this.currentPage=currentPage;
			return this;
		}
		
		public Builder setSizePage(int sizePage) {
			this.sizePage=sizePage;
			return this;
		}
		
		public Page build() {
			return new Page(this);
		}
	}
	
	public Page() {
		this.currentPage = 1;
		this.sizePage = 10;
	}
	
	private Page(Builder builder) {
		this.currentPage = builder.currentPage;
		this.sizePage = builder.sizePage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getSizePage() {
		return sizePage;
	}

	public void setSizePage(int sizePage) {
		this.sizePage = sizePage;
	}
	
	public void setNbPages(int nb) {
		this.nbPages = (int) Math.ceil((double) nb/(double) this.sizePage);
	}

	public int getNbPages() {
		return nbPages;
	}
	
	public void nextPage() {
		this.currentPage += this.sizePage;
	}
	
	public void previousPage() {
		this.currentPage -= this.sizePage;
	}
}
