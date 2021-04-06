<template>
  <div class="login">
    <RegisterForm
      msg="Ceci est le formulaire d'inscription"
      @errorMessage="resendErrorMessage($event)"
      @successMessage="resendSuccessMessage($event)"
    />
  </div>
</template>

<script>
// @ is an alias to /src
import RegisterForm from '@/components/RegisterForm.vue'

export default {
  name: 'Register',
  components: {
    RegisterForm
  },
  methods: {
    resendErrorMessage (event) {
      this.$emit('errorMessage', event)
    },
    resendSuccessMessage (event) {
      this.$emit('successMessage', event)
    }
  }
}
</script>
