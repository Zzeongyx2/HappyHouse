import { apiInstance } from "./index.js";

const api = apiInstance();

function listQuestion(success, fail) {
  api.get(`/qna/question`).then(success).catch(fail);
}

function listAnswer(questionno, success, fail) {
    api.get(`/qna/answer/q/${questionno}`).then(success).catch(fail);
  }

function writeQuestion(question, success, fail) {
  api.post(`/qna/question`, JSON.stringify(question)).then(success).catch(fail);
}

function writeAnswer(answer, success, fail) {
    api.post(`/qna/answer`, JSON.stringify(answer)).then(success).catch(fail);
  }

function getQuestion(questionno, success, fail) {
  api.get(`/qna/question/${questionno}`).then(success).catch(fail);  
}

function getAnswer(answerno, success, fail) {
    api.get(`/qna/answer/${answerno}`).then(success).catch(fail);  
  }

function modifyQuestion(question, success, fail) {
  api
    .put(`/qna/question/${question.questionno}`, JSON.stringify(question))
    .then(success)
    .catch(fail);
}

function modifyAnswer(answer, success, fail) {
    api
      .put(`/qna/answer/${answer.answerno}`, JSON.stringify(answer))
      .then(success)
      .catch(fail);
  }

function deleteQuestion(questionno, success, fail) {
  api.delete(`/qna/question/${questionno}`).then(success).catch(fail);
}

function deleteAnswer(answerno, success, fail) {
    api.delete(`/qna/answer/${answerno}`).then(success).catch(fail);
  }

export { listQuestion, listAnswer, writeQuestion, writeAnswer, getQuestion, getAnswer, modifyQuestion, modifyAnswer, deleteQuestion, deleteAnswer };
