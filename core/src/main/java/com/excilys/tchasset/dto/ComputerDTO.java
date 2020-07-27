package com.excilys.tchasset.dto;

import com.google.common.base.Objects;

public class ComputerDTO {

	private String 	id="0",
				   	name="",
				   	introduced="",
				   	discontinued="";
	private CompanyDTO companyDTO;
	
	public static class Builder {
		private final String name;
		private String 	id="0",
						introduced="",
						discontinued="";
		private CompanyDTO companyDTO;
		
		public Builder setId(String id) {
			this.id = id;
			return this;
		}
		
		public Builder (String name) {
			this.name = name;
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
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ComputerDTO computer = (ComputerDTO) o;
		return id.equals(computer.id) &&
				name.equals(computer.name) &&
				introduced.equals(computer.introduced) &&
				discontinued.equals(computer.discontinued) &&
				Objects.equal(companyDTO, computer.companyDTO);
	}

	@Override
	public String toString() {
		return "ComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", companyDTO=" + companyDTO + "]";
	}
	
	
	
}
