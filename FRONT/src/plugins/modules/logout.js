import { AUTH_LOGOUT } from '@/plugins/actions/auth'

const actions = {

  [AUTH_LOGOUT]: ({ commit, dispatch }) => {
    return new Promise((resolve, reject) => {
      commit(AUTH_LOGOUT)
      localStorage.removeItem('user-token') // clear your user's token from localstorage
      resolve()
    })
  }
}
