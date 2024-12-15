基于Vue3+SpringBoot3+Sa-Token实现前后端分离权限系统

哔哩哔哩：https://www.bilibili.com/video/BV1DF2mY3E5o/

## 01. 课程介绍

### 1、课程适合对象

```
有vue2、vue组合api基础知识，TypeScript、Spring Boot、sa-token基础知识的小伙伴
```

### 2、课程涉及技术

```
1、CSS3
2、TypeScript
3、Vue3.2
4、Pinia
5、Vue Router4.x
6、Vit2.x
7、Element Plus
8、SpringBoot3
9、jwt、token
10、sa-token
```

### 3、课程收获

```
1、掌握vue3语法糖的使用
2、掌握vue3中组合api的使用
3、掌握组件中业务逻辑抽离的方法
4、掌握TypeScript在vue3中的使用
5、掌握动态菜单、动态路由、按钮权限的实现
6、vue3中全局挂载使用方式
7、vue3父子组件的使用
8、vue3中echarts的使用
9、token、权限验证
10、掌握Pinia在实际项目中的使用方式
11、Icons图标动态生成
12、掌握springboot开发前后端分离项目的能力
13、掌握从0到1开发完整前后端分离项目的能力
14、掌握sa-token在前后端分离项目中的使用
```



## 02、创建前端项目

**兼容性注意：**

```
Vite2官网：https://vitejs.cn/vite5-cn/

Vite 需要 Node.js 版本 >= 12.0.0。建议使用16版本以上。

node -v #查看node版本号
```

### 1、新建项目

创建存放项目目录，cmd进入到存放目录，执行如下命令创建项目

```shell
npm init vite@latest //Vite2
// OR
npm create vite@latest //Vite4
```

![10001.png](https://s2.loli.net/2024/11/17/w81JrvfDtQxmdWc.png)

### 2、输入项目名选择Vue框架

![10002.png](https://s2.loli.net/2024/11/17/6Jna4CMqlLp1IvK.png)

### 3、选择TypeScript

![10003.png](https://s2.loli.net/2024/11/17/wZQlVNhiIrbdESY.png)

### 4、运行项目

```shell
cd project-web
npm install
npm run dev
```



## 03、路由安装

### 1、项目配置别名

#### 1.1、vite.config.ts配置文件，添加如下配置

```js
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
    plugins: [
    vue()
    ],
    server: {
        host: '0.0.0.0',
        port: 8081,
        strictPort: true,
        open: true,
    }
})
```

#### 1.2、vite配置别名

```shell
npm install @types/node --save-dev
```

```js
import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import { resolve } from 'path'

// https://vite.dev/config/
export default defineConfig({
    plugins: [
    vue()
    ],
    server: {
        host: '0.0.0.0', // 解决“vite use`--host`to expose”
        port: 8081,
        strictPort: true,
        open: true,
    },
    resolve: {
        alias: [{
            find: '@',
            replacement: resolve(__dirname, 'src')
        }]
    }
})
```

#### 1.3、tsconfig.json里面添加如下代码

添加baseUrl和paths

```json
{
  "files": [],
  "references": [
    { "path": "./tsconfig.app.json" },
    { "path": "./tsconfig.node.json" }
  ],
  "compilerOptions": {
    "baseUrl": ".", // paths路径解析起点
    "paths": {
      "@/*": ["src/*"] // 别名路径配置
    }
  }
}
```

#### 1.4、如果别名配置还报错的，src下新建vite-env.d.ts

```typescript
/// <reference types="vite/client" />
declare module '*.vue' {
  import type { DefineComponent } from 'vue'
  const component: DefineComponent<{}, {}, any>
  export default component
}
```



### 2、安装路由

官网：https://router.vuejs.org/zh/guide/

#### 2.1、安装依赖

```shell
npm install vue-router@4
```

#### 2.2、新建路由文件

src目录下新建router文件夹，然后新建index.ts文件

```typescript
//vue2-router
const router = new VueRouter({
    mode:history
    ...
})


//vue-next-router
import {createRouter,createwebHistory} from 'vue-router'
const router = createRouter ({
	history:createwebHistory(),
    ...
})
```

```typescript
import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import HelloWorld from '@/components/HelloWorld.vue'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'HelloWorld',
        component: HelloWorld
    },
    {
        path: '/home',
        name: 'Home',
        component: HelloWorld
    }
]


const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
```

#### 2.3、main.ts里面引入路由

```js
import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router'

const app = createApp(App)
app.use(router)
app.mount('#app')
```

#### 2.4、修改App.vue为如下所示

注：vue3+vite项目引入SCSS及使用SCSS全局变量：https://blog.csdn.net/m0_61486963/article/details/127494962

```vue
<template>
  <router-view />
</template>

<style lang="scss">

</style>
```



## 04、安装 element plus

官网：https://element-plus.org/zh-CN/

国内镜像站点：https://cn.element-plus.org/zh-CN/

### 1、安装element plus

```shell
# 安装element plus
npm install element-plus --save
# 安装图标库
npm install @element-plus/icons-vue
```

### 2、main.ts引入

![10004.png](https://s2.loli.net/2024/11/17/XvjHbuocwTYlKLZ.png)



## 05、安装Pinia

### 1、相关插件

```
1、禁用Vetur
2、安装Vue Language Features(Volar)
3、安装Element UI Snippets
4、Element UI VSCode Snippets
5、Vue VSCode Snippets
```



### 2、Pinia安装

官网：https://pinia.vuejs.org/zh/

#### 2.1、安装依赖

```shell
npm install pinia
```

#### 2.2、main.ts引入pinia

```typescript
// 引入pinia构造函数
import { createPinia } from 'pinia'
// 实例化 pinia
const pinia = createPinia()
const app = createApp(App)
app.use(pinia)
app.mount('#app')
```

### 3、使用pinia

#### 3.1、src下新建store

然后store下新建test目录，并新建index.ts文件

```typescript
import { defineStore } from 'pinia'

// 定义Store
export const useTestStore = defineStore('testStore', {
    state: () => ({
        count: 0,
        name: 'Eduardo'
    }),
    getters: {
        getCount(state) { 
            return state.count
        }
    },
    actions: {
        setCount(count: number) {
            console.log(count)
            this.count = count
        }
    }
})
```

#### 3.2、Vue组件中使用

```vue
<template>
    <div>
        <h1>hello world</h1>
        <div>
            <el-button type="primary" @click="add">新增</el-button>
            <div>
                {{count}}
                {{ testStore.name }}
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { useTestStore } from '@/store/test'
// import { useTestStore } from '@/store/test/index'

// import { useTestStore }  from '@/store/test/index'

const testStore = useTestStore()

const count = computed(() => testStore.getCount)

const add = () => {
    // 方式一
    // testStore.count++
    // 方式二（推荐）
    testStore.setCount(++testStore.count)
    // 方式三
    // testStore.$patch(state => {
    //     state.count++
    // })
    // 方式四
    // testStore.$patch({
    //     count: ++testStore.count
    // })
 }

</script>
```



## 06、主界面布局

布局容器：https://element-plus.org/zh-CN/component/container.html

### 1、安装Sass

```
npm install sass --save-dev
```

### 2、找到index.html添加如下style里面的样式

```html
<!doctype html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <link rel="icon" type="image/svg+xml" href="/vite.svg" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Vite + Vue + TS</title>
  </head>
  <body>
    <div id="app"></div>
    <script type="module" src="/src/main.ts"></script>
  </body>
</html>
<style>
    html,body,#app {
        padding: 0px;
        margin: 0px;
        height: 100%;
        box-sizing: border-box;
    }
</style>
```

### 3、App.vue修改为如下所示

```vue
<template>
  <router-view />
</template>

<style lang="scss">
</style>

```

### 4、src目录下新建layout目录，并新建Index.vue主页面组件

```vue
<template>
    <el-container class="mycontainer">
        <el-aside width="200px" class="leftmenu">Aside</el-aside>
        <el-container>
            <el-header class="header">Header</el-header>
            <el-main class="main">Main</el-main>
        </el-container>
    </el-container>
</template>

<script setup lang="ts">

</script>

<style lang="scss" scoped>
.mycontainer {
    height: 100%;

    .leftmenu {
        background-color: rebeccapurple;
    }

    .header {
        background-color: brown;
    }

    .main {
        background-color: cadetblue;
    }
}
</style>
```

### 5、在router中引入主页面组件

```typescript
import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/index.vue'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/home',
        name: 'Home',
        component: Layout
    }
]


const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
```

### 6、启动项目

```shell
npm run dev
```

访问地址：http://localhost:8081/#/home



## 07、左侧导航菜单

菜单：https://element-plus.org/zh-CN/component/menu.html

### 1、layout下新建header目录，然后新建Header.vue组件

```vue
<template>
    <div>
        头部
    </div>
</template>

<script setup lang="ts">

</script>

<style lang="scss" scoped></style>
```

### 2、layout下新建menu目录，然后新建MenuBar.vue和Menultem.vue组件

MenuBar.vue

```vue
<template>
    <el-menu
             default-active="2"
             class="el-menu-vertical-demo"
             :collapse="isCollapse"
             unique-opened
             background-color="#304156">
        <Menultem :menuList="menuList" />
    </el-menu>
</template>

<script setup lang="ts">
import Menultem from '@/layout/menu/Menultem.vue';

const isCollapse = ref(false)
// 菜单数据
let menuList = reactive([{
    path: '/system',
    component: "Layout",
    name: "system",
    meta: {
        title: "系统管理",
        icon: 'Setting',
        roles: ['sys:admin']
    },
    children: [{
        path: '/userList',
        component: '/system/User/UserList',
        name: 'userList',
        meta: {
            title: '用户管理',
            icon: 'UserFilled',
            roles: ['sys:user']
        }
    }, {
        path: 'roleList',
        component: '/system/Role/RoleList',
        name: 'roleList',
        meta: {
            title: '角色管理',
            icon: 'Wallet',
            roles: ['sys:role']
        }
    }, {
        path: 'menuList',
        component: '/system/Role/MenuList',
        name: 'menuList',
        meta: {
            title: '菜单管理',
            icon: 'Menu',
            roles: ['sys:menu']
        }
    }]
}, {
    path: '/goodsRoot',
    component: "Layout",
    name: "goodsRoot",
    meta: {
        title: "商品管理",
        icon: 'Setting',
        roles: ['sys:goodsRoot']
    },
    children: [{
        path: "/category",
        component: "/goods/Category",
        name: "category",
        meta: {
            title: "物资类型",
            icon: "UserFilled",
            roles: ["sys:category"],
        }
    }, {
        path: "/goodsList",
        component: "/goods/GoodsList",
        name: "goodsList",
        meta: {
            title: "商品信息",
            icon: "Wallet",
            roles: ["sys:goodsList"],
        }
    }]
}])
const handleOpen = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
</script>

<style lang="scss" scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 230px;
    min-height: 400px;
}

.el-menu {
    border-right: none;
}

:deep(.el-sub-menu .el-sub-menu__title) {
    color: #f4f4f5 !important;
}

:deep(.el-menu .el-menu-item) {
    color: #bfcbd9;
}

:deep(.el-menu-item.is-active) {
    color: #409eff important;
}

:deep(.is-opened .el-menu-item) {
    background-color: #1f2d3d important;
}

:deep(.el-menu-item:hover) {
    background-color: #001528 !important;
}
</style>
```

Menultem.vue

```vue
<template>
    <template v-for="menu in menuList" :key="menu.path">
        <!-- 有下级菜单 -->
        <el-sub-menu v-if="menu.children && menu.children.length" :index="menu.path">
            <template #title>
                <el-icon>
                    <component :is="menu.meta.icon" />
                </el-icon>
                <span>{{ menu.meta.title }}</span>
            </template>
            <!-- 递归调用 -->
            <Menultem :menuList="menu.children" />
        </el-sub-menu>
        <!-- 没有下级菜单 -->
        <el-menu-item v-else :index="menu.path" style="color: #f4f4f5;">
            <el-icon>
                <component :is="menu.meta.icon" />
            </el-icon>
            <template #title>{{ menu.meta.title }}</template>
        </el-menu-item>
    </template>
</template>

<script setup lang="ts">
// 通过defineProps接收父组件传递的数据
defineProps(['menuList'])
</script>

<style lang="scss" scoped></style>
```

### 3、layout/Index.vue

```vue
<template>
    <el-container class="layout">
        <el-aside width="auto" class="aside">
            <MenuBar />
        </el-aside>
        <el-container>
            <el-header class="header">
                <Header />
            </el-header>
            <el-main class="main">Main</el-main>
        </el-container>
    </el-container>
</template>

<script setup lang="ts">
import Header from '@/layout/header/Header.vue';
import MenuBar from '@/layout/menu/MenuBar.vue';

</script>

<style lang="scss" scoped>
.layout {
    height: 100%;

    .aside {
        background-color: #304156;
    }

    .header {
        background-color: #009688;
    }

    .main {
        background-color: darkgoldenrod;
    }
}
</style>
```



## 08、菜单Logo

### 1、项目assets里面加入Logo

### 2、layout/menu下新建MenuLogo.vue组件

```vue
<template>
    <div class="logo">
        <img :src="Logo">
        <span class="logo-title">{{ title }}</span>
    </div>
</template>

<script setup lang="ts">
import Logo from '@/assets/logo.png'
const title = ref('通用后台权限系统')

</script>

<style lang="scss" scoped>
.logo {
    display: flex;
    width: 100%;
    height: 60px;
    background-color: #2b2f3a;
    text-align: center;
    cursor: pointer;
    align-items: center;

    img {
        width: 36px;
        height: 36px;
        margin-left: 5px;
        margin-right: 5px;
    }

    .logo-title {
        color: #fff;
        font-weight: 800;
        line-height: 60px;
        font-size: 22px;
        font-family: FangSong;
    }
}
</style>
```

### 3、MenuBar.vue里面引入

```vue
<template>
    <MenuLogo />
    <el-menu
             default-active="2"
             class="el-menu-vertical-demo"
             :collapse="isCollapse"
             unique-opened
             background-color="#304156">
        <Menultem :menuList="menuList" />
    </el-menu>
</template>

<script setup lang="ts">
import MenuLogo from '@/layout/menu/MenuLogo.vue';
import Menultem from '@/layout/menu/Menultem.vue';

const isCollapse = ref(false)
// 菜单数据
let menuList = reactive([{
    path: '/system',
    component: "Layout",
    name: "system",
    meta: {
        title: "系统管理",
        icon: 'Setting',
        roles: ['sys:admin']
    },
    children: [{
        path: '/userList',
        component: '/system/User/UserList',
        name: 'userList',
        meta: {
            title: '用户管理',
            icon: 'UserFilled',
            roles: ['sys:user']
        }
    }, {
        path: 'roleList',
        component: '/system/Role/RoleList',
        name: 'roleList',
        meta: {
            title: '角色管理',
            icon: 'Wallet',
            roles: ['sys:role']
        }
    }, {
        path: 'menuList',
        component: '/system/Role/MenuList',
        name: 'menuList',
        meta: {
            title: '菜单管理',
            icon: 'Menu',
            roles: ['sys:menu']
        }
    }]
}, {
    path: '/goodsRoot',
    component: "Layout",
    name: "goodsRoot",
    meta: {
        title: "商品管理",
        icon: 'Setting',
        roles: ['sys:goodsRoot']
    },
    children: [{
        path: "/category",
        component: "/goods/Category",
        name: "category",
        meta: {
            title: "物资类型",
            icon: "UserFilled",
            roles: ["sys:category"],
        }
    }, {
        path: "/goodsList",
        component: "/goods/GoodsList",
        name: "goodsList",
        meta: {
            title: "商品信息",
            icon: "Wallet",
            roles: ["sys:goodsList"],
        }
    }]
}])
const handleOpen = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
const handleClose = (key: string, keyPath: string[]) => {
    console.log(key, keyPath)
}
</script>

<style lang="scss" scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 230px;
    min-height: 400px;
}

.el-menu {
    border-right: none;
}

:deep(.el-sub-menu .el-sub-menu__title) {
    color: #f4f4f5 !important;
}

:deep(.el-menu .el-menu-item) {
    color: #bfcbd9;
}

:deep(.el-menu-item.is-active) {
    color: #409eff important;
}

:deep(.is-opened .el-menu-item) {
    background-color: #1f2d3d important;
}

:deep(.el-menu-item:hover) {
    background-color: #001528 !important;
}
</style>
```



## 09、路由配置与页面创建

Vue-Router官网：https://router.vuejs.org/zh/

```js
由于我们在setup里面没有访问this,所以我们不能再直接访问this.$router或this.$route;我们使用useRouter和useRoute替代；
const router = useRouter() -> this.router
const route = useRoute() -> this.route
```

### 1、router/index.ts路由修改为如下

```js
import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/index.vue'

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        component: Layout,
        redirect: '/dashboard',
        children: [{
            path: '/dashboard',
            name: 'Dashboard',
            component: () => import('@/views/dashboard/index.vue'),
            meta: {
                title: '首页',
                icon: '#icondashboard'
            }
        }]
    },
    {
        path: '/system',
        component: Layout,
        name: "system",
        meta: {
            title: "系统管理",
            icon: 'Setting',
            roles: ['sys:admin']
        },
        children: [{
            path: '/userList',
            component: () => import('@/views/system/User/UserList.vue'),
            name: 'userList',
            meta: {
                title: '用户管理',
                icon: 'UserFilled',
                roles: ['sys:user']
            }
        }, {
            path: 'roleList',
            component:  () => import('@/views/system/Role/RoleList.vue'),
            name: 'roleList',
            meta: {
                title: '角色管理',
                icon: 'Wallet',
                roles: ['sys:role']
            }
        }, {
            path: 'menuList',
            component: () => import('@/views/system/Menu/MenuList.vue'),
            name: 'menuList',
            meta: {
                title: '菜单管理',
                icon: 'Menu',
                roles: ['sys:menu']
            }
        }]
    }, {
        path: '/goodsRoot',
        component: Layout,
        name: "goodsRoot",
        meta: {
            title: "商品管理",
            icon: 'Setting',
            roles: ['sys:goodsRoot']
        },
        children: [{
            path: "/category",
            component: () => import('@/views/category/CategoryList.vue'),
            name: "category",
            meta: {
                title: "商品类型",
                icon: "UserFilled",
                roles: ["sys:category"],
            }
        }, {
            path: "/goodsList",
            component: () => import('@/views/goods/GoodsList.vue'),
            name: "goodsList",
            meta: {
                title: "商品信息",
                icon: "Wallet",
                roles: ["sys:goodsList"],
            }
        }]
    }
]


const router = createRouter({
    history: createWebHashHistory(),
    routes
})

export default router
```

### 2、创建路由对应的页面

### 3、在MenuBar.vue组件的el-menu添加router属性

> router:是否启用 vue-router 模式。启用该模式会在激活导航时以 index 作为 path 进行路由跳转。

```vue
<template>
    <MenuLogo />
    <el-menu
             default-active="2"
             class="el-menu-vertical-demo"
             :collapse="isCollapse"
             unique-opened
             background-color="#304156"
             router>
        <Menultem :menuList="menuList" />
    </el-menu>
</template>
```

### 4、src/layout/Index.vue添加路由视图

```vue
 <el-main class="main">
 	<router-view />
 </el-main>
```

### 5、MenuBar.vue设置当前激活的菜单default-active

在MenuBar.vue添加如下代码

```vue
const route = useRoute()
// 获取激活的菜单
const activeIndex = computed(() => route.path)
```

```vue
<template>
    <MenuLogo />
    <el-menu
             :default-active="activeIndex"
             class="el-menu-vertical-demo"
             :collapse="isCollapse"
             unique-opened
             background-color="#304156"
             router>
        <Menultem :menuList="menuList" />
    </el-menu>
</template>
```



## 10、菜单折叠收起

菜单：https://element-plus.org/zh-CN/component/menu.html

### 1、store下新建menu目录，然后新建index.ts

```typescript
import { defineStore } from 'pinia'

// 定义Store
export const useMenuStore = defineStore('menuStore', {
    state: () => ({
        collapse: false
    }),
    getters: {
        getCollapse(state) { 
            return state.collapse
        }
    },
    actions: {
        setCollapse(collapse: boolean) { 
            this.collapse = collapse
        }
    }
})
```

### 2、layout/header下新建Collapse.vue组件

```vue
<template>
    <el-icon class="icons" color="#fff" size="28" @click="changeCollapse">
        <component :is="status ? 'Expand' : 'Fold'" />
    </el-icon>
</template>

<script setup lang="ts">
import { useMenuStore } from '@/store/menu'
const store = useMenuStore()

const status = computed(() => store.getCollapse)

const changeCollapse = () => {
    store.setCollapse(!store.getCollapse)
}
</script>

<style scoped>
.icons {
    cursor: pointer;
}
</style>
```

### 3、Header.vue组件使用

```vue
<template>
    <div>
        <Collapse />
    </div>
</template>

<script setup lang="ts">
import Collapse from './Collapse.vue';
</script>

<style lang="scss" scoped></style>
```



### 4、layout下的Index.vue的header样式修改为如下

```css
.header {
    display: flex;
    align-items: center;
    background-color: #009688;
}
```

### 5、MenuBar.vue组件的el-menu添加：collapse属性

```vue
<template>
    <MenuLogo />
    <el-menu
             :default-active="$route.path"
             class="el-menu-vertical-demo"
             :collapse="menuStore.getCollapse"
             unique-opened
             background-color="#304156"
             router>
        <Menultem :menuList="menuList" />
    </el-menu>
</template>

<script setup lang="ts">
import MenuLogo from '@/layout/menu/MenuLogo.vue';
import Menultem from '@/layout/menu/Menultem.vue';

import { useMenuStore } from '@/store/menu'

const menuStore = useMenuStore()

// 方式一
// const route = useRoute()
// // 获取激活的菜单
// const defaultActive = computed(() => {
//     const { path } = route
//     console.log(path)
//     return path
// })

const isCollapse = ref(false)
// 菜单数据
let menuList = reactive([{
    path: '/system',
    component: "Layout",
    name: "system",
    meta: {
        title: "系统管理",
        icon: 'Setting',
        roles: ['sys:admin']
    },
    children: [{
        path: '/userList',
        component: '/system/User/UserList',
        name: 'userList',
        meta: {
            title: '用户管理',
            icon: 'UserFilled',
            roles: ['sys:user']
        }
    }, {
        path: '/roleList',
        component: '/system/Role/RoleList',
        name: 'roleList',
        meta: {
            title: '角色管理',
            icon: 'Wallet',
            roles: ['sys:role']
        }
    }, {
        path: '/menuList',
        component: '/system/Menu/MenuList',
        name: 'menuList',
        meta: {
            title: '菜单管理',
            icon: 'Menu',
            roles: ['sys:menu']
        }
    }]
}, {
    path: '/goodsRoot',
    component: "Layout",
    name: "goodsRoot",
    meta: {
        title: "商品管理",
        icon: 'Setting',
        roles: ['sys:goodsRoot']
    },
    children: [{
        path: "/category",
        component: "/goods/Category",
        name: "category",
        meta: {
            title: "商品类型",
            icon: "UserFilled",
            roles: ["sys:category"],
        }
    }, {
        path: "/goodsList",
        component: "/goods/GoodsList",
        name: "goodsList",
        meta: {
            title: "商品信息",
            icon: "Wallet",
            roles: ["sys:goodsList"],
        }
    }]
}])
</script>

<style lang="scss" scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 230px;
    min-height: 400px;
}

.el-menu {
    border-right: none;
}

:deep(.el-sub-menu .el-sub-menu__title) {
    color: #f4f4f5 !important;
}

:deep(.el-menu .el-menu-item) {
    color: #bfcbd9;
}

:deep(.el-menu-item.is-active) {
    color: #409eff !important;
}

:deep(.is-opened .el-menu-item) {
    background-color: #1f2d3d important;
}

:deep(.el-menu-item:hover) {
    background-color: #001528 !important;
}
</style>
```

### 6、MenuLogo.vue修改为如下

```vue
<template>
    <div class="logo">
        <img :src="Logo">
        <span v-if="show" class="logo-title">{{ title }}</span>
    </div>
</template>

<script setup lang="ts">
import { useMenuStore } from '@/store/menu'
import Logo from '@/assets/logo.png'
const title = ref('通用后台权限系统')

const menuStore = useMenuStore()

const show = ref(true)

watch(() => menuStore.collapse, (collapsed: boolean) => {
    if (!collapsed) {
        setTimeout(() => {
            show.value = !collapsed
        }, 300)
    } else {
        show.value = !collapsed
    }
})

</script>

<style lang="scss" scoped>
.logo {
    display: flex;
    width: 100%;
    height: 60px;
    background-color: #2b2f3a;
    text-align: center;
    cursor: pointer;
    align-items: center;
    /* justify-content: center; */

    img {
        width: 36px;
        height: 36px;
        margin-left: 5px;
        margin-right: 5px;
    }

    .logo-title {
        color: #fff;
        font-weight: 800;
        line-height: 60px;
        font-size: 22px;
        font-family: FangSong;
    }
}
</style>
```



## 11、面包屑导航

### 1、面包屑导航实现原理

面包屑：https://element-plus.org/zh-CN/component/breadcrumb.html

> 从当前路由里面获取所有title

### 2、header目录下新建Breadcrumb.vue组件

```vue
<template>
    <el-breadcrumb class="bred" separator="/">
        <el-breadcrumb-item v-for="item in tabs" :key="item.path" :to="item.path">{{ item.meta.title }}</el-breadcrumb-item>
    </el-breadcrumb>
</template>

<script setup lang="ts">
import { RouteLocationMatched } from 'vue-router';


const route = useRoute();

// 定义面包屑导航数据
const tabs: Ref<RouteLocationMatched[]> = ref([]);
// 获取面包屑数据
const getBreadcrumb = () => {
    let matched = route.matched.filter((item) => item.meta && item.meta.title);
    // 获取第一个数据，判断是否首页
    const first = matched[0];
    if (first.path !== '/dashboard') {
        matched = [{ path: '/dashboard', meta: { title: '首页' } } as any].concat(matched)
    }
    tabs.value = matched;
}
getBreadcrumb()
// 监听路由变化
watch(
    () => route.path,
    () => {
        getBreadcrumb()
    }
)
</script>

<style lang="scss" scoped>
.bred {
    margin-left: 20px;
}

:deep(.el-breadcrumb__inner) {
    color: #fff !important;

    a {
        color: #fff !important;
    }
}
</style>
```

### 3、Header.vue组件使用

```vue
<template>
    <div class="header-container">
        <Collapse />
        <Breadcrumb />
    </div>
</template>

<script setup lang="ts">
import Collapse from './Collapse.vue';
import Breadcrumb from './Breadcrumb.vue';
</script>

<style lang="scss" scoped>
.header-container {
    display: flex;
    align-items: center;
    
}
</style>
```



## 12、tabs选项卡

Tabs 标签页：https://element-plus.org/zh-CN/component/tabs.html

### 1、store下新建tabs目录

```typescript
import { defineStore } from 'pinia'

export type Tab = {
    title: string,
    path: string
}

export type TabState = {
    tabList: Tab[]   
}

// 定义Store
export const useTabsStore = defineStore('tabsStore', {
    state: ():TabState => ({
        tabList: []
    }),
    getters: {
        getTabList(state) { 
            return state.tabList
        }
    },
    actions: {
        addTabList(tab: Tab) {
            // 判断数据是否存在选项卡
            if (!this.tabList.some(item => item.path === tab.path)) {
                if (tab.path === '/login') {
                    return
                }
                this.tabList.push(tab)
            }
        }
    }
})
```

### 2、layout目录新建tabs目录，然后新建Tabs.vue

```vue
<template>
    <el-tabs
             v-model="activeTab"
             type="card"
             class="demo-tabs"
             closable
             @tab-remove="removeTab"
             @tab-click="clickTab">
        <el-tab-pane
                     v-for="item in tabsList"
                     :key="item.path"
                     :label="item.title"
                     :name="item.path">
        </el-tab-pane>
    </el-tabs>
</template>

<script setup lang="ts">
import { Tab, useTabsStore } from '@/store/tabs'
import { TabsPaneContext } from 'element-plus'

const tabsStore = useTabsStore()
const tabsList = computed(() => tabsStore.getTabList)

const route = useRoute()
const router = useRouter()

// 选中的tab
const activeTab = ref('')

// 选项卡点击
const clickTab = (pane: TabsPaneContext) => {
    const { props } = pane
    // 跳转路由
    router.push({
        path: props.name as string
    })
}

// 删除的tab
const removeTab = (targetName: string) => {
    const tabs = tabsList.value
    let activeName = activeTab.value
    if (activeName === targetName) {
        tabs.forEach((tab: Tab, index: number) => {
            if (tab.path === targetName) {
                const nextTab = tabs[index + 1] || tabs[index - 1] // 后一个tab或前一个tab
                if (nextTab) {
                    console.log(nextTab.path, 'nextTab.path')
                    activeName = nextTab.path
                }
            }
        })
    }
    // 激活的选项卡
    activeTab.value = activeName
    // 重新设置选项卡数据
    tabsStore.tabList = tabs.filter((tab) => tab.path !== targetName)
    // 跳转到激活的选项卡
    router.push({
        path: activeName
    })
}

const addTab = () => {
    const { path, meta } = route
    const tab: Tab = {
        title: meta.title as string,
        path: path
    }
    tabsStore.addTabList(tab)
}

// 添加选项卡：监听当前路由
watch(() => route.path, () => {
    setActiveTab()
    // 添加选项卡数据
    addTab()
})

// 设置激活的选项卡
const setActiveTab = () => {
    activeTab.value = route.path
}
onMounted(() => {
    setActiveTab()
    addTab()
})

</script>

<style lang="scss" scoped>
:deep(.el-tabs__header) {
    margin: 0px;
}

:deep(.el-tabs__item) {
    height: 26px !important;
    line-height: 26px !important;
    text-align: center !important;
    border: 1px solid #d8dce5 !important;
    margin: 0 3px !important;
    color: #495060;
    font-size: 12px !important;
    padding: 0 10px !important;
}

:deep(.el-tabs__nav) {
    border: none !important;
}

:deep(.is-active) {
    border-bottom: 1px solid transparent !important;
    border: 1px solid #42b983 !important;
    background-color: #42b983 !important;
    color: #fff !important
}

:deep(.el-tabs_item:hover) {
    color: #495060 !important;
}

:deep(.is-active:hover) {
    color: #fff !important;
}

:deep(.el-tabs_nav-next) {
    line-height: 26px important;
}

:deep(.el-tabs__nav-prev) {
    line-height: 26px !important
}
</style>
```

### 3、layout/Index.vue使用

```vue
<template>
    <el-container class="layout">
        <el-aside width="auto" class="aside">
            <MenuBar />
        </el-aside>
        <el-container>
            <el-header class="header">
                <Header />
            </el-header>
            <el-main class="main">
                <Tabs />
                <router-view />
            </el-main>
        </el-container>
    </el-container>
</template>

<script setup lang="ts">
import Header from '@/layout/header/Header.vue';
import MenuBar from '@/layout/menu/MenuBar.vue';
import Tabs from '@/layout/tabs/Tabs.vue';

</script>

<style lang="scss" scoped>
.layout {
    height: 100%;

    .aside {
        background-color: #304156;
    }

    .header {
        display: flex;
        align-items: center;
        background-color: #009688;
    }

    .main {
        padding: 0 10px !important;
        /* background-color: darkgoldenrod; */
    }
}
</style>
```



## 13、pinia持久化操作解决数据丢失

### 1、安装pinia持久化插件

#### 1.1、持久化插件官网

```
https://seb-l.github.io/pinia-plugin-persist/
```

#### 1.2、安装插件

```shell
npm install pinia-plugin-persist
```

### 2、持久化插件引入main.ts

```typescript
import piniaPersist from 'pinia-plugin-persist'

// 实例化 Pinia
const pinia = createPinia()
// 使用持久化插件
pinia.use(piniaPersist)
```

#### 3、tabStore中使用

#### 4、解决引入报错问题



## 14、选项卡关闭标签



### 1、tabs目录下新建ColseTabs.vue组件

### 2、layout/Index.vue组件

### 3、Tabs.vue组件



## 15、关闭选项卡功能

### 1、ColseTabs.vue组件



### 2、tabStore修改为如下



## 16、安装axios

### 1、安装axios

```shell
npm install axios@1.5
```

### 2、http工具封装





## 17、弹框组件封装

```
https://element-plus.org/zh-CN/component/dialog.html
```

### 1、components下新建SysDialog.vue



### 2、弹框组件使用



### 3、弹框Hooks封装



#### 3.1、src下新建type,然后新建BaseType.ts



#### 3.2、src下新建hooks目录，然后新建useDialog.ts



#### 3.3、使用useDialog



## 18、权限相关数据库设计

### 1、用户表字段(sys_user)

| 字段名称                   | 数据类型 | 字段大小 | 是否主键 | 是否为空 | 备注                               |
| -------------------------- | -------- | -------- | -------- | -------- | ---------------------------------- |
| user_id                    | int      | 11       | 是       | 否       | 用户id                             |
| username                   | varchar  | 64       | 否       | 是       | 登录账户                           |
| password                   | varchar  | 128      | 否       | 是       | 登录密码                           |
| phone                      | varchar  | 13       | 否       | 是       | 用户电话                           |
| email                      | varchar  | 36       | 否       | 是       | 邮箱                               |
| sex                        | varchar  | 2        | 否       | 是       | 0:男 1:女                          |
| is_admin                   | tinyint  | 2        | 否       | 是       | 是否超级管理员 1:是   0:否         |
| is_account_non_expired     | tinyint  | 2        | 否       | 是       | 账户是否过期(1:未过期，0:已过期)   |
| is_account_non_locked      | tinyint  | 2        | 否       | 是       | 账户是否被锁定(1:未锁定，0:已锁定) |
| is_credentials_non_expired | tinyint  | 2        | 否       | 是       | 密码是否被锁定(1:未过期，0:已过期) |
| is_enabled                 | tinyint  | 2        | 否       | 是       | 账户是否可用(1:可用，0:删除用户)   |
| nick_name                  | varchar  | 36       | 否       | 是       | 姓名                               |
| create_time                | datetime |          | 否       | 是       | 创建时间                           |
| update_time                | datetime |          | 否       | 是       | 更新时间                           |



### 2、用户角色表(sys_user_role)

| 字段名称     | 数据类型 | 字段大小 | 是否主键 | 是否为空 | 备注   |
| ------------ | -------- | -------- | -------- | -------- | ------ |
| user_role_id | int      | 11       | 是       | 否       | 主键id |
| user_id      | int      | 11       | 否       | 否       | 用户Id |
| role_id      | int      | 11       | 否       | 否       | 角色Id |

### 3、角色表字段(sys_role)

| 字段名称    | 数据类型 | 字段大小 | 是否主键 | 是否为空 | 备注     |
| ----------- | -------- | -------- | -------- | -------- | -------- |
| role_id     | int      | 11       | 是       | 否       | 角色id   |
| role_name   | varchar  | 64       | 否       | 是       | 角色名称 |
| type        | varchar  | 10       | 否       | 是       | 扩展字段 |
| remark      | varchar  | 128      | 否       | 是       | 备注     |
| create_time | datetime |          | 否       | 是       | 创建时间 |
| update_time | datetime |          | 否       | 是       | 更新时间 |

### 4、角色菜单表(sys_role_menu)

| 字段名称     | 数据类型 | 字段大小 | 是否主键 | 是否为空 | 备注   |
| ------------ | -------- | -------- | -------- | -------- | ------ |
| role_menu_id | int      | 11       | 是       | 否       | 主键id |
| menu_id      | int      | 11       | 否       | 否       | 菜单Id |
| role_id      | int      | 11       | 否       | 否       | 角色Id |

### 5、菜单表字段(sys_menu)

| 字段名称    | 数据类型 | 字段大小 | 是否主键 | 是否为空 | 备注                    |
| ----------- | -------- | -------- | -------- | -------- | ----------------------- |
| menu_id     | int      | 11       | 是       | 否       | 主键id，递增            |
| parent_id   | int      | 11       | 否       | 是       | 父级菜单Id              |
| title       | varchar  | 64       | 否       | 是       | 菜单名称                |
| code        | varchar  | 64       | 否       | 是       | 权限字段                |
| name        | varchar  | 36       | 否       | 是       | 路由名称                |
| path        | varchar  | 36       | 否       | 是       | 路由地址                |
| url         | varchar  | 128      | 否       | 是       | 组件路径                |
| type        | varchar  | 2        | 否       | 是       | 类型(0目录1菜单，2按钮) |
| icon        | varchar  | 36       | 否       | 是       | 菜单图标                |
| parent_name | varchar  | 64       | 否       | 是       | 上级菜单名称            |
| order_num   | int      | 11       | 否       | 是       | 序号                    |
| create_time | datetime |          | 否       | 是       | 创建时间                |
| update_time | datetime |          | 否       | 是       | 更新时间                |

### 6、sql

```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `parent_id` int(0) NULL DEFAULT NULL COMMENT '父级菜单Id',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单名称',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '权限字段',
  `name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由name',
  `path` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由path',
  `url` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '组件路径',
  `type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '类型(0目录1，菜单，2按钮)',
  `icon` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '菜单图标',
  `parent_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '上级菜单名称',
  `order_num` int(0) NULL DEFAULT NULL COMMENT '序号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '角色Id',
  `role_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '角色名称',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '扩展字段',
  `remark` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_menu_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键Id',
  `menu_id` int(0) NULL DEFAULT NULL COMMENT '菜单Id',
  `role_id` int(0) NULL DEFAULT NULL COMMENT '角色Id',
  PRIMARY KEY (`role_menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `user_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录账户',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '登录密码',
  `phone` varchar(18) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '用户电话',
  `email` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮箱',
  `sex` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '性别(0:男 1:女)',
  `is_admin` tinyint(0) NULL DEFAULT NULL COMMENT '是否是超级管理员(1:是   0:否)',
  `is_account_non_expired` tinyint(0) NULL DEFAULT NULL COMMENT '账户是否过期(1:未过期，0:已过期)',
  `is_account_non_locked` tinyint(0) NULL DEFAULT NULL COMMENT '账户是否被锁定(1:未锁定，0:已锁定)',
  `is_credentials_non_expired` tinyint(0) NULL DEFAULT NULL COMMENT '密码是否被锁定(1:未过期，0:已过期)',
  `is_enabled` tinyint(0) NULL DEFAULT NULL COMMENT '账户是否可用(1:可用，0:删除用户)',
  `nick_name` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '姓名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_role_id` int(0) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '用户Id',
  `role_id` int(0) NULL DEFAULT NULL COMMENT '角色Id',
  PRIMARY KEY (`user_role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
```



## 19、后台多模块系统搭建

**开发环境配置：**

- JDK 1.8+
- IDEA 2020+
- Maven 3.5.4+
- Mysql 5.7+

**Maven配置**

```xml
<mirrors>
    <mirror>
        <id>alimaven</id>
        <mirrorOf>central</mirrorOf>
        <name>阿里云公共仓库</name>
        <url>https://maven.aliyun.com/repository/public</url>
    </mirror>
</mirrors>
```

### 1、项目依赖版本号

JDk1.8及以上、Maven.3.5以上、M小ySq5.7以上

### 2、项目结构

| 模板编码         | 描述                            |
| ---------------- | ------------------------------- |
| itmk-base-parent | 父模块，pom类型、统一管理子模块 |
| itmk-base-common | 公共模块、如通用工具的封装等    |
| itmk-base-web    | 前端接口模块，提供api接口       |



### 3、创建itmk-base-parent父模块

![10001](/images/10001.png)

### 4、创建itmk-base-common模块

![10002](/images/10002.png)

### 5、创建itmk-base-web模快

同【itmk-base-common】创建步骤



## 20、引入各个模块依赖

### 1、修改package类型

- 修改父模块itmk-base-parent的pom.Xml打包类型为pom

- itmk-base-common的pom.xml打包类型为jar 

  - ```xml
    <packaging>jar</packaging>
    ```

- itmk-base-web的pom.xml打包类型为jar

  - ```xml
    <packaging>jar</packaging>
    ```

### 2、itmk-base-parent修改pom.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.itmk</groupId>
    <artifactId>itmk-base-parent</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>
    <modules>
        <module>itmk-base-common</module>
        <module>itmk-base-web</module>
    </modules>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.4.4</version>
    </parent>

    <properties>
        <java.version>1.8</java.version>
        <swagger2.version>3.0.0</swagger2.version>
        <lombok.version>1.18.12</lombok.version>
        <mybatis-plus.version>3.4.2</mybatis-plus.version>
        <druid.version>1.2.6</druid.version>
        <kaptcha.version>2.3.2</kaptcha.version>
        <fastjson.version>1.2.68</fastjson.version>
        <commons-lang.version>2.6</commons-lang.version>
        <commons-collections.version>3.2.2</commons-collections.version>
        <commons-io.version>2.6</commons-io.version>
        <mysql.version>8.0.21</mysql.version>
    </properties>

    <!--本地没有该依赖的所有jar包
        将<dependencyManagement>标签先去除。
        先让maven去把jar下载到本地仓库
        下好了再加上<dependencyManagement>标签即可解决
        或者不去处理,子工程需要使用相应的jar引入依赖即可
    -->
    <dependencyManagement>
        <dependencies>
            <!--lombok依赖-->
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
                <scope>provided</scope>
            </dependency>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
                <scope>runtime</scope>
            </dependency>
            <!--druid连接池-->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid-spring-boot-starter</artifactId>
                <version>${druid.version}</version>
            </dependency>
            <!--mybatis-plus依赖-->
            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-boot-starter</artifactId>
                <version>${mybatis-plus.version}</version>
            </dependency>
            <!--captcha图形验证码-->
            <dependency>
                <groupId>com.github.penggle</groupId>
                <artifactId>kaptcha</artifactId>
                <version>${kaptcha.version}</version>
            </dependency>
            <!--JS0N转换工具类依赖-->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>fastjson</artifactId>
                <version>${fastjson.version}</version>
            </dependency>
            <dependency>
                <groupId>commons-lang</groupId>
                <artifactId>commons-lang</artifactId>
                <version>${commons-lang.version}</version>
            </dependency>
            <dependency>
                <groupId>commons-collections</groupId>
                <artifactId>commons-collections</artifactId>
                <version>${commons-collections.version}</version>
            </dependency>
            <dependency>
                <groupId>commons-io</groupId>
                <artifactId>commons-io</artifactId>
                <version>${commons-io.version}</version>
            </dependency>
            <dependency>
                <groupId>io.springfox</groupId>
                <artifactId>springfox-boot-starter</artifactId>
                <version>${swagger2.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

</project>
```

### 3、itmk-base-common修改pom.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>itmk-base-parent</artifactId>
        <groupId>com.itmk</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <packaging>jar</packaging>
    <artifactId>itmk-base-common</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <!--swagger api文档-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-boot-starter</artifactId>
        </dependency>
        <!--jwt token-->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.1</version>
        </dependency>
        <!--工具类依赖-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
        </dependency>
    </dependencies>

</project>
```

### 4、itmk-base-web修改pom.xml文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>itmk-base-parent</artifactId>
        <groupId>com.itmk</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <packaging>jar</packaging>
    <artifactId>itmk-base-web</artifactId>

    <dependencies>
        <dependency>
            <groupId>com.itmk</groupId>
            <artifactId>itmk-base-common</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!--web启动器，对springmvc,serlvet等支持-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
        <!--数据库依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
        </dependency>
        <!--mybatis-plus启动器-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
        </dependency>
        <!--druid连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
        </dependency>
        <!--swagger api文档-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-compress</artifactId>
            <version>1.18</version>
        </dependency>
        <dependency>
            <groupId>commons-lang</groupId>
            <artifactId>commons-lang</artifactId>
        </dependency>
    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <version>2.1.4.RELEASE</version>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.19.1</version>
                <configuration>
                    <skipTests>true</skipTests>    <!--打包过程默认关掉单元测试 -->
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```



## 21、整合MyBatis Plus

MyBatis Plus官方教程：https://baomidou.com/

### 1、新建application.yml文件

1.1、在itmk-base-web模块resources文件下，新建application-test.yml文件

```xml
# 端口号配置
server:
  port: 8089
# 数据库配置
spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/itmk-base?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=UTC
    username: root
    password: 123456
# mybatis-plus配置
mybatis-plus:
  configuration:
    log-impl: org.apache.ibatis.logging.stdout.StdOutImpl
  global-config:
    db-config:
      # 配置mybatis plus 在更新时只更新非空和非NULL的字段
      update-strategy: not_empty
logging:
  pattern:
    console: '%d{yyyy-MM-dd} [%thread] %-5level %logger- %msg%n'
```

1.2、在itmk-base-web模块resources文件下新建application.yml文件

```xml
spring:
  profiles:
    active: test
```

### 2、配置MyBatis Plus配置文件

【itmk-base-web】

```java
package com.itmk.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/21 22:36
 */
@Configuration
@MapperScan("com.itmk.*.*.mapper")
public class MyBatisPlusConfig {
    /**
     * 添加分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL)); // 如果配置多个插件, 切记分页最后添加
        // 如果有多数据源可以不配具体类型, 否则都建议配上具体的 DbType
        return interceptor;
    }
}

```

### 3、封装返回值

在【itmk-base-common】下，新建com.itmk.utils包

```java
package com.itmk.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/21 22:44
 */
@Data
@AllArgsConstructor
public class ResultVo<T> {
    private int code;
    private String msg;
    private T data;
}
```

### 4、返回状态码定义

在【itmk-base-common】模块，新建com.itmk.status包，然后新建StatusCode类，如下所示

```java
package com.itmk.status;

/**
 * 返回状态码
 * @Author: AlbertHPW
 * @Date: 2024/11/21 22:49
 */

public class StatusCode {
    // 返回成功
    public static final int SUCCESS_CODE = 200;
    // 错误状态码
    public static final int ERROR_CODE = 500;
    // 无权限
    public static final int NO_LOGIN = 600;
    public static final int NO_AUTH = 700;
}
```

### 5、返回工具类的封装

在【itmk-base-common】模块下的com.itmk.utils包中，新建ResultUtils类

```java
package com.itmk.utils;

import com.itmk.status.StatusCode;

/**
 * 数据返回工具类
 * @Author: AlbertHPW
 * @Date: 2024/11/21 22:52
 */
public class ResultUtils {

    /**
     * 无参数返回
     * @return
     */
    public static ResultVo success(){
        return vo(null, StatusCode.SUCCESS_CODE, null);
    }

    public static ResultVo success(String msg){
        return vo(msg, StatusCode.SUCCESS_CODE, null);
    }

    /**
     * 带参数返回
     * @param msg
     * @param data
     * @return
     */
    public static ResultVo success(String msg, Object data){
        return vo(msg, StatusCode.SUCCESS_CODE, data);
    }
    public static ResultVo success(String msg, int code, Object data){
        return vo(msg, code, data);
    }
    public static ResultVo vo(String msg, int code, Object data){
        return new ResultVo<>(msg, code, data);
    }

    /**
     * 错误返回
     * @return
     */
    public static ResultVo error(){
        return vo(null, StatusCode.ERROR_CODE, null);
    }
    public static ResultVo error(String msg){
        return vo(msg, StatusCode.ERROR_CODE, null);
    }
    public static ResultVo error(String msg, int code, Object data){
        return vo(msg, code, data);
    }
    public static ResultVo vo(String msg, int code) {
        return vo(msg, code, null);
    }
    public static ResultVo vo(String msg, Object data) {
        return vo(msg, StatusCode.ERROR_CODE, data);
    }
}
```



## 22、角色管理模块接口开发

### 1、新建实体类

```java
package com.itmk.web.sys_role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/21 23:15
 */
@Data
@TableName("sys_role")
public class SysRole {
    @TableId(type = IdType.AUTO)
    private Long roleId;
    private String roleName;
    private String type;
    private String remark;
    private Date createTime;
    private Date updateTime;
}

```

### 2、新建mapper

```java
package com.itmk.web.sys_role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.sys_role.entity.SysRole;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/21 23:24
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {
}

```

**新建映射文件**

在resources目录下面，新建mapper目录，然后新建SysRoleMapper.xml文件

```xml
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace命名空间，对应mapper接口-->
<mapper namespace="com.itmk.web.sys_role.mapper.SysRoleMapper">

</mapper>
```

### 3、service层

新建service接口，继承IService

```
package com.itmk.web.sys_role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.sys_role.entity.SysRole;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/22 1:11
 */
public interface SysRoleService extends IService<SysRole> {
}
```

新建service接口的实现类



### 4、控制器



## 23、新增角色页面

### 1、element plus网站

https://element-plus.org/zh-CN/

### 2、RoleList.vue页面





## 24、新增角色接口对接

### 1、给后台接口添加启动类

```java
package com.itmk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/22 2:08
 */
@SpringBootApplication
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}
```

### 2、前端src下新建api，然后新建role目录，并建index.ts和RoleModel.ts



### 3、RoleList.vue页面使用

### 4、提交表单，报如下跨域错误

### 5、跨域的两种解决方案

#### 5.1、前端配置跨域

#### 5.2、后端配置跨域



## 25、角色列表接口对接

表格：https://element-plus.org/zh-CN/component/table.html

### 1、api/role/index.ts里面添加getListApi，RoleModel.ts添加RoleListParm

```typescript
import http from '@/http'
import { RoleListParm, SysRole } from './RoleModel'

// 新增角色
export const addApi = (parm: SysRole) => {
  return http.post('/api/role', parm)
}

// 角色列表
export const getListApi = (parm: RoleListParm) => {
  return http.get('/api/role/getList', parm)
}
```

```typescript
// 角色数据类型
export type SysRole = {
  roleId: string
  roleName: string
  remark: string
}

// 列表查询的参数
export type RoleListParm = {
  roleName: string
  currentPage: number
  pageSize: number
  total: number
}
```

### 2、RoleList.vue页面

### 3、分页添加国际化





## 26、编辑角色接口对接

### 1、api/role/index.ts里面添加editApi

```typescript
import http from '@/http'
import { RoleListParm, SysRole } from './RoleModel'

// 新增角色
export const addApi = (parm: SysRole) => {
  return http.post('/api/role', parm)
}

// 角色列表
export const getListApi = (parm: RoleListParm) => {
  return http.get('/api/role/getList', parm)
}

// 编辑角色
export const editApi = (parm: SysRole) => {
  return http.put('/api/role', parm)
}
```

### 2、RoleList.vue页面



### 3、列表添加排序功能



## 27、角色删除接口对接

### 1、信息确认框封装

在src下新建utils目录，并创建myconfirm.ts

```typescript
import { ElMessageBox } from 'element-plus'

export default function myconfirm(text: string, title: string = '系统提示') {
  return new Promise((resolve, reject) => {
    ElMessageBox.confirm(text, title, {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(() => {
        // 确定
        resolve(true)
      })
      .catch(() => {
        // 取消
        reject(false)
      })
  }).catch(() => {
    return false
  })
}
```

### 2、在main.ts中挂载myconfirm.ts工具

```typescript
import myconfirm from './utils/myconfirm'
// 全局挂载
app.config.globalProperties.$myconfirm = myconfirm
```

### 3、封装获取全局属性的hooks

```typescript
import { getCurrentInstance, ComponentInternalInstance } from 'vue'

export default function useInstance() {
  const { appContext, proxy } = getCurrentInstance() as ComponentInternalInstance
  const global = appContext.config.globalProperties
  return {
    global,
    proxy
  }
}
```

### 4、api/role下新增deleteApi

```typescript
import http from '@/http'
import { RoleListParm, SysRole } from './RoleModel'

// 新增角色
export const addApi = (parm: SysRole) => {
  return http.post('/api/role', parm)
}

// 角色列表
export const getListApi = (parm: RoleListParm) => {
  return http.get('/api/role/getList', parm)
}

// 编辑角色
export const editApi = (parm: SysRole) => {
  return http.put('/api/role', parm)
}

// 删除角色
export const deleteApi = (roleId: string) => {
  return http.delete(`/api/role/${roleId}`)
}
```

### 5、RoleList.vue页面

```vue
<template>
  <el-main>
    <!-- 搜索栏 -->
    <el-form :model="searchParm" :inline="true" size="default">
      <el-form-item label="">
        <el-input v-model="searchParm.roleName" placeholder="请输入角色名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" @click="search">搜索</el-button>
        <el-button icon="Close" type="danger" plain @click="reset">重置</el-button>
        <el-button icon="Plus" type="primary" @click="add">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格数据 -->
    <el-table :height="tableHeight" :data="tableList" border stripe>
      <el-table-column prop="roleName" label="角色名称"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column label="操作" width="220" align="center">
        <template #default="scope">
          <el-button type="primary" icon="Edit" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" icon="Delete" @click="handleDelete(scope.row.roleId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page.sync="searchParm.currentPage"
      :page-sizes="[10, 20, 40, 80, 100]"
      :page-size="searchParm.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="searchParm.total"
      background
    >
      :pager-count="7">
    </el-pagination>

    <!-- 新增、编辑 -->
    <SysDialog
      :title="dialog.title"
      :visible="dialog.visible"
      :width="dialog.width"
      :height="dialog.height"
      @onClose="onClose"
      @onConfirm="commit"
    >
      <template v-slot:content>
        <el-form :model="addModel" ref="addRef" :rules="rules" label-width="80px" :inline="false" size="default">
          <el-form-item label="角色名称" prop="roleName">
            <el-input v-model="addModel.roleName"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="addModel.remark"></el-input>
          </el-form-item>
        </el-form>
      </template>
    </SysDialog>
  </el-main>
</template>

<script setup lang="ts">
import SysDialog from '@/components/SysDialog.vue'
import useDialog from '@/hooks/useDialog'
import { ElMessage, FormInstance, FormRules } from 'element-plus'
import { addApi, deleteApi, editApi, getListApi } from '@/api/role'
import { SysRole } from '@/api/role/RoleModel'
import useInstance from '@/hooks/useInstance'

const { dialog, onClose, onShow } = useDialog()
const { global } = useInstance()
const addRef = ref<FormInstance>()

const searchParm = reactive({
  currentPage: 1,
  pageSize: 10,
  roleName: '',
  total: 0
})

const addModel = reactive({
  roleId: '',
  roleName: '',
  remark: ''
})

const rules = reactive<FormRules<SysRole>>({
  roleName: [
    {
      required: true,
      message: '请输入角色名称',
      trigger: 'change'
    }
  ]
})
// 表格高度
const tableHeight = ref(0)
// 判断新增还是编辑的标识
const tags = ref('')

// 搜索
const search = () => {
  getList()
}
// 重置
const reset = () => {
  searchParm.roleName = ''
  searchParm.currentPage = 1
  getList()
}
// 新增
const add = () => {
  tags.value = 'add'
  dialog.title = '新增角色'
  dialog.height = 200
  onShow()
  // 清空表单
  addRef.value?.resetFields()
}
// 表单提交
const commit = () => {
  if (!addRef) return
  addRef.value?.validate(async (valid, fields) => {
    if (valid) {
      console.log('submit!')
      // 提交请求
      let res = null
      if (tags.value == 'add') {
        res = await addApi(addModel)
      } else {
        res = await editApi(addModel)
      }
      if (res && res.code == 200) {
        ElMessage.success(res.msg)
        getList()
        onClose()
      } else {
        ElMessage.error(res.msg)
      }
    } else {
      console.log('error submit!', fields)
    }
  })
}
// 表格数据
const tableList = ref([])
// 查询列表
const getList = async () => {
  let res = await getListApi(searchParm)
  if (res && res.code == 200) {
    // 设置表格数据
    tableList.value = res.data.records
    // 设置总条数
    searchParm.total = res.data.total
  } else {
    ElMessage.error(res.msg)
  }
}
onMounted(() => {
  nextTick(() => {
    tableHeight.value = window.innerHeight - 230
  })
  getList()
})
// 编辑
const handleEdit = (row: SysRole) => {
  tags.value = 'edit'
  dialog.visible = true
  dialog.title = '编辑角色'
  dialog.height = 200
  nextTick(() => {
    //   回写数据
    Object.assign(addModel, row)
  })
  // 清空表单
  addRef.value?.resetFields()
}
// 删除
const handleDelete = async (roleId: string) => {
  const confirm = await global.$myconfirm('确定删除该数据吗？', '')
  console.log(confirm)
  if (confirm) {
    let res = await deleteApi(roleId)
    if (res && res.code == 200) {
      ElMessage.success(res.msg)
      getList()
    } else {
      ElMessage.error(res.msg)
    }
  }
}
// 页容量改变时触发
const sizeChange = (size: number) => {
  searchParm.pageSize = size
  getList()
}
// 页数改变时触发
const currentChange = (page: number) => {
  searchParm.currentPage = page
  getList()
}
</script>

<style scoped></style>
```



## 28、用户管理模块接口开发

### 1、新建实体类SysUser和UserParm

### 2、新建mapper层

### 3、新建service层

### 4、新建控制器

```java
package com.itmk.web.sys_user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.sys_user.entity.SysUser;
import com.itmk.web.sys_user.entity.UserParm;
import com.itmk.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/22 21:12
 */
@RequestMapping("/api/sysUser")
@RestController
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    // 新增
    @PostMapping
    public ResultVo add(@RequestBody SysUser sysUser) {
        sysUser.setCreateTime(new Date());
        if(sysUserService.save(sysUser)) {
            return ResultUtils.success("新增成功");
        }
        return ResultUtils.error("新增失败");
    }

    // 编辑
    @PutMapping
    public ResultVo edit(@RequestBody SysUser sysUser) {
        sysUser.setUpdateTime(new Date());
        if(sysUserService.updateById(sysUser)) {
            return ResultUtils.success("编辑成功");
        }
        return ResultUtils.error("编辑失败");
    }

    // 删除
    @DeleteMapping("/{userId}")
    public ResultVo delete(@PathVariable("userId") Long userId) {
        if(sysUserService.removeById(userId)) {
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }

    //列表
    @GetMapping("/list")
    public ResultVo getList(UserParm parm) {
        // 构造分页对象
        IPage<SysUser> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        // 构造查询条件
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getNickName())) {
            query.lambda().like(SysUser::getNickName, parm.getNickName());
        }
        if(StringUtils.isNotEmpty(parm.getPhone())) {
            query.lambda().like(SysUser::getPhone, parm.getPhone());
        }
        query.lambda().orderByDesc(SysUser::getCreateTime);
        IPage<SysUser> list = sysUserService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
}
```



## 29、新增用户页面

### 1、UserList.vue页面



## 30、用户角色下拉

### 1、定义下拉多选组件SelectChecked.vue



### 2、使用下拉多选组件



### 3、api/role/index.ts添加getSelectApi0方法



### 4、RoleList.vue组件



## 31、用户角色保存接口开发

### 1、新建SysUserRole实体类



### 2、新建mapper层



### 3、新建SysUserRoleService接口



### 4、SysUser实体类添加roleld字段



### 5、SysUserService接口修改为如下所示



### 6、SysUserController控制器



## 32、新增用户保存接口对接

### 1、api/user下新建index.ts和UserModel.ts

### 2、SelectChecked.vue组件添加clear方法

### 3、UserList.vue组件



## 33、用户列表页面与接口对接

### 1、UserModel.ts添加列表查询参数类型



### 2、api/user/index.ts添加getListApi方法



### 3、UserList.vue页面



## 34、编辑用户接口对接

### 1、SysUserController控制器添加getRoleList方法

### 2、api/user/indexts添加getRoleListApi和editApi方法



### 3、SelectChecked.vue



### 4、UserList.vue页面



## 35、删除用户接口对接

### 1、api/user/index.ts添加deleteApi方法



### 2、UserList.vue页面



## 36、用户重置密码功能开发

### 1、SysUserController控制器添加resetPassword方法



### 2、api/user/index.ts添力resetPasswordAp方法



### 3、UserList.vue页面



## 37、菜单管理模块接口开发

### 1、创建实体类SysMenu

```java
package com.itmk.web.sys_menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/23 17:48
 */
@Data
@TableName("sys_menu")
public class SysMenu {
    @TableId(type = IdType.AUTO)
    private Long menuId;
    private Long parentId;
    private String title;
    private String code;
    private String name;
    private String path;
    private String url;
    private String type;
    private String icon;
    private String parentName;
    private Integer orderNum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    // 下级菜单 children字段不属于sys_menu表，需要排除
    @TableField(exist = false)
    private List<SysMenu> children = new ArrayList<>();
    @TableField(exist = false)
    private Long value;
    @TableField(exist = false)
    private String label;
}
```

### 2、创建组装树构建树

```java
package com.itmk.web.sys_menu.entity;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/23 18:12
 */
public class MakeMenuTree {
    // 组装树工具
    public  static List<SysMenu> makeTree(List<SysMenu> menuList, Long pid){
        // 存放组装的树数据
        List<SysMenu> list =  new ArrayList<>();
        // 组装树
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getParentId().equals(pid))
                .forEach(item -> {
                    SysMenu menu = new SysMenu();
                    BeanUtils.copyProperties(item,menu);
                    menu.setLabel(item.getTitle());
                    menu.setValue(item.getMenuId());
                    // 查找下级：递归调用
                    List<SysMenu> children = makeTree(menuList, item.getMenuId());
                    menu.setChildren(children);
                    list.add(menu);
                });
        return list;
    }
}
```

### 3、新建mapper层

```java
package com.itmk.web.sys_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.sys_menu.entity.SysMenu;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/23 17:58
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
}
```

### 4、新建service层

```java
```



### 5、新建控制器



## 38、新增菜单页面

### 1、api下新建menu目录，并新建index.ts



### 2、MenuList.vue组件



## 39、新增菜单页面对接

### 1、api/menu下新建MenuModel.ts，并新建MenuType



### 2、api/menu/index.ts添加addApi方法



### 3、MenuList.vue页面

```vue
<template>
  <el-main>
    <el-button type="primary" icon="Plus" size="default" @click="add">新增</el-button>

    <SysDialog
      :title="dialog.title"
      :visible="dialog.visible"
      :width="dialog.width"
      :height="dialog.height"
      @onClose="onClose"
      @onConfirm="commit"
    >
      <template v-slot:content>
        <el-form :model="addModel" ref="addForm" :rules="rules" label-width="80px" :inline="false" size="default">
          <el-form-item label="菜单类型" prop="type">
            <el-radio-group v-model="addModel.type">
              <el-radio :value="'0'">目录</el-radio>
              <el-radio :value="'1'">菜单</el-radio>
              <el-radio :value="'2'">按钮</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item label="上级菜单" prop="parentId">
                <el-tree-select
                  v-model="addModel.parentId"
                  :data="treeList"
                  :render-after-expand="false"
                  show-checkbox
                  default-expand-all
                  check-strictly
                  @check-change="treeCheckChange"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="菜单名称" prop="title">
                <el-input v-model="addModel.title"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20" v-if="addModel.type !== '2'">
            <el-col :span="12" :offset="0">
              <el-form-item label="菜单图标" prop="icon">
                <el-input v-model="addModel.icon"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="路由名称" prop="name">
                <el-input v-model="addModel.name"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item label="菜单序号" prop="orderNum">
                <el-input v-model="addModel.orderNum"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="权限字段" prop="code">
                <el-input v-model="addModel.code"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0" v-if="addModel.type !== '2'">
              <el-form-item label="路由地址" prop="path">
                <el-input v-model="addModel.path"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0" v-if="addModel.type === '1'">
              <el-form-item label="组件路径" prop="url">
                <el-input v-model="addModel.url"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </template>
    </SysDialog>
  </el-main>
</template>

<script setup lang="ts">
import SysDialog from '@/components/SysDialog.vue'
import useDialog from '@/hooks/useDialog'
import { FormInstance, ElMessage } from 'element-plus'
import { getParentApi, addMenuApi } from '@/api/menu'

const { dialog, onClose, onShow } = useDialog()

const addModel = reactive({
  menuId: '',
  parentId: '',
  title: '',
  code: '',
  name: '',
  path: '',
  url: '',
  type: '',
  icon: '',
  parentName: '',
  orderNum: ''
})
const addForm = ref<FormInstance>()

// 表单验证规则
const rules = reactive({
  parentId: [
    {
      required: true,
      message: '请选择上级菜单',
      trigger: ['blur', 'change']
    }
  ],
  title: [
    {
      required: true,
      message: '请输入菜单名称',
      trigger: ['blur', 'change']
    }
  ],
  code: [
    {
      required: true,
      message: '请输入权限字段',
      trigger: ['blur', 'change']
    }
  ],
  name: [
    {
      required: true,
      message: '请输入路由名称',
      trigger: ['blur', 'change']
    }
  ],
  path: [
    {
      required: true,
      message: '请输入路由地址',
      trigger: ['blur', 'change']
    }
  ],
  url: [
    {
      required: true,
      message: '请输入路由地址',
      trigger: ['blur', 'change']
    }
  ],
  type: [
    {
      required: true,
      message: '请输入菜单类型',
      trigger: ['blur', 'change']
    }
  ],
  icon: [
    {
      required: true,
      message: '请输入菜单图标',
      trigger: ['blur', 'change']
    }
  ],
  orderNum: [
    {
      required: true,
      message: '请输入菜单序号',
      trigger: ['blur', 'change']
    }
  ]
})

// 获取上级菜单数据
const treeList = ref([])
const getParent = async () => {
  let res = await getParentApi()
  if (res && res.code == 200) {
    treeList.value = res.data
  }
}

// 新增
const add = () => {
  // tags.value = 'add'
  dialog.title = '新增角色'
  dialog.height = 300
  // 获取上级菜单
  getParent()

  onShow()
}

// 提交表单
const commit = () => {
  if (!addForm) return
  addForm.value?.validate(async (valid, fields) => {
    if (valid) {
      console.log('submit!', addModel)
      let res = await addMenuApi(addModel) //新增用户
      if (res && res.code == 200) {
        ElMessage.success(res.msg)
        onClose()
      }
      //   let res = null
      //   if (tags.value == 'add') {
      //     res = await addUserApi(addModel) //新增用户
      //   } else {
      //     res = await editUserApi(addModel) // 编辑用户
      //   }
      //   if (res && res.code == 200) {
      //     ElMessage.success(res.msg)
      //     getList()
      //     onClose()
      //   } else {
      //     ElMessage.error(res?.msg)
      //   }
    } else {
      console.log('error submit!', fields)
    }
  })
}

// 上级选中事件
const treeCheckChange = (item: any) => {
  console.log(item)
  addModel.parentName = item.title ? item.title : ''
}
</script>
<style scoped></style>
```



## 40、菜单列表页面

### 1、api/menu/index.ts添加getListApi方法

```typescript
import http from '@/http'
import { MenuType } from './MenuModel'

// 获取上级菜单
export const getParentApi = () => {
  return http.get('/api/sysMenu/getParent')
}

// 新增
export const addMenuApi = (parm: MenuType) => {
  return http.post('/api/sysMenu', parm)
}

// 列表
export const getListApi = () => {
  return http.get('/api/sysMenu/list')
}
```

### 2、MenuList.vue页面

```vue
<template>
  <el-main>
    <!-- 搜索栏 -->
    <el-form :model="searchParm" :inline="true" size="default">
      <el-form-item label="">
        <el-input v-model="searchParm.roleName" placeholder="请输入角色名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" @click="search">搜索</el-button>
        <el-button icon="Close" type="danger" plain @click="reset">重置</el-button>
        <el-button icon="Plus" type="primary" @click="add">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格数据 -->
    <el-table :height="tableHeight" :data="tableList" border stripe>
      <el-table-column prop="roleName" label="角色名称"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column label="操作" width="220" align="center">
        <template #default="scope">
          <el-button type="primary" icon="Edit" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" icon="Delete" @click="handleDelete(scope.row.roleId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page.sync="searchParm.currentPage"
      :page-sizes="[10, 20, 40, 80, 100]"
      :page-size="searchParm.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="searchParm.total"
      background
    ></el-pagination>

    <!-- 新增、编辑 -->
    <SysDialog
      :title="dialog.title"
      :visible="dialog.visible"
      :width="dialog.width"
      :height="dialog.height"
      @onClose="onClose"
      @onConfirm="commit"
    >
      <template v-slot:content>
        <el-form :model="addModel" ref="addRef" :rules="rules" label-width="80px" :inline="false" size="default">
          <el-form-item label="角色名称" prop="roleName">
            <el-input v-model="addModel.roleName"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="addModel.remark"></el-input>
          </el-form-item>
        </el-form>
      </template>
    </SysDialog>
  </el-main>
</template>

<script setup lang="ts">
import SysDialog from '@/components/SysDialog.vue'
import useDialog from '@/hooks/useDialog'
import { ElMessage, FormInstance, FormRules } from 'element-plus'
import { addApi, deleteApi, editApi, getListApi } from '@/api/role'
import { SysRole } from '@/api/role/RoleModel'
import useInstance from '@/hooks/useInstance'

const { dialog, onClose, onShow } = useDialog()
const { global } = useInstance()
const addRef = ref<FormInstance>()

const searchParm = reactive({
  currentPage: 1,
  pageSize: 10,
  roleName: '',
  total: 0
})

const addModel = reactive({
  roleId: '',
  roleName: '',
  remark: ''
})

const rules = reactive<FormRules<SysRole>>({
  roleName: [
    {
      required: true,
      message: '请输入角色名称',
      trigger: 'change'
    }
  ]
})
// 表格高度
const tableHeight = ref(0)
// 判断新增还是编辑的标识
const tags = ref('')

// 搜索
const search = () => {
  getList()
}
// 重置
const reset = () => {
  searchParm.roleName = ''
  searchParm.currentPage = 1
  getList()
}
// 新增
const add = () => {
  tags.value = 'add'
  dialog.title = '新增角色'
  dialog.height = 200
  onShow()
  // 清空表单
  addRef.value?.resetFields()
}
// 表单提交
const commit = () => {
  if (!addRef) return
  addRef.value?.validate(async (valid, fields) => {
    if (valid) {
      console.log('submit!')
      // 提交请求
      let res = null
      if (tags.value == 'add') {
        res = await addApi(addModel)
      } else {
        res = await editApi(addModel)
      }
      if (res && res.code == 200) {
        ElMessage.success(res.msg)
        getList()
        onClose()
      } else {
        ElMessage.error(res.msg)
      }
    } else {
      console.log('error submit!', fields)
    }
  })
}
// 表格数据
const tableList = ref([])
// 查询列表
const getList = async () => {
  let res = await getListApi(searchParm)
  if (res && res.code == 200) {
    // 设置表格数据
    tableList.value = res.data.records
    // 设置总条数
    searchParm.total = res.data.total
  } else {
    ElMessage.error(res.msg)
  }
}
onMounted(() => {
  nextTick(() => {
    tableHeight.value = window.innerHeight - 230
  })
  getList()
})
// 编辑
const handleEdit = (row: SysRole) => {
  tags.value = 'edit'
  dialog.visible = true
  dialog.title = '编辑角色'
  dialog.height = 200
  nextTick(() => {
    //   回写数据
    Object.assign(addModel, row)
  })
  // 清空表单
  addRef.value?.resetFields()
}
// 删除
const handleDelete = async (roleId: string) => {
  const confirm = await global.$myconfirm('确定删除该数据吗？', '')
  console.log(confirm)
  if (confirm) {
    let res = await deleteApi(roleId)
    if (res && res.code == 200) {
      ElMessage.success(res.msg)
      getList()
    } else {
      ElMessage.error(res.msg)
    }
  }
}
// 页容量改变时触发
const sizeChange = (size: number) => {
  searchParm.pageSize = size
  getList()
}
// 页数改变时触发
const currentChange = (page: number) => {
  searchParm.currentPage = page
  getList()
}
</script>

<style scoped></style>
```



## 41、编辑、删除菜单接口对接

### 1、api/menu/index.ts添加editApi和deleteApi

```typescript
import http from '@/http'
import { MenuType } from './MenuModel'

// 获取上级菜单
export const getParentApi = () => {
  return http.get('/api/sysMenu/getParent')
}

// 新增
export const addMenuApi = (parm: MenuType) => {
  return http.post('/api/sysMenu', parm)
}

// 列表
export const getListApi = () => {
  return http.get('/api/sysMenu/list')
}

// 编辑
export const editApi = (parm: MenuType) => {
  return http.put('/api/sysMenu', parm)
}

// 删除
export const deleteApi = (menuId: string) => {
  return http.delete(`/api/sysMenu/${menuId}`)
}
```

### 2、MenuList.vue组件

```vue
<template>
  <el-main>
    <el-button type="primary" icon="Plus" size="default" @click="add">新增</el-button>

    <!-- 表格 -->
    <el-table :data="tableList" row-key="menuId" border stripe default-expand-all style="margin-top: 20px">
      <el-table-column prop="title" label="菜单名称"></el-table-column>
      <el-table-column prop="icon" label="菜单图标">
        <template #default="scope">
          <el-icon v-if="scope.row.icon">
            <component :is="scope.row.icon" />
          </el-icon>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="菜单类型">
        <template #default="scope">
          <el-tag v-if="scope.row.type === '0'" type="danger" size="default">目录</el-tag>
          <el-tag v-if="scope.row.type === '1'" type="success" size="default">菜单</el-tag>
          <el-tag v-if="scope.row.type === '2'" type="primary" size="default">按钮</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="parentName" label="上级菜单"></el-table-column>
      <el-table-column prop="name" label="路由名称"></el-table-column>
      <el-table-column prop="path" label="路由地址"></el-table-column>
      <el-table-column prop="url" label="组件路径" width="300"></el-table-column>
      <el-table-column prop="orderNum" label="序号"></el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column label="操作" width="220" align="center">
        <template #default="scope">
          <el-button type="primary" icon="Edit" size="default" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="danger" icon="Delete" size="default" @click="handleDelete(scope.row.menuId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <SysDialog
      :title="dialog.title"
      :visible="dialog.visible"
      :width="dialog.width"
      :height="dialog.height"
      @onClose="onClose"
      @onConfirm="commit"
    >
      <template v-slot:content>
        <el-form :model="addModel" ref="addForm" :rules="rules" label-width="80px" :inline="false" size="default">
          <el-form-item label="菜单类型" prop="type">
            <el-radio-group v-model="addModel.type">
              <el-radio :value="'0'">目录</el-radio>
              <el-radio :value="'1'">菜单</el-radio>
              <el-radio :value="'2'">按钮</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item label="上级菜单" prop="parentId">
                <el-tree-select
                  v-model="addModel.parentId"
                  :data="treeList"
                  :render-after-expand="false"
                  show-checkbox
                  default-expand-all
                  check-strictly
                  @check-change="treeCheckChange"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="菜单名称" prop="title">
                <el-input v-model="addModel.title"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20" v-if="addModel.type !== '2'">
            <el-col :span="12" :offset="0">
              <el-form-item label="菜单图标" prop="icon">
                <el-input v-model="addModel.icon"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="路由名称" prop="name">
                <el-input v-model="addModel.name"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item label="菜单序号" prop="orderNum">
                <el-input v-model="addModel.orderNum"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="权限字段" prop="code">
                <el-input v-model="addModel.code"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0" v-if="addModel.type !== '2'">
              <el-form-item label="路由地址" prop="path">
                <el-input v-model="addModel.path"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0" v-if="addModel.type === '1'">
              <el-form-item label="组件路径" prop="url">
                <el-input v-model="addModel.url"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </template>
    </SysDialog>
  </el-main>
</template>

<script setup lang="ts">
import SysDialog from '@/components/SysDialog.vue'
import useDialog from '@/hooks/useDialog'
import { FormInstance, ElMessage } from 'element-plus'
import { getParentApi, addMenuApi, getListApi, editApi, deleteApi } from '@/api/menu'
import { MenuType } from '@/api/menu/MenuModel'
import useInstance from '@/hooks/useInstance'

const { dialog, onClose, onShow } = useDialog()
const { global } = useInstance()

const addModel = reactive({
  menuId: '',
  parentId: '',
  title: '',
  code: '',
  name: '',
  path: '',
  url: '',
  type: '',
  icon: '',
  parentName: '',
  orderNum: ''
})
const addForm = ref<FormInstance>()

// 表单验证规则
const rules = reactive({
  parentId: [
    {
      required: true,
      message: '请选择上级菜单',
      trigger: ['blur', 'change']
    }
  ],
  title: [
    {
      required: true,
      message: '请输入菜单名称',
      trigger: ['blur', 'change']
    }
  ],
  code: [
    {
      required: true,
      message: '请输入权限字段',
      trigger: ['blur', 'change']
    }
  ],
  name: [
    {
      required: true,
      message: '请输入路由名称',
      trigger: ['blur', 'change']
    }
  ],
  path: [
    {
      required: true,
      message: '请输入路由地址',
      trigger: ['blur', 'change']
    }
  ],
  url: [
    {
      required: true,
      message: '请输入路由地址',
      trigger: ['blur', 'change']
    }
  ],
  type: [
    {
      required: true,
      message: '请输入菜单类型',
      trigger: ['blur', 'change']
    }
  ],
  icon: [
    {
      required: true,
      message: '请输入菜单图标',
      trigger: ['blur', 'change']
    }
  ],
  orderNum: [
    {
      required: true,
      message: '请输入菜单序号',
      trigger: ['blur', 'change']
    }
  ]
})

// 判断新增还是编辑的标识
const tags = ref('')

// 获取上级菜单数据
const treeList = ref([])
const getParent = async () => {
  let res = await getParentApi()
  if (res && res.code == 200) {
    treeList.value = res.data
  }
}

// 新增
const add = () => {
  tags.value = 'add'
  dialog.title = '新增角色'
  dialog.height = 300
  // 获取上级菜单
  getParent()
  // 显示弹框
  onShow()
  // 清空表单
  addForm.value?.resetFields()
}

// 提交表单
const commit = () => {
  if (!addForm) return
  addForm.value?.validate(async (valid, fields) => {
    if (valid) {
      console.log('submit!', addModel)
      let res = null
      if (tags.value == 'add') {
        res = await addMenuApi(addModel) //新增用户
      } else {
        res = await editApi(addModel) // 编辑用户
      }
      if (res && res.code == 200) {
        ElMessage.success(res.msg)
        getList()
        onClose()
      } else {
        ElMessage.error(res?.msg)
      }
    } else {
      console.log('error submit!', fields)
    }
  })
}

// 上级选中事件
const treeCheckChange = (item: any) => {
  console.log(item)
  addModel.parentName = item.title ? item.title : ''
}
// 获取列表
const tableList = ref([])
const getList = async () => {
  let res = await getListApi()
  if (res && res.code == 200) {
    tableList.value = res.data
  }
}
onMounted(() => {
  getList()
})

// 编辑
const handleEdit = (row: MenuType) => {
  tags.value = 'edit'
  dialog.title = '新增角色'
  dialog.height = 300
  dialog.visible = true
  // 获取上级菜单
  getParent()
  nextTick(() => {
    //   回写数据
    Object.assign(addModel, row)
  })
  // 清空表单
  addForm.value?.resetFields()
}
// 删除
const handleDelete = async (menuId: string) => {
  const confirm = await global.$myconfirm('确定删除该数据吗？', '')
  if (confirm) {
    let res = await deleteApi(menuId)
    if (res && res.code == 200) {
      ElMessage.success(res.msg)
      getList()
    } else {
      ElMessage.error(res.msg)
    }
  }
}
</script>
<style scoped></style>
```



## 42、登录页面

### 1、views下新建login目录，并建login.vue页面

```vue
<template>
  <div class="login-container">
    <el-form class="loginForm" :model="loginModel" ref="form" :rules="rules" :inline="false" size="large">
      <el-form-item>
        <div class="loginTitle">系统登录</div>
      </el-form-item>
      <el-form-item prop="username">
        <el-input v-model="loginModel.username" placeholder="请输入账户"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginModel.password" placeholder="请输入密码"></el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-row :gutter="10" style="width: 100%">
          <el-col :span="16" :offset="0">
            <el-input v-model="loginModel.code" placeholder="请输入验证码"></el-input>
          </el-col>
          <el-col :span="8" :offset="0" style="height: 40px">
            <img class="images" src="@/assets/logo.png" alt="验证码" />
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row :gutter="20" style="width: 100%">
          <el-col :span="12" :offset="0">
            <el-button class="mybtn" type="primary" @click="onSubmit">登录</el-button>
          </el-col>
          <el-col :span="12" :offset="0">
            <el-button class="mybtn" type="danger" plain>重置</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
// 表单绑定对象
const loginModel = reactive({
  username: '',
  password: '',
  code: ''
})

// 表单ref属性
const form = ref()

// 表单验证规则
const rules = reactive({
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
  ],
  password: []
})
</script>

<style lang="scss" scoped>
.login-container {
  height: 100%;
  background-color: #fff;
  background: url('../../assets/login.jpg') no-repeat;
  background-size: 100% 100%; /* 背景大小设置为100% */
  background-attachment: fixed;
  display: flex;
  justify-content: center;
  align-items: center;
  .loginForm {
    height: 300px;
    width: 400px;
    padding: 20px 35px;
    border-radius: 10px;
    background-color: #fff;
    .loginTitle {
      display: flex;
      justify-content: center;
      color: #606266;
      width: 100%;
      font-size: 24px;
      font-weight: 600;
    }
    .images {
      height: 100%;
      width: 100%;
      cursor: pointer;
    }
    .mybtn {
      width: 100%;
    }
  }
}
</style>
```

### 2、配置页面路由

```typescript
 {
    path: '/login',
    component: () => import('@/views/login/login.vue'),
    name: 'login'
  },
```



## 43、登录和验证码接口开发

### 1、itmk-base-web的pom.xml下添加如下依赖

```xml
<!--图片验证码-->
<dependency>
    <groupId>com.github.penggle</groupId>
    <artifactId>kaptcha</artifactId>
</dependency>
```

### 2、后端接口src的config下，新建imagecode包，创建lmageConfig验证码配置类

```java
package com.itmk.config.imagecode;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 验证码配置类
 * @Author: AlbertHPW
 * @Date: 2024/11/26 19:38
 */
@Configuration
public class ImageConfig {

    @Bean
    public DefaultKaptcha getDefaultKaptcha() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // 验证吗是否有边框
        properties.setProperty(Constants.KAPTCHA_BORDER, "yes");
        // 边框颜色
        properties.setProperty(Constants.KAPTCHA_BORDER_COLOR, "105,179,90");
        // 字体颜色
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "blue");
        // 验证码图片宽度
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "200");
        // 验证码图片高度
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "36");
        // 生成验证码的字符
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "0123456789");
        // 去掉干扰线
        properties.setProperty(Constants.KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
        // 字体大小
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "34");
        // 字体样式
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "楷体");
        // 验证码位数
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        // 图片效果
        properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");

        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
```

### 3、sysUserController控制器，添加imageCode和login方法

```java
 // 图片验证码
    @PostMapping("/captcha")
    public ResultVo imageCode(HttpServletRequest request) {
        // 生成验证码 4562
        String text = defaultKaptcha.createText();
        //验证码存到session
        HttpSession session = request.getSession();
        session.setAttribute("code", text);
        // 生成图片，转换为base64
        BufferedImage bufferedImage = defaultKaptcha.createImage(text);
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", outputStream);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64 = encoder.encode(outputStream.toByteArray());
            String captchaBase64 = "data:image/jpeg;base64," + base64.replaceAll("\r\n", "");
            return ResultUtils.success("验证码生成成功", 200, captchaBase64);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    // 登录
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParm parm, HttpServletRequest request) {
        // 从session里面获取code验证码
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute("code");
        // 获取从前端传递过来的验证码
        String codeParm = parm.getCode();
        if (StringUtils.isEmpty(code)) {
            return ResultUtils.error("验证码已失效");
        }
        // 对比验证码
        if (!codeParm.equals(code)) {
            return ResultUtils.error("验证码错误");
        }
        // 验证用户信息
        // DigestUtils.md5DigestAsHex(parm.getPassword().getBytes())
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        query.lambda().eq(SysUser::getUsername, parm.getUsername())
                .eq(SysUser::getPassword, parm.getPassword());
        SysUser user = sysUserService.getOne(query);
        if (user == null) {
            return ResultUtils.error("用户名或密码错误");
        }
        //if(user.get)
        // 返回登录信息
        LoginVo vo = new LoginVo();
        vo.setUserId(user.getUserId());
        vo.setNickName(user.getNickName());
        return ResultUtils.success("登录成功", vo);
    }
```

### 4、参数接收实体类

```java
package com.itmk.web.sys_user.entity;

import lombok.Data;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/26 20:18
 */
@Data
public class LoginParm {
    private String username;
    private String password;
    private String code;
}
```

```java
package com.itmk.web.sys_user.entity;

import lombok.Data;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/26 20:27
 */
@Data
public class LoginVo {
    private Long userId;
    private String nickName;
    private String token;
}
```



## 44、登录和验证码接口对接

### 1、api/user/index.ts添加getlmgApi和loginApi方法

### 2、UserModel.ts添加Login类型

### 3、http/index.ts的axios的配置项config添加withCredentials:true

### 4、login.vue页面



## 45、分配权限菜单树数据查询与回显

### 1、新建参数接收实体类

AssignTreeParm

```java
package com.itmk.web.sys_menu.entity;

import lombok.Data;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/26 23:05
 */
@Data
public class AssignTreeParm {
    private Long userId;
    private Long roleId;
}
```

### 2、新建返回值实体类

AssignTreeVo

```java
package com.itmk.web.sys_menu.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/26 23:07
 */
@Data
public class AssignTreeVo {
    private List<SysMenu> menuList = new ArrayList<>();
    private Object[] checkList;
}
```

### 3、SysMenuMapper接口添加如下方法

```java
package com.itmk.web.sys_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.sys_menu.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/23 17:58
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    // 根据用户Id查询菜单
    List<SysMenu> getMenuByUserId(@Param("userId") Long userId);

    // 根据角色Id查询菜单
    List<SysMenu> getMenuByRoleId(@Param("roleId") Long roleId);

}
```

### 4、SysMenuMapper.xml映射

```xml
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace命名空间，对应mapper接口-->
<mapper namespace="com.itmk.web.sys_menu.mapper.SysMenuMapper">

    <select id="getMenuByUserId" resultType="com.itmk.web.sys_menu.entity.SysMenu">
        select DISTINCT m.*
        from sys_user_role as ur
                 left join sys_role as r on ur.role_id = r.role_id
                 left join sys_role_menu as rm on r.role_id = rm.role_id
                 left join sys_menu as m on rm.menu_id = m.menu_id
        where ur.user_id = #{userId}
        order by m.order_num asc
    </select>

    <select id="getMenuByRoleId" resultType="com.itmk.web.sys_menu.entity.SysMenu">
        select m.*
        from sys_role_menu as rm,
             sys_menu as m
        where rm.menu_id = m.menu_id
          and rm.role_id = #{roleId}
    </select>

</mapper>
```

### 5、SysMenuService添加getMenuByUserld和getMenuByRoleld方法

```java
package com.itmk.web.sys_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.sys_menu.entity.AssignTreeParm;
import com.itmk.web.sys_menu.entity.AssignTreeVo;
import com.itmk.web.sys_menu.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/23 18:00
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> getParent();

    List<SysMenu> getMenuByUserId(Long userId);

    // 根据角色Id查询菜单
    List<SysMenu> getMenuByRoleId(Long roleId);


}
```

### 6、SysUserService接口添加getAssignTree方法

```java
package com.itmk.web.sys_user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.sys_menu.entity.AssignTreeParm;
import com.itmk.web.sys_menu.entity.AssignTreeVo;
import com.itmk.web.sys_user.entity.SysUser;

/**
 * @Author: AlbertHPW
 * @Date: 2024/11/22 21:09
 */
public interface SysUserService extends IService<SysUser> {
    // 新增
    void saveUser(SysUser sysUser);
    // 编辑
    void editUser(SysUser sysUser);
    // 删除
    void deleteUser(Long userId);
    // 查询菜单树
    AssignTreeVo getAssignTree(AssignTreeParm parm);
}
```

### 7、SysUserController控制器添加getAssingTree方法

```java
// 查询菜单树
@GetMapping("/getAssignTree")
public ResultVo getAssignTree(AssignTreeParm parm) {
    AssignTreeVo assignTree = sysUserService.getAssignTree(parm);
    return ResultUtils.success("查询成功", assignTree);
}
```



## 46、分配菜单页面

### 1、UserModel.ts添加加AssignParm

```typescript
// 菜单树参数
export type AssignParm = {
  userId: string
  roleId: string
}
```

### 2、api/user/index.ts添加getAssignTreeApi方法

```typescript
//查询菜单树
export const getAssignTreeApi = (pram: AssignParm) => {
  return http.get(`/api/sysUser/getAssignTree`, pram)
}
```

### 3、views/system/Role下新建AssignTree.vue

```vue
<template>
  <SysDialog
    :title="dialog.title"
    :visible="dialog.visible"
    :width="dialog.width"
    :height="dialog.height"
    @onClose="onClose"
    @onConfirm="commit"
  >
    <template v-slot:content>
      <el-tree
        :data="assignTreeData.list"
        :props="defaultProps"
        :default-expanded-keys="assignTreeData.assignTreeChecked"
        show-checkbox
        default-expand-all
      />
    </template>
  </SysDialog>
</template>

<script setup lang="ts">
import { getAssignTreeApi } from '@/api/user'
import { AssignParm } from '@/api/user/UserModel'
import SysDialog from '@/components/SysDialog.vue'
import useDialog from '@/hooks/useDialog'
import { useUserStore } from '@/store/user'

const { getUserId } = useUserStore()

const { dialog, onClose, onConfirm, onShow } = useDialog()
// 树数据
const assignTreeData = reactive({
  list: [],
  assignTreeChecked: []
})
// 树的属性配置
const defaultProps = {
  children: 'children',
  label: 'title'
}
// 树数据查询的参数
const parms = reactive<AssignParm>({
  userId: '',
  roleId: ''
})
// 查询菜单树
const getAssignTree = async () => {
  parms.userId = getUserId
  let res = await getAssignTreeApi(parms)
  if (res && res.code === 200) {
    // 设置权限树数据
    assignTreeData.list = res.data.menuList
    // 设置角色原来的权限Id
    assignTreeData.assignTreeChecked = res.data.checkList
    // 数据回显，判断角色原来是否已经分配过权限，如果有，回显
    if (assignTreeData.assignTreeChecked.length > 0) {
      let newArr: any = []
      assignTreeData.assignTreeChecked.forEach((item: any) => {
        checked(item, assignTreeData.list, newArr)
      })
      assignTreeData.assignTreeChecked = newArr
    }
  }
}
const checked = (id: number, data: any, newArr: any) => {
  data.forEach((item: any) => {
    if (item.menuId === id) {
      if (item.children && item.children.length > 0) {
      }
      newArr.push(item.menuId)
    } else {
      if (item.children && item.children.length > 0) {
        // 递归调用
        checked(id, item.children, newArr)
      }
    }
  })
}

// 提交
const commit = () => {}

// 弹框显示
const show = async (roleId: string, name: string) => {
  parms.roleId = roleId
  dialog.height = 450
  dialog.width = 300
  dialog.title = `为【${name}】分配菜单`
  await getAssignTree()
  onShow()
}
// 暴露出去，给外部组件使用
defineExpose({
  show
})
</script>

<style scoped></style>
```

### 4、RoleList.vue页面使用

```vue
<template>
  <el-main>
    <!-- 搜索栏 -->
    <el-form :model="searchParm" :inline="true" size="default">
      <el-form-item label="">
        <el-input v-model="searchParm.roleName" placeholder="请输入角色名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" @click="search">搜索</el-button>
        <el-button icon="Close" type="danger" plain @click="reset">重置</el-button>
        <el-button icon="Plus" type="primary" @click="add">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格数据 -->
    <el-table :height="tableHeight" :data="tableList" border stripe>
      <el-table-column prop="roleName" label="角色名称"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column label="操作" width="320" align="center">
        <template #default="scope">
          <el-button type="primary" icon="Edit" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button type="success" icon="Setting" @click="assignTreeEdit(scope.row)">分配菜单</el-button>
          <el-button type="danger" icon="Delete" @click="handleDelete(scope.row.roleId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page.sync="searchParm.currentPage"
      :page-sizes="[10, 20, 40, 80, 100]"
      :page-size="searchParm.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="searchParm.total"
      background
    ></el-pagination>

    <!-- 新增、编辑 -->
    <SysDialog
      :title="dialog.title"
      :visible="dialog.visible"
      :width="dialog.width"
      :height="dialog.height"
      @onClose="onClose"
      @onConfirm="commit"
    >
      <template v-slot:content>
        <el-form :model="addModel" ref="addRef" :rules="rules" label-width="80px" :inline="false" size="default">
          <el-form-item label="角色名称" prop="roleName">
            <el-input v-model="addModel.roleName"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="addModel.remark"></el-input>
          </el-form-item>
        </el-form>
      </template>
    </SysDialog>
    <!-- 分配菜单 -->
    <AssignTree ref="assignTree" />
  </el-main>
</template>

<script setup lang="ts">
import SysDialog from '@/components/SysDialog.vue'
import useDialog from '@/hooks/useDialog'
import { ElMessage, FormInstance, FormRules } from 'element-plus'
import { addApi, deleteApi, editApi, getListApi } from '@/api/role'
import { SysRole } from '@/api/role/RoleModel'
import useInstance from '@/hooks/useInstance'
import AssignTree from './AssignTree.vue'

const { dialog, onClose, onShow } = useDialog()
const { global } = useInstance()
const addRef = ref<FormInstance>()

const searchParm = reactive({
  currentPage: 1,
  pageSize: 10,
  roleName: '',
  total: 0
})

const addModel = reactive({
  roleId: '',
  roleName: '',
  remark: ''
})

const rules = reactive<FormRules<SysRole>>({
  roleName: [
    {
      required: true,
      message: '请输入角色名称',
      trigger: 'change'
    }
  ]
})
// 表格高度
const tableHeight = ref(0)
// 判断新增还是编辑的标识
const tags = ref('')

const assignTree = ref()

// 搜索
const search = () => {
  getList()
}
// 重置
const reset = () => {
  searchParm.roleName = ''
  searchParm.currentPage = 1
  getList()
}
// 新增
const add = () => {
  tags.value = 'add'
  dialog.title = '新增角色'
  dialog.height = 200
  onShow()
  // 清空表单
  addRef.value?.resetFields()
}
// 表单提交
const commit = () => {
  if (!addRef) return
  addRef.value?.validate(async (valid, fields) => {
    if (valid) {
      console.log('submit!')
      // 提交请求
      let res = null
      if (tags.value == 'add') {
        res = await addApi(addModel)
      } else {
        res = await editApi(addModel)
      }
      if (res && res.code == 200) {
        ElMessage.success(res.msg)
        getList()
        onClose()
      } else {
        ElMessage.error(res.msg)
      }
    } else {
      console.log('error submit!', fields)
    }
  })
}
// 表格数据
const tableList = ref([])
// 查询列表
const getList = async () => {
  let res = await getListApi(searchParm)
  if (res && res.code == 200) {
    // 设置表格数据
    tableList.value = res.data.records
    // 设置总条数
    searchParm.total = res.data.total
  } else {
    ElMessage.error(res.msg)
  }
}
onMounted(() => {
  nextTick(() => {
    tableHeight.value = window.innerHeight - 230
  })
  getList()
})
// 编辑
const handleEdit = (row: SysRole) => {
  tags.value = 'edit'
  dialog.visible = true
  dialog.title = '编辑角色'
  dialog.height = 200
  nextTick(() => {
    //   回写数据
    Object.assign(addModel, row)
  })
  // 清空表单
  addRef.value?.resetFields()
}
// 删除
const handleDelete = async (roleId: string) => {
  const confirm = await global.$myconfirm('确定删除该数据吗？', '')
  if (confirm) {
    let res = await deleteApi(roleId)
    if (res && res.code == 200) {
      ElMessage.success(res.msg)
      getList()
    } else {
      ElMessage.error(res.msg)
    }
  }
}
// 页容量改变时触发
const sizeChange = (size: number) => {
  searchParm.pageSize = size
  getList()
}
// 页数改变时触发
const currentChange = (page: number) => {
  searchParm.currentPage = page
  getList()
}

// 分配菜单
const assignTreeEdit = (row: SysRole) => {
  assignTree.value.show(row.roleId)
}
</script>

<style scoped></style>
```

## 47、分配权限树保存与对接

### 1、新建实体类

```java
package com.itmk.web.sys_role_menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: AlbertHPW
 * @Date: 2024/12/3 20:14
 */
@Data
@TableName("sys_role_menu")
public class RoleMenu {
    @TableId(type = IdType.AUTO)
    private Long roleMenuId;
    private Long roleId;
    private Long menuId;
}
```

```java
package com.itmk.web.sys_role.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: AlbertHPW
 * @Date: 2024/12/3 20:16
 */
@Data
public class SaveMenuParm {
    private Long roleId;
    private List<Long> list;
}
```

### 2、新建mapper层

RoleMenuMapper

```java
package com.itmk.web.sys_role_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.sys_role_menu.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: AlbertHPW
 * @Date: 2024/12/4 21:06
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    // 保存角色菜单
    boolean saveRoleMenu(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);
}
```

RoleMenuMapper.xml

```xml
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace命名空间，对应mapper接口-->
<mapper namespace="com.itmk.web.sys_role_menu.mapper.RoleMenuMapper">
    <insert id="saveRoleMenu">
        insert into sys_role_menu (role_id, menu_id) values
        <foreach collection="menuIds" item="menuId" separator="," index="index">
            (#{roleId}, #{menuId})
        </foreach>
    </insert>
</mapper>
```

### 3、新建service层

RoleMenuService

```java
package com.itmk.web.sys_role_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.sys_role.entity.SaveMenuParm;
import com.itmk.web.sys_role_menu.entity.RoleMenu;

/**
 * @Author: AlbertHPW
 * @Date: 2024/12/4 21:17
 */
public interface RoleMenuService extends IService<RoleMenu> {
    void saveRoleMenu(SaveMenuParm parm);
}
```

RoleMenuServiceImpl

```java
package com.itmk.web.sys_role_menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.sys_role.entity.SaveMenuParm;
import com.itmk.web.sys_role_menu.entity.RoleMenu;
import com.itmk.web.sys_role_menu.mapper.RoleMenuMapper;
import com.itmk.web.sys_role_menu.service.RoleMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: AlbertHPW
 * @Date: 2024/12/4 21:18
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    @Transactional
    public void saveRoleMenu(SaveMenuParm parm) {
        // 先删除
        QueryWrapper<RoleMenu> query = new QueryWrapper<>();
        query.lambda().eq(RoleMenu::getRoleId, parm.getRoleId());
        this.baseMapper.delete(query);
        // 再保存
        this.baseMapper.saveRoleMenu(parm.getRoleId(), parm.getList());
    }
}
```

### 4、SysRoleController控制器添加saveRoleMenu方法

```java
 // 保存角色菜单
@PostMapping("/saveRoleMenu")
public ResultVo saveRoleMenu(@RequestBody SaveMenuParm parm) {
    roleMenuService.saveRoleMenu(parm);
    return ResultUtils.success("分配成功");
}
```

### 5、RoleModel.ts添SaveMenuParm

```typescript
// 分配菜单数据类型
export type saveMenuParm = {
  roleId: string
  list: Array<string>
}
```

### 6、api/role/index.ts添加saveRoleMenuApi方法

```ty
// 分配角色菜单
export const saveRoleMenuApi = (parm: saveMenuParm) => {
  return http.post('/api/role/saveRoleMenu', parm)
}
```

### 7、AssignTree.vue组件

```vue
<template>
  <SysDialog
    :title="dialog.title"
    :visible="dialog.visible"
    :width="dialog.width"
    :height="dialog.height"
    @onClose="onClose"
    @onConfirm="commit"
  >
    <!-- :default-expanded-keys="assignTreeData.assignTreeChecked" -->
    <template v-slot:content>
      <el-tree
        ref="treeRef"
        node-key="menuId"
        :data="assignTreeData.list"
        :props="defaultProps"
        :default-checked-keys="assignTreeData.assignTreeChecked"
        show-checkbox
        default-expand-all
      />
    </template>
  </SysDialog>
</template>

<script setup lang="ts">
import { getAssignTreeApi } from '@/api/user'
import { AssignParm } from '@/api/user/UserModel'
import SysDialog from '@/components/SysDialog.vue'
import useDialog from '@/hooks/useDialog'
import { useUserStore } from '@/store/user'
import { saveRoleMenuApi } from '@/api/role'
import { ElMessage, ElTree } from 'element-plus'

const { getUserId } = useUserStore()

const { dialog, onClose, onConfirm, onShow } = useDialog()

const commitParm = reactive({
  roleId: '',
  list: [] as string[]
})

// 树数据
const assignTreeData = reactive({
  list: [],
  assignTreeChecked: [] as number[] // 原来分配的权限id集合
})
const treeRef = ref<InstanceType<typeof ElTree>>()
// 树的属性配置
const defaultProps = {
  children: 'children',
  label: 'title'
}
// 树数据查询的参数
const parms = reactive<AssignParm>({
  userId: '',
  roleId: ''
})
// 查询菜单树
const getAssignTree = async () => {
  parms.userId = getUserId
  let res = await getAssignTreeApi(parms)
  if (res && res.code === 200) {
    // 设置权限树数据
    assignTreeData.list = res.data.menuList
    // 设置角色原来的权限Id
    assignTreeData.assignTreeChecked = res.data.checkList
    // 数据回显，判断角色原来是否已经分配过权限，如果有，回显
    if (assignTreeData.assignTreeChecked.length > 0) {
      let newArr: any = []
      assignTreeData.assignTreeChecked.forEach(item => {
        checked(item, assignTreeData.list, newArr)
      })
      assignTreeData.assignTreeChecked = newArr
    }
  }
}
const checked = (id: number, data: any, newArr: any) => {
  data.forEach((item: any) => {
    if (item.menuId == id) {
      if (item.children && item.children.length == 0) {
        newArr.push(item.menuId)
      }
    } else {
      if (item.children && item.children.length != 0) {
        // 递归调用
        checked(id, item.children, newArr)
      }
    }
  })
}

// 提交
const commit = async () => {
  // 获取选择的菜单数据
  console.log(treeRef.value!.getCheckedKeys())
  console.log(treeRef.value!.getHalfCheckedKeys())
  const checkIds = treeRef.value!.getCheckedKeys() as string[]
  const halfCheckIds = treeRef.value!.getHalfCheckedKeys() as string[]
  let ids = checkIds?.concat(halfCheckIds)
  // 设置选中的节点
  commitParm.list = ids

  // 判断是否已经选择菜单
  if (ids.length === 0) {
    ElMessage.error('请选择菜单')
    return
  }
  let res = await saveRoleMenuApi(commitParm)
  if (res && res.code === 200) {
    ElMessage.success(res.msg)
    onClose()
  }
}

// 弹框显示
const show = async (roleId: string, name: string) => {
  commitParm.roleId = roleId
  assignTreeData.assignTreeChecked = []
  assignTreeData.list = []
  commitParm.list = []
  parms.roleId = roleId
  dialog.height = 450
  dialog.width = 300
  dialog.title = `为【${name}】分配菜单`
  // 获取树数据
  await getAssignTree()
  onShow()
}
// 暴露出去，给外部组件使用
defineExpose({
  show
})
</script>

<style scoped></style>
```



## 48、个人中心头像布局

### 1、layout/header下创建LoginOut.vue组件

```vue
<template>
  <el-dropdown>
    <span class="el-dropdown-link">
      <img class="userimg" src="@/assets/login.jpg" />
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item>修改密码</el-dropdown-item>
        <el-dropdown-item>Action 2</el-dropdown-item>
        <el-dropdown-item>Action 3</el-dropdown-item>
        <el-dropdown-item divided>退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script setup lang="ts"></script>

<style lang="scss" scoped>
.el-dropdown-link {
  outline: none;
  .userimg {
    width: 48px;
    height: 48px;
    border-radius: 50%;
    cursor: pointer;
  }
}
</style>
```

### 2、Header.vue里面使用

```vue
<template>
  <div class="header-container">
    <div class="left">
      <Collapse />
      <Breadcrumb />
    </div>
    <div class="right">
      <span>欢迎您，{{ nickName }}</span>
      <LoginOut />
    </div>
  </div>
</template>

<script setup lang="ts">
import Collapse from './Collapse.vue'
import Breadcrumb from './Breadcrumb.vue'
import LoginOut from './LoginOut.vue'
import { useUserStore } from '@/store/user'

const { nickName } = useUserStore()
</script>

<style lang="scss" scoped>
.header-container {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  .left {
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .right {
    display: flex;
    align-items: center;
    justify-content: center;
    span {
      color: #fff;
      margin-right: 20px;
    }
  }
}
/* :deep(.el-dropdown-link:focus) {
  outline: none;
} */
</style>
```



## 49、修改密码功能开发

### 1、新建参数接收实体类

```java
package com.itmk.web.sys_user.entity;

import lombok.Data;

/**
 * @Author: AlbertHPW
 * @Date: 2024/12/10 23:20
 */
@Data
public class UpdatePasswordPram {
    private Long userId;
    private String oldPassword;
    private String password;
}
```

### 2、SysUserController控制器添加updatePassword方法

```java
// 修改密码
@PostMapping("/updatePassword")
public ResultVo updatePassword(@RequestBody UpdatePasswordPram parm) {
    SysUser user = sysUserService.getById(parm.getUserId());
    if (!parm.getOldPassword().equals(user.getPassword())) {
        return ResultUtils.error("原密码不正确！");
    }
    // 更新条件
    UpdateWrapper<SysUser> query = new UpdateWrapper<>();
    query.lambda().set(SysUser::getPassword, parm.getPassword()).eq(SysUser::getUserId, parm.getUserId());
    if (sysUserService.update(query)) {
        return ResultUtils.success("密码修改成功！");
    }
    return ResultUtils.error("密码修改失败！");
}
```

### 3、api/user/index.ts添加updatePasswordApi方法

添加参数类型

```typescript
// 修改密码参数
export type UpdatePwd = {
  userId: string
  oldPassword: string
  password: string
}
```

添加updatePasswordApi方法

```typescript
import http from '@/http'
import { AssignParm, Login, SysUser, UpdatePwd, UserListParm } from './UserModel'

// 新增用户
export const addUserApi = (parm: SysUser) => {
  return http.post('/api/sysUser', parm)
}

// 用户列表
export const getUserListApi = (parm: UserListParm) => {
  return http.get('/api/sysUser/list', parm)
}

// 删除用户
export const deleteUserApi = (userId: string) => {
  return http.delete(`/api/sysUser/${userId}`)
}

// 根据用户Id查询角色
export const getUserRoleApi = (userId: string) => {
  return http.get(`/api/sysUser/getRoleList`, { userId })
}

// 编辑用户
export const editUserApi = (parm: SysUser) => {
  return http.put('/api/sysUser', parm)
}

// 重置密码
export const resetPasswordApi = (parm: { userId: string }) => {
  return http.post('/api/sysUser/resetPassword', parm)
}

// 验证码
export const getCaptchaApi = () => {
  return http.get('/api/sysUser/captcha')
}

// 登录
export const loginApi = (parm: Login) => {
  return http.post('/api/sysUser/login', parm)
}

//查询菜单树
export const getAssignTreeApi = (pram: AssignParm) => {
  return http.get(`/api/sysUser/getAssignTree`, pram)
}

// 修改密码
export const updatePasswordApi = (parm: UpdatePwd) => {
  return http.post('/api/sysUser/updatePassword', parm)
}
```

### 4、LoginOut.vue组件

```vue
<template>
  <el-dropdown>
    <span class="el-dropdown-link">
      <img class="userimg" src="@/assets/login.jpg" />
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item @click="updateBtn">修改密码</el-dropdown-item>
        <el-dropdown-item divided>退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
  <SysDialog
    :title="dialog.title"
    :visible="dialog.visible"
    :width="dialog.width"
    :height="dialog.height"
    @onClose="onClose"
    @onConfirm="commit"
  >
    <template v-slot:content>
      <el-form :model="upModel" ref="formRef" :rules="rules" label-width="80px" :inline="false" size="default">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="upModel.oldPassword" type="password"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="password">
          <el-input v-model="upModel.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="upModel.confirmPassword" type="password"></el-input>
        </el-form-item>
      </el-form>
    </template>
  </SysDialog>
</template>

<script setup lang="ts">
import SysDialog from '@/components/SysDialog.vue'
import useDialog from '@/hooks/useDialog'
import { ElMessage, type FormInstance } from 'element-plus'
import { updatePasswordApi } from '@/api/user'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'

const { dialog, onClose, onShow } = useDialog()
const router = useRouter()
const { userId } = useUserStore()

const formRef = ref<FormInstance>()

// 表单数据
const upModel = reactive({
  userId: userId,
  oldPassword: '',
  password: '',
  confirmPassword: ''
})

// 表单验证规则
const rules = reactive({
  oldPassword: [
    {
      required: true,
      message: '原密码不能为空',
      trigger: ['blur', 'change']
    }
  ],
  password: [
    {
      required: true,
      message: '新密码不能为空',
      trigger: ['blur', 'change']
    }
  ],
  confirmPassword: [
    {
      required: true,
      message: '确认密码不能为空',
      trigger: ['blur', 'change']
    }
  ]
})

// 修改密码
const updateBtn = () => {
  dialog.title = '修改密码'
  onShow()
}
// 确认
const commit = () => {
  formRef.value?.validate(async valid => {
    if (valid) {
      console.log('submit!')
      // 判断新密码和确认密码是否一致
      if (upModel.password !== upModel.confirmPassword) {
        ElMessage.error('新密码和确认密码不一致')
        return
      }
      let res = await updatePasswordApi(upModel)
      if (res && res.code === 200) {
        ElMessage.success(res.msg)
        // 清空缓存
        sessionStorage.clear()
        router.push('/login')
      } else {
        ElMessage.error(res.msg)
      }
    } else {
      console.log('error submit!')
    }
  })
}
</script>

<style lang="scss" scoped>
.el-dropdown-link {
  outline: none;
  .userimg {
    width: 48px;
    height: 48px;
    border-radius: 50%;
    cursor: pointer;
  }
}
</style>
```



## 50、项目整合jwt、token

token依赖

在父工程中添加

```xml
 <jwt.version>3.10.3</jwt.version>
```

```xml
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
    <version>${jwt.version}</version>
</dependency>
```

在子工程中添加

```xml
<!--jwt-->
<dependency>
    <groupId>com.auth0</groupId>
    <artifactId>java-jwt</artifactId>
</dependency>
```

### 1、jwt工具类讲解

#### 1.1、jwt配置文件

```yaml
# jwt配置
jwt:
  #颁发者
  issuer: itmk
  #密钥
  secret: com.itmk
  #过期时间:30分钟
  expiration: 30
```

#### 1.2、JwtUtils工具

```java
```

#### 1.3、Spring Boot Configuration Annotation Processor no tconfigured解决方法

在【itmk-base-web】工程中添加

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
</dependency>
```

### 2、修改SysUserController控制器的登录方法login为如下

```java
// 登录
@PostMapping("/login")
public ResultVo login(@RequestBody LoginParm parm, HttpServletRequest request) {
    // 从session里面获取code验证码
    HttpSession session = request.getSession();
    String code = (String) session.getAttribute("code");
    // 获取从前端传递过来的验证码
    String codeParm = parm.getCode();
    if (StringUtils.isEmpty(code)) {
        return ResultUtils.error("验证码已失效");
    }
    // 对比验证码
    if (!codeParm.equals(code)) {
        return ResultUtils.error("验证码错误");
    }
    // 验证用户信息
    // DigestUtils.md5DigestAsHex(parm.getPassword().getBytes())
    QueryWrapper<SysUser> query = new QueryWrapper<>();
    query.lambda().eq(SysUser::getUsername, parm.getUsername())
        .eq(SysUser::getPassword, parm.getPassword());
    SysUser user = sysUserService.getOne(query);
    if (user == null) {
        return ResultUtils.error("用户名或密码错误");
    }
    //if(user.get)
    // 返回登录信息
    LoginVo vo = new LoginVo();
    vo.setUserId(user.getUserId());
    vo.setNickName(user.getNickName());
    // 生成token
    Map<String, String> map = new HashMap<>();
    map.put("userId", user.getUserId().toString());
    String token = jwtUtils.generateToken(map);
    vo.setToken(token);
    return ResultUtils.success("登录成功", vo);
}
```

### 3、ore/user/index.ts添加token字段和相应的方法

```typescript
import { defineStore } from 'pinia'

// 定义UserState类型
interface UserState {
  userId: string
  nickName: string
  token: string
}

// 定义Store
export const useUserStore = defineStore('userStore', {
  state: (): UserState => ({
    userId: '',
    nickName: '',
    token: ''
  }),
  getters: {
    getUserId(state) {
      return state.userId
    },
    getNickName(state) {
      return state.nickName
    },
    getToken(state) {
      return state.token
    }
  },
  actions: {
    setUserId(userId: string) {
      this.userId = userId
    },
    setNickName(nickName: string) {
      this.nickName = nickName
    },
    setToken(token: string) {
      this.token = token
    }
  },
  persist: [
    {
      key: 'user',
      //   pick: ['userId', 'nickName', 'token'],
      storage: localStorage
    }
  ]
})
```

### 4、login.vue登录页面存储token

```js
// 登录
const onSubmit = () => {
  form.value.validate(async (valid: boolean) => {
    if (valid) {
      let res = await loginApi(loginModel)
      if (res && res.code === 200) {
        // 存储用户信息
        setUserId(res.data.userId)
        setNickName(res.data.nickName)
        setToken(res.data.token)
        // 跳转首页
        router.push({ path: '/' })
      }
    } else {
      console.log('error submit!!')
      return false
    }
  })
}
```



## 51、获取用户信息接口开发

### 1、新建实体类

```java
package com.itmk.web.sys_user.entity;

import lombok.Data;

/**
 * @Author: AlbertHPW
 * @Date: 2024/12/11 22:05
 */
@Data
public class UserInfo {
    private Long userId;
    private String name;
    private Object[] permissons;
}
```

### 2、SysUserController控制台添加getInfo方法

````java
// 获取用户信息
@GetMapping("/getInfo")
public ResultVo getInfo(Long userId){
    // 根据用户ID查询用户信息
    SysUser user = sysUserService.getById(userId);
    List<SysMenu> menuList = null;
    // 判断是否是超级管理员
    if(StringUtils.isNotEmpty(user.getIsAdmin()) && "1".equals(user.getIsAdmin())){
        // 超级管理员,查询所有菜单
        menuList = sysMenuService.list();
    } else {
        menuList = sysMenuService.getMenuByUserId(userId);
    }
    // 获取菜单的code
    List<String> collect = Optional.ofNullable(menuList).orElse(new ArrayList<>())
        .stream()
        .map(item -> item.getCode())
        .filter(code -> StringUtils.isNotEmpty(code))
        .collect(Collectors.toList());
    // 转换为数组
    //String[] strings = collect.toArray(new String[collect.size()]);
    // 设置返回值
    UserInfo userInfo = new UserInfo();
    userInfo.setName(user.getNickName());
    userInfo.setUserId(userId);
    userInfo.setPermissons(collect.toArray());
    return ResultUtils.success("查询成功！", userInfo);
}
````



## 52、动态菜单接口开发

菜单数据格式

```js
[
  {
    path: '/system',
    component: 'Layout',
    name: 'system',
    meta: {
      title: '系统管理',
      icon: 'Setting',
      roles: ['sys:admin']
    },
    children: [
      {
        path: '/userList',
        component: '/system/User/UserList',
        name: 'userList',
        meta: {
          title: '用户管理',
          icon: 'UserFilled',
          roles: ['sys:user']
        }
      },
      {
        path: '/roleList',
        component: '/system/Role/RoleList',
        name: 'roleList',
        meta: {
          title: '角色管理',
          icon: 'Wallet',
          roles: ['sys:role']
        }
      },
      {
        path: '/menuList',
        component: '/system/Menu/MenuList',
        name: 'menuList',
        meta: {
          title: '菜单管理',
          icon: 'Menu',
          roles: ['sys:menu']
        }
      }
    ]
  },
  {
    path: '/goodsRoot',
    component: 'Layout',
    name: 'goodsRoot',
    meta: {
      title: '商品管理',
      icon: 'Setting',
      roles: ['sys:goodsRoot']
    },
    children: [
      {
        path: '/category',
        component: '/goods/Category',
        name: 'category',
        meta: {
          title: '商品类型',
          icon: 'UserFilled',
          roles: ['sys:category']
        }
      },
      {
        path: '/goodsList',
        component: '/goods/GoodsList',
        name: 'goodsList',
        meta: {
          title: '商品信息',
          icon: 'Wallet',
          roles: ['sys:goodsList']
        }
      }
    ]
  }
]
```

### 1、新建实体类

```java
package com.itmk.web.sys_menu.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @JsonInclude(JsonInclude.Include.NON_EMPTY): null、集合数组等没有内容、空字符串等，都不会被序列化
 * @Author: AlbertHPW
 * @Date: 2024/12/11 22:46
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVo {
    private String path;
    private String component;
    private String name;
    private String redirect;
    private Meta meta;

    @Data
    @AllArgsConstructor
    public class Meta {
        private String title;
        private String icon;
        private Object[] roles;
    }

    private List<RouterVo> children = new ArrayList<>();

}
```

### 2、MakeMenuTree添加makeRourer方法

```java
// 构造路由数据
public static List<RouterVo> makeRouter(List<SysMenu> menuList, Long pid) {
    // 构建存放路由数据的容器
    List<RouterVo> list = new ArrayList<>();
    Optional.ofNullable(menuList).orElse(new ArrayList<>())
        .stream()
        .filter(item -> item != null && item.getParentId().equals(pid))
        .forEach(item -> {
            // 组装
            RouterVo router = new RouterVo();
            // 设置路由名称
            router.setName(item.getName());
            // 设置路由路径
            router.setPath(item.getPath());
            // 设置children 递归调用：自己调用自己
            List<RouterVo> children = makeRouter(menuList, item.getMenuId());
            router.setChildren(children);
            if (item.getParentId() == 0L) { // 如果上级是0，那么他的Component是Layout
                router.setComponent("Layout");
                // 判断该数据是目录还是菜单
                if ("1".equals(item.getType())) { // 如果一级菜单是菜单类型，单独处理
                    router.setRedirect(item.getPath());
                    // 菜单需要设置children
                    List<RouterVo> listChild = new ArrayList<>();
                    RouterVo child = new RouterVo();
                    child.setName(item.getName());
                    child.setPath(item.getPath());
                    child.setComponent(item.getUrl());
                    child.setMeta(child.new Meta(
                        item.getTitle(),
                        item.getIcon(),
                        item.getCode().split(",")
                    ));
                    listChild.add(child);
                    router.setChildren(listChild);
                    router.setPath(item.getPath() + "parent");
                    router.setName(item.getName() + "parent");
                }
            } else {
                router.setComponent(item.getUrl());
            }
            router.setMeta(router.new Meta(
                item.getTitle(),
                item.getIcon(),
                item.getCode().split(",")
            ));
            list.add(router);
        });
    return list;
}
```

### 3、SysUserController控制台添加getMenuList方法

```java
// 获取路由菜单
@GetMapping("/getMenuList")
public ResultVo getMenuList(Long userId) {
    // 获取用户信息
    SysUser user = sysUserService.getById(userId);
    // 菜单数据
    List<SysMenu> menuList = null;
    // 判断是否是超级管理员
    if(StringUtils.isNotEmpty(user.getIsAdmin()) && "1".equals(user.getIsAdmin())){
        menuList = sysMenuService.list();
    } else {
        menuList = sysMenuService.getMenuByUserId(userId);
    }
    // 过滤菜单数据,去掉按钮数据
    List<SysMenu> collect = Optional.ofNullable(menuList).orElse(new ArrayList<>())
        .stream()
        .filter(item -> StringUtils.isNotEmpty(item.getType()) && !"2".equals(item.getType()))
        .collect(Collectors.toList());
    // 组装路由数据
    List<RouterVo> router = MakeMenuTree.makeRouter(collect, 0L);

    return  ResultUtils.success("查询成功！", router);
}
```



## 53、动态路由生成

### 1、http/index.ts请求头部添加token

```typescript
import axios, { AxiosInstance, InternalAxiosRequestConfig, AxiosResponse, AxiosRequestConfig } from 'axios'
import { ElMessage } from 'element-plus'
import { Result } from '@/types'
import { useUserStore } from '@/store/user'

const config = {
  //   baseURL: 'http://localhost:8089', // 请求接口的地址
  baseURL: '/api', // 请求接口的地址
  timeout: 5000, // 请求超时时间
  withCredentials: true // 是否允许携带凭证
  //   headers: {
  //     'Content-Type': 'application/json;charset=UTF-8'
  //   }
}

// axios.defaults.withCredentials = true // 携带cookie

class Http {
  // 初始化axios
  private instance: AxiosInstance
  // 初始化
  constructor(configs: AxiosRequestConfig) {
    // 创建axios实例
    this.instance = axios.create(configs)
    // 配置拦截器
    this.interceptors()
  }
  // 请求发送，返回之后处理
  private interceptors() {
    // 添加请求拦截器
    this.instance.interceptors.request.use(
      (config: InternalAxiosRequestConfig) => {
        let store = useUserStore()
        let user = JSON.parse(localStorage.getItem('user') || '{}')

        // 获取token
        let token = store.getToken || user.token
        if (token) {
          //   config.headers['Authorization'] = token
          config.headers!['token'] = token
        }
        return config
      },
      (error: any) => {
        error.data = {}
        error.data.msg = '服务器异常，请联系管理员!'
        return error
      }
    )
    // 添加响应拦截器
    this.instance.interceptors.response.use(
      (response: AxiosResponse) => {
        if (response.data.code === 200) {
          return response.data
        } else {
          ElMessage.error(response.data.msg || '接口报错')
          return Promise.reject(response.data.msg || '接口报错')
        }
      },
      (error: any) => {
        error.data = {}
        if (error & error.response) {
          switch (error.response.status) {
            case 400:
              error.data.msg = '错误请求'
              ElMessage.error(error.data.msg)
              break
            case 401:
              error.data.msg = '未授权，请重新登录'
              ElMessage.error(error.data.msg)
              break
            case 403:
              error.data.msg = '拒绝访问'
              ElMessage.error(error.data.msg)
              break
            case 404:
              error.data.msg = '请求错误，未找到接口'
              ElMessage.error(error.data.msg)
              break
            case 405:
              error.data.msg = '请求方法未允许'
              ElMessage.error(error.data.msg)
              break
            case 408:
              error.data.msg = '请求超时'
              ElMessage.error(error.data.msg)
              break
            case 500:
              //   error.data.msg = '服务器端出错'
              error.data.msg = '后台接口出错'
              ElMessage.error(error.data.msg)
              break
            case 501:
              error.data.msg = '网络未实现'
              ElMessage.error(error.data.msg)
              break
            case 502:
              error.data.msg = '网铬错误'
              ElMessage.error(error.data.msg)
              break
            case 503:
              error.data.msg = '服务不可用'
              ElMessage.error(error.data.msg)
              break
            case 504:
              error.data.msg = '网铬超时'
              ElMessage.error(error.data.msg)
              break
            case 505:
              error.data.msg = 'http版本不支持该请求'
              ElMessage.error(error.data.msg)
              break
            default:
              error.data.msg = `连接错误${error.response.status}`
              ElMessage.error(error.data.msg)
          }
        } else {
          error.data.msg = '连接到服务器失败'
          ElMessage.error(error.data.msg)
        }
        return Promise.reject(error)
      }
    )
  }
  // Post
  post<T = Result>(url: string, data?: object): Promise<T> {
    return this.instance.post(url, data)
  }
  //Put
  put<T = Result>(url: string, data?: object): Promise<T> {
    return this.instance.put(url, data)
  }
  //Get
  get<T = Result>(url: string, params?: object): Promise<T> {
    return this.instance.get(url, { params })
  }
  // Delete
  delete<T = Result>(url: string): Promise<T> {
    return this.instance.delete(url)
  }
  // 图片上传
  upload<T = Result>(url: string, params?: object): Promise<T> {
    return this.instance.post(url, params, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  }
}

export default new Http(config)
```

### 2、api/user/index.ts添加getMenuListApi方法

```typescript
// 获取路由菜单
export const getMenuListApi = (userId: string) => {
  return http.get('/api/sysMenu/getMenuList', { userId })
}
```

### 3、router路由添加constantRoutes，修改为如下

constantRoutes 是静态路由，不需要动态权限判断

```typescript
 // 路由菜单数据
menuList: [
    {
        path: '/dashboard',
        component: 'Layout',
        name: 'Dashboard',
        meta: {
            title: '首页',
            icon: 'HomeFilled',
            roles: ['sys:dashboard']
        }
    }
]
```

### 4、store/menu/index.ts添加加menuList和生成动态路由

```typescript
import { defineStore } from 'pinia'
import { getMenuListApi } from '@/api/menu'
import { RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/Index.vue'
import Center from '@/layout/center/Center.vue'
// 获取views下面所有.vue文件
const modules = import.meta.glob('../../views/**/*.vue')

// 定义Store
export const useMenuStore = defineStore('menuStore', {
  state: () => ({
    collapse: false,
    // 路由菜单数据
    menuList: [
      {
        path: '/dashboard',
        component: 'Layout',
        name: 'Dashboard',
        meta: {
          title: '首页',
          icon: 'HomeFilled',
          roles: ['sys:dashboard']
        }
      }
    ]
  }),
  getters: {
    getCollapse(state) {
      return state.collapse
    }
  },
  actions: {
    setCollapse(collapse: boolean) {
      this.collapse = collapse
    },
    // 获取路由菜单
    setMenuList(router: any, userId: string) {
      return new Promise((resolve, reject) => {
        // 调用接口获取菜单数据
        getMenuListApi(userId)
          .then((res: any) => {
            let accessRoutes = null
            if (res && res.code === 200) {
              // 生成路由
              accessRoutes = generateRoute(res.data, router) as any
              this.menuList = this.menuList.concat(accessRoutes)
            }
            resolve(this.menuList)
          })
          .catch(error => {
            reject(error)
          })
      })
    }
  }
})

// 动态生成路由
export function generateRoute(routes: RouteRecordRaw[], router: any) {
  // 路由数据
  const res: Array<RouteRecordRaw> = []
  routes.forEach((route: any) => {
    const tmp = { ...route }
    const component = tmp.component
    if (route.component) {
      if (component === 'Layout') {
        tmp.component = Layout
      } else {
        tmp.component = modules[`${component}`]
        // tmp.component = () => import(`${component}`)
      }
    }
    // 有下级
    if (tmp.children && tmp.children.length > 0) {
      if (route.component != 'Layout') {
        tmp.component = Center
        // tmp.children = generateRoute(tmp.children, router)
      }
      // 递归生成下级
      tmp.children = generateRoute(tmp.children, router)
    }
    console.log(tmp, '路由菜单')
    res.push(tmp)
    // 加入路由
    router.addRoute(tmp)
  })
}
```

### 5、layout下新建center，并建Center.vue组件

```vue
<template>
  <
  <router-view />
</template>

<script setup lang="ts"></script>

<style scoped></style>
```



## 54、用户信息接口对接

### 1、api/user/index.ts添加getlnfoApi方法

```typescript
// 获取用户信息
export const getUserInfoApi = (userId: string) => {
  return http.get(`/api/sysUser/getInfo`, { userId })
}
```

### 2、store/user/index.ts添加codeList和getlnfo方法

```typescript
import { defineStore } from 'pinia'
import { getUserInfoApi } from '@/api/user'

// 定义UserState类型
interface UserState {
  userId: string
  nickName: string
  token: string
  codeList: any
}

// 定义Store
export const useUserStore = defineStore('userStore', {
  state: (): UserState => ({
    userId: '',
    nickName: '',
    token: '',
    codeList: []
  }),
  getters: {
    getUserId(state) {
      return state.userId
    },
    getNickName(state) {
      return state.nickName
    },
    getToken(state) {
      return state.token
    },
    getCodeList(state) {
      return state.codeList
    }
  },
  actions: {
    setUserId(userId: string) {
      this.userId = userId
    },
    setNickName(nickName: string) {
      this.nickName = nickName
    },
    setToken(token: string) {
      this.token = token
    },
    getUserInfo() {
      return new Promise<void>((resolve, reject) => {
        getUserInfoApi(this.userId)
          .then(res => {
            if (res && res.code === 200) {
              this.codeList = res.data.permissons
            }
            resolve(this.codeList)
          })
          .catch(error => {
            reject(error)
          })
      })
    }
  },
  persist: [
    {
      key: 'user',
      //   pick: ['userId', 'nickName', 'token'],
      storage: localStorage
    }
  ]
})
```



## 55、权限验证流程讲解

### 1、权限验证流程图

![10003](.\images\10003.png)



## 56、动态路由生成对接

### 1、router路由添加添加constantRoutes，修改为如下

```typescript
import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/Index.vue'

// 动态生成
export const constantRoutes: Array<RouteRecordRaw> = [
  {
    path: '/login',
    component: () => import('@/views/login/login.vue'),
    name: 'login'
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/Index.vue'),
        meta: {
          title: '首页',
          icon: 'House'
        }
      }
    ]
  }
]
// 静态路由
const routes: Array<RouteRecordRaw> = [
  {
    path: '/login',
    component: () => import('@/views/login/login.vue'),
    name: 'login'
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/Index.vue'),
        meta: {
          title: '首页',
          icon: '#icondashboard'
        }
      }
    ]
  },
  {
    path: '/system',
    component: Layout,
    name: 'system',
    redirect: '/userList',
    meta: {
      title: '系统管理',
      icon: 'Setting',
      roles: ['sys:manage']
    },
    children: [
      {
        path: '/userList',
        component: () => import('@/views/system/User/UserList.vue'),
        name: 'userList',
        meta: {
          title: '用户管理',
          icon: 'UserFilled',
          roles: ['sys:user']
        }
      },
      {
        path: '/roleList',
        component: () => import('@/views/system/Role/RoleList.vue'),
        name: 'roleList',
        meta: {
          title: '角色管理',
          icon: 'Wallet',
          roles: ['sys:role']
        }
      },
      {
        path: '/menuList',
        component: () => import('@/views/system/Menu/MenuList.vue'),
        name: 'menuList',
        meta: {
          title: '菜单管理',
          icon: 'Menu',
          roles: ['sys:menu']
        }
      }
    ]
  },
  {
    path: '/goodsRoot',
    component: Layout,
    name: 'goodsRoot',
    redirect: '/category',
    meta: {
      title: '商品管理',
      icon: 'Setting',
      roles: ['sys:goodsRoot']
    },
    children: [
      {
        path: '/category',
        component: () => import('@/views/category/CategoryList.vue'),
        name: 'category',
        meta: {
          title: '商品类型',
          icon: 'UserFilled',
          roles: ['sys:category']
        }
      },
      {
        path: '/goodsList',
        component: () => import('@/views/goods/GoodsList.vue'),
        name: 'goodsList',
        meta: {
          title: '商品信息',
          icon: 'Wallet',
          roles: ['sys:goodsList']
        }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
```



## 57、动态菜单数据对接

### 1、layout/menu/MenuBar.vue修改menuList为如下

```vue
<template>
  <MenuLogo />
  <el-menu
    :default-active="$route.path"
    class="el-menu-vertical-demo"
    :collapse="menuStore.getCollapse"
    unique-opened
    background-color="#304156"
    router
  >
    <MenuItem :menuList="menuList" />
  </el-menu>
</template>

<script setup lang="ts">
import MenuLogo from '@/layout/menu/MenuLogo.vue'
import MenuItem from '@/layout/menu/MenuItem.vue'

import { useMenuStore } from '@/store/menu'

const menuStore = useMenuStore()

// 方式一
// const route = useRoute()
// // 获取激活的菜单
// const defaultActive = computed(() => {
//     const { path } = route
//     console.log(path)
//     return path
// })

// 菜单数据
const menuList = computed(() => {
  return menuStore.getMenu
})
// let menuList = reactive([
//   {
//     path: '/dashboard',
//     component: '/dashboard/Index',
//     name: 'dashboard',
//     meta: {
//       title: '首页',
//       icon: 'House',
//       roles: ['sys:dashboard']
//     }
//   },
//   {
//     path: '/system',
//     component: 'Layout',
//     name: 'system',
//     meta: {
//       title: '系统管理',
//       icon: 'Setting',
//       roles: ['sys:admin']
//     },
//     children: [
//       {
//         path: '/userList',
//         component: '/system/User/UserList',
//         name: 'userList',
//         meta: {
//           title: '用户管理',
//           icon: 'UserFilled',
//           roles: ['sys:user']
//         }
//       },
//       {
//         path: '/roleList',
//         component: '/system/Role/RoleList',
//         name: 'roleList',
//         meta: {
//           title: '角色管理',
//           icon: 'Wallet',
//           roles: ['sys:role']
//         }
//       },
//       {
//         path: '/menuList',
//         component: '/system/Menu/MenuList',
//         name: 'menuList',
//         meta: {
//           title: '菜单管理',
//           icon: 'Menu',
//           roles: ['sys:menu']
//         }
//       }
//     ]
//   },
//   {
//     path: '/goodsRoot',
//     component: 'Layout',
//     name: 'goodsRoot',
//     meta: {
//       title: '商品管理',
//       icon: 'Setting',
//       roles: ['sys:goodsRoot']
//     },
//     children: [
//       {
//         path: '/category',
//         component: '/goods/Category',
//         name: 'category',
//         meta: {
//           title: '商品类型',
//           icon: 'UserFilled',
//           roles: ['sys:category']
//         }
//       },
//       {
//         path: '/goodsList',
//         component: '/goods/GoodsList',
//         name: 'goodsList',
//         meta: {
//           title: '商品信息',
//           icon: 'Wallet',
//           roles: ['sys:goodsList']
//         }
//       }
//     ]
//   }
// ])
</script>

<style lang="scss" scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 230px;
  min-height: 400px;
}

.el-menu {
  border-right: none;
}

:deep(.el-sub-menu .el-sub-menu__title) {
  color: #f4f4f5 !important;
}

:deep(.el-menu .el-menu-item) {
  color: #bfcbd9;
}

:deep(.el-menu-item.is-active) {
  color: #409eff !important;
}

:deep(.is-opened .el-menu-item) {
  background-color: #1f2d3d important;
}

:deep(.el-menu-item:hover) {
  background-color: #001528 !important;
}
</style>
```



## 58、自定义指令按钮权限

### 1、src下新建directive目录，并新建permission.ts

```typescript
import { useUserStore } from '@/store/user'
import { Directive } from 'vue'

// 自定义按钮权限指令
export const permission: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    const { value } = binding
    // if (!value) return
    // 按钮权限字段
    const store = useUserStore()
    const codeList = JSON.parse(localStorage.getItem('codeList') || '[]')
    if (value && value instanceof Array && value.length > 0) {
      // 判断按钮权限是否存在
      const permissionRoles = value
      const roleList = store.getCodeList || codeList
      const hasPermission = roleList.some((role: any) => {
        return permissionRoles.includes(role)
      })
      // 如果没有权限，则移除当前元素
      if (!hasPermission) {
        el.style.display = 'none'
      }
    } else {
      throw new Error(`need roles! Like v-permission="['sys:user:add','sys:user:update']"`)
    }
  }
}
```

### 2、main.ts里面引入

```typescript
import { createApp } from 'vue'
import '@/assets/styles/style.css'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import router from './router/index'

import piniaPluginPersistedstate from 'pinia-plugin-persistedstate' // 引入持久化插件
import myconfirm from './utils/myconfirm'
// 权限验证
import './permissons'
// 按钮权限
// 方式一：自定义指令
// import { permission } from '@/directive/permission'
// 方式二：全局挂载
import hasPerm from '@/directive/hasPerm'

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate) // 使用持久化插件

const app = createApp(App)
// app.directive('permission', permission)
app.config.globalProperties.$hasPerm = hasPerm
app.use(ElementPlus, {
  locale: zhCn
})

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.use(router)
app.use(pinia)
// 全局挂载
app.config.globalProperties.$myconfirm = myconfirm

app.mount('#app')
```

### 3、使用方式

```
v-permission="['sys:user:add','sys:user:update']"
```



## 59、按钮权限实现方式2

### 1、directive目录下新建hasPerm.ts

```typescript
import { useUserStore } from '@/store/user'

export default function hasPerm(value: any) {
  // 按钮权限字段
  const store = useUserStore()
  const codeList = JSON.parse(localStorage.getItem('codeList') || '[]')
  if (value && value instanceof Array && value.length > 0) {
    // 判断按钮权限是否存在
    const permissionRoles = value
    const roleList = store.getCodeList || codeList
    const hasPermission = roleList.some((role: any) => {
      return permissionRoles.includes(role)
    })
    return hasPermission
  } else {
    throw new Error(` v-if="global.$hasPerm(['sys:user:add','sys:user:update']")`)
  }
}
```

### 2、main.ts里面引入挂载

```typescript
import { createApp } from 'vue'
import '@/assets/styles/style.css'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import router from './router/index'

import piniaPluginPersistedstate from 'pinia-plugin-persistedstate' // 引入持久化插件
import myconfirm from './utils/myconfirm'
// 权限验证
import './permissons'
// 按钮权限
// 方式一：自定义指令
// import { permission } from '@/directive/permission'
// 方式二：全局挂载
import hasPerm from '@/directive/hasPerm'

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate) // 使用持久化插件

const app = createApp(App)
// app.directive('permission', permission)
app.config.globalProperties.$hasPerm = hasPerm
app.use(ElementPlus, {
  locale: zhCn
})

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.use(router)
app.use(pinia)
// 全局挂载
app.config.globalProperties.$myconfirm = myconfirm

app.mount('#app')
```

### 3、页面使用

```
v-if="global.$hasPerm(['sys:user:update', 'sys:user:updatePassword', 'sys:user:delete'])"
```



## 60、项目整合echarts

echarts官网：https://echarts.apache.org/zh/index.html

### 1、安装

```sh
npm install echarts --save
```

### 2、main.ts引入全局挂载

```typescript
// echarts
import * as echarts from 'echarts'
app.config.globalProperties.$echarts = echarts
```

### 3、页面使用

```vue
<template>
  <el-main :style="{ height: mainHeight + 'px' }">
    <div style="display: flex">
      <el-card style="flex: 1">
        <template #header>
          <div class="card-header">订单统计</div>
        </template>
        <div ref="myChart" :style="{ width: '100%', height: '300px' }"></div>
      </el-card>
      <el-card style="flex: 1; margin-left: 20px">
        <template #header>
          <div class="card-header">热门商品</div>
        </template>
        <div ref="myChart1" :style="{ width: '100%', height: '300px' }"></div>
      </el-card>
      <el-card style="flex: 1; margin-left: 20px">
        <template #header>
          <div class="card-header">最受欢迎</div>
        </template>
        <div ref="myChart2" :style="{ width: '100%', height: '300px' }"></div>
      </el-card>
    </div>
  </el-main>
</template>

<script setup lang="ts">
import useInstance from '@/hooks/useInstance'

const { global } = useInstance()
const mainHeight = ref(0)
const myChart = ref(null)
const myChart1 = ref(null)
const myChart2 = ref(null)

// 柱状图
const charts1 = () => {
  const chartInstance = global.$echarts.init(myChart.value)
  let option = reactive({
    xAxis: {
      type: 'category',
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: [120, 200, 150, 80, 70, 110, 130],
        type: 'bar'
      }
    ]
  })
  chartInstance.setOption(option)
}
// 饼图
const charts2 = () => {
  const myChart = global.$echarts.init(myChart1.value)
  let option = reactive({
    title: {
      // text: 'Referer of a Website',
      subtext: 'Fake Data',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: '50%',
        data: [
          { value: 1048, name: 'Search Engine' },
          { value: 735, name: 'Direct' },
          { value: 580, name: 'Email' },
          { value: 484, name: 'Union Ads' },
          { value: 300, name: 'Video Ads' }
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  })
  myChart.setOption(option)
}
// 环形图
const charts3 = () => {
  const myChart = global.$echarts.init(myChart2.value)
  let option = reactive({
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '5%',
      left: 'center'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 40,
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: [
          { value: 1048, name: 'Search Engine' },
          { value: 735, name: 'Direct' },
          { value: 580, name: 'Email' },
          { value: 484, name: 'Union Ads' },
          { value: 300, name: 'Video Ads' }
        ]
      }
    ]
  })
  myChart.setOption(option)
}
onMounted(() => {
  charts1()
  charts2()
  charts3()
  nextTick(() => {
    mainHeight.value = window.innerHeight - 102
  })
})
</script>

<style scoped></style>
```



## 61、权限系统优化

### 1、菜单管理列表类型优化

```vue
<template>
  <el-main>
    <el-button type="primary" icon="Plus" size="default" @click="add" v-if="global.$hasPerm(['sys:menu:add'])">
      新增
    </el-button>

    <!-- 表格 -->
    <el-table
      :data="tableList"
      row-key="menuId"
      border
      stripe
      default-expand-all
      style="margin-top: 20px"
      :header-cell-style="{ 'text-align': 'center' }"
    >
      <el-table-column prop="title" label="菜单名称"></el-table-column>
      <el-table-column prop="icon" label="菜单图标">
        <template #default="scope">
          <el-icon v-if="scope.row.icon">
            <component :is="scope.row.icon" />
          </el-icon>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="菜单类型" width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.type === '0'" type="danger" size="default">目录</el-tag>
          <el-tag v-if="scope.row.type === '1'" type="success" size="default">菜单</el-tag>
          <el-tag v-if="scope.row.type === '2'" type="primary" size="default">按钮</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="parentName" label="上级菜单"></el-table-column>
      <el-table-column prop="name" label="路由名称"></el-table-column>
      <el-table-column prop="path" label="路由地址"></el-table-column>
      <el-table-column prop="url" label="组件路径" width="300"></el-table-column>
      <el-table-column prop="code" label="权限字段"></el-table-column>
      <el-table-column prop="orderNum" label="序号" width="60"></el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column
        label="操作"
        width="220"
        align="center"
        v-if="global.$hasPerm(['sys:menu:update', 'sys:menu:delete'])"
      >
        <template #default="scope">
          <el-button
            type="primary"
            icon="Edit"
            size="default"
            @click="handleEdit(scope.row)"
            v-if="global.$hasPerm(['sys:menu:update'])"
          >
            编辑
          </el-button>
          <el-button
            type="danger"
            icon="Delete"
            size="default"
            @click="handleDelete(scope.row.menuId)"
            v-if="global.$hasPerm(['sys:menu:delete'])"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <SysDialog
      :title="dialog.title"
      :visible="dialog.visible"
      :width="dialog.width"
      :height="dialog.height"
      @onClose="onClose"
      @onConfirm="commit"
    >
      <template v-slot:content>
        <el-form :model="addModel" ref="addForm" :rules="rules" label-width="80px" :inline="false" size="default">
          <el-form-item label="菜单类型" prop="type">
            <el-radio-group v-model="addModel.type">
              <el-radio :value="'0'">目录</el-radio>
              <el-radio :value="'1'">菜单</el-radio>
              <el-radio :value="'2'">按钮</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item label="上级菜单" prop="parentId">
                <el-tree-select
                  v-model="addModel.parentId"
                  :data="treeList"
                  :render-after-expand="false"
                  show-checkbox
                  default-expand-all
                  check-strictly
                  @check-change="treeCheckChange"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="菜单名称" prop="title">
                <el-input v-model="addModel.title"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20" v-if="addModel.type !== '2'">
            <el-col :span="12" :offset="0">
              <el-form-item label="菜单图标" prop="icon">
                <el-input v-model="addModel.icon"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="路由名称" prop="name">
                <el-input v-model="addModel.name"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item label="菜单序号" prop="orderNum">
                <el-input v-model="addModel.orderNum"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="权限字段" prop="code">
                <el-input v-model="addModel.code"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0" v-if="addModel.type !== '2'">
              <el-form-item label="路由地址" prop="path">
                <el-input v-model="addModel.path"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0" v-if="addModel.type === '1'">
              <el-form-item label="组件路径" prop="url">
                <el-input v-model="addModel.url"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </template>
    </SysDialog>
  </el-main>
</template>

<script setup lang="ts">
import SysDialog from '@/components/SysDialog.vue'
import useDialog from '@/hooks/useDialog'
import { FormInstance, ElMessage } from 'element-plus'
import { getParentApi, addMenuApi, getListApi, editApi, deleteApi } from '@/api/menu'
import { MenuType } from '@/api/menu/MenuModel'
import useInstance from '@/hooks/useInstance'

const { dialog, onClose, onShow } = useDialog()
const { global } = useInstance()

const addModel = reactive({
  menuId: '',
  parentId: '',
  title: '',
  code: '',
  name: '',
  path: '',
  url: '',
  type: '',
  icon: '',
  parentName: '',
  orderNum: ''
})
const addForm = ref<FormInstance>()

// 表单验证规则
const rules = reactive({
  parentId: [
    {
      required: true,
      message: '请选择上级菜单',
      trigger: ['blur', 'change']
    }
  ],
  title: [
    {
      required: true,
      message: '请输入菜单名称',
      trigger: ['blur', 'change']
    }
  ],
  code: [
    {
      required: true,
      message: '请输入权限字段',
      trigger: ['blur', 'change']
    }
  ],
  name: [
    {
      required: true,
      message: '请输入路由名称',
      trigger: ['blur', 'change']
    }
  ],
  path: [
    {
      required: true,
      message: '请输入路由地址',
      trigger: ['blur', 'change']
    }
  ],
  url: [
    {
      required: true,
      message: '请输入路由地址',
      trigger: ['blur', 'change']
    }
  ],
  type: [
    {
      required: true,
      message: '请输入菜单类型',
      trigger: ['blur', 'change']
    }
  ],
  icon: [
    {
      required: true,
      message: '请输入菜单图标',
      trigger: ['blur', 'change']
    }
  ],
  orderNum: [
    {
      required: true,
      message: '请输入菜单序号',
      trigger: ['blur', 'change']
    }
  ]
})

// 判断新增还是编辑的标识
const tags = ref('')

// 获取上级菜单数据
const treeList = ref([])
const getParent = async () => {
  let res = await getParentApi()
  if (res && res.code == 200) {
    treeList.value = res.data
  }
}

// 新增
const add = () => {
  tags.value = 'add'
  dialog.title = '新增角色'
  dialog.height = 300
  // 获取上级菜单
  getParent()
  // 显示弹框
  onShow()
  // 清空表单
  addForm.value?.resetFields()
}

// 提交表单
const commit = () => {
  if (!addForm) return
  addForm.value?.validate(async (valid, fields) => {
    if (valid) {
      console.log('submit!', addModel)
      let res = null
      if (tags.value == 'add') {
        res = await addMenuApi(addModel) //新增用户
      } else {
        res = await editApi(addModel) // 编辑用户
      }
      if (res && res.code == 200) {
        ElMessage.success(res.msg)
        getList()
        onClose()
      } else {
        ElMessage.error(res?.msg)
      }
    } else {
      console.log('error submit!', fields)
    }
  })
}

// 上级选中事件
const treeCheckChange = (item: any) => {
  console.log(item)
  addModel.parentName = item.title ? item.title : ''
}
// 获取列表
const tableList = ref([])
const getList = async () => {
  let res = await getListApi()
  if (res && res.code == 200) {
    tableList.value = res.data
  }
}
onMounted(() => {
  getList()
})

// 编辑
const handleEdit = (row: MenuType) => {
  tags.value = 'edit'
  dialog.title = '新增角色'
  dialog.height = 300
  dialog.visible = true
  // 获取上级菜单
  getParent()
  nextTick(() => {
    //   回写数据
    Object.assign(addModel, row)
  })
  // 清空表单
  addForm.value?.resetFields()
}
// 删除
const handleDelete = async (menuId: string) => {
  const confirm = await global.$myconfirm('确定删除该数据吗？', '')
  if (confirm) {
    let res = await deleteApi(menuId)
    if (res && res.code == 200) {
      ElMessage.success(res.msg)
      getList()
    } else {
      ElMessage.error(res.msg)
    }
  }
}
</script>
<style scoped></style>
```

### 2、左侧导航菜单排序

```java
// 获取路由菜单
@GetMapping("/getMenuList")
public ResultVo getMenuList(Long userId) {
    // 获取用户信息
    SysUser user = sysUserService.getById(userId);
    // 菜单数据
    List<SysMenu> menuList = null;
    // 判断是否是超级管理员
    if(StringUtils.isNotEmpty(user.getIsAdmin()) && "1".equals(user.getIsAdmin())){
        QueryWrapper<SysMenu> query = new QueryWrapper<>();
        query.lambda().orderByAsc(SysMenu::getOrderNum);
        menuList = sysMenuService.list(query);
    } else {
        menuList = sysMenuService.getMenuByUserId(userId);
    }
    // 过滤菜单数据,去掉按钮数据
    List<SysMenu> collect = Optional.ofNullable(menuList).orElse(new ArrayList<>())
        .stream()
        .filter(item -> item != null && StringUtils.isNotEmpty(item.getType()) && !"2".equals(item.getType()))
        .collect(Collectors.toList());
    // 组装路由数据
    List<RouterVo> router = MakeMenuTree.makeRouter(collect, 0L);

    return  ResultUtils.success("查询成功！", router);
}
```

### 3、token过期时，返回登录页面

```typescript
// 添加响应拦截器
this.instance.interceptors.response.use(
    (response: AxiosResponse) => {
        let userStore = useUserStore()
        let menuStore = useMenuStore()
        if (response.data.code === 600) {
            // token过期，调到登录页面
            // 清空缓存
            sessionStorage.clear()
            localStorage.clear()
            userStore.setUserId('')
            userStore.setToken('')
            userStore.setCodeList([])
            menuStore.setMenu([])
            window.location.href = '/login'
        } else if (response.data.code === 200) {
            return response.data
        } else {
            ElMessage.error(response.data.msg || '服务器出错！')
            return Promise.reject(response.data.msg || '服务器出错！')
        }
    },
    (error: any) => {
        error.data = {}
        if (error & error.response) {
            switch (error.response.status) {
                case 400:
                    error.data.msg = '错误请求'
                    ElMessage.error(error.data.msg)
                    break
                case 401:
                    error.data.msg = '未授权，请重新登录'
                    ElMessage.error(error.data.msg)
                    break
                case 403:
                    error.data.msg = '拒绝访问'
                    ElMessage.error(error.data.msg)
                    break
                case 404:
                    error.data.msg = '请求错误，未找到接口'
                    ElMessage.error(error.data.msg)
                    break
                case 405:
                    error.data.msg = '请求方法未允许'
                    ElMessage.error(error.data.msg)
                    break
                case 408:
                    error.data.msg = '请求超时'
                    ElMessage.error(error.data.msg)
                    break
                case 500:
                    //   error.data.msg = '服务器端出错'
                    error.data.msg = '后台接口出错'
                    ElMessage.error(error.data.msg)
                    break
                case 501:
                    error.data.msg = '网络未实现'
                    ElMessage.error(error.data.msg)
                    break
                case 502:
                    error.data.msg = '网铬错误'
                    ElMessage.error(error.data.msg)
                    break
                case 503:
                    error.data.msg = '服务不可用'
                    ElMessage.error(error.data.msg)
                    break
                case 504:
                    error.data.msg = '网铬超时'
                    ElMessage.error(error.data.msg)
                    break
                case 505:
                    error.data.msg = 'http版本不支持该请求'
                    ElMessage.error(error.data.msg)
                    break
                default:
                    error.data.msg = `连接错误${error.response.status}`
                    ElMessage.error(error.data.msg)
            }
        } else {
            error.data.msg = '连接到服务器失败'
            ElMessage.error(error.data.msg)
        }
        return Promise.reject(error)
    }
)
```



## 62、给系统换主题色

### 1、assets下新建styles文件夹，并建theme.css文件

```css
/* 注意事项：白色统一用 white */

.green {
  /* 标题字体颜色 */
  --el-color-logo-color: white;
  /* 头部高度 */
  --el-header-height: 60px;
  /*头部背景色*/
  --el-color-myheader: rgb(22, 123, 216);
  /*按钮颜色*/
  --el-color-primary: #409eff;
  /*选项卡颜色*/
  --el-color-mytab: #409eff;
  /*伸缩图标颜色*/
  --el-color-collapse: white;
  /*logo背景色*/
  --el-color-mylogo: rgb(22, 123, 216);
  /*左侧菜单字体大小*/
  --el-mymenu-font: 16px;
  /*菜单颜色*/
  --el-color-mymenu: white;
  /*一级菜单字体颜色*/
  --el-color-mymenu-first: #303133;
  /*菜单背景颜色*/
  --el-color-mymenubg: white;
  /* 鼠标放上去，菜单颜色 */
  --el-color-mymenu-hover: #ecf5ff;
  /*子菜单背景颜色*/
  --el-color-mymenu-child: white;
  /*菜单点中文字的颜色*/
  --el-color-active-menu: #409eff;
  /*logo和右侧头部分割线*/
  --el-color-myheaderleft: #409eff;
  /*弹框头部背景色*/
  --el-color-mydialog: #409eff;
  /*面包屑导航分割线颜色*/
  --el-text-color-separator: white;
  /*面包屑导字体颜色*/
  --el-text-color-bred: white;
  /*面包屑导字体大小*/
  --el-text-bred-font-size: 16px;
}

.brown {
  /*标题字体颜色*/
  --el-color-logo-color: white;
  /*头部高度*/
  --el-header-height: 60px;
  /*头部背景色*/
  --el-color-myheader: rgb(22, 123, 216);
  /*按钮颜色*/
  --el-color-primary: #409eff;
  /*选项卡颜色*/
  --el-color-mytab: #409eff;
  /*伸缩图标颜色*/
  --el-color-collapse: white;
  /*logo背景色*/
  --el-color-mylogo: rgb(22, 123, 216);
  /*左侧菜单字体大小*/
  --el-mymenu-font: 16px;
  /*菜单颜色*/
  --el-color-mymenu: #0e1f37;
  /*一级菜单字体颜色*/
  --el-color-mymenu-first: #f4f4f5;
  /*子菜单背景颜色*/
  --el-color-mymenu-child: #1f2d3d;
  /*菜单背景颜色*/
  --el-color-mymenubg: #0e1f37;
  /*鼠标放上去，菜单颜色*/
  --el-color-mymenu-hover: #001528;
  /*菜单点中文字的颜色*/
  --el-color-active-menu: #409eff;
  /*logo和右侧头部分割线*/
  --el-color-myheaderleft: white;
  /*弹框头部背景色*/
  --el-color-mydialog: #409eff;
  /*面包屑导航分割线颜色*/
  --el-text-color-separator: white;
  /*面包屑导字体颜色*/
  --el-text-color-bred: white;
  /*面包屑导字体大小*/
  --el-text-bred-font-size: 16px;
}

.grape {
  /*标题字体颜色*/
  --el-color-logo-color: white;
  /*头部高度*/
  --el-header-height: 60px;
  /*头部背景色*/
  --el-color-myheader: #ff2551;
  /*按钮颜色*/
  --el-color-primary: #ff2551;
  /*选项卡颜色*/
  --el-color-mytab: #ff2551;
  /*伸缩图标颜色*/
  --el-color-collapse: white;
  /*logo背景色*/
  --el-color-mylogo: #000000;
  /*左侧菜单字体大小*/
  --el-mymenu-font: 16px;
  /*菜单颜色*/
  --el-color-mymenu: #0e1f37;
  /*一级菜单字体颜色*/
  --el-color-mymenu-first: #f4f4f5;
  /*子菜单背景颜色*/
  --el-color-mymenu-child: #1f2d3d;
  /*菜单背景颜色*/
  --el-color-mymenubg: #0e1f37;
  /*鼠标放上去，菜单颜色*/
  --el-color-mymenu-hover: #001528;
  /*菜单点中文字的颜色*/
  --el-color-active-menu: #ff2551;
  /*logo和右侧头部分割线*/
  --el-color-myheaderleft: #ff2551;
  /*弹框头部背景色*/
  --el-color-mydialog: #ff2551;
  /*面包屑导航分割线颜色*/
  --el-text-color-separator: white;
  /*面包屑导字体颜色*/
  --el-text-color-bred: white;
  /*面包屑导字体大小*/
  --el-text-bred-font-size: 16px;
}

.default {
  /*标题字体颜色*/
  --el-color-logo-color: white;
  /*头部高度*/
  --el-header-height: 60px;
  /*头部背景色*/
  --el-color-myheader: #009688;
  /*按钮颜色*/
  --el-color-primary: #42b983;
  /*选项卡颜色*/
  --el-color-mytab: #42b983;
  /*伸缩图标颜色*/
  --el-color-collapse: white;
  /*logo背景色*/
  --el-color-mylogo: #000000;
  /*左侧菜单字体大小*/
  --el-mymenu-font: 16px;
  /*菜单颜色*/
  --el-color-mymenu: #0e1f37;
  /*一级菜单字体颜色*/
  --el-color-mymenu-first: #f4f4f5;
  /*子菜单背景颜色*/
  --el-color-mymenu-child: #1f2d3d;
  /*菜单背景颜色*/
  --el-color-mymenubg: #0e1f37;
  /*鼠标放上去，菜单颜色*/
  --el-color-mymenu-hover: #001528;
  /*菜单点中文字的颜色*/
  --el-color-active-menu: #1890ff;
  /*logo和右侧头部分割线*/
  --el-color-myheaderleft: #009688;
  /*弹框头部背景色*/
  --el-color-mydialog: #009688;
  /*面包屑导航分割线颜色*/
  --el-text-color-separator: white;
  /*面包屑导字体颜色*/
  --el-text-color-bred: white;
  /*面包屑导字体大小*/
  --el-text-bred-font-size: 16px;
}

.blacks {
  /*标题字体颜色*/
  --el-color-logo-color: white;
  /*头部高度*/
  --el-header-height: 60px;
  /*头部背景色*/
  --el-color-myheader: #0f2542;
  /*按钮颜色*/
  --el-color-primary: #42b983;
  /*选项卡颜色*/
  --el-color-mytab: #42b983;
  /*伸缩图标颜色*/
  --el-color-collapse: white;
  /*logo背景色*/
  --el-color-mylogo: #000000;
  /*左侧菜单字体大小*/
  --el-mymenu-font: 16px;
  /*菜单颜色*/
  --el-color-mymenu: #0e1f37;
  /*一级菜单字体颜色*/
  --el-color-mymenu-first: #f4f4f5;
  /*子菜单背景颜色*/
  --el-color-mymenu-child: #1f2d3d;
  /*菜单背景颜色*/
  --el-color-mymenubg: #0e1f37;
  /*鼠标放上去，菜单颜色*/
  --el-color-mymenu-hover: #001528;
  /*菜单点中文字的颜色*/
  --el-color-active-menu: #f07810;
  /*logo和右侧头部分割线*/
  --el-color-myheaderleft: white;
  /*弹框头部背景色*/
  --el-color-mydialog: #0e1f37;
  /*面包屑导航分割线颜色*/
  --el-text-color-separator: white;
  /*面包屑导字体颜色*/
  --el-text-color-bred: white;
  /*面包屑导字体大小*/
  --el-text-bred-font-size: 16px;
}

.whites {
  /*标题字体颜色*/
  --el-color-logo-color: white;
  /*头部高度*/
  --el-header-height: 60px;
  /*头部背景色*/
  --el-color-myheader: white;
  /*按钮颜色*/
  --el-color-primary: #42b983;
  /*选项卡颜色*/
  --el-color-mytab: #42b983;
  /*伸缩图标颜色*/
  --el-color-collapse: #73767a;
  /*logo背景色*/
  --el-color-mylogo: #000000;
  /*左侧菜单字体大小*/
  --el-mymenu-font: 16px;
  /*菜单颜色*/
  --el-color-mymenu: #0e1f37;
  /*一级菜单字体颜色*/
  --el-color-mymenu-first: #f4f4f5;
  /*子菜单背景颜色*/
  --el-color-mymenu-child: #1f2d3d;
  /*菜单背景颜色*/
  --el-color-mymenubg: #0e1f37;
  /*鼠标放上去，菜单颜色*/
  --el-color-mymenu-hover: #001528;
  /*菜单点中文字的颜色*/
  --el-color-active-menu: #1890ff;
  /*logo和右侧头部分割线*/
  --el-color-myheaderleft: white;
  /*弹框头部背景色*/
  --el-color-mydialog: #009688;
  /*面包屑导航分割线颜色*/
  --el-text-color-separator: #606266;
  /*米面包屑导字体颜色*/
  --el-text-color-bred: #606266;
  /*面包屑导字体大小*/
  --el-text-bred-font-size: 16px;
}

.default1 {
  /*标题字体颜色*/
  --el-color-logo-color: white;
  /*头部高度*/
  --el-header-height: 60px;
  /*头部背景色*/
  --el-color-myheader: #009688;
  /*按钮颜色*/
  --el-color-primary: #42b983;
  /*选项卡颜色*/
  --el-color-mytab: #42b983;
  /*伸缩图标颜色*/
  --el-color-collapse: white;
  /*logo背景色*/
  --el-color-mylogo: #2b2f3a;
  /*左侧菜单字体大小*/
  --el-mymenu-font: 16px;
  /*菜单颜色*/
  --el-color-mymenu: #304156;
  --el-color-mymenu-first: #f4f4f5;
  /*子菜单背景颜色*/
  --el-color-mymenu-child: #1f2d3d;
  /*菜单背景颜色*/
  --el-color-mymenubg: #304156;
  /*鼠标放上去，菜单颜色*/
  --el-color-mymenu-hover: #001528;
  /*菜单点中文字的颜色*/
  --el-color-active-menu: #1890ff;
  /*logo和右侧头部分割线*/
  --el-color-myheaderleft: #009688;
  /*弹框头部背景色*/
  --el-color-mydialog: #009688;
  /*面包屑导航分割线颜色*/
  --el-text-color-separator: white;
  /*面包屑导字体颜色*/
  --el-text-color-bred: white;
  /*面包屑导字体大小*/
  --el-text-bred-font-size: 16px;
}

.el-button:focus {
  color: var(--el-button-text-color);
  background-color: var(--el-button-bg-color);
  border-color: var(--el-button-border-color);
}

.el-button:hover {
  color: var(--el-button-text-color);
  background-color: var(--el-button-bg-color);
  border-color: var(--el-button-border-color);
}
```

### 2、main.ts里面引入

```js
// 主题
import '@/assets/styles/theme.css'
document.getElementsByTagName('html')[0].className = 'whites'
```

### 3、修改各页面

#### 3.1、layout\header\Breadcrumb.vue

```vue
<template>
  <el-breadcrumb class="bred" separator="/">
    <el-breadcrumb-item v-for="item in tabs" :key="item.path" :to="item.path">
      {{ item.meta.title }}
    </el-breadcrumb-item>
  </el-breadcrumb>
</template>

<script setup lang="ts">
import { RouteLocationMatched } from 'vue-router'

const route = useRoute()

// 定义面包屑导航数据
const tabs: Ref<RouteLocationMatched[]> = ref([])
// 获取面包屑数据
const getBreadcrumb = () => {
  let matched = route.matched.filter(item => item.meta && item.meta.title)
  // 获取第一个数据，判断是否首页
  const first = matched[0]
  if (first.path !== '/dashboard') {
    matched = [{ path: '/dashboard', meta: { title: '首页' } } as any].concat(matched)
  }
  tabs.value = matched
}
getBreadcrumb()
// 监听路由变化
watch(
  () => route.path,
  () => {
    getBreadcrumb()
  }
)
</script>

<style lang="scss" scoped>
.bred {
  margin-left: 20px;
}
// 修改字体颜色
:deep(.el-breadcrumb__inner) {
  color: var(--el-text-color-bred) !important;
  a {
    color: var(--el-text-color-bred) !important;
  }
}
// 分割线颜色
:deep(.el-breadcrumb__separator) {
  color: var(--el-text-color-separator) !important;
}
// 修改字体大小
:deep(.el-breadcrumb__item) {
  font-size: var(--el-text-bred-font-size) !important;
}
</style>
```

#### 3.2、layout\header\Header.vue

```vue
<template>
  <div class="header-container" id="mymenu">
    <div class="left">
      <Collapse />
      <Breadcrumb />
    </div>
    <div class="right">
      <span>欢迎您，{{ nickName }}</span>
      <LoginOut />
    </div>
  </div>
</template>

<script setup lang="ts">
import Collapse from './Collapse.vue'
import Breadcrumb from './Breadcrumb.vue'
import LoginOut from './LoginOut.vue'
import { useUserStore } from '@/store/user'

const { nickName } = useUserStore()
// 头部背景色
// let headerbg = ref('')
// nextTick(() => {
//   let box = document.getElementById('mymenu') as HTMLElement
//   headerbg.value = getComputedStyle(box).getPropertyValue('--el-color-mymenu')
//   console.log(headerbg.value)
// })
</script>

<style lang="scss" scoped>
.header-container {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;
  .left {
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .right {
    display: flex;
    align-items: center;
    justify-content: center;
    span {
      color: #fff;
      margin-right: 20px;
    }
  }
}
/* :deep(.el-dropdown-link:focus) {
  outline: none;
} */
</style>
```

#### 3.3、layout\menu\MenuBar.vue

```vue
<template>
  <MenuLogo />
  <el-menu
    :default-active="$route.path"
    class="el-menu-vertical-demo"
    :collapse="menuStore.getCollapse"
    unique-opened
    id="mymenubg"
    :background-color="mymenubg"
    router
  >
    <MenuItem :menuList="menuList" />
  </el-menu>
</template>

<script setup lang="ts">
import MenuLogo from '@/layout/menu/MenuLogo.vue'
import MenuItem from '@/layout/menu/MenuItem.vue'

import { useMenuStore } from '@/store/menu'

const menuStore = useMenuStore()

// 方式一
// const route = useRoute()
// // 获取激活的菜单
// const defaultActive = computed(() => {
//     const { path } = route
//     console.log(path)
//     return path
// })

// 菜单数据
const menuList = computed(() => {
  return menuStore.getMenu
})
// let menuList = reactive([
//   {
//     path: '/dashboard',
//     component: '/dashboard/Index',
//     name: 'dashboard',
//     meta: {
//       title: '首页',
//       icon: 'House',
//       roles: ['sys:dashboard']
//     }
//   },
//   {
//     path: '/system',
//     component: 'Layout',
//     name: 'system',
//     meta: {
//       title: '系统管理',
//       icon: 'Setting',
//       roles: ['sys:admin']
//     },
//     children: [
//       {
//         path: '/userList',
//         component: '/system/User/UserList',
//         name: 'userList',
//         meta: {
//           title: '用户管理',
//           icon: 'UserFilled',
//           roles: ['sys:user']
//         }
//       },
//       {
//         path: '/roleList',
//         component: '/system/Role/RoleList',
//         name: 'roleList',
//         meta: {
//           title: '角色管理',
//           icon: 'Wallet',
//           roles: ['sys:role']
//         }
//       },
//       {
//         path: '/menuList',
//         component: '/system/Menu/MenuList',
//         name: 'menuList',
//         meta: {
//           title: '菜单管理',
//           icon: 'Menu',
//           roles: ['sys:menu']
//         }
//       }
//     ]
//   },
//   {
//     path: '/goodsRoot',
//     component: 'Layout',
//     name: 'goodsRoot',
//     meta: {
//       title: '商品管理',
//       icon: 'Setting',
//       roles: ['sys:goodsRoot']
//     },
//     children: [
//       {
//         path: '/category',
//         component: '/goods/Category',
//         name: 'category',
//         meta: {
//           title: '商品类型',
//           icon: 'UserFilled',
//           roles: ['sys:category']
//         }
//       },
//       {
//         path: '/goodsList',
//         component: '/goods/GoodsList',
//         name: 'goodsList',
//         meta: {
//           title: '商品信息',
//           icon: 'Wallet',
//           roles: ['sys:goodsList']
//         }
//       }
//     ]
//   }
// ])
let mymenubg = ref('')
nextTick(() => {
  let box = document.getElementById('mymenubg') as HTMLElement
  mymenubg.value = getComputedStyle(box).getPropertyValue('--el-color-mymenubg')
})
</script>

<style lang="scss" scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 230px;
  min-height: 400px;
}

.el-menu {
  border-right: none;
}

:deep(.el-sub-menu .el-sub-menu__title) {
  /* color: #f4f4f5 !important; */
  color: var(--el-color-mymenu-first) !important;
}

/* :deep(.el-menu .el-menu-item) { */
/* color: #bfcbd9; */
/* color: var(--el-color-mymenu-first) !important;
} */

:deep(.el-menu-item.is-active) {
  /* color: #409eff !important; */
  color: var(--el-color-active-menu) !important;
}

:deep(.is-opened .el-menu-item) {
  /* background-color: #1f2d3d !important; */
  background-color: var(--el-color-mymenu-child) !important;
}

:deep(.el-menu-item:hover) {
  /* background-color: #001528 !important; */
  background-color: var(--el-color-mymenu-hover) !important;
}
</style>
```

#### 3.4、layout\menu\MenuLogo.vue

```vue
<template>
  <div class="logo">
    <img :src="Logo" />
    <span v-if="show" class="logo-title">{{ title }}</span>
  </div>
</template>

<script setup lang="ts">
import { useMenuStore } from '@/store/menu'
import Logo from '@/assets/logo.png'
const title = ref('通用后台权限系统')

const menuStore = useMenuStore()

const show = ref(true)

watch(
  () => menuStore.collapse,
  (collapsed: boolean) => {
    if (!collapsed) {
      setTimeout(() => {
        show.value = !collapsed
      }, 300)
    } else {
      show.value = !collapsed
    }
  }
)
</script>

<style lang="scss" scoped>
.logo {
  display: flex;
  width: 100%;
  height: var(--el-header-height);
  background-color: var(--el-color-mylogo);
  text-align: center;
  cursor: pointer;
  align-items: center;
  /* justify-content: center; */

  img {
    width: 36px;
    height: 36px;
    margin-left: 5px;
    margin-right: 5px;
  }

  .logo-title {
    color: var(--el-color-logo-color);
    font-weight: 800;
    line-height: var(--el-header-height);
    font-size: 21px;
    font-family: FangSong;
  }
}
</style>
```

#### 3.5、layout\menu\MenuItem.vue

```vue
<template>
  <template v-for="menu in menuList" :key="menu.path">
    <!-- 有下级菜单 -->
    <el-sub-menu v-if="menu.children && menu.children.length" :index="menu.path">
      <template #title>
        <el-icon>
          <component :is="menu.meta.icon" />
        </el-icon>
        <span>{{ menu.meta.title }}</span>
      </template>
      <!-- 递归调用 -->
      <MenuItem :menuList="menu.children" />
    </el-sub-menu>
    <!-- 没有下级菜单 -->
    <el-menu-item v-else :index="menu.path" style="color: var(--el-color-mymenu-first)">
      <el-icon>
        <component :is="menu.meta.icon" />
      </el-icon>
      <template #title>{{ menu.meta.title }}</template>
    </el-menu-item>
  </template>
</template>

<script setup lang="ts">
// 通过defineProps接收父组件传递的数据
defineProps(['menuList'])
</script>

<style lang="scss" scoped></style>
```

#### 3.6、layout\Index.vue

```vue
<template>
  <el-container class="layout">
    <el-aside width="auto" class="aside">
      <MenuBar />
    </el-aside>
    <el-container>
      <el-header class="header">
        <Header />
      </el-header>
      <el-main class="main">
        <di class="tabs">
          <Tabs class="el-tabs" />
          <ColseTabs />
        </di>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup lang="ts">
import Header from '@/layout/header/Header.vue'
import MenuBar from '@/layout/menu/MenuBar.vue'
import Tabs from '@/layout/tabs/Tabs.vue'
import ColseTabs from '@/layout/tabs/ColseTabs.vue'
</script>

<style lang="scss" scoped>
.layout {
  height: 100%;

  .aside {
    background-color: var(--el-color-mymenubg);
  }

  .header {
    display: flex;
    align-items: center;
    background-color: var(--el-color-myheader);
  }

  .main {
    /* padding: 0 10px !important; */
    padding: 0px;
    .tabs {
      display: flex;
      align-items: center;
      justify-content: space-between;
      flex-grow: 1;
      border-bottom: 1px solid #e4e7ed;
      .el-tabs {
        width: calc(100% - 76px);
      }
    }
  }
}
</style>
```

#### 3.7、layout\header\Collapse.vue

```vue
<template>
  <el-icon id="icons" class="icons" color="var(--el-color-collapse)" size="24" @click="changeCollapse">
    <component :is="status ? 'Expand' : 'Fold'" />
  </el-icon>
</template>

<script setup lang="ts">
import { useMenuStore } from '@/store/menu'
const store = useMenuStore()

const status = computed(() => store.getCollapse)

const changeCollapse = () => {
  store.setCollapse(!store.getCollapse)
}

// 头部背景色
// let collapse = ref('')
// nextTick(() => {
//   let box = document.getElementById('icons') as HTMLElement
//   collapse.value = getComputedStyle(box).getPropertyValue('--el-color-collapse')
// })
</script>

<style scoped>
.icons {
  cursor: pointer;
}
</style>
```

#### 3.8、layout\header\LoginOut.vue

```vue
```

#### 3.9、layout\tabs\Tabs.vue

```vue
```

#### 3.10、layout\tabs\ColseTabs.vue

```vue
```



## 63、springboot2升级到springboot3

### 1、itmk-base-parent pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.itmk</groupId>
    <artifactId>itmk-base-parent</artifactId>
    <packaging>pom</packaging>
    <version>1.0-SNAPSHOT</version>
    <modules>
        <module>itmk-base-common</module>
        <module>itmk-base-web</module>
    </modules>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <!--<version>2.4.4</version>-->
        <version>3.2.8</version>
    </parent>

    <properties>
        <!--<java.version>1.8</java.version>-->
        <java.version>17</java.version>
        <swagger2.version>3.0.0</swagger2.version>
        <!--<lombok.version>1.18.12</lombok.version>-->
        <lombok.version>1.18.20</lombok.version>
        <!--<mybatis-plus.version>3.4.2</mybatis-plus.version>-->
        <mybatis-plus.version>3.5.8</mybatis-plus.version>
        <!--<druid.version>1.2.6</druid.version>-->
        <druid.version>1.2.24</druid.version>
        <kaptcha.version>2.3.2</kaptcha.version>
        <fastjson.version>1.2.68</fastjson.version>
        <commons-lang.version>2.6</commons-lang.version>
        <commons-collections.version>3.2.2</commons-collections.version>
        <commons-io.version>2.6</commons-io.version>
        <mysql.version>8.0.21</mysql.version>
        <jwt.version>3.10.3</jwt.version>
        <commons-codec.version>1.8</commons-codec.version>
    </properties>

    <!--
        本地没有该依赖的所有jar包
        将<dependencyManagement>标签先去除
        先让maven去把jar下载到本地仓库
        下好了再加上<dependencyManagement>标签即可解决
        或者不去处理,子工程需要使用相应的jar引入依赖即可
    -->
    <dependencyManagement>
        <dependencies>
            <!--lombok依赖-->
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
                <scope>provided</scope>
            </dependency>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
                <scope>runtime</scope>
            </dependency>
            <!--druid连接池-->
            <dependency>
                <groupId>com.alibaba</groupId>
                <!--<artifactId>druid-spring-boot-starter</artifactId>-->
                <artifactId>druid-spring-boot-3-starter</artifactId>
                <version>${druid.version}</version>
            </dependency>
            <!--mybatis-plus依赖-->
            <!--<dependency>-->
            <!--    <groupId>com.baomidou</groupId>-->
            <!--    <artifactId>mybatis-plus-boot-starter</artifactId>-->
            <!--    <version>${mybatis-plus.version}</version>-->
            <!--</dependency>-->
            <dependency>
                <groupId>com.baomidou</groupId>
                <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
                <version>${mybatis-plus.version}</version>
            </dependency>
            <!--captcha图形验证码-->
            <dependency>
                <groupId>com.github.penggle</groupId>
                <artifactId>kaptcha</artifactId>
                <version>${kaptcha.version}</version>
            </dependency>
            <!--JS0N转换工具类依赖-->
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>fastjson</artifactId>
                <version>${fastjson.version}</version>
            </dependency>
            <dependency>
                <groupId>commons-lang</groupId>
                <artifactId>commons-lang</artifactId>
                <version>${commons-lang.version}</version>
            </dependency>
            <dependency>
                <groupId>commons-collections</groupId>
                <artifactId>commons-collections</artifactId>
                <version>${commons-collections.version}</version>
            </dependency>
            <dependency>
                <groupId>commons-io</groupId>
                <artifactId>commons-io</artifactId>
                <version>${commons-io.version}</version>
            </dependency>
            <dependency>
                <groupId>io.springfox</groupId>
                <artifactId>springfox-boot-starter</artifactId>
                <version>${swagger2.version}</version>
            </dependency>
            <dependency>
                <groupId>com.auth0</groupId>
                <artifactId>java-jwt</artifactId>
                <version>${jwt.version}</version>
            </dependency>
            <dependency>
                <groupId>org.apache.directory.studio</groupId>
                <artifactId>org.apache.commons.codec</artifactId>
                <version>${commons-codec.version}</version>
            </dependency>

            <dependency>
                <groupId>jakarta.servlet</groupId>
                <artifactId>jakarta.servlet-api</artifactId>
                <version>6.1.0</version>
                <scope>provided</scope>
            </dependency>

            <!--<dependency>-->
            <!--    <groupId>org.mybatis.spring.boot</groupId>-->
            <!--    <artifactId>mybatis-spring-boot-starter</artifactId>-->
            <!--    <version>3.0.4</version>-->
            <!--</dependency>-->

        </dependencies>
    </dependencyManagement>

</project>
```

### 2、itmk-base-web pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>itmk-base-parent</artifactId>
        <groupId>com.itmk</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>
    <packaging>jar</packaging>
    <artifactId>itmk-base-web</artifactId>

    <dependencies>
        <dependency>
            <groupId>com.itmk</groupId>
            <artifactId>itmk-base-common</artifactId>
            <version>1.0-SNAPSHOT</version>
        </dependency>
        <!--web启动器，对springmvc,serlvet等支持-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>
        <!--数据库依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--mybatis-plus启动器-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
        </dependency>
        <!--druid连接池-->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-3-starter</artifactId>
        </dependency>
        <!--swagger api文档-->
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-compress</artifactId>
            <version>1.18</version>
        </dependency>
        <dependency>
            <groupId>commons-lang</groupId>
            <artifactId>commons-lang</artifactId>
        </dependency>
        <!--图片验证码-->
        <dependency>
            <groupId>com.github.penggle</groupId>
            <artifactId>kaptcha</artifactId>
        </dependency>
        <!--jwt-->
        <dependency>
            <groupId>com.auth0</groupId>
            <artifactId>java-jwt</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
        </dependency>

        <dependency>
            <groupId>org.apache.directory.studio</groupId>
            <artifactId>org.apache.commons.codec</artifactId>
        </dependency>

        <dependency>
            <groupId>jakarta.servlet</groupId>
            <artifactId>jakarta.servlet-api</artifactId>
            <scope>provided</scope>
        </dependency>

        <!--<dependency>-->
        <!--    <groupId>org.mybatis.spring.boot</groupId>-->
        <!--    <artifactId>mybatis-spring-boot-starter</artifactId>-->
        <!--</dependency>-->

    </dependencies>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <!--<version>2.1.4.RELEASE</version>-->
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>${java.version}</source>
                    <target>${java.version}</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>2.19.1</version>
                <configuration>
                    <skipTests>true</skipTests>    <!--打包过程默认关掉单元测试 -->
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

## 3、maven配置

- 版本：3.6.4及以上
- jdk17及以上

### 4、SysUserController的BASE64Encoder报错

#### 4.1、imageCode方法报错改为如下

```java
BASE64Encoder encoder = new BASE64Encoder();
String base64 = encoder.encode(outputStream.toByteArray());
```

#### 4.2、如下修改

**添加依赖**

```
<commons-codec.version>1.8</commons-codec.version>
```

```xml
<dependency>
    <groupId>org.apache.directory.studio</groupId>
    <artifactId>org.apache.commons.codec</artifactId>
    <version>${commons-codec.version}</version>
</dependency>
```

```java
String base64 = Base64.encodeBase64String(outputStream.toByteArray());
```

### 5、启动报如下错误

**编译报错，`import javax.servlet.*;` 不存在**

这个报错主要是Spring Boot3.0已经为所有依赖项从 `Java EE` 迁移到 `Jakarta EE API`，导致 `servlet` 包名的修改，Spring团队这样做的原因，主要是避免 `Oracle` 的版权问题，解决办法很简单，两步走：

1 添加 `jakarta.servlet` 依赖

```cobol
<dependency>
    <groupId>jakarta.servlet</groupId>
    <artifactId>jakarta.servlet-api</artifactId>
</dependency>
```

1. 修改项目内所有代码的导入依赖

```haskell
修改前：
import javax.servlet.*
修改后：
import jakarta.servlet.*
```

### 6、在index.html添加如下样式，设置滚动条样式

```css
.el-upload-list__item {
    transition: none !important;
}
/* 定义滚动条高宽及背景高宽分别对应横竖滚动条的尺寸 */
::-webkit-scrollbar {
    width: 7px;
    height: 5px;
    background-color: #f5f5f5;
}
/* 定义滚动条轨道内阴影+圆角 */
:-webkit-scrollbar-track {
    box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
    border-radius: 8px;
    background-color: #f5f5f5;
}
/* 定义滑块内阴影+圆角 */
:-webkit-scrollbar-thumb {
    border-radius: 8px;
    box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.1);
    -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.1);
    background-color: #c8c8c8;
}
```

### 7、菜单管理，给表格添加高度

```js
nextTick(() =>{
	mianHeight.value = window.innerHeight - 130
})
```



## 64、添加安全框架sa-token

### 1、现在的接口，不安全，可以直接获取数据



### 2、添加依赖



### 3、sa-token的使用



#### 3.1、MvcConfiguration修改为如下



#### 3.2、新建Stplnterfacelmp类，如下所示



#### 3.3、创建全局异常处理器



#### 3.4、application-test.yml配置文件改为如下



#### 3.5、添加CorsConfig类



#### 3.6、登录方法改为如下所示



## 65、密码加密处理

