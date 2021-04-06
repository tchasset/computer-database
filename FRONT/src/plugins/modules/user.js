import Vue from 'vue'
import { AUTH_LOGOUT } from '../actions/auth'
import { USER_ERROR, USER_REQUEST, USER_SUCCESS } from '@/plugins/actions/user'
import Axios from 'axios'

const state = { status: '', profile: {} }

const getters = {
  getProfile: state => state.profile,
  isProfileLoaded: state => !!state.profile.name
}

const actions = {
  [USER_REQUEST]: ({ commit, dispatch }, user) => {
    commit(USER_REQUEST)
    localStorage.setItem('username', user.username)
    Axios.get('http://10.0.1.248:8081/computer-database/user/login?username=' + user.username + '&password=' + user.password)
      .then(resp => {
        commit(USER_SUCCESS, resp)
      })
      .catch(() => {
        commit(USER_ERROR)
        // if resp is unauthorized, logout, to
        dispatch(AUTH_LOGOUT)
      })
  }
}

const mutations = {
  [USER_REQUEST]: state => {
    state.status = 'loading'
  },
  [USER_SUCCESS]: (state, resp) => {
    state.status = 'success'
    Vue.set(state, 'profile', resp)
  },
  [USER_ERROR]: state => {
    state.status = 'error'
  },
  [AUTH_LOGOUT]: state => {
    state.profile = {}
  }
}

export default {
  state,
  getters,
  actions,
  mutations
}
