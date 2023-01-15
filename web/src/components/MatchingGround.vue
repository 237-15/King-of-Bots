<template>
    <div class = "matchground">
        <div class="row justify-content-md-center">
            <div class="col-4">
                <div class="user-photo">
                    <img :src="store.state.user.photo" alt="" />
                </div>
                <div class="user-username">
                    {{ store.state.user.username }}
                </div>
            </div>
            <div class="col-4">
                <div class="time" v-if="store.state.pk.time !== 'success'">
                    <div>计时</div>
                    {{ min }} : {{ second }}
                </div>
                <div class="time" v-if="store.state.pk.time === 'success'">
                    匹配成功！！
                </div>
                <div style="margin-bottom: 0px; padding-left: 8vh; color:bisque; padding-top: 15vh;">出战！！</div>
                <div class="select-user-bot">
                    <select v-model="bot_id" :disabled="showSelect" class="form-select" aria-label="Default select example">
                        <option value="-1" selected>亲自出马</option>
                        <option :value="bot.id" v-for="bot in bots" :key="bot.id">{{ bot.title }}</option>
                    </select>
                </div>
            </div>
            <div class="col-4">
                <div class="user-photo">
                    <img :src="store.state.pk.opponent_photo" alt="" />
                </div>
                <div class="user-username">
                    {{ store.state.pk.opponent_username }}
                </div>
            </div>
            <button @click="click_btn" class="btn btn-primary">{{ btn_info }}</button>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex';
import { ref } from 'vue';
import $ from 'jquery'

export default {
    setup() {
        const store = new useStore()

        let btn_info = ref("开始匹配")
        let min = ref(0)  //分
        let second = ref(0)  //秒
        let millisecond = ref(0); //毫秒
        let time_id
        let bots = ref([])
        let bot_id = ref("-1")
        let showSelect = ref(false)

        const click_btn = () => {
            if(btn_info.value === "开始匹配") {
                btn_info.value = "取消" 
                showSelect.value = true;  //禁用选择框

                store.commit("updateTime", "start")
                time_id = setInterval(timer,50)  //每1000毫秒执行一下timer函数

                store.state.pk.socket.send(JSON.stringify({  //stringify() 可以将JSON封装成字符串 || send() 向后端发送信息
                    event: "start-matching",
                    bot_id: bot_id.value,
                }))
            } else {
                btn_info.value = "开始匹配"
                showSelect.value = false;  //开启选择框

                store.commit("updateTime", "stop")
                window.clearInterval(time_id);  //停止计时
                min.value = 0;  //重置
                second.value = 0;

                store.state.pk.socket.send(JSON.stringify({  //stringify 可以将JSON封装成字符串
                    event: "stop-matching"
                }))
            }
            
        }

        const refresh_bots = () => {  //更新bot信息函数
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    bots.value = resp;
                }
            })
        }

        refresh_bots();

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
            bots,
            bot_id,
            showSelect,
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
    margin-top: 15vh;
}

div.user-photo > img {
    border-radius: 50%;
    width: 20vh;
}

button {
    text-align: center;
    margin-top: 8vh;
    width: 7vw;
}

div.time {
    padding-top: 5vh;
    text-align: center;
    color:aliceblue;
}

div.select-user-bot {
    padding-left: 8vh;
    width: 30vh;
}
</style>