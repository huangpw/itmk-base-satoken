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
  line-height: var(--el-header-height);
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
