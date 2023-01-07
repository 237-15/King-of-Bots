<template>
    <div class="result_board">
        <div class="result_board_grade" v-if="$store.state.pk.loser === 'all'">
            Draw!
        </div>
        <div class="result_board_grade" v-else-if="$store.state.pk.loser === 'A' && $store.state.pk.idA == $store.state.user.id">
            Lose!
        </div>
        <div class="result_board_grade" v-else-if="$store.state.pk.loser === 'B' && $store.state.pk.idB == $store.state.user.id">
            Lose!
        </div>
        <div class="result_board_grade" v-else>
            win!!
        </div>
        <div class="result_board_btn">
            <button @click="button" class="btn btn-warning">再来一局!!</button>
        </div>
    </div>
</template>

<script>
import { useStore } from 'vuex';

export default {
    setup() {
        const store = new useStore();

        const button = () => {
            store.commit("updateStatus", "matching");
            store.commit("updateLoser", "none");
            store.commit("updateOpponent", {
                opponent_username: "我的对手",
                opponent_photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png",
            })
        }

        return {
            store,
            button,
        }
    }
}
</script>

<style scoped>
div.result_board {
    width: 30vw;
    height: 30vh;
    top: 30vh;
    left: 35vw;
    position: absolute;
    background-color: rgba(50, 50, 50, 0.7);
}
div.result_board_grade {
    padding-top: 6vh;
    text-align: center;
    color: aliceblue;
    font-size: 40px;
    font-weight: 1000;
    font-style: italic;
}
div.result_board_btn {
    text-align: center;
    padding-top: 4vh;
}

</style>