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
