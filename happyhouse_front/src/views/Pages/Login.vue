<template>
  <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">HAPPYHOUSE에 오신걸<br />환영합니다.</h1>
              <p class="text-lead text-white">
                아파트 매매 정보와 기능들을 확인하세요.
              </p>
            </b-col>
          </b-row>
        </div>
      </b-container>
      <div class="separator separator-bottom separator-skew zindex-100">
        <svg
          x="0"
          y="0"
          viewBox="0 0 2560 100"
          preserveAspectRatio="none"
          version="1.1"
          xmlns="http://www.w3.org/2000/svg"
        >
          <polygon
            class="fill-default"
            points="2560 0 2560 100 0 100"
          ></polygon>
        </svg>
      </div>
    </div>
    <!-- Page content -->
    <b-container class="mt--8 pb-5">
      <b-row class="justify-content-center">
        <b-col lg="5" md="7">
          <b-card no-body class="bg-secondary border-0 mb-0">
            <b-card-body class="px-lg-5 py-lg-5">
              <div class="text-center text-muted mb-4">
                <small>Happyhouse Login</small>
              </div>
              <b-alert show variant="danger" v-if="isLoginError"
                >아이디 또는 비밀번호를 확인하세요.</b-alert
              >
              <b-form role="form">
                <base-input
                  alternative
                  class="mb-3"
                  name="userid"
                  id="userid"
                  :rules="{ required: true }"
                  prepend-icon="ni ni-single-02"
                  placeholder="ID..."
                  v-model="user.userid"
                  @keyup.enter="confirm"
                >
                </base-input>

                <base-input
                  alternative
                  class="mb-3"
                  name="userpwd"
                  id="userpwd"
                  :rules="{ required: true }"
                  prepend-icon="ni ni-lock-circle-open"
                  type="password"
                  placeholder="Password..."
                  v-model="user.userpwd"
                  @keyup.enter="confirm"
                >
                </base-input>
                <div class="text-center">
                  <base-button type="primary" class="my-4" @click="confirm"
                    >로그인</base-button
                  >
                </div>
              </b-form>
            </b-card-body>
          </b-card>
          <b-row class="mt-3">
            <b-col cols="6">
              <router-link to="/findpwd" class="text-light"
                ><small>비밀번호 찾기</small></router-link
              >
            </b-col>
            <b-col cols="6" class="text-right">
              <router-link to="/memberregister" class="text-light"
                ><small>새로 회원가입하기</small></router-link
              >
            </b-col>
          </b-row>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
import { mapState, mapActions, mapMutations } from "vuex";

const memberStore = "memberStore";

export default {
  name: "MemberLogin",
  data() {
    return {
      user: {
        userid: null,
        userpwd: null,
      },
    };
  },
  created() {
    this.SET_IS_LOGIN_ERROR(false);
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo"]),
    ...mapMutations(memberStore, ["SET_IS_LOGIN_ERROR"]),
    async confirm() {
      await this.userConfirm(this.user);
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.$router.push({ name: "happyhouse" });
      }
    },
  },
};
</script>
