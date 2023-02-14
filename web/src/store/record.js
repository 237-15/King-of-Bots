export default {
    state: {
        is_record: false,  //表示是否是回放
        current_page: 1,  //表示回放的对局处在第几页
        stepsA: "",
        stepsB: "",
        record_loser: "",
    },
    getters: {
    },
    mutations: {  
        updateIsRecord(state, is_record) {  
            state.is_record = is_record
        },
        updateSteps(state, steps) {
            state.stepsA = steps.stepsA
            state.stepsB = steps.stepsB
        },
        updateRecordLoser(state, record_loser) {
            state.record_loser = record_loser
        },
        updateCurrent_page(state,current_page) {
            state.current_page = current_page
        },
    },
    actions: {  

    },
    modules: {
    }
}
