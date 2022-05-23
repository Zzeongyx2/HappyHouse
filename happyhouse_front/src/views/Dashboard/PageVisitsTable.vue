<template>
  <b-card body-class="p-0" header-class="border-0">
    <template v-slot:header>
      <b-row align-v="center">
        <b-col>
          <h3 class="mb-0">부동산 관련 뉴스</h3>
        </b-col>
        <b-col class="text-right">
          <a href="#!" class="btn btn-sm btn-primary">더 보기</a>
        </b-col>
      </b-row>
    </template>

    <el-table
      class="table-responsive table"
      :data="newsData"
      header-row-class-name="thead-light"
      max-height="500px"
      v-el-table-infinite-scroll
    >
      <el-table-column label="Title" min-width="60px" prop="title">
        <template v-slot="{ row }">
          <div class="font-weight-600">{{ row.title }}</div>
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
export default {
  name: "page-visits-table",
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
      tableData: [
        {
          page: "/argon/",
          visitors: "4,569",
          unique: "340",
          bounceRate: "46,53%",
        },
        {
          page: "/argon/index.html",
          visitors: "3,985",
          unique: "319",
          bounceRate: "46,53%",
        },
        {
          page: "/argon/charts.html",
          visitors: "3,513",
          unique: "294",
          bounceRate: "36,49%",
        },
        {
          page: "/argon/tables.html",
          visitors: "2,050",
          unique: "147",
          bounceRate: "50,87%",
        },
        {
          page: "/argon/profile.html",
          visitors: "1,795",
          unique: "190",
          bounceRate: "46,53%",
        },
      ],
      newsData: [
        {
          title: "집값 잡는다더니...반포 이어 역삼도 평당 1억 넘었다",
          originallink:
            "http://www.newsway.co.kr/news/view?tp=1&ud=2022052002561803469",
          link: "http://www.newsway.co.kr/news/view?tp=1&ud=2022052002561803469",
          description:
            "윤석열 정부의 '<b>부동산</b>시장 안정화' 바램이 오히려 시장에 기름을 부었다. 정비사업 규제 완화 등을... 일대 재건축 속도 기대감과 <b>부동산</b>세 완화, 대출 완화 등으로 시장이 다시 반등할 것이라는 전망이 나오면서 일대... ",
          pubDate: "Fri, 20 May 2022 07:36:00 +0900",
        },
        {
          title: "메트로신문 - 5월 20일 한줄 뉴스",
          originallink: "http://www.metroseoul.co.kr/article/20220519500531",
          link: "http://www.metroseoul.co.kr/article/20220519500531",
          description:
            "/뉴시스 메트로신문 - 5월 20일 한줄 뉴스 ▲서울시장 승패를 가르는 키워드는 '경제 회복·<b>부동산</b>'으로 꼽을... &lt;금융·마켓·<b>부동산</b>&gt; ▲이창용 한국은행 총재의 취임 후 첫 금융통화위원회(금통위)에 관심이 모이고 있다.... ",
          pubDate: "Fri, 20 May 2022 07:32:00 +0900",
        },
        {
          title: "공실률 하락에 청량리 상가 분양가 '고공행진'",
          originallink:
            "http://www.econovill.com/news/articleView.html?idxno=576070",
          link: "http://www.econovill.com/news/articleView.html?idxno=576070",
          description:
            "상업용 <b>부동산</b> 데이터 전문기업 알스퀘어가 한국<b>부동산</b>원 자료를 분석한 결과 도심과 강남, 여의도 권역의 오해 1분기 서울 소규모 상가(2층 이하·연면적 330㎡ 이하) 공실률은 6.2%로 집계됐다. 지난해 3분기 이후 반년... ",
          pubDate: "Fri, 20 May 2022 07:32:00 +0900",
        },
        {
          title:
            "'똘똘한 한채'만 사들인다...강남4구·분당만 아파트 매수심리 상승",
          originallink: "https://www.ajunews.com/view/20220520072258593",
          link: "https://www.ajunews.com/view/20220520072258593",
          description:
            "20일 한국<b>부동산</b>원이 조사한 이번주 서울 아파트 매매수급지수는 90.8로, 전주(91.0)에 이어 2주 연속 하락했다. 매매수급지수가 기준선인 100을 밑돌면 시장에 집을 사려는 사람보다 팔려는 사람이 더 많다는 것을... ",
          pubDate: "Fri, 20 May 2022 07:32:00 +0900",
        },
        {
          title: "'똘똘한 1채'…서울 아파트 강남만 수요 몰려",
          originallink:
            "http://www.wowtv.co.kr/NewsCenter/News/Read?articleId=A202205200016&t=NN",
          link: "https://news.naver.com/main/read.naver?mode=LSD&mid=sec&sid1=101&oid=215&aid=0001032676",
          description:
            "20일 한국<b>부동산</b>원이 조사한 이번주 서울 아파트 매매수급지수는 90.8을 기록하며 지난주(91.0)에 이어 2주... <b>부동산</b>빅데이터 업체 아실에 따르면 전날 기준 서울 아파트 매물은 6만47건으로 6만건을 넘어서며 양도세... ",
          pubDate: "Fri, 20 May 2022 07:27:00 +0900",
        },
        {
          title: "경남도민 충효교육 실시",
          originallink: "http://www.cfnews.kr/news.aspx/59502",
          link: "http://www.cfnews.kr/news.aspx/59502",
          description:
            "제복 착용순서, 옷도 짝이 있다 등 그리고 국가제사 제기의 종류, 제상 차리기 등에 대해 이론과 실습을 했다.끝으로 향교재단 강원석 사무국장의 향교재산 등기업무, <b>부동산</b>(토지, 건물) 처분에 대한 설명이 있었다.",
          pubDate: "Fri, 20 May 2022 07:26:00 +0900",
        },
        {
          title: "&quot;이번엔 서쪽이다&quot;…부산 사람들 요즘 주목하는 곳",
          originallink:
            "https://www.hankyung.com/realestate/article/2022051978436",
          link: "https://news.naver.com/main/read.naver?mode=LSD&mid=sec&sid1=101&oid=015&aid=0004701318",
          description:
            "20일 <b>부동산</b> 업계에 따르면 강서구 명지동·강동동·대저동 일대 1188만5000㎡ 부지에 조성중인... 현지의 한 <b>부동산</b> 시장 관계자는 &quot;개발이 완료되면 남북으로 연결되는 대지면적 3640만㎡와 총 25만여명이 상시 거주하고... ",
          pubDate: "Fri, 20 May 2022 07:22:00 +0900",
        },
        {
          title: "우리은행도 만기 40년 주택대출 선보인다",
          originallink:
            "http://www.smedaily.co.kr/news/articleView.html?idxno=231023",
          link: "http://www.smedaily.co.kr/news/articleView.html?idxno=231023",
          description:
            "대상 상품은 우리아파트론과 우리<b>부동산</b>론(주택), 집단 입주자금 대출이다. 우리은행 관계자는 &quot;금융소비자의 원리금 상환 부담을 경감하고 정부 정책에 부응하는 차원&quot;이라고 설명했다. 원리금을 해마다 나눠 갚은... ",
          pubDate: "Fri, 20 May 2022 07:18:00 +0900",
        },
        {
          title: "'똘똘한 한 채' 찾는다…서울 아파트 매매수급지수 강남만 상승",
          originallink:
            "https://www.yna.co.kr/view/AKR20220520002600003?input=1195m",
          link: "https://news.naver.com/main/read.naver?mode=LSD&mid=sec&sid1=101&oid=001&aid=0013191783",
          description:
            "20일 한국<b>부동산</b>원이 조사한 이번주 서울 아파트 매매수급지수는 90.8을 기록하며 지난주(91.0)에 이어 2주... <b>부동산</b>빅데이터 업체 아실에 따르면 전날 기준 서울 아파트 매물은 6만47건으로 6만건을 넘어서며 양도세... ",
          pubDate: "Fri, 20 May 2022 07:17:00 +0900",
        },
        {
          title: "'요즘 대세' 재개발 투자, 이 3가지는 반드시 확인하세요",
          originallink:
            "https://realty.chosun.com/site/data/html_dir/2022/05/20/2022052000484.html",
          link: "https://realty.chosun.com/site/data/html_dir/2022/05/20/2022052000484.html",
          description:
            "우리나라 <b>부동산</b> 시장 중심지인 서울은 대규모 택지개발을 할 수 있는 부지를 찾기 힘들다. 거의 유일한 신규... 눈덩이처럼 커질 수 있어 주의해야 한다. /글=김효선 NH농협은행 <b>부동산</b>수석위원, 정리=박기람 땅집고 기자",
          pubDate: "Fri, 20 May 2022 07:14:00 +0900",
        },
      ],
    };
  },
};
</script>
<style></style>
