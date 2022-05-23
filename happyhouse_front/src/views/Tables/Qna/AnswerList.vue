<template>
    <div>
        <b-card no-body>
            <b-card-header class="border-0">
                <b-row class="mb-1">
                    <b-col>
                        <h2 class="mb-0">Answer</h2>
                    </b-col>
                </b-row>
            </b-card-header>

            <el-table class="table-responsive table"
                    v-b-modal.my-modal 
                    header-row-class-name="thead-light"
                    @row-click="modifiable"
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
                    <b-input v-model="newAns.comment"></b-input>
                </b-col>
                <b-col md="2">
                    <b-button variant="outline-light" @click="registerAnswer">답변 추가</b-button>
                </b-col>
            </b-row>
            </form>

            <b-card-footer class="py-4 d-flex justify-content-end">
                <base-pagination v-model="currentPage" :per-page="perPage" :total="rows" @change="onPageChanged"></base-pagination>
            </b-card-footer>

        </b-card>

        <b-modal id="my-modal" title="답변 수정" @ok="deleteAnswer" @cancel="modifyAnswer" ok-title="삭제" cancel-title="수정"
                                                    ok-variant="warning" cancel-variant="success">
            <b-input type="text" v-model="comment"/>
        </b-modal>
    </div>
</template>

<script>
import { listAnswer, deleteAnswer, modifyAnswer, writeAnswer } from "@/api/qna";
import { Table, TableColumn} from 'element-ui'
import Swal from 'sweetalert2'

export default {
    name: 'answerList',
    props: {
         questionno : Number
    },
    components: {
      [Table.name]: Table,
      [TableColumn.name]: TableColumn,
    },
    data() {
      return {
        answerListItem: [],
        paginatedItems: [],
        currentPage: 1,
        perPage: 3,
        answer: {},
        comment: "",
        newAns: {
            userid: "ssafy",
        },
      };
    },
    created() {
         listAnswer(
                this.$route.params.questionno,
                ({ data }) => {
                    this.answerListItem = data;
                    this.newAns.questionno = this.$route.params.questionno;
                    this.paginate(this.perPage, 0);
                },
                () => {},
            );
    },
    computed: {
        rows(){
            return this.answerListItem.length;
        },
    },
    mounted() {
        this.paginate(this.perPage, 0);
        
    },
    methods: {
        paginate(page_size, page_number) {
            let itemsToParse = this.answerListItem;
            // console.log(itemsToParse);
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
            this.answer = row;
            this.comment = row.comment;
            console.log(this.answer);
        },
        modifyAnswer(){
            if(this.comment != ""){
                this.answer.comment = this.comment;
                modifyAnswer(
                    this.answer,
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
        registerAnswer(){
            if(this.newAns.comment === ""){
                Swal.fire(
                    'Failed!',
                    '답변을 입력하세요.',
                    'warning'
                );
            }else{
                writeAnswer(
                    this.newAns,
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
                    this.newAns.comment="";
                    this.$router.go();
                    },
                    () => {}
                );
            }
        },
        deleteAnswer(){
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
              deleteAnswer(
                this.answer.answerno,
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