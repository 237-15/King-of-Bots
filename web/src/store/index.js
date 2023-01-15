// 全局变量
import { createStore } from 'vuex'
import ModuleUser from './User'
import ModulePk from './pk'
import ModuleRecord from './record'

export default createStore({
  state: {  //全局参数
  },
  getters: {  //这里是get方法
  },
  mutations: {  //这里是update方法
  },
  actions: {  //连接后端的ajax
  },
  modules: {  //为了给全局变量分组，所以需要提前声明其他store文件，然后引入这里
    user: ModuleUser,
    pk: ModulePk,
    record: ModuleRecord,
  }
})
