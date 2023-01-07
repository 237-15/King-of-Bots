<template>
    <div class = "BACK" v-if="!$store.state.user.pulling_info">
        <div class="row justify-content-md-center box">
            <div class="col-10">
                <form @submit.prevent="login">
                    <div class="mb-3" style="text-align: center; font-size: x-large; color: aliceblue;">
                        LOGIN
                    </div>
                    <div class="mb-3">
                        <label for="username" class="form-label" style="color: aliceblue; margin-top: 10px">用户名</label>
                        <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label" style="color: aliceblue">密码</label>
                        <input v-model="password" type="password" class="form-control" id="password" placeholder="请输入密码">
                    </div>
                    <div class="error-message">{{ error_message }}</div>
                    <button type="submit" class="btn btn-primary" style="margin-top: 10px;">登录</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex'
import { ref } from 'vue'
import router from '../../../router/index'

export default {
    setup() {
        const store = useStore();
        const current_webPage_name = localStorage.getItem("current_webPage_name");
        let username = ref('');
        let password = ref('');
        let error_message = ref('');

        if(current_webPage_name == "user_account_login") {
            store.commit("updatePullingInfo", false);  //展示登录页面
        } else {
            const jwt_token = localStorage.getItem("jwt_token");  //获取浏览器保存的token，如果不存在则返回空
            if(jwt_token) {  //如果token存在要验证其合法性,即有没有过期或者能不能被解析出来
                store.commit("updateToken", jwt_token);  //更新全局变量token
                store.dispatch("getinfo", {  //验证token的合法性，即检验token能不能被解析
                    success() {
                        if(!store.state.user.button){  
                            router.push({ name: current_webPage_name });  //跳转到上次打开的页面
                        } else {  //如果是点击退出登录，则不用跳回原来的页面
                            store.commit("updatePullingInfo", false);  //展示登录页面
                        }
                    },
                    error() {
                        store.commit("updatePullingInfo", false);  //展示登录页面
                    }
                })
            } else {  //如果token不存在，也就是浏览器里的token过期了，或者压根没有token
                store.commit("updatePullingInfo", false);  //展示登录页面
            }
        }
    
        const login = () => {
            error_message.value = "";
            store.dispatch("login", {    //想调用actions里面的函数就要用 dispatch
                username: username.value,
                password: password.value,
                success() {
                    store.dispatch("getinfo", {
                        success() {
                            router.push({ name: 'home' });
                            store.state.user.is_login = true;
                        }
                    })
                },
                error() {
                    error_message.value = "用户名或密码错误";
                }
            })
        }

        return {
            username,
            password,
            error_message,
            login,
        }
    }
}
</script>

<style scoped>
button {
    width: 100%;
}
div.error-message {
    color: red;
}

.login {
    margin: auto;
}

div.box {
    align-items: center; 
    margin: 15vh auto; 
    background-color: rgba(20, 17, 17, 0.7); 
    width: 20vw; 
    height: 50vh;
    border-radius:25px;
}

.BACK {
    background-image: url("https://pic1.zhimg.com/v2-ad60c5f5ab4858d727045481c0cfd8c5_r.jpg?source=1940ef5c");
    background-size: cover;
    width: 100vw;
    height: 92.7vh;
    position: absolute;
}
</style>