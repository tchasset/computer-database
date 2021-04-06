import Axios from 'axios'

export class UserService {
  create (user) {
    return Axios.post('http://10.0.1.248:8081/computer-database/user?username=' + user.username + '&password=' + user.password, { headers: { 'Content-Type': 'application/json' } })
  }
}

export const userService = new UserService()
