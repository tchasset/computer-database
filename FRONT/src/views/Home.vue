  <style>
  @import '../assets/Home.css';
  </style>
<template>
  <div class="Home">
    <img alt="Vue logo" src="../assets/excilys.png">
    <h1>{{$t('welcome')}} </h1>
  </div>
</template>

<script>

import { mapState } from 'vuex'

export default {
  name: 'Home',
  computed: mapState({ profile: state => state.user.profile })
}
</script>
