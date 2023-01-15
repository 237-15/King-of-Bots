<template>
    <div>
        <PlayGround />
        <div style="text-align: center;">
            <button type="button" @click="back" class="btn btn-success record-btn">
                返回
            </button>
            <button type="button" @click="play" class="btn btn-warning record-btn" style="margin-left: 10vh;">
                我也来一局
            </button>
        </div>
    </div>
    
</template>

<script>
import PlayGround from '../../components/PlayGround.vue'
// import { useStore } from 'vuex';
import router from '@/router';

export default {
    components: {
        PlayGround,
    },
    setup() {
        // const store = useStore()

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