<template>
    <ContentField>
        排行榜
    </ContentField>
</template>

<script>
import ContentField from '../../components/ContentField.vue'

export default {
    components: {
        ContentField
    },
    setup() {
        localStorage.setItem("current_webPage_name", "ranklist_index");
    }
}
</script>

<style scoped>

</style>