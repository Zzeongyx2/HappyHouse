<template>
  <b-card no-body>
    <b-card-header class="border-0">
      <b-row class="mb-1">
        <b-col>
          <h3 class="mb-0">New Notice</h3>
        </b-col>
      </b-row>
    </b-card-header>
    <b-card-body class="text-left">
      <form action="">
        <b-row class="form-group">
          <label class="col-md-1 col-form-label form-control-label">제목</label>
          <b-col md="11">
            <b-input v-model="article.subject"></b-input>
          </b-col>
        </b-row>
        <b-row class="form-group">
          <label class="col-md-1 col-form-label form-control-label">내용</label>
          <b-col md="11">
            <b-textarea
              rows="5"
              max-rows="5"
              v-model="article.content"
            ></b-textarea>
          </b-col>
        </b-row>
      </form>
    </b-card-body>
    <b-card-footer class="text-right">
      <b-button variant="outline-success" @click="registerArticle()"
        >등록</b-button
      >
      <b-button variant="outline-light" @click="moveList()">목록</b-button>
    </b-card-footer>
  </b-card>
</template>
<script>
//   import subjects from './../projects'
//   import http from "@/api/index";
import { writeNotice } from "@/api/notice";
import { Table, TableColumn } from "element-ui";
import Swal from "sweetalert2";
import { mapState } from "vuex";
const memberStore = "memberStore";
// style import
import "sweetalert2/dist/sweetalert2.css";

export default {
  name: "NoticeDetail",
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn,
  },
  data() {
    return {
      article: {
        userid: "",
      },
    };
  },
  created() {
    this.article.userid = this.userInfo.userid;
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    registerArticle() {
      writeNotice(
        this.article,
        ({ data }) => {
          if (data === "success") {
            Swal.fire("Registered!", "등록되었습니다.", "success");
          } else {
            Swal.fire("Failed!", "등록 중 문제가 발생하였습니다.", "warning");
          }
          // 현재 route를 /list로 변경.
          this.$router.push({ name: "noticeList" });
        },
        () => {}
      );
    },
    moveList() {
      this.$router.push({ name: "noticeList" });
    },
  },
};
</script>
