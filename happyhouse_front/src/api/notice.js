import { apiInstance } from "./index.js";

const api = apiInstance();

function listNotice(success, fail) {
  api.get(`/notice`).then(success).catch(fail);
}

function writeNotice(notice, success, fail) {
  api.post(`/notice`, JSON.stringify(notice)).then(success).catch(fail);
}

function getNotice(articleno, success, fail) {
  api.get(`/notice/${articleno}`).then(success).catch(fail);
}

function modifyNotice(notice, success, fail) {
  api
    .put(`/notice/${notice.articleno}`, JSON.stringify(notice))
    .then(success)
    .catch(fail);
}

function deleteNotice(articleno, success, fail) {
  api.delete(`/notice/${articleno}`).then(success).catch(fail);
}

export { listNotice, writeNotice, getNotice, modifyNotice, deleteNotice };
