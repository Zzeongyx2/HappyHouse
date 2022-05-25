<template>
  <div>
    <base-header class="pb-6 pb-4 pt-5 pt-md-8 bg-gradient-success">
      <!-- Card stats -->
      <b-row>
        <b-col xl="4" md="6">
          <stats-card title="전체 회원수"
                      type="gradient-red"
                      :sub-title="users.calcUsers"
                      icon="ni ni-single-02"
                      class="mb-4">

            <template slot="footer">
              <span class="text-success mr-2">{{users.calcUsers}}</span>
              <span class="text-nowrap">members are using HAPPY HOUSE</span>
            </template>
          </stats-card>
        </b-col>

        <b-col xl="4" md="6">
          <stats-card :title="title"
                      type="gradient-orange"
                      :sub-title="String(userInfo.visited)"
                      icon="ni ni-favourite-28"
                      class="mb-4">

            <template slot="footer">
              <span class="text-nowrap">{{userInfo.username}} visited </span>
              <span class="text-success mr-2">{{userInfo.visited}}</span>
              <span class="text-nowrap">times in total</span>
            </template>
          </stats-card>
        </b-col>

        <b-col xl="4" md="6">
          <stats-card title="누적 방문수"
                      type="gradient-green"
                      :sub-title="users.totalVisited"
                      icon="ni ni-trophy"
                      class="mb-4">

            <template slot="footer">
              <span class="text-nowrap">The total number of visits for all members is </span>
              <span class="text-success mr-2">{{users.totalVisited}}</span>
            </template>
          </stats-card>
        </b-col>
      </b-row>
    </base-header>

    <!--Charts-->
    <b-container fluid class="mt--7">
      <!--Tables-->
      <b-row class="mt-5">
        <b-col xl="8" class="mb-5 mb-xl-0">
          <news-table></news-table>
        </b-col>
        <b-col xl="4" class="mb-5 mb-xl-0">
          <subscription-table></subscription-table>
        </b-col>
      </b-row>
      <!--End tables-->
    </b-container>
  </div>
</template>
<script>
// Tables
import NewsTable from "./Dashboard/NewsTable";
import SubscriptionTable from "./Dashboard/SubscriptionTable.vue";
import {calcData} from "@/api/member";
import {mapState} from "vuex";

const memberStore = "memberStore";

export default {
  components: {
    NewsTable,
    SubscriptionTable,
  },
  data() {
    return {
      users: {
        calcUsers:0,
        totalVisited:0,
      },
    }
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    title(){
      return this.userInfo.username + "님의 방문수";
    }
  },
  mounted() {
    calcData(
      ({data}) => {
        this.users = data;
        console.log("=====", data);
      },
      () => {},
    );
  },
};
</script>
<style></style>
