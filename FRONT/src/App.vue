<style>
  @import './assets/Home.css';

  .CardDis {
    visibility: visible;
    position: fixed;
    z-index: 3;
    top: 72px;
    right: 10px;
  }

</style>

<template >
  <v-app :style="{'background': 'rgb(156,156,193);','background': 'linear-gradient(90deg, rgba(156,156,193,1) 8%, rgba(148,213,224,1) 51%, rgba(187,224,232,1) 89%)'}">>
        <v-navigation-drawer
      v-model="drawer"
      app
    >
      <v-list dense>
        <v-list-item link to="/">
          <v-list-item-action>
            <v-icon>mdi-home</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Home</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link to="/dashboard" v-if="loggedIn">
          <v-list-item-action>
            <v-icon>mdi-antenna</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>Dashboard</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
        <v-list-item link to="/register" v-if="!loggedIn">
          <v-list-item-action>
            <v-icon>mdi-account-edit</v-icon>
          </v-list-item-action>
          <v-list-item-content>
            <v-list-item-title>{{$t("register")}}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar
      app
      color="cyan"
      dark
    >
      <v-app-bar-nav-icon class="bar-nav-icon" @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
      <div class="d-flex align-center">
        <select v-model="$i18n.locale">
            <option><div>en</div></option>
            <option>fr</option>
        </select>
      </div>
      <div class="d-flex align-center" :style="{'margin' : '10px'}">
        <b> CBD</b>
      </div>
      <v-spacer></v-spacer>
      <v-btn icon class="profilButton" @click="profileStatus = !profileStatus" v-if="loggedIn">
        <v-icon>mdi-account</v-icon>
      </v-btn>
      <div class="logoutButton" v-if="loggedIn">
        <Logout
          @logout="logout()"
          @errorMessage="printErrorMessage($event)"
          @successMessage="printSuccessMessage($event)"/>
      </div>
      <v-btn class="loginButton" @click="handleVisibility()" v-if="loginButtonDisplay">
        {{$t("login")}}
      </v-btn>
    </v-app-bar>

    <v-main>
      <router-view @errorMessage="printErrorMessage($event)" @successMessage="printSuccessMessage($event)"/>
      <div class="CardDis" v-if="profileDisplay">
        <Profile :username= "username"/>
      </div>
      <div class="CardDis" v-if="loginFormDisplay">
        <LoginForm
          @exit="handleVisibility()"
          @connect="connectAttempt($event)"
          @redirectregister="handleVisibility()"
          v-bind:username.sync="username"
          @errorMessage="printErrorMessage($event)"
          @successMessage="printSuccessMessage($event)"/>
      </div>
      <v-snackbar v-model="messageVisible" :timeout="timeout" :color="color" top>
        {{ message }}
        <template v-slot:action="{ attrs }">
          <v-btn text v-bind="attrs" @click="messageVisible = false">
            X
          </v-btn>
        </template>
      </v-snackbar>
    </v-main>
  </v-app>
</template>

<script>
import LoginForm from './components/LoginForm.vue'
import Logout from '@/components/Logout'
import Profile from '@/components/Profile'

export default {
  name: 'App',
  props: {
    source: String
  },
  components: {
    Logout,
    LoginForm,
    Profile
  },
  data: () => ({
    drawer: null,
    loginDisplay: false,
    profileStatus: false,
    loggedIn: false,
    username: localStorage.getItem('username'),
    message: '',
    timeout: 6000,
    color: 'success',
    messageVisible: false

  }),
  created: function () {
    if (this.$store.getters.isAuthenticated) {
      this.loginDisplay = false
      this.loggedIn = true
      this.$forceUpdate()
    } else {
      this.$router.push({ name: 'Home' })
    }
  },
  methods: {
    connectAttempt: function (result) {
      if (result === true) {
        this.loggedIn = true
      } else {
        this.loggedIn = false
      }
      this.handleVisibility()
    },

    handleVisibility: function () {
      this.loginDisplay = !this.loginDisplay
    },

    logout () {
      this.profileStatus = false
      this.loggedIn = false
    },

    printMessage (message, color) {
      this.color = color
      this.message = message
      this.messageVisible = true
      this.resetSnackbar()
    },
    printErrorMessage (message) {
      this.printMessage(message, 'error')
    },
    printSuccessMessage (message) {
      this.printMessage(message, 'success')
    },
    resetSnackbar () {
      this.timeout = this.timeout === 6000 ? 6001 : 6000 // Le timeout doit être modifié pour le relancer
    }
  },
  computed: {
    loginFormDisplay () {
      return this.loginDisplay && !this.loggedIn
    },

    loginButtonDisplay () {
      return !this.loginDisplay && !this.loggedIn
    },

    profileDisplay () {
      return this.profileStatus && this.loggedIn
    }
  }
}
</script>
