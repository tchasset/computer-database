package com.excilys.tchasset.dto;

public class CompanyDTO {
	private String companyId;
	private String companyName;
	
	public static class Builder {
		private String companyId;
		private String companyName;
		
		public Builder setCompanyId(String companyId) {
			this.companyId = companyId;
			return this;
		}
		
		public Builder setCompanyName(String companyName) {
			this.companyName = companyName;
			return this;
		}
		
        public CompanyDTO build() {
            return new CompanyDTO(this);
        }
    }
	
	public CompanyDTO() {}
	
	public CompanyDTO(Builder builder) {
		this.companyId = builder.companyId;
		this.companyName = builder.companyName;
	}

	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
}
