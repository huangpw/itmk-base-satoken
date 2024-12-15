import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/Index.vue'

// 动态生成
export const constantRoutes: Array<RouteRecordRaw> = [
  {
    path: '/login',
    component: () => import('@/views/login/login.vue'),
    name: 'login'
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: '/dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/Index.vue'),
        meta: {
          title: '首页',
          icon: 'House'
        }
      }
    ]
  }
]
// 静态路由
// const routes: Array<RouteRecordRaw> = [
//   {
//     path: '/login',
//     component: () => import('@/views/login/login.vue'),
//     name: 'login'
//   },
//   {
//     path: '/',
//     component: Layout,
//     redirect: '/dashboard',
//     children: [
//       {
//         path: '/dashboard',
//         name: 'Dashboard',
//         component: () => import('@/views/dashboard/Index.vue'),
//         meta: {
//           title: '首页',
//           icon: '#icondashboard'
//         }
//       }
//     ]
//   },
//   {
//     path: '/system',
//     component: Layout,
//     name: 'system',
//     redirect: '/userList',
//     meta: {
//       title: '系统管理',
//       icon: 'Setting',
//       roles: ['sys:manage']
//     },
//     children: [
//       {
//         path: '/userList',
//         component: () => import('@/views/system/User/UserList.vue'),
//         name: 'userList',
//         meta: {
//           title: '用户管理',
//           icon: 'UserFilled',
//           roles: ['sys:user']
//         }
//       },
//       {
//         path: '/roleList',
//         component: () => import('@/views/system/Role/RoleList.vue'),
//         name: 'roleList',
//         meta: {
//           title: '角色管理',
//           icon: 'Wallet',
//           roles: ['sys:role']
//         }
//       },
//       {
//         path: '/menuList',
//         component: () => import('@/views/system/Menu/MenuList.vue'),
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
//     component: Layout,
//     name: 'goodsRoot',
//     redirect: '/category',
//     meta: {
//       title: '商品管理',
//       icon: 'Setting',
//       roles: ['sys:goodsRoot']
//     },
//     children: [
//       {
//         path: '/category',
//         component: () => import('@/views/category/CategoryList.vue'),
//         name: 'category',
//         meta: {
//           title: '商品类型',
//           icon: 'UserFilled',
//           roles: ['sys:category']
//         }
//       },
//       {
//         path: '/goodsList',
//         component: () => import('@/views/goods/GoodsList.vue'),
//         name: 'goodsList',
//         meta: {
//           title: '商品信息',
//           icon: 'Wallet',
//           roles: ['sys:goodsList']
//         }
//       }
//     ]
//   }
// ]

const router = createRouter({
  history: createWebHashHistory(),
  routes: constantRoutes
})

export default router
