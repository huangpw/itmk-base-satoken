<template>
  <el-main>
    <el-button type="primary" icon="Plus" size="default" @click="add" v-if="global.$hasPerm(['sys:menu:add'])">
      新增
    </el-button>

    <!-- 表格 -->
    <el-table
      :data="tableList"
      row-key="menuId"
      border
      stripe
      default-expand-all
      style="margin-top: 20px"
      :header-cell-style="{ 'text-align': 'center' }"
    >
      <el-table-column prop="title" label="菜单名称"></el-table-column>
      <el-table-column prop="icon" label="菜单图标">
        <template #default="scope">
          <el-icon v-if="scope.row.icon">
            <component :is="scope.row.icon" />
          </el-icon>
        </template>
      </el-table-column>
      <el-table-column prop="type" label="菜单类型" width="100">
        <template #default="scope">
          <el-tag v-if="scope.row.type === '0'" type="danger" size="default">目录</el-tag>
          <el-tag v-if="scope.row.type === '1'" type="success" size="default">菜单</el-tag>
          <el-tag v-if="scope.row.type === '2'" type="primary" size="default">按钮</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="parentName" label="上级菜单"></el-table-column>
      <el-table-column prop="name" label="路由名称"></el-table-column>
      <el-table-column prop="path" label="路由地址"></el-table-column>
      <el-table-column prop="url" label="组件路径" width="300"></el-table-column>
      <el-table-column prop="code" label="权限字段"></el-table-column>
      <el-table-column prop="orderNum" label="序号" width="60"></el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column
        label="操作"
        width="220"
        align="center"
        v-if="global.$hasPerm(['sys:menu:update', 'sys:menu:delete'])"
      >
        <template #default="scope">
          <el-button
            type="primary"
            icon="Edit"
            size="default"
            @click="handleEdit(scope.row)"
            v-if="global.$hasPerm(['sys:menu:update'])"
          >
            编辑
          </el-button>
          <el-button
            type="danger"
            icon="Delete"
            size="default"
            @click="handleDelete(scope.row.menuId)"
            v-if="global.$hasPerm(['sys:menu:delete'])"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <SysDialog
      :title="dialog.title"
      :visible="dialog.visible"
      :width="dialog.width"
      :height="dialog.height"
      @onClose="onClose"
      @onConfirm="commit"
    >
      <template v-slot:content>
        <el-form :model="addModel" ref="addForm" :rules="rules" label-width="80px" :inline="false" size="default">
          <el-form-item label="菜单类型" prop="type">
            <el-radio-group v-model="addModel.type">
              <el-radio :value="'0'">目录</el-radio>
              <el-radio :value="'1'">菜单</el-radio>
              <el-radio :value="'2'">按钮</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item label="上级菜单" prop="parentId">
                <el-tree-select
                  v-model="addModel.parentId"
                  :data="treeList"
                  :render-after-expand="false"
                  show-checkbox
                  default-expand-all
                  check-strictly
                  @check-change="treeCheckChange"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="菜单名称" prop="title">
                <el-input v-model="addModel.title"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20" v-if="addModel.type !== '2'">
            <el-col :span="12" :offset="0">
              <el-form-item label="菜单图标" prop="icon">
                <el-input v-model="addModel.icon"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="路由名称" prop="name">
                <el-input v-model="addModel.name"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0">
              <el-form-item label="菜单序号" prop="orderNum">
                <el-input v-model="addModel.orderNum"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0">
              <el-form-item label="权限字段" prop="code">
                <el-input v-model="addModel.code"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12" :offset="0" v-if="addModel.type !== '2'">
              <el-form-item label="路由地址" prop="path">
                <el-input v-model="addModel.path"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="0" v-if="addModel.type === '1'">
              <el-form-item label="组件路径" prop="url">
                <el-input v-model="addModel.url"></el-input>
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
import { FormInstance, ElMessage } from 'element-plus'
import { getParentApi, addMenuApi, getListApi, editApi, deleteApi } from '@/api/menu'
import { MenuType } from '@/api/menu/MenuModel'
import useInstance from '@/hooks/useInstance'

const { dialog, onClose, onShow } = useDialog()
const { global } = useInstance()

const addModel = reactive({
  menuId: '',
  parentId: '',
  title: '',
  code: '',
  name: '',
  path: '',
  url: '',
  type: '',
  icon: '',
  parentName: '',
  orderNum: ''
})
const addForm = ref<FormInstance>()

// 表单验证规则
const rules = reactive({
  parentId: [
    {
      required: true,
      message: '请选择上级菜单',
      trigger: ['blur', 'change']
    }
  ],
  title: [
    {
      required: true,
      message: '请输入菜单名称',
      trigger: ['blur', 'change']
    }
  ],
  code: [
    {
      required: true,
      message: '请输入权限字段',
      trigger: ['blur', 'change']
    }
  ],
  name: [
    {
      required: true,
      message: '请输入路由名称',
      trigger: ['blur', 'change']
    }
  ],
  path: [
    {
      required: true,
      message: '请输入路由地址',
      trigger: ['blur', 'change']
    }
  ],
  url: [
    {
      required: true,
      message: '请输入路由地址',
      trigger: ['blur', 'change']
    }
  ],
  type: [
    {
      required: true,
      message: '请输入菜单类型',
      trigger: ['blur', 'change']
    }
  ],
  icon: [
    {
      required: true,
      message: '请输入菜单图标',
      trigger: ['blur', 'change']
    }
  ],
  orderNum: [
    {
      required: true,
      message: '请输入菜单序号',
      trigger: ['blur', 'change']
    }
  ]
})

// 判断新增还是编辑的标识
const tags = ref('')

// 获取上级菜单数据
const treeList = ref([])
const getParent = async () => {
  let res = await getParentApi()
  if (res && res.code == 200) {
    treeList.value = res.data
  }
}

// 新增
const add = () => {
  tags.value = 'add'
  dialog.title = '新增角色'
  dialog.height = 300
  // 获取上级菜单
  getParent()
  // 显示弹框
  onShow()
  // 清空表单
  addForm.value?.resetFields()
}

// 提交表单
const commit = () => {
  if (!addForm) return
  addForm.value?.validate(async (valid, fields) => {
    if (valid) {
      console.log('submit!', addModel)
      let res = null
      if (tags.value == 'add') {
        res = await addMenuApi(addModel) //新增用户
      } else {
        res = await editApi(addModel) // 编辑用户
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

// 上级选中事件
const treeCheckChange = (item: any) => {
  console.log(item)
  addModel.parentName = item.title ? item.title : ''
}
// 获取列表
const tableList = ref([])
const getList = async () => {
  let res = await getListApi()
  if (res && res.code == 200) {
    tableList.value = res.data
  }
}
onMounted(() => {
  getList()
})

// 编辑
const handleEdit = (row: MenuType) => {
  tags.value = 'edit'
  dialog.title = '新增角色'
  dialog.height = 300
  dialog.visible = true
  // 获取上级菜单
  getParent()
  nextTick(() => {
    //   回写数据
    Object.assign(addModel, row)
  })
  // 清空表单
  addForm.value?.resetFields()
}
// 删除
const handleDelete = async (menuId: string) => {
  const confirm = await global.$myconfirm('确定删除该数据吗？', '')
  if (confirm) {
    let res = await deleteApi(menuId)
    if (res && res.code == 200) {
      ElMessage.success(res.msg)
      getList()
    } else {
      ElMessage.error(res.msg)
    }
  }
}
</script>
<style scoped></style>
