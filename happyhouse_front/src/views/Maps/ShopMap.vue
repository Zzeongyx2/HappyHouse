<template>
  <div>
    <b-row>
      <b-col xl="8">
        <b-card no-body class="border-0">
          <div class="map-wrap">
            <div id="map"></div>
            <ul id="category">
              <li id="BK9" data-order="0">
                <span class="category_bg bank"></span>
                은행
              </li>
              <li id="MT1" data-order="1">
                <span class="category_bg mart"></span>
                마트
              </li>
              <li id="PM9" data-order="2">
                <span class="category_bg pharmacy"></span>
                약국
              </li>
              <li id="OL7" data-order="3">
                <span class="category_bg oil"></span>
                주유소
              </li>
              <li id="CE7" data-order="4">
                <span class="category_bg cafe"></span>
                카페
              </li>
              <li id="CS2" data-order="5">
                <span class="category_bg store"></span>
                편의점
              </li>
            </ul>
            <ul id="checkbox">
              <b-form-checkbox v-model="isTrafficShow" @change="trafficinfo"
                >교통 정보</b-form-checkbox
              >
              <b-form-checkbox v-model="isBicycleShow" @change="bicycleinfo"
                >자전거 도로</b-form-checkbox
              >
            </ul>
          </div>
        </b-card>
      </b-col>
      <b-col xl="4">
        <b-card no-body class="border-0">
          <template v-slot:header>
            <b-row align-v="center">
              <b-col xl="8">
                <h3 class="mb-0">{{ address }}</h3>
              </b-col>
              <b-col class="text-right" xl="4">
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
            class="table-responsive table"
            :data="list"
            :cell-style="{ height: '40px' }"
            header-row-class-name="thead-light"
            v-el-table-infinite-scroll=""
          >
            <el-table-column type="expand">
              <template #default="props">
                <div m="4" font-family="sans-serif" font-size="15px">
                  주소 : {{ props.row.road_address_name }}
                  <br />
                  <div v-if="props.row.phone">전화 : {{ props.row.phone }}</div>
                  <a :href="props.row.place_url" target="_blank"
                    >카카오맵 검색</a
                  >
                </div>
              </template>
            </el-table-column>
            <el-table-column
              label="Name"
              min-width="110px"
              prop="place_name"
              style="max-height: 750px"
            >
              <template v-slot="{ row }">
                <div class="font-weight-600">{{ row.place_name }}</div>
              </template>
            </el-table-column>
          </el-table>
        </b-card>
      </b-col>
    </b-row>
  </div>
</template>

<script>
import { Table, TableColumn, Select, Option } from "element-ui";
import elTableInfiniteScroll from "el-table-infinite-scroll";
import { saveRegion, getRegion, deleteRegion } from "@/api/interest";
import Swal from "sweetalert2";
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
  name: "ShopMap",
  components: {
    [Select.name]: Select,
    [Option.name]: Option,
    [Table.name]: Table,
    [TableColumn.name]: TableColumn,
  },
  data() {
    return {
      infowindow: null,
      isTrafficShow: false,
      isBicycleShow: false,
      address: "",
      list: [],
      region: {
        userid: "",
        sido: "",
        gugun: "",
        dong: "",
      },
      regionList: [],
      isShow: false,
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  created() {
    this.region.userid = this.userInfo.userid;
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
  directives: {
    "el-table-infinite-scroll": elTableInfiniteScroll,
  },
  methods: {
    trafficinfo() {
      if (this.isTrafficShow === true) {
        this.map.addOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);
      } else {
        this.map.removeOverlayMapTypeId(kakao.maps.MapTypeId.TRAFFIC);
      }
    },
    bicycleinfo() {
      if (this.isBicycleShow === true) {
        this.map.addOverlayMapTypeId(kakao.maps.MapTypeId.BICYCLE);
      } else {
        this.map.removeOverlayMapTypeId(kakao.maps.MapTypeId.BICYCLE);
      }
    },
    deleteInterest(row) {
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
        },
        () => {}
      );
    },
    saveInterest() {
      saveRegion(
        this.region,
        ({ data }) => {
          if (data === "success") {
            Swal.fire("Registered!", "등록되었습니다.", "success");
          } else {
            Swal.fire("Failed!", "등록 중 문제가 발생하였습니다.", "warning");
          }
          // 현재 route를 /list로 변경.
          this.getInterest();
        },
        () => {}
      );
    },
    moveRegion(row) {
      let self = this;

      // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();
      const addr = row.sido + " " + row.gugun + " " + row.dong;

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
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567),
        level: 5,
      };

      //지도 객체를 등록합니다.
      //지도 객체는 반응형 관리 대상이 아니므로 initMap에서 선언합니다.
      this.map = new kakao.maps.Map(container, options);

      this.createCategorySearch();
    },
    createCategorySearch() {
      let self = this;
      // 마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
      let placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 5 }),
        contentNode = document.createElement("div"), // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다
        markers = [], // 마커를 담을 배열입니다
        currCategory = ""; // 현재 선택된 카테고리를 가지고 있을 변수입니다

      // 장소 검색 객체를 생성합니다
      let ps = new kakao.maps.services.Places(this.map);

      // 지도에 idle 이벤트를 등록합니다
      kakao.maps.event.addListener(this.map, "idle", searchPlaces);

      // 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다
      contentNode.className = "placeinfo_wrap";

      // 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
      // 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
      addEventHandle(contentNode, "mousedown", kakao.maps.event.preventMap);
      addEventHandle(contentNode, "touchstart", kakao.maps.event.preventMap);

      // 커스텀 오버레이 컨텐츠를 설정합니다
      placeOverlay.setContent(contentNode);

      // 각 카테고리에 클릭 이벤트를 등록합니다
      addCategoryClickEvent();

      // 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
      function addEventHandle(target, type, callback) {
        if (target.addEventListener) {
          target.addEventListener(type, callback);
        } else {
          target.attachEvent("on" + type, callback);
        }
      }

      //주소-좌표 변환 객체 생성
      var geocoder = new kakao.maps.services.Geocoder();

      // 현재 지도 중심좌표로 주소를 검색해서 지도 좌측 상단에 표시합니다
      searchAddrFromCoords(self.map.getCenter(), displayCenterInfo);

      // 중심 좌표나 확대 수준이 변경됐을 때 지도 중심 좌표에 대한 주소 정보를 표시하도록 이벤트를 등록합니다
      kakao.maps.event.addListener(self.map, "idle", function () {
        searchAddrFromCoords(self.map.getCenter(), displayCenterInfo);
      });

      function searchAddrFromCoords(coords, callback) {
        // 좌표로 행정동 주소 정보를 요청합니다
        geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
      }
      // 지도 좌측상단에 지도 중심좌표에 대한 주소정보를 표출하는 함수입니다
      function displayCenterInfo(result, status) {
        if (status === kakao.maps.services.Status.OK) {
          for (var i = 0; i < result.length; i++) {
            // 행정동의 region_type 값은 'H' 이므로
            if (result[i].region_type === "H") {
              self.address = result[i].address_name;
              self.region.sido = result[i].region_1depth_name;
              self.region.gugun = result[i].region_2depth_name;
              self.region.dong = result[i].region_3depth_name;
              break;
            }
          }
        }
      }

      // 카테고리 검색을 요청하는 함수입니다
      function searchPlaces() {
        if (!currCategory) {
          return;
        }

        // 커스텀 오버레이를 숨깁니다
        placeOverlay.setMap(null);

        // 지도에 표시되고 있는 마커를 제거합니다
        removeMarker();

        ps.categorySearch(currCategory, placesSearchCB, { useMapBounds: true });
      }

      // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
      function placesSearchCB(data, status) {
        if (status === kakao.maps.services.Status.OK) {
          // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
          self.list = data;
          displayPlaces(data);
        } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
          // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
        } else if (status === kakao.maps.services.Status.ERROR) {
          // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
        }
      }

      // 지도에 마커를 표출하는 함수입니다
      function displayPlaces(places) {
        // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
        // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
        let order = document
          .getElementById(currCategory)
          .getAttribute("data-order");

        for (let i = 0; i < places.length; i++) {
          // 마커를 생성하고 지도에 표시합니다
          let marker = addMarker(
            new kakao.maps.LatLng(places[i].y, places[i].x),
            order
          );

          // 마커와 검색결과 항목을 클릭 했을 때
          // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
          (function (marker, place) {
            kakao.maps.event.addListener(marker, "click", function () {
              displayPlaceInfo(place);
            });
          })(marker, places[i]);
        }
      }

      // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
      function addMarker(position, order) {
        let imageSrc =
            "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
          imageSize = new kakao.maps.Size(27, 28), // 마커 이미지의 크기
          imgOptions = {
            spriteSize: new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
            spriteOrigin: new kakao.maps.Point(46, order * 36), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(11, 28), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
          },
          markerImage = new kakao.maps.MarkerImage(
            imageSrc,
            imageSize,
            imgOptions
          ),
          marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage,
          });
        marker.setMap(self.map); // 지도 위에 마커를 표출합니다
        markers.push(marker); // 배열에 생성된 마커를 추가합니다
        return marker;
      }

      // 지도 위에 표시되고 있는 마커를 모두 제거합니다
      function removeMarker() {
        for (let i = 0; i < markers.length; i++) {
          markers[i].setMap(null);
        }
        markers = [];
        self.list = [];
      }

      // 클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
      function displayPlaceInfo(place) {
        let content =
          '<div class="placeinfo">' +
          '   <a class="title" href="' +
          place.place_url +
          '" target="_blank" title="' +
          place.place_name +
          '">' +
          place.place_name +
          "</a>";

        if (place.road_address_name) {
          content +=
            '    <span title="' +
            place.road_address_name +
            '">' +
            place.road_address_name +
            "</span>" +
            '  <span class="jibun" title="' +
            place.address_name +
            '">(지번 : ' +
            place.address_name +
            ")</span>";
        } else {
          content +=
            '    <span title="' +
            place.address_name +
            '">' +
            place.address_name +
            "</span>";
        }

        content +=
          '    <span class="tel">' +
          place.phone +
          "</span>" +
          "</div>" +
          '<div class="after"></div>';

        contentNode.innerHTML = content;

        placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
        placeOverlay.setMap(self.map);
      }

      // 각 카테고리에 클릭 이벤트를 등록합니다
      function addCategoryClickEvent() {
        let category = document.getElementById("category"),
          children = category.children;

        for (let i = 0; i < children.length; i++) {
          children[i].onclick = onClickCategory;
        }
      }

      // 카테고리를 클릭했을 때 호출되는 함수입니다
      function onClickCategory() {
        let id = this.id,
          className = this.className;

        placeOverlay.setMap(null);

        if (className === "on") {
          currCategory = "";
          changeCategoryClass();
          removeMarker();
        } else {
          currCategory = id;
          changeCategoryClass(this);
          searchPlaces();
        }
      }

      // 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
      function changeCategoryClass(el) {
        let category = document.getElementById("category"),
          children = category.children,
          i;

        for (i = 0; i < children.length; i++) {
          children[i].className = "";
        }

        if (el) {
          el.className = "on";
        }
      }
    },
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
#map {
  border: 5px;
  width: 100%;
  height: 500px;
}

.map_wrap,
.map_wrap * {
  margin: 0;
  padding: 0;
  font-family: sans-serif;
  font-size: 12px;
}
.map_wrap {
  position: relative;
  width: 100%;
  /* height: 350px; */
}

#checkbox {
  position: absolute;
  top: 12px;
  left: 330px;
  border-radius: 5px;
  border: 1px solid #c5c5c5;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: rgba(255, 255, 255, 0.95);
  overflow: hidden;
  z-index: 2;
  padding: 10px;
}

#hAddr {
  position: absolute;
  left: 20px;
  top: 100px;
  border-radius: 2px;
  background: #fff;
  background: rgba(255, 255, 255, 0.9);
  z-index: 2;
  padding: 5px;
}
#centerAddr {
  display: block;
  margin-top: 2px;
  font-weight: normal;
}

#category {
  position: absolute;
  top: 10px;
  left: 15px;
  border-radius: 5px;
  border: 1px solid #c5c5c5;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: rgba(255, 255, 255, 0.9);
  overflow: hidden;
  z-index: 2;
  padding: 0px;
}
#category li {
  float: left;
  list-style: none;
  width: 50px;
  border-right: 1px solid #acacac;
  padding: 6px 0;
  text-align: center;
  cursor: pointer;
}
#category li.on {
  background: #eee;
}
#category li:hover {
  background: #ffe6e6;
  border-left: 1px solid #acacac;
  margin-left: -1px;
}
#category li:last-child {
  margin-right: 0;
  border-right: 0;
}
#category li span {
  display: block;
  margin: 3px auto 5px;
  width: 27px;
  height: 28px;
}
#category li .category_bg {
  background: url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png)
    no-repeat;
}
#category li .bank {
  background-position: -5px -0px;
}
#category li .mart {
  background-position: -7px -36px;
}
#category li .pharmacy {
  background-position: -8px -72px;
}
#category li .oil {
  background-position: -8px -108px;
}
#category li .cafe {
  background-position: -8px -144px;
}
#category li .store {
  background-position: -8px -180px;
}
#category li .subway {
  background-position: -8px -180px;
}
#category li.on .category_bg {
  background-position-x: -42px;
}
.placeinfo_wrap {
  position: absolute;
  bottom: 28px;
  left: -150px;
  width: 300px;
}

.placeinfo {
  position: relative;
  width: 100%;
  border-radius: 6px;
  border: 1px solid #ccc;
  border-bottom: 2px solid #ddd;
  padding-bottom: 10px;
  background: #fff;
}
.placeinfo:nth-of-type(n) {
  border: 0;
  box-shadow: 0px 1px 2px #888;
}
.placeinfo_wrap .after {
  content: "";
  position: relative;
  margin-left: -12px;
  left: 50%;
  width: 22px;
  height: 12px;
  background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}
.placeinfo a,
.placeinfo a:hover,
.placeinfo a:active {
  color: #fff;
  text-decoration: none;
}
.placeinfo a,
.placeinfo span {
  display: block;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
.placeinfo span {
  margin: 5px 5px 0 5px;
  cursor: default;
  font-size: 13px;
}
.placeinfo .title {
  font-weight: bold;
  font-size: 14px;
  border-radius: 6px 6px 0 0;
  margin: -1px -1px 0 -1px;
  padding: 10px;
  color: #fff;
  background: #d95050;
  background: #d95050
    url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/arrow_white.png)
    no-repeat right 14px center;
}
.placeinfo .tel {
  color: #0f7833;
}
.placeinfo .jibun {
  color: #999;
  font-size: 11px;
  margin-top: 0;
}
</style>
