import { apiInstance } from "./index.js";

const api = apiInstance();
// const apt = aptInstance();

function newsList(keyword, success, fail) {
  api
    .get(`/naver/news?keyword=` + keyword)
    .then(success)
    .catch(fail);
}

export { newsList };
