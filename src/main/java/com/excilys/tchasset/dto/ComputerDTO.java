package com.excilys.tchasset.dto;

public class ComputerDTO {

	private String name,
				   introduced,
				   discontinued,
				   companyId;
	
	public static class Builder {
		private String name,
					   introduced,
					   discontinued,
					   companyId;
		
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		
        public Builder setIntroduced(String introduced) {
			this.introduced = introduced;
			return this;
		}

		public Builder setDiscontinued(String discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public Builder setCompanyId(String companyId) {
			this.companyId = companyId;
			return this;
		}

		public ComputerDTO build() {
            return new ComputerDTO(this);
        }
    }
	
	public ComputerDTO() {}
	
	public ComputerDTO(Builder builder) {
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.companyId = builder.companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
}
