<template>
    <div class = "matchground">
        <div class="row justify-content-md-center">
            <div class="col-5">
                <div class="user-photo">
                    <img :src="$store.state.user.photo" alt="" />
                </div>
                <div class="user-username">
                    {{ $store.state.user.username }}
                </div>
            </div>
            <div class="col-2 time" >
                <div>计时</div>
                {{ min }} : {{ second }}
            </div>
            <div class="col-5">
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
        let min = ref(0);  //分
        let second = ref(0);  //秒
        let millisecond = ref(0);  //毫秒
        let time_id;

        const click_btn = () => {
            if(btn_info.value === "开始匹配") {
                btn_info.value = "取消" 

                store.commit("updateTime", "start")
                time_id = setInterval(timer,50)  //每1000毫秒执行一下timer函数

                store.state.pk.socket.send(JSON.stringify({  //stringify() 可以将JSON封装成字符串 || send() 向后端发送信息
                    event: "start-matching"
                }))
            } else {
                btn_info.value = "开始匹配"

                store.commit("updateTime", "stop")
                window.clearInterval(time_id);  //停止计时
                min.value = 0;  //重置
                second.value = 0;

                store.state.pk.socket.send(JSON.stringify({  //stringify 可以将JSON封装成字符串
                    event: "stop-matching"
                }))
            }
            
        }

        const timer = () => {  //计时函数
            millisecond.value += 50;
            if(millisecond.value >= 1000) {
                second.value ++;
                millisecond.value = 0;
            }
            if(second.value >= 60) {
                min.value ++;
                second.value = 0;
            }
            if(store.state.pk.time === "stop") {
                window.clearInterval(time_id);  //停止计时
                min.value = 0;  //重置
                second.value = 0;
            }
        }

        return {
            store,
            btn_info,
            click_btn,
            min,
            second,
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
    margin: 12vh;
    width: 7vw;
}

div.time {
    padding: 3vw 7vh;
    color:aliceblue;
}
</style>