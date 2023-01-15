export default {
    state: {
        status: "matching",  //表示此时的状态，matching表示正在匹配，playing表示正在对战
        socket: null,  //后端的 WebSocket
        opponent_username: "",  //对手用户名
        opponent_photo: "",  //对手头像

        gameMap: "null",  //游戏地图
        idA: 0,
        sxA: 0,
        syA: 0,
        idB: 0,
        sxB: 0,
        syB: 0,

        gameObject: null,
        loser: "none",  //none, all, A, B  输者
        time: "",  //计时器，start stop success 开始计时，停止计时并重置，匹配成功停止计时并重置
    },
    getters: {
    },
    mutations: {  //数据更新 
        updateSocket(state, socket) {  
            state.socket = socket
        },

        updateOpponent(state, opponent) {  //如果传过来2个以上的参数就写data,这是框架定义好的,当然写别的也没问题
            state.opponent_username = opponent.opponent_username
            state.opponent_photo = opponent.opponent_photo
        },

        updateStatus(state, status) {
            state.status = status
        },

        updateGame(state, game) {
            state.gameMap = game.map
            state.idA = game.idA
            state.sxA = game.sxA
            state.syA = game.syA
            state.idB = game.idB
            state.sxB = game.sxB
            state.syB = game.syB
        },
        updateGameObject(state, gameObject) {
            state.gameObject = gameObject
        },
        updateLoser(state, loser) {
            state.loser = loser
        },
        updateTime(state, time) {
            state.time = time
        }
    },
    actions: {  //辅助函数

    },
    modules: {
    }
}
