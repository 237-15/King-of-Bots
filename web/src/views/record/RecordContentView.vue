<template>
    <div>
        <PlayGround />
        <div style="text-align: center;">
            <button type="button" @click="back()" class="btn btn-success record-btn">
                返回
            </button>
            <button type="button" @click="play" class="btn btn-warning record-btn" style="margin-left: 10vh;">
                我也来一局
            </button>
        </div>
    </div>
    
</template>

<script>
import { useStore } from 'vuex';
import PlayGround from '../../components/PlayGround.vue'
import router from '@/router';

export default {
    components: {
        PlayGround,
    },
    setup() {
        const store = useStore()  //需要用到这个因为引入了PlayGround
        let id = store.state.user.id  //用一下store,不然会报错
        store.state.user.id = id
        
        const back = () => {
            router.push({
                name: "record_index"
            })
        }

        const play = () => {
            router.push({
                name: "pk_index"
            })
        }

        return {
            back,
            play,
        }
    }
}
</script>

<style scoped>
div.record-btn {
    width: 10vh;
}
</style>