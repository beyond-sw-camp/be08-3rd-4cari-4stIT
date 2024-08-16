<template>
  <HeaderIT/>
  <router-view/>
  <FooterIT/>
</template>

<script>
  import { RouterView } from 'vue-router';
  import HeaderIT from './components/common/HeaderIT.vue';
  import FooterIT from './components/common/FooterIT.vue';

  export default {
    name: 'App',
    components: {
      HeaderIT,
      FooterIT
    }
  }
</script>

<style scoped> 

</style>