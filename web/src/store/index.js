// 全局变量
import { createStore } from 'vuex'
import ModuleUser from './User'

export default createStore({
  state: {  //全局参数
  },
  getters: {  //这里是get方法
  },
  mutations: {  //这里是set方法
  },
  actions: {  //暂时用不上
  },
  modules: {  //为了给全局变量分组，所以需要提前声明其他store文件，然后引入这里
    user: ModuleUser,
  }
})
