<template>
    <ContentField>
        <div class="title">排行榜</div>
        <table class="table table-striped table-hover">
            <thead>
                <tr>
                    <th>排名</th>
                    <th style="padding-left: 29vh;">玩家</th>
                    <th style="text-align: center;">天梯分</th>
                </tr>
            </thead>
            <tbody>
                <tr style="background-color: aquamarine;">
                    <td>
                        {{ my_ranking }}
                    </td>
                    <td style="padding-left: 25vh;">
                        <img :src="my.photo" class="record-user-photo" alt="" /> &nbsp;
                        <span class="record-user-username">{{ my.username }}</span>
                    </td>
                    <td style="text-align: center;">
                        {{ my.rating }}
                    </td>
                </tr>
                <tr v-for="user in users" :key="user.user.id">  
                    <td>
                        {{ user.ranking }}
                    </td>
                    <td style="padding-left: 25vh;">
                        <img :src="user.user.photo" class="record-user-photo" alt="" /> &nbsp;
                        <span class="record-user-username">{{ user.user.username }}</span>
                    </td>
                    <td style="text-align: center;">
                        {{ user.user.rating }}
                    </td>
                </tr>
            </tbody>
        </table>
        <nav aria-label="...">
            <ul class="pagination" style="float: right;">
                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Previous">
                        <span @click="click_page(-1)" aria-hidden="true" style="font-size: smaller;">第一页</span>
                    </a>
                </li>

                <li class="page-item" >
                    <a class="page-link" @click="click_page(-2)" href="#"><span style="font-size: smaller;">&lt;</span></a>
                </li>

                <li class="page-item" v-for="page of pages" :key="page.number">
                    <a :class="'page-link ' + page.is_active"  @click="click_page(page.number)" href="#">{{ page.number }}</a>
                </li>

                <li class="page-item" >
                    <a class="page-link" @click="click_page(-3)" href="#"><span style="font-size: smaller;">&gt;</span></a>
                </li>

                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Next">
                        <span @click="click_page(-4)" aria-hidden="true" style="font-size: smaller;">最后一页</span>
                    </a>
                </li>
            </ul>
        </nav>
    </ContentField>
</template>

<script>
import ContentField from '../../components/ContentField.vue'
import { useStore } from 'vuex';
import $ from 'jquery'
import { ref } from 'vue';

export default {
    components: {
        ContentField
    },
    setup() {
        localStorage.setItem("current_webPage_name", "ranklist_index")

        const store = useStore()
        let current_page = 1  //当前页数
        let users = ref([]);  //当前页玩家的详细信息
        let user_count = 0;  //玩家总数
        let pages = ref([]);  //可展示的页数数组   如可展示出4、5、6、7、8页，高亮在第6页
        let my = ref();  //玩家自己的信息
        let my_ranking = ref(1);  //玩家的排名

        const click_page = page => {
            let max_page = parseInt(Math.ceil((user_count / 8)))  //向上取整
            if(page === -1) current_page = 1
            else if(page === -2) current_page -= 1
            else if(page === -3) current_page += 1
            else if(page === -4) current_page = max_page
            else current_page = page

            if(current_page >= 1 && current_page <= max_page) {
                pull_page(current_page)
            }
        }

        const update_pages = () => {  //更新可展示的页数数组
            let max_page = parseInt(Math.ceil((user_count / 8)))  //向上取整

            let new_pages = []
            for(let i = current_page - 2; i <= current_page + 2; i++){
                if(i >= 1 && i <= max_page) {
                    new_pages.push({
                        number: i,
                        is_active: i === current_page ? "active" : ""
                    })
                }
            }
            pages.value = new_pages
        }

        const pull_page = page => {
            page = current_page
            users.value = []
            my.value = []

            $.ajax({
                url: "http://127.0.0.1:3000/ranklist/getlist/",
                type: "get",
                data: {
                    page,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    for(let i in resp.users) {
                        users.value.push({
                            user: resp.users[i],
                            ranking: parseInt(i) + 1
                        })
                    }
                    user_count = resp.user_count;
                    for(const user of users.value) {
                        if(user.user.id == store.state.user.id) {
                            my.value = user.user
                            my_ranking.value = user.ranking
                            break
                        }
                    }
                    update_pages();
                },
                error(resp) {
                    console.log(resp)
                }
            })
        }

        pull_page(current_page)

        return {
            users,
            pages,
            click_page,
            my,
            my_ranking,
        }
    }
}
</script>

<style scoped>
div.title {
    font-size: 40px;
    text-align: center;
    font-weight: 600;
    color:deeppink;
}
img.record-user-photo {
    width: 4vh;
    border-radius: 50%;
}
</style>