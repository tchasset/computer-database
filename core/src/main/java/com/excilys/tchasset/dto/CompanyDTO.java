package com.excilys.tchasset.dto;

public class CompanyDTO {
	private String id;
	private String name;
	
	public static class Builder {
		private String id;
		private final String name;
		
		public Builder(String name) {
			this.name = name;
		}
		
		public Builder setId(String id) {
			this.id = id;
			return this;
		}
		
        public CompanyDTO build() {
            return new CompanyDTO(this);
        }
    }
	
	public CompanyDTO() {}
	
	public CompanyDTO(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CompanyDTO company = (CompanyDTO) o;
		return id.equals(company.id) &&
				name.equals(company.name);
	}
}
