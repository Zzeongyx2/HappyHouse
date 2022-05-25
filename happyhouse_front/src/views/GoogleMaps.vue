<template>
  <div>
    <base-header
      class="pb-6 pb-8 pt-5 pt-md-5 bg-gradient-success position-relative"
    >
      <b-row class="mt-4 mb-4 text-center">
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
        <b-col
          cols="6"
          class="bg-white rounded text-left"
          style="font-size: 14px"
        >
          <span
            >Step 1 : 왼쪽에서 지역을 선택하세요! (표의 ❤를 누르면 관심지역으로
            등록됩니다! <b>List</b>를 누르면 등록한 관심지역을 확인하실 수
            있어요!)<br />
            Step 2 : <b>확장 탭</b>을 이용하여 데이터를 살펴보세요! 또
            <b>아파트 이름</b>으로 검색해보세요!</span
          >
        </b-col>
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
                  <b-form-input
                    tyle="text"
                    v-model="findApt"
                    placeholder="아파트 이름 검색..."
                  ></b-form-input>
                </b-col>
                <b-col class="text-right">
                  <b-button
                    size="sm"
                    style="margin: 3px"
                    @click="getInterest"
                    :pressed.sync="isShow"
                    >list</b-button
                  >
                  <b-button
                    variant="success"
                    size="sm"
                    style="margin: 3px"
                    @click="saveInterest"
                    >❤</b-button
                  >
                </b-col>
              </b-row>
            </template>
            <el-table
              type="expand"
              class="table-responsive table"
              :data="regionList"
              :cell-style="{ height: '40px' }"
              header-row-class-name="thead-light"
              v-el-table-infinite-scroll=""
              @row-click="moveRegion"
              v-if="isShow"
            >
              <el-table-column label="Interest" min-width="110px">
                <template v-slot="{ row }">
                  <div class="font-weight-600">
                    {{ row.sido }} {{ row.gugun }} {{ row.dong }}
                    <div style="float: right">
                      <b-button
                        size="sm"
                        style="margin: 3px"
                        @click="deleteInterest(row)"
                        >X</b-button
                      >
                    </div>
                  </div>
                </template>
              </el-table-column>
            </el-table>
            <el-table
              v-if="filteredList.length > 0"
              class="table-responsive table"
              :data="filteredList"
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
                        label="거래가격"
                        min-width="130px"
                        prop="dealAmount"
                        sortable
                      >
                      </el-table-column>
                      <el-table-column label="평당 가격" min-width="80px">
                        <template v-slot="{ row }"
                          ><div>
                            {{
                              (
                                Number(row.dealAmount.replace(",", "")) /
                                Number(row.area)
                              ).toFixed(2)
                            }}
                          </div>
                        </template>
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
                min-width="95px"
                sortable
                prop="buildYear"
              >
              </el-table-column>
              <el-table-column label="History" min-width="80px">
                <template v-slot="{ row }">
                  <div class="font-weight-600">
                    {{ row.aptDetailInfos.length }}
                  </div>
                </template>
              </el-table-column>
            </el-table>
            <el-table
              v-else
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
                        min-width="80px"
                        prop="area"
                        sortable
                      >
                      </el-table-column>
                      <el-table-column
                        label="층"
                        min-width="80px"
                        prop="floor"
                        sortable
                      >
                      </el-table-column>
                      <el-table-column
                        label="거래가격"
                        min-width="110px"
                        prop="dealAmount"
                        sortable
                      >
                      </el-table-column>
                      <el-table-column label="평당 가격" min-width="80px">
                        <template v-slot="{ row }"
                          ><div>
                            {{
                              (
                                Number(row.dealAmount.replace(",", "")) /
                                Number(row.area)
                              ).toFixed(2)
                            }}
                          </div>
                        </template>
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
                min-width="95px"
                sortable
                prop="buildYear"
              >
              </el-table-column>
              <el-table-column label="History" min-width="80px">
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
import { saveRegion, getRegion, deleteRegion } from "@/api/interest";
import Swal from "sweetalert2";
import {
  Table,
  TableColumn,
  DropdownMenu,
  DropdownItem,
  Dropdown,
} from "element-ui";
const houseStore = "houseStore";
const memberStore = "memberStore";

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
      infowindows: [],
      overlays: [],
      isShow: false,
      regionList: [],
      findApt: "",
      filteredList: [],
      region: {
        userid: "",
        sido: "",
        gugun: "",
        dong: "",
      },
    };
  },
  directives: {
    "el-table-infinite-scroll": elTableInfiniteScroll,
  },
  watch: {
    markerlist: function () {
      console.log("markers watch 실행됨");
      this.displayMarker(this.markerlist);
    },
    houses: function () {
      if (this.houses.length > 0) {
        this.region.sido = this.houses[0].sidoName;
        this.region.gugun = this.houses[0].gugunName;
        this.region.dong = this.houses[0].dongName;
      }
    },
    findApt: function () {
      this.filteredList = [];
      this.houses.map((item) => {
        if (String(item.aptName).includes(this.findApt)) {
          this.filteredList.push(item);
        }
      });
      this.displayMarker(this.filteredList);
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
    ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    // this.$store.dispatch("getSido");
    // this.sidoList();
    this.CLEAR_SIDO_LIST();
    this.CLEAR_GUGUN_LIST();
    this.CLEAR_HOUSE_LIST();
    this.getSido();
    this.region.userid = this.userInfo.userid;
    console.log(this.userInfo);
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
    deleteInterest(row) {
      // console.log("row", row);
      deleteRegion(
        row,
        ({ data }) => {
          if (data === "success") {
            Swal.fire("Deleted!", "삭제되었습니다.", "success");
          } else {
            Swal.fire("Failed!", "삭제 중 문제가 발생하였습니다.", "warning");
          }
          // 현재 route를 /list로 변경.
          this.getInterest();
        },
        () => {}
      );
    },
    getInterest() {
      getRegion(
        this.region.userid,
        ({ data }) => {
          this.regionList = data;
          console.log(this.regionList);
          console.log(this.isShow);
        },
        () => {}
      );
    },
    saveInterest() {
      console.log(this.region);
      if (
        (this.region.sido != null) &
        (this.region.gugun != null) &
        (this.dongCode != null)
      ) {
        console.log("데이터있음");
        saveRegion(
          this.region,
          ({ data }) => {
            if (data === "success") {
              Swal.fire("Registered!", "등록되었습니다.", "success");
            } else {
              Swal.fire("Failed!", "등록 중 문제가 발생하였습니다.", "warning");
            }
            this.getInterest();
          },
          () => {}
        );
      } else {
        console.log("데이터없음");
      }
    },
    moveRegion(row) {
      let self = this;
      console.log(row);
      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();
      const addr = row.sido + " " + row.gugun + " " + row.dong;
      console.log(addr);
      // 주소로 좌표를 검색합니다
      geocoder.addressSearch(addr, function (result, status) {
        // 정상적으로 검색이 완료됐으면
        if (status === kakao.maps.services.Status.OK) {
          var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
          self.map.setCenter(coords);
        }
      });
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
    displayMarker(markerarray) {
      let self = this;
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
        this.markers = [];
      }
      let placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 5 });
      let contentNode = document.createElement("div");
      contentNode.className = "aptinfo_wrap"; // place -> apt
      addEventHandle(contentNode, "mousedown", kakao.maps.event.preventMap);
      addEventHandle(contentNode, "touchstart", kakao.maps.event.preventMap);
      placeOverlay.setContent(contentNode);
      var bounds = new kakao.maps.LatLngBounds();
      deleteOverlay();
      for (var i = 0; i < markerarray.length; i++) {
        var marker = new kakao.maps.Marker({
          map: self.map,
          position: new kakao.maps.LatLng(
            markerarray[i].lat,
            markerarray[i].lng
          ),
        });
        let content =
          '<div class="aptinfo">' +
          '   <span class="title" ' +
          'target="_blank" title="' +
          markerarray[i].aptName +
          '" onclick="self.sayhello">' +
          markerarray[i].aptName +
          "</span>";

        content +=
          '    <span title="' +
          markerarray[i].recentDealDate +
          '">' +
          "최근거래 : " +
          markerarray[i].recentDealDate +
          "</span>" +
          '  <span class="amount" title="' +
          markerarray[i].recentDealAmount +
          '">가격 : ' +
          markerarray[i].recentDealAmount +
          "(만)</span>";

        content +=
          '    <span class="build" title="' +
          markerarray[i].buildYear +
          '">건축년도 : ' +
          markerarray[i].buildYear +
          "년</span>";

        content += "</div>" + '<div class="after"></div>';

        contentNode.innerHTML = content;
        (function (marker, place) {
          kakao.maps.event.addListener(marker, "mouseover", function () {
            placeOverlay.setPosition(
              new kakao.maps.LatLng(place.lat, place.lng)
            );
            placeOverlay.setMap(self.map);
          });
        })(marker, markerarray[i]);
        (function (marker) {
          kakao.maps.event.addListener(marker, "mouseout", function () {
            placeOverlay.setMap(null);
          });
        })(marker);

        this.markers.push(marker);
        bounds.extend(
          new kakao.maps.LatLng(markerarray[i].lat, markerarray[i].lng)
        );

        marker.setMap(self.map);
      }
      self.map.setBounds(bounds);

      function addEventHandle(target, type, callback) {
        if (target.addEventListener) {
          target.addEventListener(type, callback);
        } else {
          target.attachEvent("on" + type, callback);
        }
      }
      function deleteOverlay() {
        // 커스텀 오버레이를 숨깁니다
        placeOverlay.setMap(null);
        // 지도에 표시되고 있는 마커를 제거합니다
      }

      function displayPlaceInfo(place) {
        console.log(place);
        let content =
          '<div class="aptinfo">' +
          '   <span class="title" ' +
          'target="_blank" title="' +
          place.aptName +
          '" onclick="self.sayhello">' +
          place.aptName +
          "</span>";

        content +=
          '    <span title="' +
          place.recentDealDate +
          '">' +
          "최근거래 : " +
          place.recentDealDate +
          "</span>" +
          '  <span class="amount" title="' +
          place.recentDealAmount +
          '">가격 : ' +
          place.recentDealAmount +
          "(만)</span>";

        content +=
          '    <span class="build" title="' +
          place.buildYear +
          '">건축년도 : ' +
          place.buildYear +
          "년</span>";

        content += "</div>" + '<div class="after"></div>';

        contentNode.innerHTML = content;

        console.log(contentNode);

        placeOverlay.setPosition(new kakao.maps.LatLng(place.lat, place.lng));
        placeOverlay.setMap(self.map);
      }
    },
    sayhello() {
      console.log("hello");
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

<style>
.aptinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -90px;
  width: 180px;
}

.aptinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.aptinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.aptinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
}
.aptinfo a,
.aptinfo a:hover,
.aptinfo a:active {
  color: #fff;
  text-decoration: none;
}
.aptinfo a,
.aptinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.aptinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.aptinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #ffaaaa;
  background: #ffaaaa no-repeat right 14px center;
}
.aptinfo .amount {
  color: #0f7833;
}
.aptinfo .build {
  color: #29141a;
}
</style>
