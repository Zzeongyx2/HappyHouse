<template>
  <div class="wrapper">
    <notifications></notifications>
    <side-bar>
      <template slot="links">
        <sidebar-item
          :link="{
            name: 'Main',
            path: '/happyhouse',
            icon: 'ni ni-tv-2 text-primary',
          }"
        >
        </sidebar-item>

        <!-- <sidebar-item
            :link="{
              name: 'Icons',
              path: '/icons',
              icon: 'ni ni-planet text-blue'
              }"
            >
        </sidebar-item> -->

        <sidebar-item
          :link="{
            name: 'Apt',
            path: '/aps',
            icon: 'ni ni-pin-3 text-orange',
          }"
        >
        </sidebar-item>

        <!-- 주변 상권 따로 한다면 -->
        <sidebar-item
          :link="{
            name: 'Shop',
            path: '/shop',
            icon: 'ni ni-shop text-yellow',
          }"
        >
        </sidebar-item>

        <sidebar-item
          v-if="!isLogin"
          :link="{
            name: 'Login',
            path: '/login',
            icon: 'ni ni-key-25 text-info',
          }"
        >
        </sidebar-item>

        <sidebar-item
          v-if="!isLogin"
          :link="{
            name: 'Register',
            path: '/memberregister',
            icon: 'ni ni-circle-08 text-pink',
          }"
        >
        </sidebar-item>
      </template>

      <template slot="links-board">
        <sidebar-item
          :link="{
            name: 'Notice',
            path: '/notice',
            icon: 'ni ni-notification-70',
          }"
        >
        </sidebar-item>

        <sidebar-item
          :link="{
            name: 'Q&A',
            path: '/qna',
            icon: 'ni ni-send',
          }"
        >
        </sidebar-item>

        <sidebar-item
          :link="{
            name: 'Community',
            path: '/community',
            icon: 'ni ni-chat-round',
          }"
        >
        </sidebar-item>
      </template>

      <!-- <template slot="links-after">
        <hr class="my-3">
        <h6 class="navbar-heading p-0 text-muted">Documentation</h6>

        <b-nav class="navbar-nav mb-md-3">
          <b-nav-item
               href="https://www.creative-tim.com/learning-lab/bootstrap-vue/quick-start/argon-dashboard"
               >
              <i class="ni ni-spaceship"></i>
              <b-nav-text class="p-0">Getting started</b-nav-text>
          </b-nav-item>
          <b-nav-item href="https://www.creative-tim.com/learning-lab/bootstrap-vue/colors/argon-dashboard">
              <i class="ni ni-palette"></i>
              <b-nav-text class="p-0">Foundation</b-nav-text>
          </b-nav-item>
          <b-nav-item href="https://www.creative-tim.com/learning-lab/bootstrap-vue/avatar/argon-dashboard">
              <i class="ni ni-ui-04"></i>
              <b-nav-text class="p-0">Components</b-nav-text>
          </b-nav-item>
        </b-nav>
      </template> -->
    </side-bar>
    <div class="main-content">
      <dashboard-navbar :type="$route.meta.navbarType"></dashboard-navbar>

      <div @click="$sidebar.displaySidebar(false)">
        <fade-transition :duration="200" origin="center top" mode="out-in">
          <!-- your content here -->
          <router-view></router-view>
        </fade-transition>
      </div>
      <content-footer v-if="!$route.meta.hideFooter"></content-footer>
    </div>
  </div>
</template>
<script>
/* eslint-disable no-new */
import PerfectScrollbar from "perfect-scrollbar";
import "perfect-scrollbar/css/perfect-scrollbar.css";
import { mapState } from "vuex";
const memberStore = "memberStore";

function hasElement(className) {
  return document.getElementsByClassName(className).length > 0;
}

function initScrollbar(className) {
  if (hasElement(className)) {
    new PerfectScrollbar(`.${className}`);
  } else {
    // try to init it later in case this component is loaded async
    setTimeout(() => {
      initScrollbar(className);
    }, 100);
  }
}

import DashboardNavbar from "./DashboardNavbar.vue";
import ContentFooter from "./ContentFooter.vue";
import DashboardContent from "./Content.vue";
import { FadeTransition } from "vue2-transitions";

export default {
  components: {
    DashboardNavbar,
    ContentFooter,
    DashboardContent,
    FadeTransition,
  },
  methods: {
    initScrollbar() {
      let isWindows = navigator.platform.startsWith("Win");
      if (isWindows) {
        initScrollbar("sidenav");
      }
    },
  },
  computed: {
    ...mapState(memberStore, ["isLogin"]),
  },
  mounted() {
    this.initScrollbar();
  },
};
</script>
<style lang="scss"></style>
