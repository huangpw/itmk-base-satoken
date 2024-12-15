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
