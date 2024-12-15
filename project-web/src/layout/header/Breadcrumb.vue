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
