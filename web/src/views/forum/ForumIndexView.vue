<template>
    <ul v-infinite-scroll="load" infinite-scroll-distance="20px" class="infinite-list" style="overflow: auto; height: 100%;">
        <ContentField style="width: 60vw;">
            <div>
                <svg style="width: 3vh;" xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-justify" viewBox="0 0 16 16">
                    <path fill-rule="evenodd" d="M2 12.5a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm0-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm0-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm0-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5z"/>
                </svg>
                <span style="font-size: 30px; font-weight: 600;">社区</span>
                <button type="button" class="btn add_post" data-bs-toggle="modal" data-bs-target="#addpost">
                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-plus-lg" viewBox="0 0 16 16">
                        <path fill-rule="evenodd" d="M8 2a.5.5 0 0 1 .5.5v5h5a.5.5 0 0 1 0 1h-5v5a.5.5 0 0 1-1 0v-5h-5a.5.5 0 0 1 0-1h5v-5A.5.5 0 0 1 8 2Z"/>
                    </svg>
                </button>
                <!-- modal -->
                <div class="modal fade" id="addpost" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h1 class="modal-title fs-5" id="exampleModalLabel">创建新帖子</h1>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form>
                                    <div class="mb-3">
                                        <!-- <label for="message-text" class="col-form-label">分享你的生活趣事吧</label> -->
                                        <v-md-editor 
                                            v-model="new_post" 
                                            height="400px"
                                            :disabled-menus="[]"
                                            @upload-image="handleUploadImage"
                                            placeholder="分享你的生活吧！"
                                        />
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <button type="button" class="btn btn-primary" @click="add_post">发表</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- modal -->
                
                <!-- 走马灯 -->
                <el-carousel :interval="4000" type="card" height="200px" style="width: 50vw; margin: auto;">
                    <el-carousel-item v-for="carousel_photo of carousel_photos" :key="carousel_photo">
                        <h3 text="2xl" justify="center">
                            <img :src="carousel_photo" />
                        </h3>
                    </el-carousel-item>
                </el-carousel>
                <!-- 走马灯 -->
            </div>
            
        </ContentField>
        <li v-for="post of posts" :key="post.id" class="infinite-list-item" style="height: auto; width: 58vw; border-radius: 10px; margin-left:auto; margin-right:auto;">
            <div class="row" style="padding: 25px;">
                <div class="col-1">
                    <img class="photo" :src="post.photo" alt="卡哇伊" />
                </div>
                <div class="col-6">
                    <div style="font-size: 22px;">{{ post.username }}</div>
                    <div style="color: darkgrey;">
                        {{ post.create_time }}
                        <svg style="width: 4px;" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-circle-fill" viewBox="0 0 16 16">
                            <circle cx="8" cy="8" r="8"/>
                        </svg>
                        {{ post.read_count }} 阅读
                    </div>
                </div>
                <div class="col-5">
                    <button type="button" class="btn btn-danger" style="width: 12vh; margin-left: 17vh;">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart" viewBox="0 0 16 16">
                            <path d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
                        </svg>
                        关注
                    </button>
                    <!-- <button type="button" class="btn btn-danger" style="width: 12vh;">
                        <svg style="width: 1.5vh" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart-fill" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"/>
                        </svg>
                        已关注
                    </button> -->
                    <button type="button" class="btn btn-info" style="width: 12vh; margin-left: 1vh;">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-chat-dots" viewBox="0 0 16 16">
                            <path d="M5 8a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm4 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                            <path d="m2.165 15.803.02-.004c1.83-.363 2.948-.842 3.468-1.105A9.06 9.06 0 0 0 8 15c4.418 0 8-3.134 8-7s-3.582-7-8-7-8 3.134-8 7c0 1.76.743 3.37 1.97 4.6a10.437 10.437 0 0 1-.524 2.318l-.003.011a10.722 10.722 0 0 1-.244.637c-.079.186.074.394.273.362a21.673 21.673 0 0 0 .693-.125zm.8-3.108a1 1 0 0 0-.287-.801C1.618 10.83 1 9.468 1 8c0-3.192 3.004-6 7-6s7 2.808 7 6c0 3.193-3.004 6-7 6a8.06 8.06 0 0 1-2.088-.272 1 1 0 0 0-.711.074c-.387.196-1.24.57-2.634.893a10.97 10.97 0 0 0 .398-2z"/>
                        </svg>
                        私信
                    </button>
                </div>
                <div style="margin-top: 1vh;">
                    <span style="padding-left: 4vh;">
                        {{ post.content }}
                    </span>
                    <div>
                        ————————————————————————————————————————————————
                    </div>
                    <div>
                        <span class="hover" @click="like_post(post.id.toString())">
                            <svg style="color: #212529" v-if="post.is_love === 'no'" xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-hand-thumbs-up" viewBox="0 0 16 16">
                                <path d="M8.864.046C7.908-.193 7.02.53 6.956 1.466c-.072 1.051-.23 2.016-.428 2.59-.125.36-.479 1.013-1.04 1.639-.557.623-1.282 1.178-2.131 1.41C2.685 7.288 2 7.87 2 8.72v4.001c0 .845.682 1.464 1.448 1.545 1.07.114 1.564.415 2.068.723l.048.03c.272.165.578.348.97.484.397.136.861.217 1.466.217h3.5c.937 0 1.599-.477 1.934-1.064a1.86 1.86 0 0 0 .254-.912c0-.152-.023-.312-.077-.464.201-.263.38-.578.488-.901.11-.33.172-.762.004-1.149.069-.13.12-.269.159-.403.077-.27.113-.568.113-.857 0-.288-.036-.585-.113-.856a2.144 2.144 0 0 0-.138-.362 1.9 1.9 0 0 0 .234-1.734c-.206-.592-.682-1.1-1.2-1.272-.847-.282-1.803-.276-2.516-.211a9.84 9.84 0 0 0-.443.05 9.365 9.365 0 0 0-.062-4.509A1.38 1.38 0 0 0 9.125.111L8.864.046zM11.5 14.721H8c-.51 0-.863-.069-1.14-.164-.281-.097-.506-.228-.776-.393l-.04-.024c-.555-.339-1.198-.731-2.49-.868-.333-.036-.554-.29-.554-.55V8.72c0-.254.226-.543.62-.65 1.095-.3 1.977-.996 2.614-1.708.635-.71 1.064-1.475 1.238-1.978.243-.7.407-1.768.482-2.85.025-.362.36-.594.667-.518l.262.066c.16.04.258.143.288.255a8.34 8.34 0 0 1-.145 4.725.5.5 0 0 0 .595.644l.003-.001.014-.003.058-.014a8.908 8.908 0 0 1 1.036-.157c.663-.06 1.457-.054 2.11.164.175.058.45.3.57.65.107.308.087.67-.266 1.022l-.353.353.353.354c.043.043.105.141.154.315.048.167.075.37.075.581 0 .212-.027.414-.075.582-.05.174-.111.272-.154.315l-.353.353.353.354c.047.047.109.177.005.488a2.224 2.224 0 0 1-.505.805l-.353.353.353.354c.006.005.041.05.041.17a.866.866 0 0 1-.121.416c-.165.288-.503.56-1.066.56z"/>
                            </svg>
                            <svg v-if="post.is_love === 'yes'" style="color: #FF5252;" xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-hand-thumbs-up-fill" viewBox="0 0 16 16">
                                <path d="M6.956 1.745C7.021.81 7.908.087 8.864.325l.261.066c.463.116.874.456 1.012.965.22.816.533 2.511.062 4.51a9.84 9.84 0 0 1 .443-.051c.713-.065 1.669-.072 2.516.21.518.173.994.681 1.2 1.273.184.532.16 1.162-.234 1.733.058.119.103.242.138.363.077.27.113.567.113.856 0 .289-.036.586-.113.856-.039.135-.09.273-.16.404.169.387.107.819-.003 1.148a3.163 3.163 0 0 1-.488.901c.054.152.076.312.076.465 0 .305-.089.625-.253.912C13.1 15.522 12.437 16 11.5 16H8c-.605 0-1.07-.081-1.466-.218a4.82 4.82 0 0 1-.97-.484l-.048-.03c-.504-.307-.999-.609-2.068-.722C2.682 14.464 2 13.846 2 13V9c0-.85.685-1.432 1.357-1.615.849-.232 1.574-.787 2.132-1.41.56-.627.914-1.28 1.039-1.639.199-.575.356-1.539.428-2.59z"/>
                            </svg>
                            
                        </span>
                        <span style="color: #212529"> {{ post.love_count }}</span>

                        <span style="margin-left: 3vh;" class="hover" @click="star_post(post.id.toString())">
                            <svg style="color: #212529" v-if="post.is_star === 'no'" xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-star" viewBox="0 0 16 16">
                                <path d="M2.866 14.85c-.078.444.36.791.746.593l4.39-2.256 4.389 2.256c.386.198.824-.149.746-.592l-.83-4.73 3.522-3.356c.33-.314.16-.888-.282-.95l-4.898-.696L8.465.792a.513.513 0 0 0-.927 0L5.354 5.12l-4.898.696c-.441.062-.612.636-.283.95l3.523 3.356-.83 4.73zm4.905-2.767-3.686 1.894.694-3.957a.565.565 0 0 0-.163-.505L1.71 6.745l4.052-.576a.525.525 0 0 0 .393-.288L8 2.223l1.847 3.658a.525.525 0 0 0 .393.288l4.052.575-2.906 2.77a.565.565 0 0 0-.163.506l.694 3.957-3.686-1.894a.503.503 0 0 0-.461 0z"/>
                            </svg>
                            <svg v-if="post.is_star === 'yes'" style="color: gold;" xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-star-fill" viewBox="0 0 16 16">
                                <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"/>
                            </svg>
                            
                        </span>
                        <span style="color: #212529"> {{ post.star_count }}</span>
                        
                        <span style="margin-left: 3vh;">
                            <!-- <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-chat-square-dots-fill" viewBox="0 0 16 16">
                                <path d="M0 2a2 2 0 0 1 2-2h12a2 2 0 0 1 2 2v8a2 2 0 0 1-2 2h-2.5a1 1 0 0 0-.8.4l-1.9 2.533a1 1 0 0 1-1.6 0L5.3 12.4a1 1 0 0 0-.8-.4H2a2 2 0 0 1-2-2V2zm5 4a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm4 0a1 1 0 1 0-2 0 1 1 0 0 0 2 0zm3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/>
                            </svg> -->
                            <button class="btn" type="button" data-bs-toggle="collapse" :data-bs-target="'#comment' + post.id" aria-expanded="false" aria-controls="collapseExample">
                                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-chat-square-text" viewBox="0 0 16 16">
                                    <path d="M14 1a1 1 0 0 1 1 1v8a1 1 0 0 1-1 1h-2.5a2 2 0 0 0-1.6.8L8 14.333 6.1 11.8a2 2 0 0 0-1.6-.8H2a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1h12zM2 0a2 2 0 0 0-2 2v8a2 2 0 0 0 2 2h2.5a1 1 0 0 1 .8.4l1.9 2.533a1 1 0 0 0 1.6 0l1.9-2.533a1 1 0 0 1 .8-.4H14a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H2z"/>
                                    <path d="M3 3.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM3 6a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9A.5.5 0 0 1 3 6zm0 2.5a.5.5 0 0 1 .5-.5h5a.5.5 0 0 1 0 1h-5a.5.5 0 0 1-.5-.5z"/>
                                </svg>
                                
                            </button>
                            <span style="color: #212529">{{ post.comment_count }}</span>
                            <div class="collapse" :id="'comment' + post.id">
                                <div class="card">
                                    <div class="container">
                                        <div class="row">
                                            <h1>{{ post.comment_count }}评论</h1>
                                            <div class="col-1">
                                                <img class="photo" :src="$store.state.user.photo" />
                                            </div>
                                            <div class="col-11">
                                                <textarea v-model="new_comment" style="margin-left: 10px; border-radius: 10px;" cols="85"></textarea>
                                                <!-- <v-md-editor 
                                                    v-model="new_comment" 
                                                    height="200px"
                                                    :disabled-menus="[]"
                                                /> -->
                                            </div>
                                            <button @click="add_comment(post.id)" style="width: 15vh; margin: 20px auto;" class="btn btn-primary" type="button">提交评论</button>
                                            <div class="card" v-for="comment_level1 of post.comment_level1s" :key="comment_level1.id">
                                                <ul class="list-group list-group-flush">
                                                    <li class="list-group-item">
                                                        <div class="container">
                                                            <div class="row">
                                                                <div class="col-1">
                                                                    <img :src="comment_level1.photo" class="photo">
                                                                </div>
                                                                <div class="col-11">
                                                                    <div>
                                                                        <span style="margin-left: 2vh; color: #59caff; font-weight: 600;">{{ comment_level1.username }} </span>
                                                                        &nbsp;&nbsp;
                                                                        <span style="font-size: 2px; color: darkgrey;">{{ comment_level1.create_time }}</span>
                                                                        &nbsp;&nbsp;
                                                                        <span style="cursor:pointer;">  
                                                                            <svg @click="love_comment_level1(comment_level1.id, comment_level1.post_id, null, comment_level1.level)" v-if="comment_level1.is_love === 'no'" class="bi bi-heart" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                                                <path fill-rule="evenodd" d="M8 2.748l-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01L8 2.748zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143c.06.055.119.112.176.171a3.12 3.12 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15z"/>
                                                                            </svg>
                                                                            <svg @click="love_comment_level1(comment_level1.id, comment_level1.post_id, null, comment_level1.level)" v-if="comment_level1.is_love === 'yes'" style="color: #FF5252;" class="bi bi-heart-fill" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                                                                                <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314z"/>
                                                                            </svg>
                                                                            {{ comment_level1.love_count }}
                                                                        </span>
                                                                        <span class="huifu">回复</span>
                                                                    </div>
                                                                    <div style="margin-left: 2vh;">{{ comment_level1.content }}</div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </span>
                    </div>
                </div>
            </div>
        </li>
    </ul>
</template>

<script>
import ContentField from "@/components/ContentField.vue"
import { Modal } from 'bootstrap/dist/js/bootstrap'
import { useStore } from "vuex";
import { ref, reactive } from "vue"
import $ from 'jquery'

export default {
    components: {
        ContentField,
    },
    setup() {
        //element-plus
        const count = ref(0)
        const load = () => {
            count.value += 10
        }

        localStorage.setItem("current_webPage_name", "forum_index");
        const store = useStore();
        let posts= ref([]);  //所有的帖子和评论
        let new_post = ref();
        let new_comment = ref("");
        let show_post_count = 10;  //当前展示post的个数,第一次打开论坛加载10条帖子
        let carousel_photos = reactive([
            "https://tse3-mm.cn.bing.net/th/id/OIP-C.jb-OE259cWU7Y_29TRf7bAHaEK?pid=ImgDet&rs=1",
            "https://tse1-mm.cn.bing.net/th/id/OIP-C.o9L3gfJLCNdwFODmZrgcOAHaEK?pid=ImgDet&rs=1",
            "https://pic4.zhimg.com/v2-86778bc5ed9aeeaa74822a6cd4936265_r.jpg?source=1940ef5c"
        ])

        const get_posts = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/api/post/getposts/",
                type: "get",
                data: {
                    user_id: store.state.user.id,
                    show_post_count
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                success(resp) {
                    posts.value = resp
                    console.log(posts.value)
                },
                error() {
                    console.log("error_message: getposts")
                }
            })
        }
        get_posts();

        const add_post = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/api/post/addpost/",
                type: "post",
                data: {
                    user_id: store.state.user.id,
                    content: new_post.value,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                success() {
                    get_posts()
                    Modal.getInstance("#addpost").hide()
                    new_post.value = ""
                },
                error() {

                }
            })
        }

        const like_post = (post_id) => {
            $.ajax({
                url: "http://127.0.0.1:3000/api/post/likepost/",
                type: "post",
                data: {
                    user_id: store.state.user.id,
                    post_id: post_id
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                success() {
                    for(let post of posts.value) {  //即时更改，显示已赞
                        if(post.id == post_id) {
                            if(post.is_love === "no") {
                                post.is_love = "yes"
                                post.love_count++
                            } else if (post.is_love === "yes") {
                                post.is_love = "no"
                                post.love_count--
                            }
                            break
                        } 
                    }
                },
                error() {
                    console.log("error_message: likepost")
                }
            })
        }

        const star_post = (post_id) => {
            $.ajax({
                url: "http://127.0.0.1:3000/api/post/starpost/",
                type: "post",
                data: {
                    user_id: store.state.user.id,
                    post_id: post_id
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                success() {
                    for(let post of posts.value) {  //即时更改，显示已收藏
                        if(post.id == post_id) {
                            if(post.is_star === "no") {
                                post.is_star = "yes"
                                post.star_count++
                            } else if (post.is_star === "yes") {
                                post.is_star = "no"
                                post.star_count--
                            }
                            break
                        }
                    }
                },
                error() {
                    console.log("error_message: starpost")
                }
            })
        }

        // const get_comments = () => {
        //     $.ajax({
        //         url: "http://127.0.0.1:3000/api/post/comment/get/",
        //         type: "get",
        //         data: {
        //             user_id: store.state.user.id,
        //         },
        //         headers: {
        //             Authorization: "Bearer " + store.state.user.token
        //         },
        //         success(resp) {

        //         },
        //         error() {
        //             console.log("error_message: comment_get")
        //         }
        //     })
        // }

        const add_comment = (post_id) => {
            $.ajax({
                url: "http://127.0.0.1:3000/api/post/comment/add/",
                type: "post",
                data: {
                    user_id: store.state.user.id,
                    post_id: post_id,
                    comment: new_comment.value
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                success() {
                    new_comment.value = "";
                    get_posts();  //刷新 
                },
                error() {
                    console.log("error_message: comment_add")
                }
            })
        }

        const love_comment_level1 = (id, post_id, comment_id, level) => {
            $.ajax({
                url: "http://127.0.0.1:3000/api/post/comment/love/",
                type: "post",
                data: {
                    id: id,
                    user_id: store.state.user.id,
                    post_id: post_id,
                    comment_id: comment_id,  // null
                    level: level,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token
                },
                success() {
                    if(level == "1") {
                        for(let post of posts.value) {
                            if(post.id == post_id) {
                                for(let comment_level1 of post.comment_level1s) {
                                    if(comment_level1.id == id) {
                                        if(comment_level1.is_love === "yes") {
                                            comment_level1.is_love = "no"
                                            comment_level1.love_count--
                                        } else if(comment_level1.is_love === "no") {
                                            comment_level1.is_love = "yes"
                                            comment_level1.love_count++
                                        }
                                        break
                                    }
                                }
                            }
                        }
                    } else if(level == "2") {
                        for(let post of posts.value) {
                            if(post.id == post_id) {
                                for(let comment_level1 of post.comment_level1s) {
                                    for(let comment_level2 of comment_level1.comment_level2s) {
                                        if(comment_level2.id == id) {
                                            if(comment_level2.is_love === "yes") {
                                                comment_level2.is_love = "no";
                                                comment_level2.love_count--
                                            } else if(comment_level2.is_love === "no") {
                                                comment_level2.is_love = "yes"
                                                comment_level2.love_count++;
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                error() {
                    console.log("error_message: comment_love")
                }
            })
        }

        return {
            load,
            posts,
            new_post,
            new_comment,
            carousel_photos,
            add_post,
            like_post,
            star_post,
            add_comment,
            love_comment_level1,
        }
    }
}
</script>

<style scopde>
.add_post {
    float: right;
    border:#ffffff;
}

span.hover:hover{
    background-color: rgb(238, 238, 238);
    cursor:pointer;
}

.huifu {
    color:#757575;
    font-size: smaller;
    float: right;
}

.huifu:hover{
    color: #59caff;
    cursor:pointer;
}

.post {
    width: 52vw; 
    margin: 3vh auto;
    border-radius: 10px;
    box-shadow: 5px 5px 8px #07065e;
}

.photo {
    border-radius: 50%; 
    width: 7vh;
}

.infinite-list {
    height: 300px;
    padding: 0;
    margin: 0;
    list-style: none;
}

.infinite-list .infinite-list-item {
display: flex;
align-items: center;
justify-content: center;
height: 50px;
background: var(--el-color-primary-light-9);
margin: 10px;
color: var(--el-color-primary);
}

.infinite-list .infinite-list-item + .list-item {
margin-top: 10px;
}

</style>
