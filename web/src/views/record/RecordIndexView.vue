<template>
    <ContentField>
        对局列表
    </ContentField>
</template>

<script>
import ContentField from '../../components/ContentField.vue'

export default {
    components: {
        ContentField
    },
    setup() {
        localStorage.setItem("current_webPage_name", "record_index");
    }
}
</script>

<style scoped>

</style>