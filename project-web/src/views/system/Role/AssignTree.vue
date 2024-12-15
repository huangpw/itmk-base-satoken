<template>
  <SysDialog
    :title="dialog.title"
    :visible="dialog.visible"
    :width="dialog.width"
    :height="dialog.height"
    @onClose="onClose"
    @onConfirm="commit"
  >
    <!-- :default-expanded-keys="assignTreeData.assignTreeChecked" -->
    <template v-slot:content>
      <el-tree
        ref="treeRef"
        node-key="menuId"
        :data="assignTreeData.list"
        :props="defaultProps"
        :default-checked-keys="assignTreeData.assignTreeChecked"
        show-checkbox
        default-expand-all
      />
    </template>
  </SysDialog>
</template>

<script setup lang="ts">
import { getAssignTreeApi } from '@/api/user'
import { AssignParm } from '@/api/user/UserModel'
import SysDialog from '@/components/SysDialog.vue'
import useDialog from '@/hooks/useDialog'
import { useUserStore } from '@/store/user'
import { saveRoleMenuApi } from '@/api/role'
import { ElMessage, ElTree } from 'element-plus'

const { getUserId } = useUserStore()

const { dialog, onClose, onShow } = useDialog()

const commitParm = reactive({
  roleId: '',
  list: [] as string[]
})

// 树数据
const assignTreeData = reactive({
  list: [],
  assignTreeChecked: [] as number[] // 原来分配的权限id集合
})
const treeRef = ref<InstanceType<typeof ElTree>>()
// 树的属性配置
const defaultProps = {
  children: 'children',
  label: 'title'
}
// 树数据查询的参数
const parms = reactive<AssignParm>({
  userId: '',
  roleId: ''
})
// 查询菜单树
const getAssignTree = async () => {
  parms.userId = getUserId
  let res = await getAssignTreeApi(parms)
  if (res && res.code === 200) {
    // 设置权限树数据
    assignTreeData.list = res.data.menuList
    // 设置角色原来的权限Id
    assignTreeData.assignTreeChecked = res.data.checkList
    // 数据回显，判断角色原来是否已经分配过权限，如果有，回显
    if (assignTreeData.assignTreeChecked.length > 0) {
      let newArr: any = []
      assignTreeData.assignTreeChecked.forEach(item => {
        checked(item, assignTreeData.list, newArr)
      })
      assignTreeData.assignTreeChecked = newArr
    }
  }
}
const checked = (id: number, data: any, newArr: any) => {
  data.forEach((item: any) => {
    if (item.menuId == id) {
      if (item.children && item.children.length == 0) {
        newArr.push(item.menuId)
      }
    } else {
      if (item.children && item.children.length != 0) {
        // 递归调用
        checked(id, item.children, newArr)
      }
    }
  })
}

// 提交
const commit = async () => {
  // 获取选择的菜单数据
  console.log(treeRef.value!.getCheckedKeys())
  console.log(treeRef.value!.getHalfCheckedKeys())
  const checkIds = treeRef.value!.getCheckedKeys() as string[]
  const halfCheckIds = treeRef.value!.getHalfCheckedKeys() as string[]
  let ids = checkIds?.concat(halfCheckIds)
  // 设置选中的节点
  commitParm.list = ids

  // 判断是否已经选择菜单
  if (ids.length === 0) {
    ElMessage.error('请选择菜单')
    return
  }
  let res = await saveRoleMenuApi(commitParm)
  if (res && res.code === 200) {
    ElMessage.success(res.msg)
    onClose()
  }
}

// 弹框显示
const show = async (roleId: string, name: string) => {
  commitParm.roleId = roleId
  assignTreeData.assignTreeChecked = []
  assignTreeData.list = []
  commitParm.list = []
  parms.roleId = roleId
  dialog.height = 450
  dialog.width = 300
  dialog.title = `为【${name}】分配菜单`
  // 获取树数据
  await getAssignTree()
  onShow()
}
// 暴露出去，给外部组件使用
defineExpose({
  show
})
</script>

<style scoped></style>
