import $ from 'jquery'

export default {
    state: {
        id: "",
        username: "",
        photo: "",
        rating: 1500,
        token: "",
        is_login: false,
        pulling_info: true,  // 是否正在从云端拉取信息
        button: false,
        rate: 1000  //录像时每一回合的时间
    },
    getters: {
    },
    mutations: {  //数据更新  store.commit()
        updateUser(state, user) {
            state.id = user.id;
            state.username = user.username;
            state.photo = user.photo;
            state.rating = user.rating;
            state.is_login = user.is_login;
        },
        updateToken(state, token) {
            state.token = token;
        },
        logout(state) {
            state.id = "";
            state.username = "";
            state.photo = "";
            state.token = "";
            state.is_login = false;
        },
        updatePullingInfo(state, pulling_info) {
            state.pulling_info = pulling_info;
        },
        updateRate(state, rate) {
            state.rate = rate
        },
    },
    actions: {  //辅助函数  store.dispatch()
        login(context, data) {  //如果传过来2个以上的参数就写data,这是框架定义好的
            $.ajax({
                url: "http://127.0.0.1:3000/user/account/login/",
                type: "post",
                data: {
                    username: data.username,
                    password: data.password,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        context.commit("updateToken", resp.token);  //在actions里用mutations里面的函数要用context.commit("函数名", 参数)
                        localStorage.setItem("jwt_token", resp.token);  //在localStorage里面存下token，以便我们刷新页面时可以读取里面的token从而避免再次登录
                        data.success(resp);
                    } else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    data.error(resp);
                }
            });
        },
        getinfo(context, data) {
            $.ajax({
                url: "http://127.0.0.1:3000/user/account/info/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + context.state.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        context.commit("updateUser", {
                            ...resp,
                            is_login: true
                        });
                        data.success(resp);
                    } else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    data.error(resp);
                }
            })
        },
        logout(context) {
            context.commit("logout");
        }
    },
    modules: {
    }
}
