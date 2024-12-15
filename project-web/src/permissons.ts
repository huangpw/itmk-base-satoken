import router from './router'
import { useUserStore } from './store/user'
import { useMenuStore } from './store/menu'
// import { useRouter } from 'vue-router'

// 定义白名单
const whiteList = ['/login']

router.beforeEach(async (to, from, next) => {
  //   console.log(to)
  console.log(from, 'from')
  //   console.log(next)
  let userStore = useUserStore()
  let menuStore = useMenuStore()
  let user = JSON.parse(localStorage.getItem('user') || '{}')
  // 获取token
  let token = userStore.getToken || user.token
  // 判断token是否存在
  if (token) {
    // 判断是否登录页或首页，是：放行，不是：从服务器获取菜单数据
    if (to.path === '/login' || to.path === '/') {
      next({ path: '/' })
    } else {
      // 判断权限数据是否存在
      //   const hasRoles = userStore.getCodeList.length > 0
      //   if (hasRoles) {
      //     // 存在放行
      //     next()
      //   } else {
      // 不存在，从服务器获取权限数据
      try {
        // 获取菜单数据
        if (menuStore.getMenu.length <= 1) {
          // 获取用户数据
          await userStore.getUserInfo()
          await menuStore.getMenuList(router, userStore.getUserId)
          next({ ...to, replace: true })
        }

        // 等待路由完全挂载
        next()
      } catch (error) {
        // 获取失败，清除token，跳转到登录页
        localStorage.clear()
        userStore.setToken('')
        next({ path: '/login' })
      }
      //   }
    }
  } else {
    // 路由在白名单,放行
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next('/login')
    }
  }
})
