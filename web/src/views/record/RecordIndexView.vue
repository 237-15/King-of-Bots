<template>
    <ContentField>
        <div class="title">对局列表</div>
        <table class="table table-striped table-hover"  style="text-align: center;">
            <thead>
                <tr>
                    <th>A</th>
                    <th>B</th>
                    <th>对战结果</th>
                    <th>对战时间</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <!-- record包含record也就是游戏信息和两名玩家的用户名和头像 -->
                <tr v-for="record in records" :key="record.record.id">  
                    <td>
                        <img :src="record.photoA" class="record-user-photo" alt="" /> &nbsp;
                        <span class="record-user-username">{{ record.usernameA }}</span>
                    </td>
                    <td>
                        <img :src="record.photoB" class="record-user-photo" alt="" /> &nbsp;
                        <span class="record-user-username">{{ record.usernameB }}</span>
                    </td>
                    <td>
                        <span class="record-result">{{ record.result }}</span>
                    </td>
                    <td>
                        <span class="record-record-createTime">{{ record.record.createTime }}</span>
                    </td>
                    <td>
                        <button @click="open_record_content(record.record.id)" type="button" class="btn btn-info">查看</button>
                    </td>
                </tr>
            </tbody>
        </table>
        <nav aria-label="...">
            <ul class="pagination" style="float: right;">
                <li class="page-item" >
                    <a class="page-link" @click="click_page(-5)" href="#"><span style="font-size: small;">第一页</span></a>
                </li>

                <li class="page-item">
                    <a class="page-link" href="#" aria-label="Previous">
                        <span @click="click_page(-1)" aria-hidden="true">&laquo;</span>
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
                        <span @click="click_page(-4)" aria-hidden="true">&raquo;</span>
                    </a>
                </li>

                <li class="page-item" >
                    <a class="page-link" @click="click_page(-6)" href="#"><span style="font-size: small;">最后一页</span></a>
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
import router from '@/router';

export default {
    components: {
        ContentField
    },
    setup() {
        localStorage.setItem("current_webPage_name", "record_index")

        const store = useStore()
        let current_page = 1  //当前页数
        let records = ref([]);  //当前页对局的详细信息
        let record_count = 0;  //对局总场数
        let pages = ref([]);  //可展示的页数数组   如可展示出4、5、6、7、8页，高亮在第6页

        const click_page = page => {
            let max_page = parseInt(Math.ceil((record_count / 8)))  //向上取整
            if(page === -1) {
                current_page -= 5
                if(current_page < 1) current_page = 1
            }
            else if(page === -2) current_page -= 1
            else if(page === -3) current_page += 1
            else if(page === -4) {
                current_page += 5
                if(current_page > max_page) current_page = max_page
            }
            else if(page === -5) current_page = 1
            else if(page === -6) current_page = max_page
            else current_page = page

            if(current_page >= 1 && current_page <= max_page) {
                pull_page(current_page)
            }
        }

        const update_pages = () => {  //更新可展示的页数数组
            let max_page = parseInt(Math.ceil((record_count / 8)))  //向上取整

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

            $.ajax({
                url: "http://127.0.0.1:3000/record/getlist/",
                type: "get",
                data: {
                    page,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    records.value = resp.records
                    record_count = resp.record_count;
                    update_pages();
                },
                error(resp) {
                    console.log(resp)
                }
            })
        }

        pull_page(current_page)

        const stringTo2D = map => {
            let g = []
            for(let i = 0, k = 0; i < 13; i++){
                let line = [];
                for(let j = 0; j < 14; j++, k++){
                    if(map[k] === '0') line.push(0)
                    else line.push(1)
                }
                g.push(line)
            }
            return g
        }

        const open_record_content = recordId => {  
            // let left = 0, right = record_count - 1;  //左右指针，二分查找

            // while(left <= right) {
            //     let mid = left + (right - left) / 2;  //防止left + right过大，超出范围
            //     if(records[left].record.id == mid) {
            //         store.commit("updateIsRecord", true)
            //         store.commit("updateGame", {
                        // map: stringTo2D(records[left].record.map),
                        // idA: records[left].record.aId,
                        // sxA: records[left].record.aSx,
                        // syA: records[left].record.asy,
                        // idB: records[left].record.bId,
                        // sxB: records[left].record.bSx,
                        // syB: records[left].record.bsy,
            //         })
            //         store.commit("updateSteps", {
                        // stepsA: records[left].record.asteps,
                        // stepsB: records[left].record.bsteps
            //         })
            //         store.commit("updateRecordLoser", {
            //             record_loser: records[left].record.loser
            //         })
            //         router.push({
            //             name: "record_content",
            //             params: {
            //                 recordId,
            //             }
            //         })
            //         break;
            //     } else if(records[left].record.id < mid){
            //         left = mid + 1
            //     } else {
            //         right = mid - 1
            //     }
            // }

            for (const record of records.value) {
                if (record.record.id === recordId) {
                    store.commit("updateIsRecord", true);
                    store.commit("updateGame", {
                        map: stringTo2D(record.record.map),
                        idA: record.record.aId,
                        sxA: record.record.aSx,
                        syA: record.record.asy,
                        idB: record.record.bId,
                        sxB: record.record.bSx,
                        syB: record.record.bsy,
                    });
                    store.commit("updateSteps", {
                        stepsA: record.record.asteps,
                        stepsB: record.record.bsteps
                    });
                    store.commit("updateRecordLoser", record.record.loser);
                    router.push({
                        name: "record_content",
                        params: {
                            recordId
                        }
                    })
                    break;
                }
            }
        }

        return {
            records,
            open_record_content,
            pages,
            click_page,
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