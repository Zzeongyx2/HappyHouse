import DashboardLayout from "@/views/Layout/DashboardLayout.vue";
import AuthLayout from "@/views/Pages/AuthLayout.vue";
import NoticeLayout from "@/views/NoticeView.vue";
import QnaLayout from "@/views/QnaView.vue";
import CommunityLayout from "@/views/CommunityView.vue";
import store from "@/store/index.js";
import NotFound from "@/views/NotFoundPage.vue";

const onlyAuthUser = async (to, from, next) => {
  // console.log(store);
  const checkUserInfo = store.getters["memberStore/checkUserInfo"];
  const getUserInfo = store._actions["memberStore/getUserInfo"];
  let token = sessionStorage.getItem("access-token");
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }
  if (checkUserInfo === null) {
    alert("로그인이 필요한 페이지입니다..");
    next({ name: "signIn" });
    // router.push({ name: "signIn" });
  } else {
    // console.log("로그인 했다.");
    next();
  }
};

const routes = [
  {
    path: "/",
    redirect: "dashboard",
    component: DashboardLayout,
    children: [
      {
        path: "/dashboard",
        name: "dashboard",
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Dashboard.vue"),
      },
      {
        path: "/maps",
        name: "maps",
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/GoogleMaps.vue"),
      },
      {
        path: "icons",
        name: "icons",
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Icons.vue"),
      },
      {
        path: "shop",
        name: "shop",
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/ShopView.vue"),
      },
      {
        path: "notice",
        name: "notice",
        redirect: "noticelist",
        component: NoticeLayout,
        children: [
          {
            path: "/noticelist",
            name: "noticeList",
            component: () =>
              import(
                /* webpackChunkName: "demo" */ "../views/Tables/Notice/NoticeList.vue"
              ),
          },
          {
            path: "/noticeregister",
            name: "noticeRegister",
            component: () =>
              import(
                /* webpackChunkName: "demo" */ "../views/Tables/Notice/NoticeRegister.vue"
              ),
          },
          {
            path: "detail/:articleno",
            name: "noticeDetail",
            component: () =>
              import(
                /* webpackChunkName: "demo" */ "../views/Tables/Notice/NoticeDetail.vue"
              ),
          },
          {
            path: "modify/:articleno",
            name: "noticeModify",
            component: () =>
              import(
                /* webpackChunkName: "demo" */ "../views/Tables/Notice/NoticeModify.vue"
              ),
          },
        ],
      },
      {
        path: "qna",
        name: "qna",
        redirect: "questionlist",
        component: QnaLayout,
        children: [
          {
            path: "/questionlist",
            name: "questionList",
            component: () =>
              import(
                /* webpackChunkName: "demo" */ "../views/Tables/Qna/QnaList.vue"
              ),
          },
          {
            path: "/questionregister",
            name: "questionRegister",
            component: () =>
              import(
                /* webpackChunkName: "demo" */ "../views/Tables/Qna/QnaRegister.vue"
              ),
          },
          {
            path: "detail/:questionno",
            name: "questionDetail",
            component: () =>
              import(
                /* webpackChunkName: "demo" */ "../views/Tables/Qna/QnaDetail.vue"
              ),
          },
          {
            path: "modify/:questionno",
            name: "questionModify",
            component: () =>
              import(
                /* webpackChunkName: "demo" */ "../views/Tables/Qna/QnaModify.vue"
              ),
          },
        ],
      },
      {
        path: "community",
        name: "comm",
        redirect: "commlist",
        component: CommunityLayout,
        children: [
          {
            path: "/commlist",
            name: "commList",
            component: () =>
              import(
                /* webpackChunkName: "demo" */ "../views/Tables/Community/CommList.vue"
              ),
          },
          {
            path: "/communityregister",
            name: "commRegister",
            component: () =>
              import(
                /* webpackChunkName: "demo" */ "../views/Tables/Community/CommRegister.vue"
              ),
          },
          {
            path: "detail/:communityno",
            name: "commDetail",
            component: () =>
              import(
                /* webpackChunkName: "demo" */ "../views/Tables/Community/CommDetail.vue"
              ),
          },
          {
            path: "modify/:communityno",
            name: "commModify",
            component: () =>
              import(
                /* webpackChunkName: "demo" */ "../views/Tables/Community/CommModify.vue"
              ),
          },
        ],
      },
    ],
  },
  {
    path: "/member",
    redirect: "login",
    component: AuthLayout,
    children: [
      {
        path: "/login",
        name: "login",
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Pages/Login.vue"),
      },
      {
        path: "/memberregister",
        name: "memberregister",
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Pages/Register.vue"),
      },
      {
        path: "/profile",
        name: "profile",
        component: () =>
          import(
            /* webpackChunkName: "demo" */ "../views/Pages/UserProfile.vue"
          ),
      },
      { path: "*", component: NotFound },
    ],
  },
];

export default routes;
