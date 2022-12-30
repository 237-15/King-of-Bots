<template>
    <div class="BACK">
        <div class="row justify-content-md-center box">
            <div class="col-10">
                <form @submit.prevent="register">
                    <div class="mb-3" style="text-align: center; font-size: x-large; color: aliceblue">
                        REGISTER
                    </div>
                    <div class="mb-3">
                        <label for="username" class="form-label" style="color: aliceblue">用户名</label>
                        <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label" style="color: aliceblue">密码</label>
                        <input v-model="password" type="password" class="form-control" id="password" placeholder="请输入密码">
                    </div>
                    <div class="mb-3">
                        <label for="confirmedPassword" class="form-label" style="color: aliceblue">确认密码</label>
                        <input v-model="confirmedPassword" type="password" class="form-control" id="confirmedPassword" placeholder="请再次输入密码">
                    </div>
                    <div class="error-message">{{ error_message }}</div>
                    <button type="submit" class="btn btn-primary">注册</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import { ref } from 'vue';
import router from '@/router';
import $ from 'jquery';

export default {
    setup() {
        let username = ref('');
        let password = ref('');
        let confirmedPassword = ref('');
        let error_message = ref('');

        const register = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/user/account/register/",
                type: "post",  //get: 只获得数据，不修改数据库； post:可修改数据库
                data: {        //post比get更安全
                    username: username.value,
                    password: password.value,
                    confirmedPassword: confirmedPassword.value,
                },   

                success(resp) {
                    if(resp.error_message === "success") {
                        router.push({name: "user_account_login"});  //跳转到登录界面
                    } else {
                        error_message.value = resp.error_message;
                    }
                },
            })
        }

        return {
            username,
            password,
            confirmedPassword,
            error_message,
            register,
        }
    }
}
</script>

<style scoped>
.error-message {
    color: red;
}

button {
    width: 100%;
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