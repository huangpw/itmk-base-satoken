<template>
  <el-dropdown>
    <span class="el-dropdown-link">
      <img class="userimg" src="@/assets/login.jpg" />
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item @click="updateBtn">修改密码</el-dropdown-item>
        <el-dropdown-item divided @click="loginOut">退出登录</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
  <SysDialog
    :title="dialog.title"
    :visible="dialog.visible"
    :width="dialog.width"
    :height="dialog.height"
    @onClose="onClose"
    @onConfirm="commit"
  >
    <template v-slot:content>
      <el-form :model="upModel" ref="formRef" :rules="rules" label-width="80px" :inline="false" size="default">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="upModel.oldPassword" type="password"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="password">
          <el-input v-model="upModel.password" type="password"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="upModel.confirmPassword" type="password"></el-input>
        </el-form-item>
      </el-form>
    </template>
  </SysDialog>
</template>

<script setup lang="ts">
import SysDialog from '@/components/SysDialog.vue'
import useDialog from '@/hooks/useDialog'
import { ElMessage, type FormInstance } from 'element-plus'
import { updatePasswordApi } from '@/api/user'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store/user'
import useInstance from '@/hooks/useInstance'
import { useMenuStore } from '@/store/menu'

const { dialog, onClose, onShow } = useDialog()
const router = useRouter()
const { userId, setToken, setCodeList, setUserId } = useUserStore()
const { setMenu } = useMenuStore()
const { global } = useInstance()

const formRef = ref<FormInstance>()

// 表单数据
const upModel = reactive({
  userId: userId,
  oldPassword: '',
  password: '',
  confirmPassword: ''
})

// 表单验证规则
const rules = reactive({
  oldPassword: [
    {
      required: true,
      message: '原密码不能为空',
      trigger: ['blur', 'change']
    }
  ],
  password: [
    {
      required: true,
      message: '新密码不能为空',
      trigger: ['blur', 'change']
    }
  ],
  confirmPassword: [
    {
      required: true,
      message: '确认密码不能为空',
      trigger: ['blur', 'change']
    }
  ]
})

// 修改密码
const updateBtn = () => {
  dialog.title = '修改密码'
  onShow()
}
// 确认
const commit = () => {
  formRef.value?.validate(async valid => {
    if (valid) {
      console.log('submit!')
      // 判断新密码和确认密码是否一致
      if (upModel.password !== upModel.confirmPassword) {
        ElMessage.error('新密码和确认密码不一致')
        return
      }
      let res = await updatePasswordApi(upModel)
      if (res && res.code === 200) {
        ElMessage.success(res.msg)
        // 清空缓存
        sessionStorage.clear()
        router.push('/login')
      } else {
        ElMessage.error(res.msg)
      }
    } else {
      console.log('error submit!')
    }
  })
}
const loginOut = async () => {
  const confirm = await global.$myconfirm('确定退出登录吗？', '')
  if (confirm) {
    // 清空缓存
    sessionStorage.clear()
    localStorage.clear()
    setUserId('')
    setToken('')
    setCodeList([])
    setMenu([])
    router.push({ path: '/login' })
  }
}
</script>

<style lang="scss" scoped>
.el-dropdown-link {
  outline: none;
  .userimg {
    width: 48px;
    height: 48px;
    border-radius: 50%;
    cursor: pointer;
  }
}
</style>
