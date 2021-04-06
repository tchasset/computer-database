<template>
  <div class="logout">
    <v-btn  @click="logout">{{$t("logout")}}</v-btn>
  </div>
</template>

<script>
import { AUTH_LOGOUT } from '@/plugins/actions/auth'

export default {
  name: 'Logout',
  methods: {
    logout: function () {
      this.$store.dispatch(AUTH_LOGOUT)
        .then(() => {
          this.$emit('logout')
          this.$emit('successMessage', this.$t('logout.success'))
          this.$router.push('/')
        })
        .catch((error) => {
          console.error(error)
          this.$emit('errorMessage', this.$t('logout.error'))
        })
    }
  }
}
</script>

<style scoped>

</style>
