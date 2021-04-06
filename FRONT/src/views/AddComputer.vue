<template>
  <div class="addComputer">
    <ComputerInputForm :computer="computer" :submitFunction="submitFunction" @exit="resendEvent($event)"></ComputerInputForm>
  </div>
</template>

<script>
import ComputerInputForm from '../components/ComputerInputForm.vue'
import Computer from '../model/Computer.js'
import { computerService } from '../api/ComputerService'

export default {
  name: 'AddComputer',
  data () {
    return {
      computer: new Computer(0, ''),
      submitFunction (computer) {
        function eventReturn (success, message) {
          return {
            success: success,
            message: message
          }
        }
        const newComputer = { ...computer, id: undefined }
        computerService.create(newComputer).then(
          result => this.$emit('exit', eventReturn(true, this.$t('add.success'))),
          error => {
            this.$emit('exit', eventReturn(false, this.$t('add.error')))
            console.log(error)
          })
      }
    }
  },
  components: {
    ComputerInputForm
  },
  methods: {
    resendEvent: function (state) {
      this.$emit('exit', state)
    }
  }
}
</script>
