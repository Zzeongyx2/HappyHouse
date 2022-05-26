<template>
  <b-card body-class="p-0" header-class="border-0">
    <template v-slot:header>
      <b-row align-v="center">
        <b-col>
          <h3 class="mb-0">청약 분양 정보</h3>
        </b-col>
        <b-col class="text-right">
          <a
            href="https://www.applyhome.co.kr/co/coa/selectMainView.do"
            target="_blank"
            class="btn btn-sm btn-primary"
            >더 보기</a
          >
        </b-col>
      </b-row>
    </template>

    <el-table
      class="table-responsive table"
      :data="subList"
      header-row-class-name="thead-light"
      max-height="500px"
      v-el-table-infinite-scroll=""
      @row-click="rowrow"
    >
      <el-table-column type="expand">
        <template #default="props">
          <div m="4" font-family="sans-serif" font-size="15px">
            공급위치: {{ props.row.HSSPLY_ADRES }}
            <br />
            모집공고일 : {{ props.row.RCRIT_PBLANC_DE }}
            <br />
            청약 접수 : {{ props.row.RCEPT_BGNDE }} ~
            {{ props.row.RCEPT_ENDDE }}
            <br />
            입주예정 : {{ props.row.MVN_PREARNGE_YM }}
            <br />
            <a :href="props.row.HMPG_ADRES">홈페이지</a>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="NAME" min-width="110px" prop="HOUSE_NM">
        <template v-slot="{ row }">
          <div>{{ row.HOUSE_NM }}</div>
        </template>
      </el-table-column>
      <el-table-column label="SUPLY" min-width="50px" prop="TOT_SUPLY_HSHLDCO">
        <template v-slot="{ row }">
          <div>{{ row.TOT_SUPLY_HSHLDCO }}</div>
        </template>
      </el-table-column>
      <!-- <el-table-column label="Date" min-width="40px" prop="pubDate">
      </el-table-column> -->
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
import { subscriptionList } from "@/api/subscription";

export default {
  name: "subscriptionTable",
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
  data() {
    return {
      param: {
        serviceKey:
          "cG+goOX7r+H556sFCeCqakNiCNGWOQagcjsamQqj0pwG92h8N6i5IB2L66ouCGde4c9CWVUO+ikznTKfTAoyMA==",
        page: 1,
        perPage: 20,
      },
      subList: [],
    };
  },
  created() {
    subscriptionList(
      this.param,
      ({ data }) => {
        this.subList = data.data;
      },
      () => {}
    );
  },
};
</script>
<style></style>
