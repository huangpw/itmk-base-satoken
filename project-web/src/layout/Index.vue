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
