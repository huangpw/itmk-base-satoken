// 用户数据类型
export type SysUser = {
  userId: string
  username: string
  password: string
  phone: string
  email: string
  sex: string
  nickName: string
  roleId: string
}

export interface AddModel {
  userId: string
  username: string
  password: string
  phone: string
  email: string
  sex: string
  nickName: string
  roleId: string
}

// 列表查询的参数
export type UserListParm = {
  phone: string
  nickName: string
  currentPage: number
  pageSize: number
  total: number
}

export type Login = {
  username: string
  password: string
  code: string
}

// 菜单树参数
export type AssignParm = {
  userId: string
  roleId: string
}

// 修改密码参数
export type UpdatePwd = {
  userId: string
  oldPassword: string
  password: string
}
