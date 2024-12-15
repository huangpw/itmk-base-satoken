import http from '@/http'
import { AssignParm, Login, SysUser, UpdatePwd, UserListParm } from './UserModel'

// 新增用户
export const addUserApi = (parm: SysUser) => {
  return http.post('/api/sysUser', parm)
}

// 用户列表
export const getUserListApi = (parm: UserListParm) => {
  return http.get('/api/sysUser/list', parm)
}

// 删除用户
export const deleteUserApi = (userId: string) => {
  return http.delete(`/api/sysUser/${userId}`)
}

// 根据用户Id查询角色
export const getUserRoleApi = (userId: string) => {
  return http.get(`/api/sysUser/getRoleList`, { userId })
}

// 编辑用户
export const editUserApi = (parm: SysUser) => {
  return http.put('/api/sysUser', parm)
}

// 重置密码
export const resetPasswordApi = (parm: { userId: string }) => {
  return http.post('/api/sysUser/resetPassword', parm)
}

// 验证码
export const getCaptchaApi = () => {
  return http.get('/api/sysUser/captcha')
}

// 登录
export const loginApi = (parm: Login) => {
  return http.post('/api/sysUser/login', parm)
}

//查询菜单树
export const getAssignTreeApi = (pram: AssignParm) => {
  return http.get(`/api/sysUser/getAssignTree`, pram)
}

// 修改密码
export const updatePasswordApi = (parm: UpdatePwd) => {
  return http.post('/api/sysUser/updatePassword', parm)
}

// 获取用户信息
export const getUserInfoApi = (userId: string) => {
  return http.get(`/api/sysUser/getInfo`, { userId })
}
