<template>
  <b-card no-body>
    <b-card-header class="border-0">
      <b-row class="mb-1">
        <b-col>
          <h3 class="mb-0">{{ article.articleno }}. {{ article.subject }}</h3>
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
    <b-card-footer class="text-right">
      <b-button v-if="isAdmin" variant="outline-primary" @click="moveModify()"
        >공지 수정</b-button
      >
      <b-button v-if="isAdmin" variant="outline-warning" @click="showSwal()"
        >공지 삭제</b-button
      >
    </b-card-footer>
  </b-card>
</template>
<script>
//   import subjects from './../projects'
import { getNotice, deleteNotice } from "@/api/notice";
import { Table, TableColumn } from "element-ui";
// js import
import Swal from "sweetalert2";
// style import
import "sweetalert2/dist/sweetalert2.css";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "NoticeDetail",
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn,
  },
  data() {
    return {
      article: {},
    };
  },
  computed: {
    ...mapState(memberStore, ["isAdmin"]),
    message() {
      if (this.article.content)
        return this.article.content.split("\n").join("<br>");
      return "";
    },
  },
  created() {
    getNotice(
      this.$route.params.articleno,
      ({ data }) => {
        this.article = data;
      },
      () => {}
    );
  },
  methods: {
    moveModify() {
      this.$router.replace({
        name: "noticeModify",
        params: { articleno: this.article.articleno },
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
          // http.delete(`/notice/${this.$route.params.articleno}`).then(({ data }) => {
          //       if(data === "success"){
          //           Swal.fire(
          //             'Deleted!',
          //             '삭제되었습니다.',
          //             'success'
          //           )
          //       }else{
          //         Swal.fire(
          //             'Failed!',
          //             '삭제 중 문제가 발생하였습니다.',
          //             'question'
          //           )
          //       }
          //       this.$router.push({name : "noticeList"});
          //   });
          deleteNotice(
            this.$route.params.articleno,
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
              this.$router.push({ name: "noticeList" });
            },
            () => {}
          );
        }
      });
    },
  },
};
</script>
