<template>
  <base-nav
    container-classes="container-fluid"
    class="navbar-top navbar-expand"
    :class="{ 'navbar-dark': type === 'default' }"
  >
    <a
      href="#"
      aria-current="page"
      class="h4 mb-0 text-white text-uppercase d-none d-lg-inline-block active router-link-active"
    >
      {{ $route.name }}
    </a>
    <!-- Navbar links -->
    <b-navbar-nav class="align-items-center ml-md-auto">
      <!-- This item dont have <b-nav-item> because item have data-action/data-target on tag <a>, wich we cant add -->
      <li class="nav-item d-sm-none">
        <a
          class="nav-link"
          href="#"
          data-action="search-show"
          data-target="#navbar-search-main"
        >
          <i class="ni ni-zoom-split-in"></i>
        </a>
      </li>
    </b-navbar-nav>
    <b-navbar-nav class="align-items-center ml-auto ml-md-0">
      <!-- <b-form class="navbar-search form-inline mr-sm-3"
            :class="{'navbar-search-dark': type === 'default', 'navbar-search-light': type === 'light'}"
            id="navbar-search-main">
        <b-form-group class="mb-0">
          <b-input-group class="input-group-alternative input-group-merge">
            <b-form-input placeholder="Search" type="text"> </b-form-input>

            <div class="input-group-append">
              <span class="input-group-text"><i class="fas fa-search"></i></span>
            </div>
          </b-input-group>
        </b-form-group>
      </b-form> -->
      <base-dropdown
        menu-on-right
        class="nav-item"
        tag="li"
        title-tag="a"
        title-classes="nav-link pr-0"
      >
        <a href="#" class="nav-link pr-0" @click.prevent slot="title-container">
          <b-media no-body class="align-items-center">
            <!-- <span class="avatar avatar-sm rounded-circle"> -->
            <!-- </span> -->
            <i class="ni ni-circle-08"> </i>
            <b-media-body v-if="isLogin" class="ml-2 d-none d-lg-block">
              <span class="mb-0 text-sm font-weight-bold">{{
                userInfo.username + "(" + userInfo.userid + ")"
              }}</span>
            </b-media-body>
          </b-media>
        </a>

        <template>
          <b-dropdown-header class="noti-title">
            <h6 class="text-overflow m-0">Welcome!</h6>
          </b-dropdown-header>
          <router-link v-if="!isLogin" to="/login" class="dropdown-item">
            <i class="ni ni-single-02"></i>
            <span>Login</span>
          </router-link>
          <router-link
            v-if="!isLogin"
            to="/memberregister"
            class="dropdown-item"
          >
            <i class="ni ni-single-02"></i>
            <span>Sign in</span>
          </router-link>
          <router-link v-if="isLogin" to="/profile" class="dropdown-item">
            <i class="ni ni-single-02"></i>
            <span>My profile</span>
          </router-link>
          <router-link v-if="isAdmin" to="/maps" class="dropdown-item">
            <i class="ni ni-settings-gear-65"></i>
            <span>Settings</span>
          </router-link>
          <div class="dropdown-divider"></div>
          <b-dropdown-item v-if="isLogin" @click.prevent="onClickLogout">
            <i class="ni ni-user-run"></i>
            <span>Logout</span>
          </b-dropdown-item>
        </template>
      </base-dropdown>
    </b-navbar-nav>
  </base-nav>
</template>
<script>
import { CollapseTransition } from "vue2-transitions";
import { BaseNav, Modal } from "@/components";
import { mapState, mapMutations } from "vuex";

const memberStore = "memberStore";

export default {
  name: "HeaderNaviBar",
  components: {
    CollapseTransition,
    BaseNav,
    Modal,
  },
  props: {
    type: {
      type: String,
      default: "default", // default|light
      description:
        "Look of the dashboard navbar. Default (Green) or light (gray)",
    },
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo", "isAdmin"]),
    routeName() {
      const { name } = this.$route;
      return this.capitalizeFirstLetter(name);
    },
  },
  methods: {
    ...mapMutations(memberStore, [
      "SET_IS_LOGIN",
      "SET_USER_INFO",
      "SET_IS_ADMIN",
    ]),
    capitalizeFirstLetter(string) {
      return string.charAt(0).toUpperCase() + string.slice(1);
    },
    onClickLogout() {
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      this.SET_IS_ADMIN(false);
      sessionStorage.removeItem("access-token");
      if (this.$route.path != "/happyhouse")
        this.$router.push({ name: "happyhouse" });
    },
  },
};
</script>
