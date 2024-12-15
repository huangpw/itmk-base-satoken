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
import * as echarts from 'echarts'
import '@/assets/styles/theme.css'
document.getElementsByTagName('html')[0].className = 'default1'

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate) // 使用持久化插件

const app = createApp(App)
// app.directive('permission', permission)
app.config.globalProperties.$hasPerm = hasPerm
app.config.globalProperties.$echarts = echarts
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
