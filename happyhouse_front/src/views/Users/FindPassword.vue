<template>
  <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">비밀번호를 잊으셨나요?</h1>
              <p class="text-lead text-white">이름과 이메일로 확인해보세요!</p>
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
                <small>Find Your Password!</small>
              </div>
              <b-alert show variant="danger" v-if="isFindError"
                >이름 또는 이메일을 다시 입력해주세요.</b-alert
              >
              <b-form role="form">
                <base-input
                  alternative
                  class="mb-3"
                  name="YourID"
                  id="userid"
                  :rules="{ required: true }"
                  prepend-icon="ni ni-circle-08"
                  placeholder="Your ID..."
                  v-model="finduser.userid"
                  @keyup.enter="findByEmail"
                >
                </base-input>

                <base-input
                  alternative
                  class="mb-3"
                  name="email"
                  id="email"
                  :rules="{ required: true, email: true }"
                  prepend-icon="ni ni-email-83"
                  type="email"
                  placeholder="Your Email..."
                  v-model="finduser.email"
                  @keyup.enter="findByEmail"
                >
                </base-input>
                <div class="text-center">
                  <base-button type="primary" class="my-4" @click="findByEmail">
                    Find</base-button
                  >
                </div>
              </b-form>
              <b-alert
                class="text-center"
                show
                variant="danger"
                v-if="returnPWD.length > 0"
                >당신의 비밀번호는 :: {{ returnPWD }} ::입니다.</b-alert
              >
            </b-card-body>
          </b-card>
          <b-row class="mt-3">
            <b-col cols="6">
              <router-link to="/login" class="text-light"
                ><small>로그인 하기</small></router-link
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
import { findUserPwd } from "@/api/member";

export default {
  name: "FindPassword",
  data() {
    return {
      finduser: {
        userid: "",
        email: "",
      },
      isFindError: false,
      returnPWD: "",
    };
  },
  methods: {
    async findByEmail() {
      await findUserPwd(
        this.finduser,
        (response) => {
          if (response.data.message == "success") {
            this.returnPWD = response.data.answer;
          }
        },
        (error) => {
          this.FindError = true;
        }
      );
    },
  },
};
</script>
