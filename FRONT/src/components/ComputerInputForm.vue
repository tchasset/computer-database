<template>
  <div class="computerInputForm">
    <form @submit="submit" id="form">
      <fieldset>
        <div id="name">
          <label for="nameInput">{{ $t("name") }} </label>
          <v-text-field id="nameInput" v-model="computer.name" :rules="nameRules" placeholder="Name" required ></v-text-field>
        </div>

        <div id="introduced">
          <v-dialog ref="dialogIntro" v-model="dialog1" :return-value.sync="computer.introduced" persistent width="290px">
            <template v-slot:activator="{ on, attrs }">
              <v-text-field v-model="computer.introduced" :label="$t('introduction')" prepend-icon="event" clearable readonly v-bind="attrs" v-on="on"/>
            </template>
            <v-date-picker v-model="computer.introduced" class="mt-4" min="1970-01-02" :max="maxAllowedDate">
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="dialog1 = false">{{ $t("cancel") }}</v-btn>
              <v-btn text color="primary" @click="$refs.dialogIntro.save(computer.introduced)">{{ $t("ok") }}</v-btn>
            </v-date-picker>
          </v-dialog>
        </div>

        <div id="discontinued">
           <v-dialog ref="dialogDiscont" v-model="dialog2" :return-value.sync="computer.discontinued" persistent width="290px">
            <template v-slot:activator="{ on, attrs }">
              <v-text-field :disabled="!isIntroSet" v-model="computer.discontinued" :label="$t('discontinuation')" prepend-icon="event" clearable readonly v-bind="attrs" v-on="on"/>
            </template>
            <v-date-picker :disabled="!isIntroSet" v-model="computer.discontinued" class="mt-4" :min="minAllowedDate" max="2031-01-01">
              <v-spacer></v-spacer>
              <v-btn text color="primary" @click="dialog2 = false">{{ $t("cancel") }}</v-btn>
              <v-btn text color="primary" @click="$refs.dialogDiscont.save(computer.discontinued)">{{ $t("ok") }}</v-btn>
            </v-date-picker>
          </v-dialog>
        </div>

        <div id="company">
          <label for="companyId">{{ $t("company") }} </label>
          <v-select v-model="computer.companyDTO" :items="companyList" item-text="name" clearable return-object/>
        </div>
      </fieldset>
      <div id="submitButton">
        <v-btn small :disabled="!checksOk" color="primary" type="submit">{{ $t("submit") }}</v-btn>
      </div>
    </form>
  </div>
</template>

<script>
import { Computer } from '../model/Computer'
import { companyService } from '../api/CompanyService'

export default {
  name: 'ComputerInputForm',
  data () {
    return {
      companyList: [],
      nameRules: [
        name => !!name || this.$t('required.name'),
        name => /^[a-zA-Z0-9 \-/_+.]+$/.test(name) || this.$t('special.characters')
      ],
      dialog1: false,
      dialog2: false
    }
  },
  methods: {
    submit (event) {
      event.preventDefault()
      this.submitFunction(this.computer)
    },
    resetDiscontinuedDate () { if (!this.computer.introduced) { this.computer.discontinued = undefined } } // Reset the discontinued date if no introduction date is set
  },
  props: {
    computer: Computer,
    submitFunction: Function
  },
  computed: {
    isIntroSet () { this.resetDiscontinuedDate(); return !!this.computer.introduced }, // Returns true if set
    maxAllowedDate () { return this.computer.discontinued || '2031-01-01' },
    minAllowedDate () { return this.computer.introduced || '1970-01-01' },
    checksOk () { return !!this.computer.name && /^[a-zA-Z0-9 \-/_+.]+$/.test(this.computer.name) }
  },
  mounted () {
    companyService.findAll().then(result => { this.companyList = result.data }, error => console.error(error))
  }
}
</script>

<style>
.computerInputForm {
  margin: 25px;
}

.computerInputForm > form > fieldset {
  border: none;
}

#submitButton {
  display: flex;
  justify-content: center;
}
</style>
