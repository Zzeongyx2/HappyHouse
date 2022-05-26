<template>
  <b-card no-body>
    <b-card-header class="border-0">
      <b-row class="mb-1">
        <b-col>
          <h2 class="mb-0">공지사항</h2>
        </b-col>
        <b-col class="text-right">
          <b-button
            v-if="isAdmin"
            variant="outline-success"
            @click="moveRegist()"
            >공지 작성</b-button
          >
        </b-col>
      </b-row>
    </b-card-header>

    <el-table
      class="table-responsive table"
      header-row-class-name="thead-light"
      @row-click="moveDetail"
      :data="paginatedItems"
    >
      <el-table-column label="No." prop="articleno" min-width="30px">
      </el-table-column>

      <el-table-column label="subject" prop="subject" min-width="140px">
      </el-table-column>

      <el-table-column label="Userid" prop="userid" min-width="30px">
      </el-table-column>

      <el-table-column label="RegTime" prop="regtime" min-width="50px">
      </el-table-column>
    </el-table>

    <b-card-footer class="py-4 d-flex justify-content-end">
      <base-pagination
        v-model="currentPage"
        :per-page="perPage"
        :total="rows"
        @change="onPageChanged"
      ></base-pagination>
    </b-card-footer>
  </b-card>
</template>

<script>
//   import subjects from './../projects'
//   import http from "@/api/index";
import { listNotice } from "@/api/notice";
import noticeListItem from "../NoticeListItem.vue";
import { Table, TableColumn } from "element-ui";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "noticeList",
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn,
    noticeListItem,
  },
  data() {
    return {
      noticeListItem: [],
      paginatedItems: [],
      currentPage: 1,
      perPage: 3,
    };
  },
  created() {
    listNotice(
      ({ data }) => {
        this.noticeListItem = data;
        this.paginate(this.perPage, 0);
      },
      () => {}
    );
  },
  computed: {
    ...mapState(memberStore, ["isAdmin"]),
    rows() {
      return this.noticeListItem.length;
    },
  },
  mounted() {
    this.paginate(this.perPage, 0);
  },
  methods: {
    paginate(page_size, page_number) {
      let itemsToParse = this.noticeListItem;

      this.paginatedItems = itemsToParse.slice(
        page_number * page_size,
        (page_number + 1) * page_size
      );
    },
    onPageChanged(page) {
      this.paginate(this.perPage, page - 1);
    },
    moveDetail(row) {
      this.$router.push({
        name: "noticeDetail",
        params: { articleno: row.articleno },
      });
    },
    moveRegist() {
      this.$router.push({ name: "noticeRegister" });
    },
  },
};
</script>
