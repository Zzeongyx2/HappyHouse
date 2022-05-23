import jwt_decode from "jwt-decode";
import { login } from "@/api/member.js";
import { findById } from "../../api/member";
import { regist } from "@/api/member";
import { idCheck } from "../../api/member";
const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    isRegistError: false,
    CanUsable: false,
    userInfo: {
      userid: "",
      username: "",
      useremail: "",
    },
    isAdmin: false,
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_IS_ADMIN: (state, isAdmin) => {
      state.isAdmin = isAdmin;
    },
    SET_IS_REGIST_ERROR: (state, isRegistError) => {
      state.isRegistError = isRegistError;
    },
    SET_CAN_USABLE_ID: (state, CanUsable) => {
      state.CanUsable = CanUsable;
    },
    SET_USER_INFO: (state, userInfo) => {
      if (userInfo == null) {
        state.userInfo = {
          userid: "",
          username: "",
          useremail: "",
        };
      } else {
        state.isLogin = true;
        state.userInfo = userInfo;
        if (userInfo.isAdmin) {
          state.isAdmin = true;
        }
      }
    },
    CLEAR_CAN_USABLE: (state) => {
      state.CanUsable = null;
    },
    CLEAR_IS_REGIST_ERROR: (state) => {
      state.isRegistError = false;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        (response) => {
          console.log("로그인시도");
          if (response.data.message === "success") {
            let token = response.data["access-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            sessionStorage.setItem("access-token", token);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token);
      findById(
        decode_token.userid,
        (response) => {
          if (response.data.message === "success") {
            commit("SET_USER_INFO", response.data.userInfo);
          } else {
            console.log("유저 정보 없음!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async registUser({ commit }, user) {
      await regist(
        user,
        (response) => {
          console.log("회원가입시도 성공");
          console.log(response);
          if (response.data.message == "success") {
            console.log("가입성공!");
            console.log(response);
            commit("SET_IS_REGIST_ERROR", false);
          } else {
            console.log("가입실패!");
            console.log(response);
            commit("SET_IS_REGIST_ERROR", true);
          }
        },
        (error) => {
          console.log("회원가입시도 실패");
          console.log(error);
          commit("SET_IS_REGIST_ERROR", true);
        }
      );
    },
    async idDuplicateCheck({ commit }, userid) {
      if (userid.length > 3) {
        await idCheck(userid, (response) => {
          console.log("아이디 중복검사 시도 성공");
          if (response.data > 0) {
            console.log("사용할 수 없는 ID");
            commit("SET_CAN_USABLE_ID", false);
          } else {
            console.log("사용할 수 있는 ID");
            commit("SET_CAN_USABLE_ID", true);
          }
        });
      }
    },
  },
};

export default memberStore;
