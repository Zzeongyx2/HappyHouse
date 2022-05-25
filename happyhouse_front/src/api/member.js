import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function regist(user, success, fail) {
  await api
    .post(`/user/regist`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

async function idCheck(userid, success, fail) {
  await api.get(`/user/idcheck/${userid}`).then(success).catch(fail);
}

async function updateUser(user, success, fail) {
  await api
    .put(`/user/updateUser`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

async function updatePassword(user, success, fail) {
  await api
    .put(`/user/updatePwd`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

async function deleteUser(userid, success, fail) {
  await api.delete(`/user/deleteUser/${userid}`).then(success).catch(fail);
}

async function findUserPwd(user, success, fail) {
  await api
    .post(`/user/findPwd`, JSON.stringify(user))
    .then(success)
    .catch(fail);
}

function calcData(success, fail) {
  api.get(`/user/users`).then(success).catch(fail);
}

export {
  login,
  findById,
  regist,
  idCheck,
  updateUser,
  updatePassword,
  deleteUser,
  findUserPwd,
  calcData,
};
