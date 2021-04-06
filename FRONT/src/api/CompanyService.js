import { axios } from './index'

export class CompanyService {
  constructor (axiosInstance) {
    this.axios = axiosInstance
  }

  findAll () {
    return this.axios.get('/company')
  }
}

export const companyService = new CompanyService(axios)
