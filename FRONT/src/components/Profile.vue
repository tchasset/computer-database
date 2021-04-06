<template>
  <div class="profile">
    <v-card
      class="card-log-form"
      width="180"
      height="120"
      hover
      shaped
    >
      <v-card-text>
        <p class="text-h6 text-center mb-3">
          {{$t('logged')}}
        </p>
        <p class="text-button text-center mb-4 ">
          {{username}}
        </p>
      </v-card-text>
    </v-card>
  </div>
</template>

<script>

export default {
  name: 'Profile',

  props: {
    username: String
  },

  data: () => ({

  }),

  methods: {

  }
}
</script>

<style scoped>

</style>
