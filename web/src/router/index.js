import { createRouter, createWebHistory } from 'vue-router'
import PKIndexView from '../views/pk/PKIndexView.vue'
import RecordIndexView from '../views/record/RecordIndexView.vue'
import RecordContentView from '../views/record/RecordContentView.vue'
import RanklistIndexView from '../views/ranklist/RanklistIndexView.vue'
import ForumIndexView from '../views/forum/ForumIndexView.vue'
import UserBotIndexView from '../views/user/bot/UserBotIndexView.vue'
import NotFoundIndexView from '../views/error/NotFound.vue'
import UserAccountLoginView from '@/views/user/account/UserAccountLoginView'
import UserAccountRegisterView from '@/views/user/account/UserAccountRegisterView'
import store from '@/store/index'

const routes = [
  {  //设置首页路径为pk路径
    path: "/",
    name: "home",
    redirect: "/pk/",
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/pk/",
    name: "pk_index",
    component: PKIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/record/",
    name: "record_index",
    component: RecordIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/record/:recordId/",  //加:就可以在路径里传参数
    name: "record_content",
    component: RecordContentView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/ranklist/",
    name: "ranklist_index",
    component: RanklistIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/forum/",  
    name: "forum_index",
    component: ForumIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/user/bot/",
    name: "user_bot_index",
    component: UserBotIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/user/account/login/",
    name: "user_account_login",
    component: UserAccountLoginView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/user/account/register/",
    name: "user_account_register",
    component: UserAccountRegisterView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/404/",
    name: "404",
    component: NotFoundIndexView,
    meta: {
      requestAuth: false,
    }
  },
  {  //输入其他错误路径会跳转到404界面
    path: "/:catchAll(.*)",
    redirect: "/404/"
  }

  
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) => {                     //通过router进入某个页面之前执行此函数，自带的API
  if (to.meta.requestAuth && !store.state.user.is_login) {  //to表示跳转到哪个页面，from表示从哪个页面跳转过来
    next({name: "user_account_login"});                     //我们实现此功能：如果此页面需要登录后才能使用，则查看是否已登录
  } else {                                                  //若未登录，则跳转到登录界面
    next();                                                 //to.meta.requestAuth为真 表示此页面为需要授权才能进入的页面
  }                                                         //to.meta.requestAuth是自己定义的
})                                                          //next表示下一步操作，跳转到此页面，或者默认页面（默认页面就是你点击的是那个页面就跳转到哪个页面）

export default router
