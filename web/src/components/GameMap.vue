<template>
    <div ref="parent" class="gamemap">
        <!-- 加上 tabindex="0" 就可以获取用户输入操作了 -->
        <canvas ref="canvas" tabindex="0"></canvas>  
    </div>
</template>

<script>
import { GameMap } from "@/assets/scripts/GameMap";
import { ref, onMounted } from 'vue'
import { useStore } from "vuex";

export default {
    setup() {
        const store = new useStore;

        let parent = ref(null);
        let canvas = ref(null);

        onMounted(() => {
            store.commit(
                "updateGameObject", 
                new GameMap(canvas.value.getContext('2d'), parent.value, store)
            )
        });

        return {
            parent,
            canvas,
            store,
        }
    }
}
</script>

<style scoped>
div.gamemap {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>
