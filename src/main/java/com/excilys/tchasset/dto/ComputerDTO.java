package com.excilys.tchasset.dto;

public class ComputerDTO {

	private String 	id="0",
				   	name="",
				   	introduced="",
				   	discontinued="";
	private CompanyDTO companyDTO;
	
	public static class Builder {
		private String 	id="0",
						name="",
						introduced="",
						discontinued="";
		private CompanyDTO companyDTO;
		
		public Builder setId(String id) {
			this.id = id;
			return this;
		}
		
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

		public Builder setCompanyDTO(CompanyDTO companyDTO) {
			this.companyDTO = companyDTO;
			return this;
		}

		public ComputerDTO build() {
            return new ComputerDTO(this);
        }
    }
	
	public ComputerDTO() {}
	
	public ComputerDTO(Builder builder) {
		this.setId(builder.id);
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.companyDTO = builder.companyDTO;
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

	public CompanyDTO getCompanyDTO() {
		return companyDTO;
	}

	public void setCompanyDTO(CompanyDTO companyDTO) {
		this.companyDTO = companyDTO;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
