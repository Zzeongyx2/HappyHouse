<script>
import moment from "moment";
export default {
  name: "NoticeListItem",
  props: {
    articleno: Number,
    userid: String,
    subject: String,
    content: String,
    regtime : String,
  },
  filters: {
    dateFormat(regtime) {
      return moment(new Date(regtime)).format("YY.MM.DD");
    },
  },
};
</script>

<style></style>
