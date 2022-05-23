import { apiInstance } from "./index.js";

const api = apiInstance();

function listCommunity(success, fail) {
  api.get(`/comm/community`).then(success).catch(fail);
}

function listReply(communityno, success, fail) {
    api.get(`/comm/reply/c/${communityno}`).then(success).catch(fail);
  }

function writeCommunity(community, success, fail) {
  api.post(`/comm/community`, JSON.stringify(community)).then(success).catch(fail);
}

function writeReply(reply, success, fail) {
    api.post(`/comm/reply`, JSON.stringify(reply)).then(success).catch(fail);
  }

function getCommunity(communityno, success, fail) {
  api.get(`/comm/community/${communityno}`).then(success).catch(fail);  
}

function getReply(replyno, success, fail) {
    api.get(`/community/reply/${replyno}`).then(success).catch(fail);  
  }

function modifyCommunity(community, success, fail) {
  api
    .put(`/comm/community/${community.communityno}`, JSON.stringify(community))
    .then(success)
    .catch(fail);
}

function modifyReply(reply, success, fail) {
    api
      .put(`/comm/reply/${reply.replyno}`, JSON.stringify(reply))
      .then(success)
      .catch(fail);
  }

function deleteCommunity(communityno, success, fail) {
  api.delete(`/comm/community/${communityno}`).then(success).catch(fail);
}

function deleteReply(replyno, success, fail) {
    api.delete(`/comm/reply/${replyno}`).then(success).catch(fail);
  }

export { listCommunity, listReply, writeCommunity, writeReply, getCommunity, getReply, modifyCommunity, modifyReply, deleteCommunity, deleteReply };
