<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-success">
    </base-header>

    <b-container fluid class="mt--7">
      <shop-map />
    </b-container>
  </div>
</template>
<script>
import ShopMap from "./Maps/ShopMap.vue";
// import { API_KEY } from './Maps/API_KEY';

export default {
  components: {
    ShopMap,
  },
};
</script>
