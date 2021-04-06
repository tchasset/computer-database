<template>
  <v-row no-gutters justify="space-around">
    <div class="computer">
      <v-form>
        <v-container>
          <v-layout>
            <v-row justify="center" align="center">
              <v-col cols="12" md="4">
                <v-text-field v-model="search">{{$t('search')}}</v-text-field>
              </v-col>
              <v-col cols="12" md="2">
                <v-btn v-on:click="searcher" small>{{$t('search')}}</v-btn>
              </v-col>
              <v-col cols="12" md="3">
                <v-dialog v-model="addComputerDialog">
                  <template v-slot:activator="{ on }">
                    <v-btn v-on="on" small>{{$t('addComputer')}}</v-btn>
                  </template>
                  <v-card>
                  <v-card-title class="headline grey lighten-2 justify-center">{{$t('addComputer')}}</v-card-title>
                    <v-card-text>
                      <AddComputer @exit="closeAddPopup($event)"></AddComputer>
                    </v-card-text>
                  </v-card>
                </v-dialog>
              </v-col>
            </v-row>
          </v-layout>
        </v-container>
      </v-form>
      <v-simple-table class="table" :fixed-header="true" :height="500">
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left">
                <v-row>
                  <v-checkbox :input-value="allSelected" @change="selectAll"></v-checkbox>
                  <v-icon v-if="selected.length > 0" @click="deleteSelected(selected)">{{$t('delete')}}</v-icon>
                </v-row>
              </th>
              <th class="text-left">
                <v-row>
                  {{$t('name')}}
                  <div class="orderByIcons">
                  <v-icon @click="setOrderBy('name', true)">arrow_upward</v-icon>
                  <v-icon @click="setOrderBy('name', false)">arrow_downward</v-icon>
                  </div>
                </v-row>
              </th>
              <th class="text-left">
                <v-row>
                  {{$t('introduced')}}
                  <div class="orderByIcons">
                  <v-icon @click="setOrderBy('introduced', true)">arrow_upward</v-icon>
                  <v-icon @click="setOrderBy('introduced', false)">arrow_downward</v-icon>
                  </div>
                </v-row>
              </th>
              <th class="text-left">
                <v-row>
                  {{$t('discontinuation')}}
                  <div class="orderByIcons">
                  <v-icon @click="setOrderBy('discontinued', true)">arrow_upward</v-icon>
                  <v-icon @click="setOrderBy('discontinued', false)">arrow_downward</v-icon>
                  </div>
                </v-row>
              </th>
              <th class="text-left">
                <v-row>
                  {{$t('company')}}
                  <div class="orderByIcons">
                  <v-icon @click="setOrderBy('companyName', true)">arrow_upward</v-icon>
                  <v-icon @click="setOrderBy('companyName', false)">arrow_downward</v-icon>
                  </div>
                </v-row>
              </th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(computer) in computers" :key="computer.id">
              <td><v-row><v-checkbox v-model="selected" :value="computer.id"></v-checkbox></v-row></td>

              <v-dialog v-model="dialog[computer.id]">
                <template v-slot:activator="{ on }">
                  <td v-on="on" v-if="computer.companyDTO !== null">{{computer.companyDTO.name}}</td>
                  <td v-on="on" v-else></td>
                  <td v-on="on" v-if="computer.discontinued !== null">{{computer.discontinued}}</td>
                  <td v-on="on" v-else></td>
                  <td v-on="on" v-if="computer.introduced !== null">{{computer.introduced}}</td>
                  <td v-on="on" v-else></td>
                  <td v-on="on">{{computer.name}}</td>
                </template>
                <v-card>
                  <v-card-title class="headline grey lighten-2 justify-center">{{$t('editComputer')}}</v-card-title>
                  <v-card-text>
                    <EditComputer :id="parseInt(computer.id)" @exit="closeEditPopup($event, computer.id)"></EditComputer>
                  </v-card-text>
                </v-card>
              </v-dialog>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
      <v-row justify="center" align="center">
        <div class="sliderContainer">
          <v-slider
            v-model="page"
            color="orange"
            class="pageSelectionSlider"
            thumb-label
            :max="maxPage"
            min="1"
            label="page"
            @change="changePage">
          </v-slider>
        </div>
      </v-row>
      <v-row justify="center" align="center">
        <v-pagination
          :length="maxPage"
          v-model="page"
          @input="update"
          :total-visible="7"
        ></v-pagination>
      </v-row>
      <v-row justify="center" align="center">
        <v-col md="2">
          <v-overflow-btn
            class="align-center my-2"
            v-model="pageSize"
            :items="dropdown_pageSize"
            label="Page size"
            @change="pageSizeChange"
          ></v-overflow-btn>
        </v-col>
      </v-row>
    </div>
  </v-row>
</template>

<script>

import EditComputer from '../views/EditComputer.vue'
import AddComputer from '../views/AddComputer.vue'
import { axios } from '../api/index'
import { computerService } from '../api/ComputerService'

export default {
  name: 'Computer',
  data () {
    return {
      selected: [],
      addComputerDialog: false,
      dialog: {},
      on: true,
      nbPage: 15,
      data_cube: [],
      page: 1,
      pageSize: 10,
      dropdown_pageSize: [10, 15, 20, 25, 50],
      computers: [],
      search: '',
      presearch: true,
      orderByColumn: '',
      ascendent: true
    }
  },
  computed: {
    allSelected () { return this.computers.length === this.selected.length && this.selected.length > 0 },
    maxPage () { return Math.max(parseInt((this.nbPage - 1) / this.pageSize, 10) + 1, 1) }
  },
  components: {
    EditComputer,
    AddComputer
  },
  mounted () {
    axios.defaults.headers.Authorization = 'Bearer ' + localStorage.getItem('user-token')
    this.update()
  },
  methods: {
    setOrderBy (column, ascendent) {
      this.orderByColumn = column
      this.ascendent = ascendent
      this.update()
    },
    selectAll: function () {
      if (this.allSelected) {
        this.selected = []
      } else {
        this.selected = this.computers.map(comp => comp.id)
      }
    },
    deleteSelected: function (selection) {
      if (selection.length > 0) {
        computerService.delete(selection[0]).then(
          result => this.deleteSelected(selection.slice(1)),
          error => { this.$emit('errorMessage', this.$t('delete.error')); console.error(error); this.update() }
        )
      } else {
        this.computers.length === this.selected.length ? this.previousPage() : this.update()
        this.$emit('successMessage', this.$t('delete.success'))
      }
    },
    closeAddPopup: function (eventReturn) {
      this.$emit(eventReturn.success ? 'successMessage' : 'errorMessage', eventReturn.message)
      this.update()
      this.addComputerDialog = false
    },
    closeEditPopup: function (eventReturn, id) {
      this.$emit(eventReturn.success ? 'successMessage' : 'errorMessage', eventReturn.message)
      this.update()
      this.$set(this.dialog, id, false)
    },
    update: function () {
      this.selected = []
      axios
        .get(
          '/computers?page=' + this.page +
              '&search=' + this.search +
              '&by=' + this.orderByColumn +
              '&order=' + (this.ascendent ? 'ASC' : 'DESC') +
              '&size=' + this.pageSize
        )
        .then((response) => { this.computers = response.data })
        .catch((error) => { console.error(error); this.$emit('errorMessage', this.$t('database.error')) })
      this.search
        ? axios
          .get(
            '/computers/nbsearch?name=' +
            this.search
          )
          .then((response) => (this.nbPage = response.data.nb))
          .catch((error) => { console.error(error); this.$emit('errorMessage', this.$t('database.error')) })
        : axios
          .get('/computers/nb')
          .then((response) => (this.nbPage = response.data.nb))
          .catch((error) => { console.error(error); this.$emit('errorMessage', this.$t('database.error')) })
    },
    previousPage: function () {
      this.page -= 1
      this.update()
    },
    changePage: function () {
      this.update()
    },
    nextPage: function () {
      this.page += 1
      this.update()
    },
    pageSizeChange () {
      this.page = 1
      this.update()
    },
    searcher: function () {
      this.selected = []
      this.page = 1
      this.update()
    }
  }
}
</script>

<style scoped>
th {
  width: 300px;
}

.theme--light.v-data-table.v-data-table--fixed-header thead th {
  background: cornflowerblue;
  color: white;
  cursor: pointer;

}

.table {
  margin-left: 20px;
  margin-right: 20px;
}

.deleteIcon {
  float: right;
}

.orderByIcons {
  margin-left: 10px;
}

.sliderContainer {
  width: 60%;
}

</style>
