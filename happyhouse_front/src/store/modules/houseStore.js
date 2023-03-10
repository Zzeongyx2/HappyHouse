import { sidoList, gugunList, dongList, houseDetailList } from "@/api/house.js";

const houseStore = {
  namespaced: true,
  state: {
    sidos: [{ value: null, text: "선택하세요" }],
    guguns: [{ value: null, text: "선택하세요" }],
    dongs: [{ value: null, text: "선택하세요" }],
    houses: [],
    markerlist: [],
    house: null,
    egion: {
      userid: "",
      sido: "",
      gugun: "",
      dong: "",
      dongcode: "",
    },
  },

  getters: {},

  mutations: {
    SET_SIDO_LIST: (state, sidos) => {
      sidos.forEach((sido) => {
        state.sidos.push({ value: sido.sidoCode, text: sido.sidoName });
      });
    },
    SET_GUGUN_LIST: (state, guguns) => {
      guguns.forEach((gugun) => {
        state.guguns.push({ value: gugun.gugunCode, text: gugun.gugunName });
      });
    },
    SET_DONG_LIST: (state, dongs) => {
      dongs.forEach((dong) => {
        state.dongs.push({ value: dong.dongCode, text: dong.dongName });
      });
    },
    SET_MARKER_LIST: (state, infos) => {
      state.markerlist = [];
      infos.forEach((item) => {
        state.markerlist.push({
          aptName: item.aptName,
          lat: item.lat,
          lng: item.lng,
          recentDealDate:
            item.aptDetailInfos[0].dealYear +
            "." +
            item.aptDetailInfos[0].dealMonth +
            "." +
            item.aptDetailInfos[0].dealDay,
          recentDealAmount: item.aptDetailInfos[0].dealAmount.trim(),
          buildYear: item.buildYear,
          sidoName: item.sidoName,
          gugunName: item.gugunName,
          jibun: item.jibun,
          dongName: item.dongName,
        });
      });
    },
    CLEAR_SIDO_LIST: (state) => {
      state.sidos = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_GUGUN_LIST: (state) => {
      state.guguns = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_DONG_LIST: (state) => {
      state.dongs = [{ value: null, text: "선택하세요" }];
    },
    CLEAR_HOUSE_LIST: (state) => {
      state.houses = [];
    },
    SET_HOUSE_LIST: (state, houses) => {
      state.houses = houses;
    },
    SET_DETAIL_HOUSE: (state, house) => {
      state.house = house;
    },
    SET_REGION: (state, house) => {
      state.region.sido = house.sidoName;
      state.region.gugun = house.gugunName;
      state.region.dong = house.dongName;
      state.region.dongcode = house.dongCode;
    },
  },

  actions: {
    getSido: ({ commit }) => {
      sidoList(
        ({ data }) => {
          commit("SET_SIDO_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getGugun: ({ commit }, sidoCode) => {
      const params = {
        sido: sidoCode,
      };
      gugunList(
        params,
        ({ data }) => {
          commit("SET_GUGUN_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getDong: ({ commit }, gugunCode) => {
      const params = { gugun: gugunCode };
      dongList(
        params,
        ({ data }) => {
          commit("SET_DONG_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    getHouseList: ({ commit }, dongCode) => {
      const params = {
        dong: dongCode,
      };
      houseDetailList(
        params,
        (response) => {
          commit("SET_HOUSE_LIST", response.data);
          commit("SET_MARKER_LIST", response.data);
          commit("SET_REGION", response.data[0]);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    detailHouse: ({ commit }, house) => {
      // 나중에 house.일련번호를 이용하여 API 호출
      commit("SET_DETAIL_HOUSE", house);
    },
  },
};

export default houseStore;
