<template>
  <nav
    class="navbar navbar-vertical fixed-left navbar-expand-md navbar-light bg-white"
    id="sidenav-main"
  >
    <div class="container-fluid">
      <!--Toggler-->
      <navbar-toggle-button @click.native="showSidebar"> </navbar-toggle-button>
      <router-link class="navbar-brand" to="/">
        <img :src="logo" class="navbar-brand-img" alt="..." />
      </router-link>

      <slot name="mobile-right">
        <ul class="nav align-items-center d-md-none">
          <base-dropdown class="nav-item" menu-on-right tag="li" title-tag="a">
            <a slot="title-container" class="nav-link" href="#" role="button">
              <div class="media align-items-center">
                <span class="avatar avatar-sm rounded-circle">
                  <img alt="Image placeholder" src="img/theme/vue.jpg" />
                </span>
              </div>
            </a>

            <div class="dropdown-header noti-title">
              <h6 class="text-overflow m-0">Welcome!</h6>
            </div>
            <router-link v-if="isLogin" to="/profile" class="dropdown-item">
              <i class="ni ni-single-02"></i>
              <span>My profile</span>
            </router-link>
            <router-link v-if="isAdmin" to="/admin" class="dropdown-item">
              <i class="ni ni-settings-gear-65"></i>
              <span>Settings</span>
            </router-link>
            <router-link v-if="!isLogin" to="/login" class="dropdown-item">
              <i class="ni ni-calendar-grid-58"></i>
              <span>Login</span>
            </router-link>
            <router-link
              v-if="!isLogin"
              to="/memberregister"
              class="dropdown-item"
            >
              <i class="ni ni-support-16"></i>
              <span>Register</span>
            </router-link>
            <div class="dropdown-divider"></div>
            <a v-if="isLogin" href="#!" class="dropdown-item">
              <i class="ni ni-user-run"></i>
              <span>Logout</span>
            </a>
          </base-dropdown>
        </ul>
      </slot>
      <slot></slot>
      <div
        v-show="$sidebar.showSidebar"
        class="navbar-collapse collapse show"
        id="sidenav-collapse-main"
      >
        <div class="navbar-collapse-header d-md-none">
          <div class="row">
            <div class="col-6 collapse-brand">
              <router-link to="/">
                <img :src="logo" />
              </router-link>
            </div>
            <div class="col-6 collapse-close">
              <navbar-toggle-button
                @click.native="closeSidebar"
              ></navbar-toggle-button>
            </div>
          </div>
        </div>

        <ul class="navbar-nav">
          <slot name="links"> </slot>
        </ul>
        <!--Divider-->
        <hr class="my-3" />

        <ul class="navbar-nav">
          <h6 class="navbar-heading text-muted">BOARD</h6>
          <slot name="links-board"> </slot>
        </ul>
        <!--Heading-->
        <!-- <h6 class="navbar-heading text-muted">BOARD</h6> -->
        <!--Navigation-->
        <!-- <ul class="navbar-nav mb-md-3">
                    <li class="nav-item">
                        <a class="nav-link"
                           href="https://www.creative-tim.com/learning-lab/bootstrap-vue/alerts/argon-dashboard">
                            <i class="ni ni-notification-70"></i> Notice
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="https://www.creative-tim.com/learning-lab/bootstrap-vue/colors/argon-dashboard">
                            <i class="ni ni-send"></i> Q&A
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link"
                           href="https://www.creative-tim.com/learning-lab/bootstrap-vue/alerts/argon-dashboard">
                            <i class="ni ni-chat-round"></i> Community
                        </a>
                    </li>
                </ul> -->
      </div>
    </div>
  </nav>
</template>
<script>
import { mapState } from "vuex";
import NavbarToggleButton from "@/components/NavbarToggleButton";
const memberStore = "memberStore";
export default {
  name: "sidebar",
  components: {
    NavbarToggleButton,
  },
  props: {
    logo: {
      type: String,
      default: "img/brand/happyhouse_logo.png",
      description: "Sidebar app logo",
    },
    autoClose: {
      type: Boolean,
      default: true,
      description:
        "Whether sidebar should autoclose on mobile when clicking an item",
    },
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isAdmin"]),
  },
  provide() {
    return {
      autoClose: this.autoClose,
    };
  },
  methods: {
    closeSidebar() {
      this.$sidebar.displaySidebar(false);
    },
    showSidebar() {
      this.$sidebar.displaySidebar(true);
    },
  },
  beforeDestroy() {
    if (this.$sidebar.showSidebar) {
      this.$sidebar.showSidebar = false;
    }
  },
};
</script>
