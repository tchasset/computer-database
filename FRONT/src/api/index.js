import Axios from 'axios'

export const axios = Axios.create({
  baseURL: 'http://10.0.1.248:8081/computer-database',
  headers: {
    Authorization: 'Bearer ' + localStorage.getItem('user-token')
  }
})
