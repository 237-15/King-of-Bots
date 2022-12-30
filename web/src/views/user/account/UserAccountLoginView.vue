<template>
    <div class = "BACK">
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
        let username = ref('');
        let password = ref('');
        let error_message = ref('');

        const login = () => {
            error_message.value = "";
            store.dispatch("login", {    //想调用actions里面的函数就要用 dispatch
                username: username.value,
                password: password.value,
                success() {
                    store.dispatch("getinfo", {
                        success() {
                            router.push({ name: 'home' });
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