<template>
    <div class = "playground">
        <GameMap />
        <div class="position" v-if="$store.state.record.is_record === false">
            {{ position }}
        </div>
    </div>
</template>

<script>
import GameMap from './GameMap.vue'
import { useStore } from 'vuex'
import { ref } from 'vue'

export default {
    components: {
        GameMap,
    },
    setup() {
        const store = useStore()

        let position = ref("")
        if(store.state.pk.idA == store.state.user.id) {
            position.value = "左下角"
        } else {
            position.value = "右上角"
        }

        return {
            position,
        }
    }
}
</script>

<style scoped>
div.playground {
    width: 60vw;   
    height: 70vh;
    margin: 40px auto;
}
div.position {
    color:white;
    font-size: 10vh;
    font-weight: 900;
    text-align: center;
}
</style>



