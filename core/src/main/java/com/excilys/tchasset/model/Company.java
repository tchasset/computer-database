package com.excilys.tchasset.model;

import javax.annotation.Nonnull;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name ="company")
public class Company {

	@Id
	private int id;
	@Nonnull
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
	
	public Company(int id) {
		this.id = id;
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
