<template>
    <div class = "matchground">
        <div class="row justify-content-md-center">
            <div class="col-6">
                <div class="user-photo">
                    <img :src="$store.state.user.photo" alt="" />
                </div>
                <div class="user-username">
                    {{ $store.state.user.username }}
                </div>
            </div>
            <div class="col-6">
                <div class="user-photo">
                    <img :src="$store.state.pk.opponent_photo" alt="" />
                </div>
                <div class="user-username">
                    {{ $store.state.pk.opponent_username }}
                </div>
            </div>
            <button @click="click_btn" class="btn btn-primary">{{ btn_info }}</button>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex';
import { ref } from 'vue';

export default {
    setup() {
        const store = new useStore()

        let btn_info = ref("开始匹配")

        const click_btn = () => {
            if(btn_info.value === "开始匹配") {
                btn_info.value = "取消"
                store.state.pk.socket.send(JSON.stringify({  //stringify() 可以将JSON封装成字符串 || send() 向后端发送信息
                    event: "start-matching"
                }))
            } else {
                btn_info.value = "开始匹配"
                store.state.pk.socket.send(JSON.stringify({  //stringify 可以将JSON封装成字符串
                    event: "stop-matching"
                }))
            }
            
        }
        return {
            store,
            btn_info,
            click_btn,
        }
    }
}
</script>

<style scoped>
div.matchground {
    width: 60vw;   
    height: 70vh;
    margin: 40px auto;
    background-color: rgba(28, 25, 25, 0.7);
}

div.user-username {
    text-align: center;
    margin-top: 3vh;
    color:aliceblue;
    font-size: larger;
    font-weight: 600;
}

div.user-photo {
    text-align: center;
    margin-top: 10vh;
}

div.user-photo > img {
    border-radius: 50%;
    width: 22vh;
}

button {
    margin: 12vh auto;
    width: 7vw;
}
</style>