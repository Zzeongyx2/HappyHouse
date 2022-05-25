import { newsList } from "@/api/news.js";

const newsStore = {
  namespaced: true,
  state: {
    newsList: [],
  },
  getters: {},
  mutations: {
    SET_NEWS_LIST(state, newsList) {
      state.newsList = newsList;
    },
  },
  actions: {
    getNewsList: ({ commit }) => {
      const params = "부동산";
      newsList(
        params,
        (response) => {
          commit("SET_NEWS_LIST", response.data.items);
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default newsStore;