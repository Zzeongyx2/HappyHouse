<template>
  <div>
    <base-header
      class="pb-6 pb-8 pt-5 pt-md-5 bg-gradient-success position-relative"
    >
      <!-- Card stats -->
      <!-- <b-row>
        <b-col xl="3" md="6">
          <stats-card
            title="Total traffic"
            type="gradient-red"
            sub-title="350,897"
            icon="ni ni-active-40"
            class="mb-4"
          >
            <template slot="footer">
              <span class="text-success mr-2">3.48%</span>
              <span class="text-nowrap">Since last month</span>
            </template>
          </stats-card>
        </b-col>
        <b-col xl="3" md="6">
          <stats-card
            title="Total traffic"
            type="gradient-orange"
            sub-title="2,356"
            icon="ni ni-chart-pie-35"
            class="mb-4"
          >
            <template slot="footer">
              <span class="text-success mr-2">12.18%</span>
              <span class="text-nowrap">Since last month</span>
            </template>
          </stats-card>
        </b-col>
        <b-col xl="3" md="6">
          <stats-card
            title="Sales"
            type="gradient-green"
            sub-title="924"
            icon="ni ni-money-coins"
            class="mb-4"
          >
            <template slot="footer">
              <span class="text-danger mr-2">5.72%</span>
              <span class="text-nowrap">Since last month</span>
            </template>
          </stats-card>
        </b-col>
        <b-col xl="3" md="6">
          <stats-card
            title="Performance"
            type="gradient-info"
            sub-title="49,65%"
            icon="ni ni-chart-bar-32"
            class="mb-4"
          >
            <template slot="footer">
              <span class="text-success mr-2">54.8%</span>
              <span class="text-nowrap">Since last month</span>
            </template>
          </stats-card>
        </b-col>
      </b-row> -->
      <b-row class="mt-4 mb-4 text-center">
        <!-- <b-col class="sm-3">
      <b-form-input
        v-model.trim="dongCode"
        placeholder="동코드 입력...(예 : 11110)"
        @keypress.enter="sendKeyword"
      ></b-form-input>
    </b-col>
    <b-col class="sm-3" align="left">
      <b-button variant="outline-primary" @click="sendKeyword">검색</b-button>
    </b-col> -->
        <b-col cols="2">
          <b-form-select
            v-model="sidoCode"
            :options="sidos"
            @change="gugunList"
          ></b-form-select>
        </b-col>
        <b-col cols="2">
          <b-form-select
            v-model="gugunCode"
            :options="guguns"
            @change="dongList"
          ></b-form-select>
        </b-col>
        <b-col cols="2">
          <b-form-select
            v-model="dongCode"
            :options="dongs"
            @change="searchApt"
          ></b-form-select>
        </b-col>
        <b-col cols="6"> <b-card></b-card> </b-col>
      </b-row>
    </base-header>

    <b-container fluid class="mt--7" style="height: 700px">
      <b-row class="h-100">
        <b-col xl="6">
          <b-card no-body class="border-0">
            <div id="kakao-map" class="map-canvas" style="height: 700px"></div>
          </b-card>
        </b-col>
        <b-col xl="6">
          <b-card no-body class="border-0">
            <template v-slot:header>
              <b-row align-v="center">
                <b-col>
                  <h3
                    v-if="houses.length > 0"
                    class="mb-0"
                    v-html="houses[0].dongName + `의 APT LIST`"
                  >
                    의
                  </h3>
                  <h3 v-else class="mb-0">APT LIST</h3>
                </b-col>
                <b-col class="text-right">
                  <a href="#!" class="btn btn-sm btn-primary">관심지역 추가</a>
                </b-col>
              </b-row>
            </template>
            <el-table
              class="table-responsive table"
              :data="houses"
              :cell-style="{ height: '40px' }"
              header-row-class-name="thead-light"
              max-height="700"
              v-el-table-infinite-scroll
            >
              <el-table-column type="expand">
                <template #default="props">
                  <b-card>
                    <template v-slot:header>
                      <b-row align-v="center">
                        <b-col>
                          <h3 class="mb-0">{{ props.row.aptName }}</h3>
                        </b-col>
                        <b-col class="text-right">
                          <a href="#!" class="btn btn-sm btn-primary">검색</a>
                        </b-col>
                      </b-row>
                    </template>
                    <el-table
                      class="table-responsive table"
                      :data="props.row.aptDetailInfos"
                      :cell-style="{ height: '40px' }"
                      header-row-class-name="thead-light"
                      id="innertable"
                      v-el-table-infinite-scroll
                    >
                      <el-table-column
                        label="거래 날짜"
                        min-width="90px"
                        prop="row"
                        :formatter="formatter1"
                        sortable
                      >
                      </el-table-column>
                      <el-table-column
                        label="평 수"
                        min-width="90px"
                        prop="area"
                        sortable
                      >
                      </el-table-column>
                      <el-table-column
                        label="층"
                        min-width="60px"
                        prop="floor"
                        sortable
                      >
                      </el-table-column>
                      <el-table-column
                        label="dealAmount (단위 : 만)"
                        min-width="130px"
                        prop="dealAmount"
                        sortable
                      >
                      </el-table-column>
                    </el-table>
                  </b-card>
                </template>
              </el-table-column>
              <el-table-column label="Address" min-width="140px"
                ><template v-slot="{ row }">
                  <div class="font-weight-600">
                    {{
                      row.sidoName +
                      " " +
                      row.gugunName +
                      " " +
                      row.dongName +
                      " " +
                      row.jibun
                    }}
                  </div>
                </template>
              </el-table-column>
              <el-table-column
                label="Name"
                min-width="100px"
                sortable
                prop="aptName"
              >
              </el-table-column>
              <el-table-column
                label="Build Year"
                min-width="75px"
                sortable
                prop="buildYear"
              >
              </el-table-column>
              <el-table-column label="History" min-width="60px">
                <template v-slot="{ row }">
                  <div class="font-weight-600">
                    {{ row.aptDetailInfos.length }}
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import { Select, Option } from "element-ui";
import { mapState, mapActions, mapMutations } from "vuex";
import elTableInfiniteScroll from "el-table-infinite-scroll";
import {
  Table,
  TableColumn,
  DropdownMenu,
  DropdownItem,
  Dropdown,
} from "element-ui";
const houseStore = "houseStore";

export default {
  name: "GoogleMap",
  components: {
    [Select.name]: Select,
    [Option.name]: Option,
    [Table.name]: Table,
    [TableColumn.name]: TableColumn,
    [Dropdown.name]: Dropdown,
    [DropdownItem.name]: DropdownItem,
    [DropdownMenu.name]: DropdownMenu,
  },
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      infowindow: null,
      markers: [],
      markerPositions2: [
        [37.499590490909185, 127.0263723554437],
        [37.499427948430814, 127.02794423197847],
        [37.498553760499505, 127.02882598822454],
        [37.497625593121384, 127.02935713582038],
        [37.49629291770947, 127.02587362608637],
        [37.49754540521486, 127.02546694890695],
        [37.49646391248451, 127.02675574250912],
      ],
    };
  },
  directives: {
    "el-table-infinite-scroll": elTableInfiniteScroll,
  },
  watch: {
    markerlist: function () {
      console.log("markers watch 실행됨");
      this.displayMarker(this.markerPositions2, this.markerlist);
    },
  },
  computed: {
    ...mapState(houseStore, [
      "sidos",
      "guguns",
      "dongs",
      "houses",
      "markerlist",
    ]),
  },
  created() {
    // this.$store.dispatch("getSido");
    // this.sidoList();
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.CLEAR_HOUSE_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(houseStore, [
      "getSido",
      "getGugun",
      "getDong",
      "getHouseList",
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
      "CLEAR_HOUSE_LIST",
    ]),
    gugunList() {
      console.log(this.sidoCode);
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;
      if (this.sidoCode) this.getGugun(this.sidoCode);
    },
    dongList() {
      console.log(this.gugunCode);
      this.CLEAR_DONG_LIST();
      this.dongCode = null;
      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    searchApt() {
      if (this.dongCode) {
        this.getHouseList(this.dongCode);
        console.log(this.markerlist);
      }
    },
    initMap() {
      const container = document.getElementById("kakao-map");
      const options = {
        center: new kakao.maps.LatLng(37.50149, 127.03965),
        level: 4,
      };
      var mapTypeControl = new kakao.maps.MapTypeControl();
      var zoomControl = new kakao.maps.ZoomControl();

      this.map = new kakao.maps.Map(container, options);
      this.map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);
      this.map.addControl(zoomControl, kakao.maps.ControlPosition.RIGHT);
    },
    changeSize(size) {
      const container = document.getElementById("map");
      container.style.width = `${size}px`;
      container.style.height = `${size}px`;
      this.map.relayout();
    },
    displayMarker(markerPositions, markerarray) {
      console.log("display안에서 찍는", markerarray);
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }

      const positions = markerPositions.map(
        (position) => new kakao.maps.LatLng(...position)
      );
      const positions1 = markerarray.map(
        (position) => new kakao.maps.LatLng(position.lat, position.lng)
      );
      console.log("postions1", positions1);
      if (positions1.length > 0) {
        this.markers = positions1.map(
          (position) =>
            new kakao.maps.Marker({
              map: this.map,
              position,
            })
        );

        const bounds = positions1.reduce(
          (bounds, latlng) => bounds.extend(latlng),
          new kakao.maps.LatLngBounds()
        );

        this.map.setBounds(bounds);
      }
    },
    displayInfoWindow() {
      if (this.infowindow && this.infowindow.getMap()) {
        //이미 생성한 인포윈도우가 있기 때문에 지도 중심좌표를 인포윈도우 좌표로 이동시킨다.
        this.map.setCenter(this.infowindow.getPosition());
        return;
      }

      var iwContent = '<div style="padding:5px;">Hello World!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        iwPosition = new kakao.maps.LatLng(33.450701, 126.570667), //인포윈도우 표시 위치입니다
        iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

      this.infowindow = new kakao.maps.InfoWindow({
        map: this.map, // 인포윈도우가 표시될 지도
        position: iwPosition,
        content: iwContent,
        removable: iwRemoveable,
      });

      this.map.setCenter(iwPosition);
    },
    formatter1(row, column) {
      return `${row.dealYear}.${row.dealMonth}.${row.dealDay}`;
    },
  },
  mounted() {
    if (window.kakao && window.kakao.maps) {
      this.initMap();
    } else {
      const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      script.src =
        "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=48c5481becc496317193e6ddc98de584&libraries=services";
      document.head.appendChild(script);
    }
  },
};
</script>

<style scoped>
.table {
  display: flex;
  flex-direction: column;
  height: 100%;
  width: 100%;
}

.table >>> .el-table__body-wrapper {
  height: 600px !important;
}

#innertable >>> .el-table__body-wrapper {
  height: 300px !important;
}
</style>
