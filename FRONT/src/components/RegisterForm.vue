<template>
  <div class="registerForm">
    <v-card
      class="card-log-form"
      max-width="340"
      hover
    >
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-container>
          <v-text-field
            v-model="user.username"
            :rules="[rules.required, rules.max, rules.min]"
            :counter="24"
            :label="$t('username')"
          ></v-text-field>
          <v-text-field
            v-model="user.password"
            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="[rules.required, rules.min, rules.max, rules.match]"
            :type="show1 ? 'text' : 'password'"
            :label="$t('password')"
            :hint="$t('required')"
            @click:append="show1 = !show1"
          ></v-text-field>
          <v-text-field
            v-model="password2"
            :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="[rules.required, rules.match]"
            :type="show2 ? 'text' : 'password'"
            :label="$t('confirmpassword')"
            :hint="$t('samepass')"
            @click:append="show2 = !show2"
          ></v-text-field>
        </v-container>
      </v-form>
      <v-card-actions>
          <v-btn class="register-btn" :disabled="!valid" @click="register" color="success">{{ $t("register") }}</v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
import { userService } from '@/api/UserService'

export default {
  name: 'RegisterForm',

  props: {
  },

  data () {
    return {
      valid: false,
      user: {
        username: '',
        password: ''
      },
      password2: '',
      show1: false,
      show2: false,
      rules: {
        required: v => !!v || this.$t('required'),
        max: v => v.length <= 24 || this.$t('rulename'),
        min: v => v.length > 5 || this.$t('rulename6'),
        match: v => (!!v && v) === this.password2 || this.$t('samepass')
      }
    }
  },

  methods: {
    register () {
      if (this.$refs.form.validate()) {
        this.createUser()
      }
    },

    validateField () {
      this.$refs.form.validate()
    },

    createUser () {
      const user = { ...this.user }
      userService.create(user)
        .then(() => { this.$emit('successMessage', this.$t('register.success')); this.$router.push('/') })
        .catch((error) => {
          console.error(error)
          this.$emit('errorMessage', error.response && error.response.status === 409 ? this.$t('register.used') : this.$t('register.error'))
        })
    }
  },

  computed: {

  },

  watch: {
    password2: 'validateField'
  }

}
</script>

<style scoped>
  .registerForm {
    position: absolute;
    top: 40%;
    left: 50%;
    transform: translate(-50%,-40%);
    z-index: 2;
    margin-left:10px;
  }
</style>
