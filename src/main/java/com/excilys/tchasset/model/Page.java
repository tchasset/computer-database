package com.excilys.tchasset.model;

import org.springframework.stereotype.Component;

@Component
public class Page {
	
	private int currentPage=1;
	private int size=10;
	private int nbPages;
	
	public static class Builder{
		private int currentPage;
		private int size;
		
		public Builder setCurrentPage(int currentPage) {
			this.currentPage=currentPage;
			return this;
		}
		
		public Builder setSize(int size) {
			this.size=size;
			return this;
		}
		
		public Page build() {
			return new Page(this);
		}
	}
	
	public Page() {
		this.currentPage = 1;
		this.size = 10;
	}
	
	private Page(Builder builder) {
		this.currentPage = builder.currentPage;
		this.size = builder.size;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public void setNbPages(int nb) {
		this.nbPages = (int) Math.ceil((double) nb/(double) this.size);
	}

	public int getNbPages() {
		return nbPages;
	}
	
	public void nextPage() {
		this.currentPage += 1;
	}
	
	public void previousPage() {
		this.currentPage -= 1;
	}
}
