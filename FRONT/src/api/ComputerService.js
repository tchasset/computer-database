import { axios } from './index'

export class ComputerService {
  constructor (axiosInstance) {
    this.axios = axiosInstance
  }

  findPage (page) {
    return this.axios.get('/computers?page=' + page)
  }

  findById (id) {
    return this.axios.get('/computers/' + id)
  }

  create (newComputer) {
    return this.axios.post('/computers', newComputer, { headers: { 'Content-Type': 'application/json' } })
  }

  edit (computer) {
    return this.axios.put('/computers', computer, { headers: { 'Content-Type': 'application/json' } })
  }

  delete (id) {
    return this.axios.delete('/computers/' + id)
  }
}

export const computerService = new ComputerService(axios)
