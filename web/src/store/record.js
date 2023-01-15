export default {
    state: {
        is_record: false,  //表示是否是回放
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
    },
    actions: {  

    },
    modules: {
    }
}
