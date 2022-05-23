<template>
  <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container class="container">
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">HappyHouse 계정 생성</h1>
              <p class="text-lead text-white">새로운 계정을 만들어 보세요.</p>
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
      <!-- Table -->
      <b-row class="justify-content-center">
        <b-col lg="10" md="auto">
          <b-card no-body class="bg-secondary border-0">
            <b-card-body class="px-lg-5 py-lg-5">
              <div class="text-center text-muted mb-4">
                <small>모든 항목을 채워주세요.</small>
              </div>

              <validation-observer ref="formValidator">
                <b-form role="form">
                  <base-input
                    alternative
                    class="mb-3"
                    prepend-icon="ni ni-hat-3"
                    placeholder="ID"
                    name="ID"
                    @keyup="GoidCheck"
                    :rules="{ required: true, min: 4 }"
                    v-model="user.userid"
                  >
                  </base-input>
                  <base-input
                    alternative
                    class="mb-3"
                    prepend-icon="ni ni-hat-3"
                    placeholder="Name"
                    name="Name"
                    :rules="{ required: true }"
                    v-model="user.username"
                  >
                  </base-input>

                  <base-input
                    alternative
                    class="mb-3"
                    prepend-icon="ni ni-email-83"
                    placeholder="Email"
                    name="Email"
                    :rules="{ required: true, email: true }"
                    v-model="user.email"
                  >
                  </base-input>

                  <base-input
                    alternative
                    class="mb-3"
                    prepend-icon="ni ni-lock-circle-open"
                    placeholder="password..."
                    type="password"
                    name="Password"
                    :rules="{ required: true, min: 4 }"
                    v-model="user.userpwd"
                  >
                  </base-input>

                  <base-input
                    alternative
                    class="mb-3"
                    prepend-icon="ni ni-lock-circle-open"
                    placeholder="password check..."
                    type="password"
                    name="Passwordchk"
                    :rules="{ required: true, min: 4 }"
                    v-model="pwdckd"
                  >
                  </base-input>
                  <b-alert
                    class="text-center"
                    show
                    variant="danger"
                    v-if="!CanUsable"
                    >사용불가능한 ID입니다.</b-alert
                  >
                  <b-alert
                    class="text-center"
                    show
                    variant="danger"
                    v-if="isRegistError"
                    >회원가입 실패! 아이디 또는 비밀번호를 확인하세요.</b-alert
                  >

                  <div class="text-center">
                    <b-button
                      type="submit"
                      variant="primary"
                      class="mt-4"
                      @click.prevent="onSubmit"
                      >계정생성하기</b-button
                    >
                  </div>
                </b-form>
              </validation-observer>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
import { mapState, mapActions, mapMutations } from "vuex";
import Swal from "sweetalert2";
const memberStore = "memberStore";
export default {
  name: "MemberRegister",
  data() {
    return {
      user: {
        userid: "",
        username: "",
        email: "",
        userpwd: "",
      },
      pwdckd: "",
    };
  },
  created() {
    this.CLEAR_CAN_USABLE();
    this.CLEAR_IS_REGIST_ERROR();
  },
  computed: {
    ...mapState(memberStore, [
      "isLogin",
      "isLoginError",
      "isRegistError",
      "CanUsable",
    ]),
  },
  methods: {
    ...mapActions(memberStore, ["registUser", "idDuplicateCheck"]),
    ...mapMutations(memberStore, ["CLEAR_IS_REGIST_ERROR", "CLEAR_CAN_USABLE"]),
    async onSubmit() {
      if (this.user.userpwd != this.pwdckd) {
        Swal.fire("비밀번호를 확인하세요");
      } else {
        await this.registUser(this.user);
        if (this.isRegistError) {
          Swal.fire("회원가입 실패! 입력 정보를 확인하세요!");
        } else {
          Swal.fire("회원가입 성공! 로그인 페이지로 이동합니다!").then(() => {
            this.$router.push({ name: "login" });
          });
        }
      }
    },
    async GoidCheck() {
      await this.idDuplicateCheck(this.user.userid);
    },
  },
};
</script>
<style></style>
