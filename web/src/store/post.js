import $ from 'jquery'
import store from '.'

export default {
    state: {  
        posts: [],
        
    },
    getters: { 
    },
    mutations: {  
        updatePosts(state, posts) {
            state.posts = posts
            console.log(store.state.post.posts)
        },

    },
    actions: {
        getPosts(context) {
            $.ajax({
                url: "http://127.0.0.1:3000/post/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                success(resp) {
                    context.commit("updatePosts", resp)
                },
                error() {
                    console.log("error_message: getposts")
                }
            })
        }
    },
    modules: { 
    }
}
