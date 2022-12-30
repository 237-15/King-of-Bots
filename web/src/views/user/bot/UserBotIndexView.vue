<!-- float-end: 按钮右对齐 -->

<template>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-body">
                        <img :src="$store.state.user.photo" alt="" style="width:100%"/>
                    </div>
                </div>
            </div>

            <div class="col-9">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-header">
                        <span style="font-size: 120%;">我的bot</span>
                        <button type="button" class="btn btn-primary float-end" data-bs-toggle="modal" data-bs-target="#add-bot-btn">创建</button>
                        
                        <!-- Modal-add -->
                        <div class="modal fade" id="add-bot-btn" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">
                            <div class="modal-dialog modal-xl">
                            <div class="modal-content">
                                <div class="modal-header">
                                <h5 class="modal-title">创建</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <!-- 表单 -->
                                    <div class="mb-3">
                                        <label for="add-bot-title" class="form-label">标题</label>
                                        <input v-model="botadd.title" type="email" class="form-control" id="add-bot-title" placeholder="请输入bot的标题">
                                    </div>
                                    <div class="mb-3">
                                        <label for="add-bot-description" class="form-label">简介</label>
                                        <textarea v-model="botadd.description" class="form-control" id="add-bot-description" rows="3" placeholder="请输入bot的简介"></textarea>
                                    </div>
                                    <div class="mb-3">
                                        <label for="add-bot-code" class="form-label">代码</label>
                                        <!-- ace编辑器 -->
                                        <VAceEditor
                                            v-model:value="botadd.content"
                                            @init="editorInit"
                                            lang="c_cpp"
                                            theme="textmate"
                                            style="height: 300px" />
                                        <!-- ace编辑器 -->
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <div class="error_message">{{ botadd.error_message }}</div>
                                <button type="button" class="btn btn-primary" @click="add_bot">提交</button>
                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                </div>
                            </div>
                            </div>
                        </div>
                        <!-- modal-add -->

                    </div>
                    <div class="card-body">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>天梯分</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="bot in bots" :key="bot.id">
                                    <td>{{ bot.title }}</td>
                                    <td>{{ bot.rating }}</td>
                                    <td>{{ bot.createTime }}</td>
                                    <td>
                                        <button type="button" class="btn btn-info" data-bs-toggle="modal" :data-bs-target="'#update-bot-modal-' + bot.id">修改</button>
                                        <button type="button" class="btn btn-danger" style="margin-left: 10px" data-bs-toggle="modal" :data-bs-target="'#remove-bot-modal' + bot.id">删除</button>

                                        <!-- Modal-update -->
                                        <div class="modal fade" :id="'update-bot-modal-' + bot.id" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1">
                                            <div class="modal-dialog modal-xl">
                                            <div class="modal-content">
                                                <div class="modal-header">
                                                <h5 class="modal-title">修改</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <!-- 表单 -->
                                                    <div class="mb-3">
                                                        <label for="add-bot-title" class="form-label">标题</label>
                                                        <input v-model="bot.title" type="email" class="form-control" id="add-bot-title">
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="add-bot-description" class="form-label">简介</label>
                                                        <textarea v-model="bot.description" class="form-control" id="add-bot-description" rows="3" placeholder="请输入Bot简介"></textarea>
                                                    </div>
                                                    <div class="mb-3">
                                                        <label for="add-bot-code" class="form-label">代码</label>
                                                        <!-- ace编辑器 -->
                                                        <VAceEditor
                                                            v-model:value="bot.content"
                                                            @init="editorInit"
                                                            lang="c_cpp"
                                                            theme="textmate"
                                                            style="height: 300px" />
                                                        <!-- ace编辑器 -->
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <div class="error_message">{{ bot.error_message }}</div>
                                                <button type="button" class="btn btn-primary" @click="update_bot(bot)">提交</button>
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                                </div>
                                            </div>
                                            </div>
                                        </div>
                                        <!-- modal-update -->

                                        <!-- modal-remove -->
                                        <div class="modal fade" :id="'remove-bot-modal' + bot.id" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
                                            <div class="modal-dialog modal-dialog-centered">
                                                <div class="modal-content">
                                                    <div class="modal-body">
                                                        <span style="font-size: 120%">您确定要删除此bot吗？</span>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <button type="button" class="btn btn-primary" @click="remove_bot(bot)">确定</button>
                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- modal-remove -->

                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, reactive } from 'vue'
import { useStore } from 'vuex'
import $ from 'jquery'
import { Modal } from 'bootstrap/dist/js/bootstrap'
import { VAceEditor } from 'vue3-ace-editor';  //编辑器
import ace from 'ace-builds';                  //编辑器

export default {
    components: {
        VAceEditor,
    },
    setup() {
        ace.config.set(  //编辑器
            "basePath", 
            "https://cdn.jsdelivr.net/npm/ace-builds@" + require('ace-builds').version + "/src-noconflict/")

        const store = new useStore();
        let bots = ref([]);  //定义了一个数组，存放所有的bot,用于展示

        const botadd = reactive({  //用户创建的bot信息
            title: "",
            description: "",
            content: "",
            error_message: "",
        })

        const refresh_bots = () => {  //更新bot信息函数
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/getlist/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    bots.value = resp;
                }
            })
        }

        refresh_bots();

        const add_bot = () => {
            botadd.error_message = "";  //将输入不合法的报错信息置空
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/add/",
                type: "post",
                data: {
                    title: botadd.title,
                    description: botadd.description,
                    content: botadd.content,
                },
                headers: {
                    authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if(resp.error_message === "success") {  // 如果成功，则刷新列表，即时更新信息
                        botadd.title = "";
                        botadd.description = "";
                        botadd.content = "";
                        Modal.getInstance("#add-bot-btn").hide();  //关闭模态框
                        refresh_bots();  
                    } else {
                        botadd.error_message = resp.error_message;
                    }
                    console.log(resp);
                },
                error(resp){
                    console.log(resp);
                }
            })
        }

        const update_bot = (bot) => {
            bot.error_message = "";  //将输入不合法的报错信息置空
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/update/",
                type: "post",
                data: {
                    bot_id: bot.id,
                    title: bot.title,
                    description: bot.description,
                    content: bot.content,
                },
                headers: {
                    authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if(resp.error_message === "success") {  // 如果成功，则刷新列表，即时更新信息
                        Modal.getInstance("#update-bot-modal-" + bot.id).hide();  //关闭模态框
                        refresh_bots();  
                    } else {
                        bot.error_message = resp.error_message;
                    }
                    console.log(resp);
                },
                error(resp){
                    console.log(resp);
                }
            })
        }

        const remove_bot = (bot) => {
            $.ajax({
                url: "http://127.0.0.1:3000/user/bot/remove/",
                type: "post",
                data: {
                    bot_id: bot.id,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        Modal.getInstance("#remove-bot-modal" + bot.id).hide();  //关闭模态框
                        refresh_bots();
                    }
                }
            })
        }

        return {
            bots,
            botadd,
            add_bot,
            update_bot,
            remove_bot,
        }
    }
}
</script>

<style>
div.error_message {
    color: red;
}
</style>