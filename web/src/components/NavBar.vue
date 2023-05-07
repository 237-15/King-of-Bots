<template>
    <nav class="navbar navbar-expand-lg" style="background-color: #e3f2fd;">
        <div class="container">
        <router-link class="navbar-brand" :to="{name: 'home'}">
            <img style="width: 3vh; height: 3vh;" src="@/assets/images/favicon.png" />
            <span style="font-size: large;"> 休闲之地</span>
        </router-link>
        <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <router-link :class="route_name == 'pk_index' ? 'nav-link active' : 'nav-link'" :to="{name: 'pk_index'}">
                        <img style="width: 5vh; height: 5vh;" src="@/assets/images/对战.png" title="对战"/>
                    </router-link>
                </li>
                <li class="nav-item">
                    <router-link :class="route_name == 'record_index' ? 'nav-link active' : 'nav-link'" :to="{name: 'record_index'}">
                        <svg class="bi bi-collection-play-fill icon" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd" d="M1.5 14.5A1.5 1.5 0 0 1 0 13V6a1.5 1.5 0 0 1 1.5-1.5h13A1.5 1.5 0 0 1 16 6v7a1.5 1.5 0 0 1-1.5 1.5h-13zm5.265-7.924A.5.5 0 0 0 6 7v5a.5.5 0 0 0 .765.424l4-2.5a.5.5 0 0 0 0-.848l-4-2.5zM2 3a.5.5 0 0 0 .5.5h11a.5.5 0 0 0 0-1h-11A.5.5 0 0 0 2 3zm2-2a.5.5 0 0 0 .5.5h7a.5.5 0 0 0 0-1h-7A.5.5 0 0 0 4 1z"/>
                        </svg>
                    </router-link>
                </li>
                <li class="nav-item">
                    <router-link :class="route_name == 'ranklist_index' ? 'nav-link active' : 'nav-link'" :to="{name: 'ranklist_index'}">
                        <svg class="bi bi-trophy icon" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path d="M3 1h10c-.495 3.467-.5 10-5 10S3.495 4.467 3 1zm0 15a1 1 0 0 1 1-1h8a1 1 0 0 1 1 1H3zm2-1a1 1 0 0 1 1-1h4a1 1 0 0 1 1 1H5z"/>
                            <path fill-rule="evenodd" d="M12.5 3a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-3 2a3 3 0 1 1 6 0 3 3 0 0 1-6 0zm-6-2a2 2 0 1 0 0 4 2 2 0 0 0 0-4zm-3 2a3 3 0 1 1 6 0 3 3 0 0 1-6 0z"/>
                            <path d="M7 10h2v4H7v-4z"/>
                            <path d="M10 11c0 .552-.895 1-2 1s-2-.448-2-1 .895-1 2-1 2 .448 2 1z"/>
                        </svg>
                    </router-link>
                </li>
                <li class="nav-item">
                    <router-link :class="route_name == 'forum_index' ? 'nav-link active' : 'nav-link'" :to="{name: 'forum_index'}">
                        <svg class="bi bi-house-fill icon" width="1em" height="1em" viewBox="0 0 16 16" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
                            <path fill-rule="evenodd" d="M8 3.293l6 6V13.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5V9.293l6-6zm5-.793V6l-2-2V2.5a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5z"/>
                            <path fill-rule="evenodd" d="M7.293 1.5a1 1 0 0 1 1.414 0l6.647 6.646a.5.5 0 0 1-.708.708L8 2.207 1.354 8.854a.5.5 0 1 1-.708-.708L7.293 1.5z"/>
                        </svg>
                    </router-link>
                </li>
            </ul>
            <ul class="navbar-nav" v-if="$store.state.user.is_login">
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        <img  :src="$store.state.user.photo" alt="" class="user-photo"/>
                        {{ $store.state.user.username }}
                    </a>
                    <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                        <li>
                            <router-link class="dropdown-item" :to="{name: 'user_bot_index'}">我的伙伴</router-link>
                        </li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#" @click="logout">退出</a></li>
                    </ul>
                </li>
            </ul>
            <ul class="navbar-nav" v-else>
                <li class="nav-item">
                    <router-link class="nav-link" :to="{name: 'user_account_login' }" role="button">
                    登录
                    </router-link>
                </li>
                <li class="nav-item">
                    <router-link class="nav-link" :to="{name: 'user_account_register'}" role="button">
                    注册
                    </router-link>
                </li>
            </ul>
        </div>
        </div>
    </nav>
</template>
    
<script>
import { useRoute } from 'vue-router'
import { computed } from 'vue'
import { useStore } from 'vuex';
    
export default {
    setup() {
        const store = useStore();
        const route = useRoute();
        let route_name = computed(() => route.name)

        const logout = () => {
            store.dispatch("logout");
            store.state.user.button = true;
            localStorage.setItem("current_webPage_name", "user_account_login");
        }

        return {
            route_name,
            logout
        }
    }
}
</script>

<style scoped>
img.user-photo {
    border-radius: 50%;
    width: 4vh;
}

.icon {
    width: 3vh;
    height: 3vh;
    color:#272636;
    margin-top:7px;
    margin-right: 5px;
}
</style>
    