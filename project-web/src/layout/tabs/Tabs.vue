<template>
  <el-tabs v-model="activeTab" type="card" class="demo-tabs" @tab-remove="removeTab" @tab-click="clickTab">
    <el-tab-pane
      v-for="item in tabsList"
      :key="item.path"
      :label="item.title"
      :name="item.path"
      :closable="item.path != '/' && item.path != '/dashboard'"
    ></el-tab-pane>
  </el-tabs>
</template>

<script setup lang="ts">
import { useTabsStore } from '@/store/tabs'
import { Tab } from '@/types'
import { TabPaneName, TabsPaneContext } from 'element-plus'

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
const removeTab = (targetName: TabPaneName) => {
  const tabs = tabsList.value
  if (targetName === '/dashboard') return
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
  tabsStore.tabList = tabs.filter((tab: Tab) => tab.path !== targetName)
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
watch(
  () => route.path,
  () => {
    setActiveTab()
    // 添加选项卡数据
    addTab()
  }
)

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
  border: 1px solid var(--el-color-mytab) !important;
  background-color: var(--el-color-mytab) !important;
  color: #fff !important;
}

:deep(.el-tabs_item:hover) {
  color: #495060 !important;
}

:deep(.is-active:hover) {
  color: #fff !important;
}

:deep(.el-tabs__nav-next) {
  line-height: 26px !important;
}

:deep(.el-tabs__nav-prev) {
  line-height: 26px !important;
}
:deep(.el-tabs__header) {
  border-bottom: none !important;
}
</style>
