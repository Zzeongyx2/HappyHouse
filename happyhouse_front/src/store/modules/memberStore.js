import jwt_decode from "jwt-decode";
import {
  login,
  findById,
  regist,
  idCheck,
  updateUser,
  updatePassword,
  deleteUser,
} from "@/api/member.js";

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    isRegistError: false,
    isUpdateError: false,
    isDeleteError: false,
    CanUsable: false,
    userInfo: {
      userid: "",
      username: "",
      useremail: "",
      isadmin: false,
      visited: 0,
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
          isadmin: false,
          visited: 0,
        };
      } else {
        state.isLogin = true;
        state.userInfo = userInfo;

        if (userInfo.isadmin) {
          state.isAdmin = true;
        }
      }
    },
    SET_IS_UPDATE_ERROR: (state, isUpdateError) => {
      state.isUpdateError = isUpdateError;
    },
    SET_IS_DELETE_ERROR: (state, isDeleteError) => {
      state.isDeleteError = isDeleteError;
    },
    CLEAR_CAN_USABLE: (state) => {
      state.CanUsable = true;
    },
    CLEAR_IS_REGIST_ERROR: (state) => {
      state.isRegistError = false;
    },
    CLEAR_IS_UPDATE_ERROR: (state) => {
      state.isUpdateError = false;
    },
    CLEAR_IS_DELETE_ERROR: (state) => {
      state.isDeleteError = false;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        (response) => {
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
          if (response.data.message == "success") {
            commit("SET_IS_REGIST_ERROR", false);
          } else {
            commit("SET_IS_REGIST_ERROR", true);
          }
        },
        (error) => {
          commit("SET_IS_REGIST_ERROR", true);
        }
      );
    },
    async idDuplicateCheck({ commit }, userid) {
      if (userid.length > 3) {
        await idCheck(
          userid,
          (response) => {
            if (response.data > 0) {
              commit("SET_CAN_USABLE_ID", false);
            } else {
              commit("SET_CAN_USABLE_ID", true);
            }
          },
          () => {}
        );
      }
    },
    async updateUserStore({ commit }, user) {
      await updateUser(
        user,
        (response) => {
          if ((response.data.message = "success")) {
            commit("SET_USER_INFO", user);
            commit("SET_IS_UPDATE_ERROR", false);
          } else {
            commit("SET_IS_UPDATE_ERROR", true);
          }
        },
        (error) => {
          commit("SET_IS_UPDATE_ERROR", true);
        }
      );
    },
    async updatePasswordStore({ commit }, { userid, userpwd }) {
      await updatePassword(
        {
          userid: userid,
          userpwd: userpwd,
        },
        (response) => {
          if (response.data.message == "success") {
            commit("SET_IS_UPDATE_ERROR", false);
          } else {
            commit("SET_IS_UPDATE_ERROR", true);
          }
        },
        (error) => {
          commit("SET_IS_UPDATE_ERROR", true);
        }
      );
    },
    async deleteUserStore({ commit }, userid) {
      await deleteUser(
        userid,
        (response) => {
          if (response.data.message == "SUCCESS") {
            commit("SET_IS_LOGIN", false);
            commit("SET_USER_INFO", null);
            commit("SET_IS_ADMIN", false);
            commit("SET_IS_DELETE_ERROR", false);
            sessionStorage.removeItem("access-token");
            if (this.$route.path != "/happyhouse")
              this.$router.push({ name: "happyhouse" });
          } else {
            commit("SET_IS_DELETE_ERROR", false);
          }
        },
        (error) => {
          commit("SET_IS_LOGIN", false);
          commit("SET_USER_INFO", null);
          commit("SET_IS_ADMIN", false);
          commit("SET_IS_DELETE_ERROR", false);
          sessionStorage.removeItem("access-token");
          if (this.$route.path != "/happyhouse")
            this.$router.push({ name: "happyhouse" });
        }
      );
    },
  },
};

export default memberStore;
