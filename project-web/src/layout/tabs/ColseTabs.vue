<template>
  <div class="colse">
    <el-dropdown>
      <span class="el-dropdown-link">
        <el-icon class="el-icon--right">
          <Close />
        </el-icon>
        关闭
      </span>
      <template #dropdown>
        <el-dropdown-menu>
          <el-dropdown-item @click="closeCurrent">关闭当前</el-dropdown-item>
          <el-dropdown-item @click="closeLeft">关闭左侧</el-dropdown-item>
          <el-dropdown-item @click="closeRight">关闭右侧</el-dropdown-item>
          <el-dropdown-item @click="closeAll">关闭所有</el-dropdown-item>
        </el-dropdown-menu>
      </template>
    </el-dropdown>
  </div>
</template>

<script setup lang="ts">
import { useTabsStore } from '@/store/tabs'
import { Tab } from '@/types'
const route = useRoute()
const router = useRouter()
const tabStore = useTabsStore()

//关闭当前
const closeCurrent = () => {
  // 当前路由
  const targetName = route.path
  if (targetName === '/dashboard') return
  //选项卡数据
  const tabs = tabStore.getTabList
  // 激活的选项卡
  let activeName = route.path

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
  //   activeTab.value = activeName
  // 重新设置选项卡数据
  tabStore.tabList = tabs.filter((tab: Tab) => tab.path !== targetName)
  // 跳转到激活的选项卡
  router.push({
    path: activeName
  })
}
//关闭左侧
const closeLeft = () => {
  const path = route.path
  const index = tabStore.tabList.findIndex((tab: Tab) => tab.path === path)
  console.log(index, 'index')
  if (index === -1) return
  tabStore.tabList.splice(1, index - 1)
}
//关闭右侧
const closeRight = () => {
  const path = route.path
  const index = tabStore.tabList.findIndex((tab: Tab) => tab.path === path)
  if (index === -1) return
  tabStore.tabList.splice(index + 1)
}
//关闭所有
const closeAll = () => {
  tabStore.tabList.splice(1, tabStore.tabList.length)
  router.push({ path: '/dashboard' })
}
</script>

<style lang="scss" scoped>
.example-showcase .el-dropdown-link {
  cursor: pointer;
  color: var(--el-color-primary);
  display: flex;
  align-items: center;
}
:deep(.el-icon) {
  top: 2px;
}

.el-dropdown-link:focus {
  outline: none;
}
.colse {
  width: 60px;
  height: 40px;
  position: fixed;
  top: 100;
  right: 0;
  z-index: 99;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  border-left: 1px solid #e4e7ed;
  padding-right: 10px;
  background-color: #fff;
}
</style>
