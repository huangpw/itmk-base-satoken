<template>
  <div class="header-container" id="mymenu">
    <div class="left">
      <Collapse />
      <Breadcrumb />
    </div>
    <div class="right">
      <span>欢迎您，{{ nickName }}</span>
      <Theme />
      <LoginOut />
    </div>
  </div>
</template>

<script setup lang="ts">
import Collapse from './Collapse.vue'
import Breadcrumb from './Breadcrumb.vue'
import LoginOut from './LoginOut.vue'
import Theme from './Theme.vue'
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
      color: var(--el-text-color-bred);
      margin-right: 20px;
    }
  }
}
/* :deep(.el-dropdown-link:focus) {
  outline: none;
} */
</style>
