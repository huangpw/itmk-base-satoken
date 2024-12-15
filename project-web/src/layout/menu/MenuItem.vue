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
