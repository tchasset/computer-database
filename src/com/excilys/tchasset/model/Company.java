package com.excilys.tchasset.model;

public class Company {

	private int id;
	private String name;
	
	public static class Builder {
		private int id;
		private String name;
		
		public Builder setId(int id) {
			this.id = id;
			return this;
		}
		
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		
        public Company build() {
            return new Company(this);
        }
    }
	
	public Company() {}
	
	public Company(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Company n°"+id+": "+name+"\n";
	}	
}
