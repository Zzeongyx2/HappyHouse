<template>
  <div>
    <b-card no-body>
      <b-card-header class="border-0">
        <b-row class="mb-1">
          <b-col>
            <h3 class="mb-0">
              {{ article.questionno }}. {{ article.subject }}
            </h3>
          </b-col>
          <b-col class="text-right">
            <h4>{{ article.userid }}</h4>
            <h5>{{ article.regtime }}</h5>
          </b-col>
        </b-row>
      </b-card-header>
      <b-card-body class="text-left">
        <div v-html="message"></div>
      </b-card-body>
      <b-card-footer
        v-if="userInfo.userid === article.userid"
        class="text-right"
      >
        <b-button variant="outline-primary" @click="moveModify()"
          >질문 수정</b-button
        >
        <b-button variant="outline-warning" @click="showSwal()"
          >질문 삭제</b-button
        >
      </b-card-footer>
    </b-card>
    <answer-list></answer-list>
  </div>
</template>
<script>
//   import subjects from './../projects'
import { getQuestion, deleteQuestion } from "@/api/qna";
import { Table, TableColumn } from "element-ui";
import answerList from "../Qna/AnswerList.vue";
// js import
import Swal from "sweetalert2";
// style import
import "sweetalert2/dist/sweetalert2.css";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "QnaDetail",
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn,
    answerList,
  },
  data() {
    return {
      article: {},
      questionno: 0,
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    getQuestion(
      this.$route.params.questionno,
      ({ data }) => {
        this.article = data;
        this.questionno = this.article.questionno;
      },
      () => {}
    );
  },
  methods: {
    moveModify() {
      this.$router.replace({
        name: "questionModify",
        params: { questionno: this.article.questionno },
      });
    },
    showSwal() {
      Swal.fire({
        title: "삭제하시겠습니까?",
        text: "삭제 후 되돌릴 수 없습니다.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#f5365c",
        cancelButtonColor: "#bcbcbc",
        confirmButtonText: "Delete",
      }).then((result) => {
        if (result.value) {
          deleteQuestion(
            this.$route.params.questionno,
            ({ data }) => {
              if (data === "success") {
                Swal.fire("Deleted!", "삭제되었습니다.", "success");
              } else {
                Swal.fire(
                  "Failed!",
                  "삭제 중 문제가 발생하였습니다.",
                  "question"
                );
              }
              this.$router.push({ name: "questionList" });
            },
            () => {}
          );
        }
      });
    },
  },
};
</script>
