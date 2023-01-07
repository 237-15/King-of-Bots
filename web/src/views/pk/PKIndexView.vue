<template>
    <PlatGround v-if="$store.state.pk.status === 'playing'" />
    <MatchingGround v-if="$store.state.pk.status === 'matching'" />
    <ResultBoard v-if="$store.state.pk.loser != 'none'" />
</template>

<script>
import PlatGround from '../../components/PlayGround.vue'
import MatchingGround from '../../components/MatchingGround.vue'
import ResultBoard from '../../components/ResultBoard.vue'
import { onMounted, onUnmounted } from 'vue';
import { useStore } from 'vuex';

export default {
    components: {
        PlatGround,
        MatchingGround,
        ResultBoard,
    },

    setup() {
        const store = useStore()
        const socketUrl = `ws://127.0.0.1:3000/websocket/${store.state.user.token}/`

        let socket = null

        localStorage.setItem("current_webPage_name", "home");

        onMounted(() => {  //挂载的意思，就是当打开pk页面是执行此函数
            store.commit("updateOpponent", {
                opponent_username: "我的对手",
                opponent_photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
            })

            socket = new WebSocket(socketUrl)

            socket.onopen = () => {  //初始化
                console.log("connect!")
                store.commit("updateSocket", socket)
            }

            socket.onmessage = msg => {  //主要逻辑
                const data = JSON.parse(msg.data)  //后端传过来的

                if (data.status === "playing") {
                    store.commit("updateTime", "stop")
                    store.commit("updateOpponent", {
                        opponent_username: data.opponent_username,
                        opponent_photo: data.opponent_photo,
                    })
                    setTimeout(() => {  //相当于Sleep函数
                        store.commit("updateStatus", data.status)
                    }, 2000)
                    store.commit("updateGame", data.game)
                } else if(data.event === "move") {
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;
                    snake0.set_direction(data.A_direction);
                    snake1.set_direction(data.B_direction);
                } else if(data.event === "result") {
                    const game = store.state.pk.gameObject;
                    const [snake0, snake1] = game.snakes;
                    if(data.loser === "all" || data.loser === "A") {
                        snake0.status = "die";
                    }
                    if(data.loser === "all" || data.loser === "B") {
                        snake1.status = "die";
                    }
                    store.commit("updateLoser", data.loser)
                }
            }

            socket.onclose = () => {  //关闭时
                console.log("disconnect!")
            }
        })

        onUnmounted(() => {  //离开本页面或关闭本页面或刷新都会执行此函数,表示此页面处于不被挂载的状态，也就是未展示
            socket.close()
            store.commit("updateStatus", "matching")
        })
    }
}
</script>

<style scoped>


























</style>