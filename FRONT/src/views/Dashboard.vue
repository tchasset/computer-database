<template>
  <div class="dashboard">
    <div class="computer">
      <Computer @errorMessage="resendErrorMessage($event)" @successMessage="resendSuccessMessage($event)"/>
    </div>
  </div>
</template>

<script>
import Computer from '../components/Computer'
export default {
  name: 'Dashboard',
  components: { Computer },
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

<style scoped>

.computer {
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
}

</style>
