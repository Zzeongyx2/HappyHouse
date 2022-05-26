<template>
  <card>
    <b-row align-v="center" slot="header">
      <b-col cols="8">
        <h3 class="mb-0">Edit profile</h3>
      </b-col>
      <b-col cols="4" class="text-right"> </b-col>
    </b-row>

    <b-form @submit.prevent="updateProfile">
      <b-row>
        <b-col cols="8">
          <h6 class="heading-small text-muted mb-4">User information</h6>
        </b-col>
        <b-col cols="4" class="text-right">
          <a class="btn btn-sm btn-primary" @click="updateProfile">정보 변경</a>
          <a class="btn btn-sm btn-danger" @click="deleteProfile">회원 탈퇴</a>
        </b-col>
      </b-row>
      <div class="pl-lg-4">
        <b-row>
          <b-col lg="6">
            <base-input
              id="userid"
              disabled
              type="text"
              :rules="{ required: true }"
              :placeholder="userInfo.userid"
              label="Your ID"
              v-model="newUser.userid"
            >
            </base-input>
          </b-col>
          <b-col lg="6"> </b-col>
        </b-row>
        <b-row>
          <b-col lg="6">
            <base-input
              type="text"
              label="Your Name"
              name="이름"
              :rules="{ required: true }"
              :placeholder="userInfo.username"
              v-model="newUser.username"
            >
            </base-input>
          </b-col>
          <b-col lg="6">
            <base-input
              type="email"
              label="Your Email address"
              name="Email"
              :rules="{ required: true }"
              :placeholder="userInfo.email"
              v-model="newUser.email"
            >
            </base-input>
          </b-col>
        </b-row>
      </div>
      <hr class="my-4" />

      <!-- Address -->

      <b-row>
        <b-col cols="8">
          <h6 class="heading-small text-muted mb-4">Change Password</h6>
        </b-col>
        <b-col cols="4" class="text-right">
          <a class="btn btn-sm btn-primary" @click="updatePassword"
            >비밀번호 변경</a
          >
        </b-col>
      </b-row>
      <div class="pl-lg-4">
        <b-row>
          <b-col lg="6">
            <base-input
              type="text"
              label="New Password"
              name="New Password"
              :rules="{ required: true, min: 4 }"
              placeholder="New Password..."
              v-model="newpwd"
            >
            </base-input>
          </b-col>
          <b-col lg="6">
            <base-input
              name="Password Check"
              type="password"
              label="Password Check"
              :rules="{ required: true }"
              placeholder="Password again..."
              v-model="checkpwd"
            >
            </base-input>
          </b-col>
        </b-row>
        <b-row
          ><b-col lg="12">
            <b-alert
              class="text-center"
              show
              variant="danger"
              v-if="!isPossiblePwd"
              >비밀번호를 확인하세요!</b-alert
            ></b-col
          >
        </b-row>
      </div>

      <hr class="my-4" />
      <!-- Description -->
    </b-form>
  </card>
</template>
<script>
import { mapMutations, mapState, mapActions } from "vuex";
import Swal from "sweetalert2";
const memberStore = "memberStore";
const swalWithBootstrapButtons = Swal.mixin({
  customClass: {
    confirmButton: "btn btn-success",
    cancelButton: "btn btn-danger",
  },
  buttonsStyling: false,
});
export default {
  name: "EditProfileForm",
  data() {
    return {
      newUser: {
        userid: "",
        username: "",
        email: "",
      },
      newpwd: "",
      checkpwd: "",
    };
  },
  created() {
    this.CLEAR_IS_UPDATE_ERROR();
    this.CLEAR_IS_DELETE_ERROR();
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "isUpdateError", "isDeleteError"]),
    isPossiblePwd() {
      return this.checkpwd == this.newpwd;
    },
  },
  methods: {
    ...mapActions(memberStore, [
      "updateUserStore",
      "updatePasswordStore",
      "deleteUserStore",
    ]),
    ...mapMutations(memberStore, [
      "CLEAR_IS_UPDATE_ERROR",
      "CLEAR_IS_DELETE_ERROR",
    ]),

    updateProfile() {
      if ((this.newUser.username != "") & (this.newUser.email != "")) {
        if (
          (this.newUser.username === this.userInfo.username) &
          (this.newUser.email === this.userInfo.email)
        ) {
          Swal.fire("변경사항이 없습니다!");
        } else {
          swalWithBootstrapButtons
            .fire({
              title: "정말 변경 하시겠어요??",
              text: "You won't be able to revert this!",
              icon: "warning",
              showCancelButton: true,
              confirmButtonText: "네, 수정할게요!",
              cancelButtonText: "아뇨 , 다시 생각해볼게요!",
              reverseButtons: true,
            })
            .then((result) => {
              if (result.isConfirmed) {
                this.updateUserStore({
                  userid: this.userInfo.userid,
                  username: this.newUser.username,
                  email: this.newUser.email,
                })
                  .then(() => {
                    if (!this.isUpdateError) {
                      swalWithBootstrapButtons
                        .fire(
                          "변경완료!",
                          "Your Profile has been Updated!",
                          "success"
                        )
                        .then((result) => {
                          this.$router.go();
                        });
                    } else {
                      swalWithBootstrapButtons.fire(
                        "Failed",
                        "Sorry.. Failed to Update Your Profile.. Try Again ",
                        "info"
                      );
                    }
                  })
                  .catch(() => {});
              } else if (
                /* Read more about handling dismissals below */
                result.dismiss === Swal.DismissReason.cancel
              ) {
                swalWithBootstrapButtons.fire(
                  "Cancelled",
                  "Your Profile is Not Changed :)",
                  "error"
                );
              }
            });
        }
      } else {
        Swal.fire("변경사항이 없습니다.");
      }
    },
    updatePassword() {
      swalWithBootstrapButtons
        .fire({
          title: "정말 변경 하시겠어요??",
          text: "You won't be able to revert this!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonText: "네, 변경할게요!",
          cancelButtonText: "아뇨 , 다시 생각해볼게요!",
          reverseButtons: true,
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.updatePasswordStore({
              userid: this.userInfo.userid,
              userpwd: this.newpwd,
            })
              .then(() => {
                if (!this.isUpdateError) {
                  swalWithBootstrapButtons
                    .fire(
                      "변경완료!",
                      "Your Password has been Updated!",
                      "success"
                    )
                    .then((result) => {
                      this.$router.go();
                    });
                } else {
                  swalWithBootstrapButtons.fire(
                    "Failed",
                    "Sorry.. Failed to Update Your Profile.. Try Again ",
                    "info"
                  );
                }
              })
              .catch(() => {});
          } else if (
            /* Read more about handling dismissals below */
            result.dismiss === Swal.DismissReason.cancel
          ) {
            swalWithBootstrapButtons.fire(
              "Cancelled",
              "Your Profile is Not Changed :)",
              "error"
            );
          }
        });
    },
    async deleteProfile() {
      swalWithBootstrapButtons
        .fire({
          title: "Are you sure?",
          text: "You won't be able to revert this!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonText: "Yes, delete it!",
          cancelButtonText: "No, cancel!",
          reverseButtons: true,
        })
        .then((result) => {
          if (result.isConfirmed) {
            this.deleteUserStore(this.userInfo.userid)
              .then(() => {
                if (!this.isDeleteError) {
                  swalWithBootstrapButtons.fire(
                    "Deleted!",
                    "Your Profile has been deleted.",
                    "success"
                  );
                  this.$router.push({ name: "happyhouse" });
                } else {
                  swalWithBootstrapButtons.fire(
                    "Failed",
                    "Sorry.. Failed to Delete Your Profile.. Try Again ",
                    "info"
                  );
                  this.$router.push({ name: "happyhouse" });
                }
              })
              .catch(() => {});
          } else if (
            /* Read more about handling dismissals below */
            result.dismiss === Swal.DismissReason.cancel
          ) {
            swalWithBootstrapButtons.fire(
              "Cancelled",
              "Your Profile is safe :)",
              "error"
            );
          }
        });
    },
  },
};
</script>
<style></style>
