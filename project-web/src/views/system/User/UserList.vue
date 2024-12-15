<template>
  <el-main>
    <el-form :model="searchParm" :inline="true" size="default">
      <el-form-item label="">
        <el-input v-model="searchParm.nickName" placeholder="请输入姓名"></el-input>
      </el-form-item>
      <el-form-item label="">
        <el-input v-model="searchParm.phone" placeholder="请输入手机号"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" @click="search">搜索</el-button>
        <el-button icon="Close" type="danger" plain @click="reset">重置</el-button>
        <el-button icon="Plus" type="primary" @click="add" v-if="global.$hasPerm(['sys:user:add'])">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格数据 -->
    <el-table :height="tableHeight" :data="tableList" border stripe :header-cell-style="{ 'text-align': 'center' }">
      <el-table-column prop="nickName" label="姓名"></el-table-column>
      <el-table-column prop="sex" label="性别" align="center">
        <template #default="scope">
          <el-tag type="primary" v-if="scope.row.sex === '0'" size="default" effect="dark">男</el-tag>
          <el-tag type="danger" v-if="scope.row.sex === '1'" size="default" effect="dark">女</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="手机号"></el-table-column>
      <el-table-column prop="email" label="邮箱"></el-table-column>
      <el-table-column prop="username" label="账户"></el-table-column>
      <el-table-column prop="isAdmin" label="是否超级管理员" align="center">
        <template #default="scope">
          <el-tag type="primary" v-if="scope.row.isAdmin === 1" size="default" effect="dark">是</el-tag>
          <el-tag type="danger" v-else size="default" effect="dark">否</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column
        label="操作"
        align="center"
        width="315"
        v-if="global.$hasPerm(['sys:user:update', 'sys:user:updatePassword', 'sys:user:delete'])"
      >
        <template #default="scope">
          <div class="action-buttons">
            <el-button
              type="primary"
              icon="Edit"
              @click="handleEdit(scope.row)"
              v-if="global.$hasPerm(['sys:user:update'])"
            >
              编辑
            </el-button>
            <el-button
              type="warning"
              icon="Setting"
              @click="resetPassword(scope.row.userId)"
              v-if="global.$hasPerm(['sys:user:updatePassword'])"
            >
              重置密码
            </el-button>
            <el-button
              type="danger"
              icon="Delete"
              @click="handleDelete(scope.row.userId)"
              v-if="global.$hasPerm(['sys:user:delete'])"
            >
              删除
            </el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <el-pagination
      @size-change="sizeChange"
      @current-change="currentChange"
      :current-page.sync="searchParm.currentPage"
      :page-sizes="[10, 20, 40, 80, 100]"
      :page-size="searchParm.pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="searchParm.total"
      background
    ></el-pagination>
    <!-- 新增、编辑 -->
    <SysDialog
      :title="dialog.title"
      :visible="dialog.visible"
      :width="dialog.width"
      :height="dialog.height"
      @onClose="onClose"
      @onConfirm="commit"
    >
      <template v-slot:content>
        <el-form :model="addModel" ref="addForm" :rules="rules" label-width="90px" :inline="false" size="default">
          <el-row :gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item label="姓名: " prop="nickName">
                <el-input v-model="addModel.nickName"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="性别: " prop="sex">
                <el-radio-group v-model="addModel.sex">
                  <el-radio :value="'0'">男</el-radio>
                  <el-radio :value="'1'">女</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item label="手机号: " prop="phone">
                <el-input v-model="addModel.phone"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="邮箱: " prop="email">
                <el-input v-model="addModel.email"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item label="角色: " prop="roleId">
                <SelectChecked ref="selectRef" :options="options" :bindValue="bindValue" @selected="selected" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item label="登录账户: " prop="username">
                <el-input v-model="addModel.username"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="登录密码: " prop="password" v-if="tags === 'add'">
                <el-input type="password" v-model="addModel.password"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </template>
    </SysDialog>
  </el-main>
</template>

<script setup lang="ts">
import SysDialog from '@/components/SysDialog.vue'
import useDialog from '@/hooks/useDialog'
import { ElMessage, FormInstance } from 'element-plus'
import SelectChecked from '@/components/SelectChecked.vue'
import { selectRoleListApi } from '@/api/role'
import { addUserApi, getUserListApi, deleteUserApi, getUserRoleApi, editUserApi, resetPasswordApi } from '@/api/user'
import { AddModel, SysUser } from '@/api/user/UserModel'
import useInstance from '@/hooks/useInstance'

const { dialog, onClose, onShow } = useDialog()
const { global } = useInstance()

let options = ref<any>([])
const searchParm = reactive({
  phone: '',
  nickName: '',
  currentPage: 1,
  pageSize: 10,
  total: 0
})

const addModel = reactive<AddModel>({
  userId: '',
  username: '',
  password: '',
  phone: '',
  email: '',
  sex: '',
  nickName: '',
  roleId: ''
})
const addForm = ref<FormInstance>()

// const rules = reactive<FormRules<SysRole>>({
const rules = reactive({
  nickName: [
    {
      required: true,
      message: '请输入姓名',
      trigger: ['blur', 'change']
    }
  ],
  sex: [
    {
      required: true,
      message: '请选择性别',
      trigger: ['blur', 'change']
    }
  ],
  phone: [
    {
      required: true,
      message: '请输入手机号',
      trigger: ['blur', 'change']
    }
  ],
  username: [
    {
      required: true,
      message: '请输入登录账号',
      trigger: ['blur', 'change']
    }
  ],
  password: [
    {
      required: true,
      message: '请输入登录密码',
      trigger: ['blur', 'change']
    }
  ],
  email: [
    {
      required: true,
      message: '请输入邮箱',
      trigger: ['blur', 'change']
    }
  ],
  roleId: [
    {
      required: true,
      message: '请选择角色',
      trigger: ['blur', 'change']
    }
  ]
})

// 判断新增还是编辑的标识
const tags = ref('')

const selectRef = ref()
// 表格高度
const tableHeight = ref(0)

// 搜索
const search = () => {
  getList()
}
// 重置
const reset = () => {
  searchParm.phone = ''
  searchParm.nickName = ''
  searchParm.currentPage = 1
  getList()
}
// 根据用户Id查询角色
const bindValue = ref([])
const roleIds = ref('')
const getRoleByUserId = async (userId: string) => {
  let res = await getUserRoleApi(userId)
  if (res && res.code == 200) {
    bindValue.value = res.data
    roleIds.value = res.data.join(',')
  }
}
// 新增
const add = () => {
  tags.value = 'add'
  dialog.title = '新增用户'
  dialog.height = 230
  // 清空下拉数据
  options.value = []
  bindValue.value = []
  // 获取角色下拉数据
  getRoleList()

  onShow()
  nextTick(() => {
    selectRef.value.clear()
  })
  // 清空表单
  addForm.value?.resetFields()
}
// 表单提交
const commit = () => {
  if (!addForm) return
  addForm.value?.validate(async (valid, fields) => {
    if (valid) {
      console.log('submit!')
      let res = null
      if (tags.value == 'add') {
        res = await addUserApi(addModel) //新增用户
      } else {
        res = await editUserApi(addModel) // 编辑用户
      }
      if (res && res.code == 200) {
        ElMessage.success(res.msg)
        getList()
        onClose()
      } else {
        ElMessage.error(res?.msg)
      }
    } else {
      console.log('error submit!', fields)
    }
  })
}

const selected = (value: Array<string | number>) => {
  addModel.roleId = value.join(',')
}
// 查询角色下拉数据
const getRoleList = async () => {
  let res = await selectRoleListApi()
  if (res && res.code == 200) {
    options.value = res.data
  }
}
// 获取用户列表
const tableList = ref([])
const getList = async () => {
  let res = await getUserListApi(searchParm)
  if (res && res.code == 200) {
    tableList.value = res.data.records
    searchParm.total = res.data.total
  }
}
onMounted(() => {
  nextTick(() => {
    tableHeight.value = window.innerHeight - 230
  })
  getList()
})

// 编辑
const handleEdit = async (row: SysUser) => {
  tags.value = 'edit'
  dialog.title = '编辑用户'
  dialog.height = 230
  // 清空下拉数据
  options.value = []
  bindValue.value = []
  // 获取角色下拉数据
  await getRoleList()
  // 查询角色Id
  await getRoleByUserId(row.userId)
  // 显示弹框
  onShow()
  nextTick(() => {
    //   回写数据
    Object.assign(addModel, row)
    // 设置角色的Id
    addModel.roleId = roleIds.value
    addModel.password = ''
  })

  // 清空表单
  addForm.value?.resetFields()
}
// 删除
const handleDelete = async (userId: string) => {
  const confirm = await global.$myconfirm('确定删除该数据吗？', '')
  //   console.log(confirm)
  if (confirm) {
    let res = await deleteUserApi(userId)
    if (res && res.code == 200) {
      ElMessage.success(res.msg)
      getList()
    } else {
      ElMessage.error(res.msg)
    }
  }
}
// 重置密码
const resetPassword = async (userId: string) => {
  const confirm = await global.$myconfirm('确定重置密码吗，重置之后密码为【666666】？', '')
  //   console.log(confirm)
  if (confirm) {
    let res = await resetPasswordApi({ userId })
    if (res && res.code == 200) {
      ElMessage.success(res.msg)
      getList()
    } else {
      ElMessage.error(res.msg)
    }
  }
}

// 页容量改变时触发
const sizeChange = (size: number) => {
  searchParm.pageSize = size
  getList()
}
// 页数改变时触发
const currentChange = (page: number) => {
  searchParm.currentPage = page
  getList()
}
</script>

<style scoped>
.action-buttons {
  display: inline-block;
  white-space: nowrap;
}
</style>
