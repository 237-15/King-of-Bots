export default {
    state: {
        status: "matching",  //表示此时的状态，matching表示正在匹配，playing表示正在对战
        socket: null,  //后端的 WebSocket
        opponent_username: "",  //对手用户名
        opponent_photo: "",  //对手头像
        gameMap: "null",  //游戏地图
    },
    getters: {
    },
    mutations: {  //数据更新
        updateSocket(state, socket) {
            state.socket = socket
        },

        updateOpponent(state, opponent) {
            state.opponent_username = opponent.opponent_username
            state.opponent_photo = opponent.opponent_photo
        },

        updateStatus(state, status) {
            state.status = status
        },

        updateGameMap(state, gameMap) {
            state.gameMap = gameMap
        },
    },
    actions: {  //辅助函数

    },
    modules: {
    }
}
