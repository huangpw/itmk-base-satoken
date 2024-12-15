import http from '@/http'
import { RoleListParm, SysRole, saveMenuParm } from './RoleModel'

// 新增角色
export const addApi = (parm: SysRole) => {
  return http.post('/api/role', parm)
}

// 角色列表
export const getListApi = (parm: RoleListParm) => {
  return http.get('/api/role/getList', parm)
}

// 编辑角色
export const editApi = (parm: SysRole) => {
  return http.put('/api/role', parm)
}

// 删除角色
export const deleteApi = (roleId: string) => {
  return http.delete(`/api/role/${roleId}`)
}

// 角色下拉
export const selectRoleListApi = () => {
  return http.get('/api/role/selectRoleList')
}

// 分配角色菜单
export const saveRoleMenuApi = (parm: saveMenuParm) => {
  return http.post('/api/role/saveRoleMenu', parm)
}
