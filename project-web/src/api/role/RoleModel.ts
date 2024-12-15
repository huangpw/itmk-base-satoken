import { saveRoleMenuApi } from '.'

// 角色数据类型
export type SysRole = {
  roleId: string
  roleName: string
  remark: string
}

// 列表查询的参数
export type RoleListParm = {
  roleName: string
  currentPage: number
  pageSize: number
  total: number
}
// 分配菜单数据类型
export type saveMenuParm = {
  roleId: string
  list: Array<string>
}
