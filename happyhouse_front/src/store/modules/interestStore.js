import { saveRegion, getRegion, deleteRegion } from "@/api/house.js";

const interestStore = {
  namespaced: true,
  CanSavable: false,
  state: {
    region: {
      userid: "",
      sido: "",
      gugun: "",
      dong: "",
    },
    regionList: [],
  },

  mutations: {
    SET_REGION: (state, region) => {
      if (region != null) {
        state.region = {
          userid: "",
          sido: "",
          gugun: "",
          dong: "",
        };
      } else {
        state.region = region;
      }
    },
    SET_CAN_SAVABLE: (state, CanSavable) => {
      state.CanSavable = CanSavable;
    },
  },

  actions: {
    async isDuplicateCheck({ commit }, region) {
      await regionCheck(
        region,
        (response) => {
          if ((response.data = "success")) {
            commit("SET_CAN_SAVABLE", true);
          } else {
            commit("SET_CAN_SAVABLE", false);
          }
        },
        () => {}
      );
    },
  },
};

export default interestStore;
