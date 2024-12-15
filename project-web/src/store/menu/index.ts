import { defineStore } from 'pinia'
import { getMenuListApi } from '@/api/menu'
import { RouteRecordRaw } from 'vue-router'
import Layout from '@/layout/Index.vue'
import Center from '@/layout/center/Center.vue'
// 获取views下面所有.vue文件
const modules = import.meta.glob('../../views/**/*.vue')

// console.log('modules', modules)

// 定义Store
export const useMenuStore = defineStore('menuStore', {
  state: () => ({
    collapse: false,
    // 路由菜单数据
    menuList: [
      {
        path: '/dashboard',
        component: 'Layout',
        name: 'Dashboard',
        meta: {
          title: '首页',
          icon: 'HomeFilled',
          roles: ['sys:dashboard']
        }
      }
    ]
  }),
  getters: {
    getCollapse(state) {
      return state.collapse
    },
    getMenu(state) {
      return state.menuList
    }
  },
  actions: {
    setCollapse(collapse: boolean) {
      this.collapse = collapse
    },
    // 获取路由菜单
    getMenuList(router: any, userId: string) {
      return new Promise((resolve, reject) => {
        let user = localStorage.getItem('user') as any
        userId = userId || user.userId || ''
        // 调用接口获取菜单数据
        getMenuListApi(userId)
          .then((res: any) => {
            let accessRoutes = null
            if (res && res.code === 200) {
              // 生成路由
              accessRoutes = generateRoute(res.data, router) as any
              this.menuList = this.menuList.concat(accessRoutes)
            }
            resolve(this.menuList)
          })
          .catch(error => {
            reject(error)
          })
      })
    },
    setMenu(menu: any) {
      let menuItem = JSON.parse(JSON.stringify(this.menuList[0]))
      this.menuList = [menuItem, ...menu]
    }
  }
  //   persist: [
  //     {
  //       key: 'menu',
  //       //   pick: ['userId', 'nickName', 'token'],
  //       storage: localStorage
  //     }
  //   ]
})

// 动态生成路由
export function generateRoute(routes: RouteRecordRaw[], router: any) {
  // 路由数据
  const res: Array<RouteRecordRaw> = []
  routes.forEach((route: any) => {
    const tmp = { ...route }
    const component = tmp.component
    if (route.component) {
      if (component == 'Layout') {
        tmp.component = Layout
      } else {
        tmp.component = modules[`../../${component}`]
        // tmp.component = () => import(`@/${component}`)
      }
    }
    // 有下级
    if (tmp.children && tmp.children.length > 0) {
      if (route.component != 'Layout') {
        tmp.children = Center
        // tmp.children = generateRoute(tmp.children, router)
      }
      // 递归生成下级
      tmp.children = generateRoute(tmp.children, router)
    }
    // console.log(tmp, '路由菜单')
    res.push(tmp)
    // 加入路由
    router.addRoute(tmp)
  })
  return res
}
