<template>
  <b-card body-class="p-0" header-class="border-0">
    <template v-slot:header>
      <b-row align-v="center">
        <b-col>
          <h3 class="mb-0">부동산 관련 뉴스</h3>
        </b-col>
        <b-col class="text-right">
          <a href="https://land.naver.com/" target="_blank" class="btn btn-sm btn-primary">더 보기</a>
        </b-col>
      </b-row>
    </template>

    <el-table
      class="table-responsive table"
      :data="newsList"
      header-row-class-name="thead-light"
      max-height="500px"
      v-el-table-infinite-scroll
    >
      <el-table-column label="Title" min-width="60px" prop="title">
        <template v-slot="{ row }">
          <a class="font-weight-600" :href="row.link" target="_blank" v-html="row.title"></a>
        </template>
      </el-table-column>
      <el-table-column label="Contents" min-width="120px" prop="description">
        <template v-slot="{ row }">
          <div v-html="row.description"></div>
        </template>
      </el-table-column>
      <el-table-column label="Date" min-width="40px" prop="pubDate">
      </el-table-column>
    </el-table>
  </b-card>
</template>

<script>
import elTableInfiniteScroll from "el-table-infinite-scroll";
import {
  Table,
  TableColumn,
  DropdownMenu,
  DropdownItem,
  Dropdown,
} from "element-ui";
import { mapState, mapActions } from "vuex";
const newsStore = "newsStore";

export default {
  name: "newsTable",
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn,
    [Dropdown.name]: Dropdown,
    [DropdownItem.name]: DropdownItem,
    [DropdownMenu.name]: DropdownMenu,
  },
  directives: {
    "el-table-infinite-scroll": elTableInfiniteScroll,
  },
  computed: {
    ...mapState(newsStore, ["newsList"]),
  },
  mounted(){
    this.getNewsList();
    console.log(this.newsList);
  },
  methods: {
    ...mapActions(newsStore, ["getNewsList"]),
  },
};
</script>
<style></style>
