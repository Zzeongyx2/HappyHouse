<template>
    <div>
        <b-card no-body>
            <b-card-header class="border-0">
                <b-row class="mb-1">
                    <b-col>
                        <h2 class="mb-0">Reply</h2>
                    </b-col>
                </b-row>
            </b-card-header>

            <el-table class="table-responsive table"
                    header-row-class-name="thead-light"
                    @row-click="modifiable"
                    v-b-modal.my-modal 
                    :data="paginatedItems">
                
                <el-table-column label="comment"
                                prop="comment"
                                min-width="140px">
                </el-table-column>

                <el-table-column label="Userid"
                                prop="userid"
                                min-width="30px">
                </el-table-column>
                
                <el-table-column label="RegTime"
                                prop="regtime"
                                min-width="50px">
                </el-table-column>
            </el-table>

            <br/>
            <form action="">
                <b-row class="form-group">
                <b-col md="1"/>
                <b-col md="9">
                    <b-input v-model="newRepl.comment"></b-input>
                </b-col>
                <b-col md="2">
                    <b-button variant="outline-light" @click="registerReply">댓글 추가</b-button>
                </b-col>
            </b-row>
            </form>

            <b-card-footer class="py-4 d-flex justify-content-end">
                <base-pagination v-model="currentPage" :per-page="perPage" :total="rows" @change="onPageChanged"></base-pagination>
            </b-card-footer>

        </b-card>

        <b-modal  v-if="userInfo.userid === reply.userid" id="my-modal" title="답변 수정" @ok="deleteReply" @cancel="modifyReply" ok-title="삭제" cancel-title="수정"
                                                    ok-variant="warning" cancel-variant="success">
            <b-input type="text" v-model="comment"/>
        </b-modal>

    </div>
</template>

<script>
import { listReply, deleteReply, modifyReply, writeReply } from "@/api/community";
import { Table, TableColumn} from 'element-ui';
import Swal from 'sweetalert2';
import { mapState } from "vuex";
const memberStore = "memberStore";

export default {
    name: 'light-table',
    components: {
      [Table.name]: Table,
      [TableColumn.name]: TableColumn,
    },
    data() {
      return {
        replyListItem: [],
        paginatedItems: [],
        currentPage: 1,
        perPage: 3,
        reply: {},
        comment: "",
        newRepl: {
            userid: "",
        },
      };
    },
    created() {
         listReply(
                this.$route.params.communityno,
                ({ data }) => {
                    this.replyListItem = data;
                    this.newRepl.communityno = this.$route.params.communityno;
                    this.paginate(this.perPage, 0);
                    this.newRepl.userid = this.userInfo.userid;
                },
                () => {},
            );
    },
    computed: {
        ...mapState(memberStore, ["userInfo"]),
        rows(){
            return this.replyListItem.length;
        },
    },
    mounted() {
        this.paginate(this.perPage, 0);
        
    },
    methods: {
        paginate(page_size, page_number) {
            let itemsToParse = this.replyListItem;
            console.log(itemsToParse);
            this.paginatedItems = itemsToParse.slice(
                page_number * page_size,
                (page_number + 1) * page_size
            );
        },
        onPageChanged(page) {
            console.log("=====" + page);
            this.paginate(this.perPage, page - 1);
        },
        modifiable(row){
            this.reply = row;
            this.comment = row.comment;
        },
        modifyReply(){
            if(this.comment != ""){
                this.reply.comment = this.comment;
                modifyReply(
                    this.reply,
                    ({ data }) => {
                    if(data === "success"){
                        Swal.fire(
                            'Modified!',
                            '수정되었습니다.',
                            'success'
                        )
                    }else{
                        Swal.fire(
                            'Failed!',
                            '수정 중 문제가 발생하였습니다.',
                            'warning'
                        )
                    }
                    // 현재 route를 /list로 변경.
                    this.$router.go();
                    },
                    () => {}
                );
            }else{
                Swal.fire(
                    'Failed!',
                    '답변을 입력하세요.',
                    'warning'
                )
            }
        },
        registerReply(){
            if(this.newRepl.comment === ""){
                Swal.fire(
                    'Failed!',
                    '답변을 입력하세요.',
                    'warning'
                );
            }else{
                writeReply(
                    this.newRepl,
                    ({ data }) => {
                    if(data === "success"){
                        Swal.fire(
                            'Registered!',
                            '등록되었습니다.',
                            'success'
                        )
                    }else{
                        Swal.fire(
                            'Failed!',
                            '등록 중 문제가 발생하였습니다.',
                            'warning'
                        )
                    }
                    this.newRepl.comment="";
                    this.$router.go();
                    },
                    () => {}
                );
            }
        },
        deleteReply(){
          Swal.fire({
          title: '삭제하시겠습니까?',
          text: "삭제 후 되돌릴 수 없습니다.",
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#f5365c',
          cancelButtonColor: '#bcbcbc',
          confirmButtonText: 'Delete'
          }).then((result) => {
            if (result.value) {
              deleteReply(
                this.reply.replyno,
                ({data}) => {
                  if(data === "success"){
                        Swal.fire(
                          'Deleted!',
                          '삭제되었습니다.',
                          'success'
                        )
                    }else{
                      Swal.fire(
                          'Failed!',
                          '삭제 중 문제가 발생하였습니다.',
                          'question'
                        )
                    }
                    this.$router.go();
                },
                () => {}
              );
              }
          });
        },
    },
  }
</script>

<style scoped>
/* #my-modal{

} */
</style>