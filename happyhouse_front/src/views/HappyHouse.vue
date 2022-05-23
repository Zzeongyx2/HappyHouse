<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-success">
      <!-- Card stats -->
    </base-header>

    <!--Charts-->
    <b-container fluid class="mt--7">
      <!--Tables-->
      <b-row class="mt-5">
        <b-col xl="8" class="mb-5 mb-xl-0">
          <!-- <page-visits-table></page-visits-table> -->
        </b-col>
        <b-col xl="4" class="mb-5 mb-xl-0">
          <!-- <social-traffic-table></social-traffic-table> -->
        </b-col>
      </b-row>
      <!--End tables-->
    </b-container>
  </div>
</template>
<script>
// Tables
import SocialTrafficTable from "./Dashboard/SocialTrafficTable";
import PageVisitsTable from "./Dashboard/PageVisitsTable";

export default {
  components: {
    PageVisitsTable,
    SocialTrafficTable,
  },
};
</script>
<style></style>
