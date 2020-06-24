package com.excilys.tchasset.model;

import java.time.LocalDate;
import java.util.Objects;

import org.springframework.stereotype.Component;

@Component
public class Computer {
	
	private int id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	
	public static class Builder {
		private int id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company;
		
		public Builder setId(int id) {
			this.id = id;
			return this;
		}
		
		public Builder setName(String name) {
			this.name = name;
			return this;
		}
		
        public Builder setIntroduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}

		public Builder setDiscontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}

		public Builder setCompany(Company company) {
			this.company = company;
			return this;
		}

		public Computer build() {
            return new Computer(this);
        }
    }
	
	public Computer() {}
	
	public Computer(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.introduced = builder.introduced;
		this.discontinued = builder.discontinued;
		this.company = builder.company;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public int getId() {
		return id;
	} 
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		if (company==null)
			return "Computer: "+name+" n°"+id+" introduced in "+introduced+" and discontinued in "+discontinued+" not in a company\n";
		else
			return "Computer: "+name+" n°"+id+" introduced in "+introduced+" and discontinued in "+discontinued+" belonging to Company n°"+company.getId()+"\n";
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Computer computer = (Computer) o;
		return id == computer.id &&
				name.equals(computer.name) &&
				Objects.equals(introduced, computer.introduced) &&
				Objects.equals(discontinued, computer.discontinued) &&
				Objects.equals(company, computer.company);
	}
	
}
