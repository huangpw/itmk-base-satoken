<template>
  <el-main>
    <!-- 搜索栏 -->
    <el-form :model="searchParm" :inline="true" size="default">
      <el-form-item label="">
        <el-input v-model="searchParm.roleName" placeholder="请输入角色名称"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button icon="Search" @click="search">搜索</el-button>
        <el-button icon="Close" type="danger" plain @click="reset">重置</el-button>
        <el-button icon="Plus" type="primary" @click="add" v-if="global.$hasPerm(['sys:role:add'])">新增</el-button>
      </el-form-item>
    </el-form>
    <!-- 表格数据 -->
    <el-table :height="tableHeight" :data="tableList" border stripe :header-cell-style="{ 'text-align': 'center' }">
      <el-table-column prop="roleName" label="角色名称"></el-table-column>
      <el-table-column prop="remark" label="备注"></el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column
        label="操作"
        width="320"
        v-if="global.$hasPerm(['sys:role:update', 'sys:role:allotMenu', 'sys:role:delete'])"
      >
        <template #default="scope">
          <el-button
            type="primary"
            icon="Edit"
            @click="handleEdit(scope.row)"
            v-if="global.$hasPerm(['sys:role:update'])"
          >
            编辑
          </el-button>
          <el-button
            type="success"
            icon="Setting"
            @click="assignTreeEdit(scope.row)"
            v-if="global.$hasPerm(['sys:role:allotMenu'])"
          >
            分配菜单
          </el-button>
          <el-button
            type="danger"
            icon="Delete"
            @click="handleDelete(scope.row.roleId)"
            v-if="global.$hasPerm(['sys:role:delete'])"
          >
            删除
          </el-button>
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
        <el-form :model="addModel" ref="addRef" :rules="rules" label-width="80px" :inline="false" size="default">
          <el-form-item label="角色名称" prop="roleName">
            <el-input v-model="addModel.roleName"></el-input>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input v-model="addModel.remark"></el-input>
          </el-form-item>
        </el-form>
      </template>
    </SysDialog>
    <!-- 分配菜单 -->
    <AssignTree ref="assignTree" />
  </el-main>
</template>

<script setup lang="ts">
import SysDialog from '@/components/SysDialog.vue'
import useDialog from '@/hooks/useDialog'
import { ElMessage, FormInstance, FormRules } from 'element-plus'
import { addApi, deleteApi, editApi, getListApi } from '@/api/role'
import { SysRole } from '@/api/role/RoleModel'
import useInstance from '@/hooks/useInstance'
import AssignTree from './AssignTree.vue'

const { dialog, onClose, onShow } = useDialog()
const { global } = useInstance()
const addRef = ref<FormInstance>()

const searchParm = reactive({
  currentPage: 1,
  pageSize: 10,
  roleName: '',
  total: 0
})

const addModel = reactive({
  roleId: '',
  roleName: '',
  remark: ''
})

const rules = reactive<FormRules<SysRole>>({
  roleName: [
    {
      required: true,
      message: '请输入角色名称',
      trigger: 'change'
    }
  ]
})
// 表格高度
const tableHeight = ref(0)
// 判断新增还是编辑的标识
const tags = ref('')

const assignTree = ref()

// 搜索
const search = () => {
  getList()
}
// 重置
const reset = () => {
  searchParm.roleName = ''
  searchParm.currentPage = 1
  getList()
}
// 新增
const add = () => {
  tags.value = 'add'
  dialog.title = '新增角色'
  dialog.height = 200
  onShow()
  // 清空表单
  addRef.value?.resetFields()
}
// 表单提交
const commit = () => {
  if (!addRef) return
  addRef.value?.validate(async (valid, fields) => {
    if (valid) {
      console.log('submit!')
      // 提交请求
      let res = null
      if (tags.value == 'add') {
        res = await addApi(addModel)
      } else {
        res = await editApi(addModel)
      }
      if (res && res.code == 200) {
        ElMessage.success(res.msg)
        getList()
        onClose()
      } else {
        ElMessage.error(res.msg)
      }
    } else {
      console.log('error submit!', fields)
    }
  })
}
// 表格数据
const tableList = ref([])
// 查询列表
const getList = async () => {
  let res = await getListApi(searchParm)
  if (res && res.code == 200) {
    // 设置表格数据
    tableList.value = res.data.records
    // 设置总条数
    searchParm.total = res.data.total
  } else {
    ElMessage.error(res.msg)
  }
}
onMounted(() => {
  nextTick(() => {
    tableHeight.value = window.innerHeight - 230
  })
  getList()
})
// 编辑
const handleEdit = (row: SysRole) => {
  tags.value = 'edit'
  dialog.visible = true
  dialog.title = '编辑角色'
  dialog.height = 200
  nextTick(() => {
    //   回写数据
    Object.assign(addModel, row)
  })
  // 清空表单
  addRef.value?.resetFields()
}
// 删除
const handleDelete = async (roleId: string) => {
  const confirm = await global.$myconfirm('确定删除该数据吗？', '')
  if (confirm) {
    let res = await deleteApi(roleId)
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

// 分配菜单
const assignTreeEdit = (row: SysRole) => {
  assignTree.value.show(row.roleId)
}
</script>

<style scoped></style>
