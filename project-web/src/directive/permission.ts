import { useUserStore } from '@/store/user'
import { Directive } from 'vue'

// 自定义按钮权限指令
export const permission: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    const { value } = binding
    // if (!value) return
    // 按钮权限字段
    const store = useUserStore()
    const codeList = JSON.parse(localStorage.getItem('codeList') || '[]')
    if (value && value instanceof Array && value.length > 0) {
      // 判断按钮权限是否存在
      const permissionRoles = value
      const roleList = store.getCodeList || codeList
      const hasPermission = roleList.some((role: any) => {
        return permissionRoles.includes(role)
      })
      // 如果没有权限，则移除当前元素
      if (!hasPermission) {
        el.style.display = 'none'
      }
    } else {
      throw new Error(`need roles! Like v-permission="['sys:user:add','sys:user:update']"`)
    }
  }
}
