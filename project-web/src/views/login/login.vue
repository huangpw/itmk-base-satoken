<template>
  <div class="login-container">
    <el-form class="loginForm" :model="loginModel" ref="form" :rules="rules" :inline="false" size="large">
      <el-form-item>
        <div class="loginTitle">系统登录</div>
      </el-form-item>
      <el-form-item prop="username">
        <el-input v-model="loginModel.username" placeholder="请输入账户"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginModel.password" placeholder="请输入密码" type="password"></el-input>
      </el-form-item>
      <el-form-item prop="code">
        <el-row style="width: 100%">
          <el-col :span="16" :offset="0" style="padding-right: 20px">
            <el-input v-model="loginModel.code" placeholder="请输入验证码"></el-input>
          </el-col>
          <el-col :span="8" :offset="0" style="height: 40px">
            <img class="images" :src="imgSrc" alt="验证码" @click="getCaptcha" />
          </el-col>
        </el-row>
      </el-form-item>
      <el-form-item>
        <el-row style="width: 100%">
          <el-col :span="12" :offset="0" style="padding-right: 20px">
            <el-button class="mybtn" type="primary" @click="onSubmit">登录</el-button>
          </el-col>
          <el-col :span="12" :offset="0">
            <el-button class="mybtn" type="danger" plain>重置</el-button>
          </el-col>
        </el-row>
      </el-form-item>
    </el-form>
  </div>
</template>

<script setup lang="ts">
import { getCaptchaApi, loginApi } from '@/api/user'
import { useUserStore } from '@/store/user'
const router = useRouter()

const { setUserId, setNickName, setToken } = useUserStore()

// 表单绑定对象
const loginModel = reactive({
  username: 'zhangsan',
  password: '666666',
  code: ''
})

// 表单ref属性
const form = ref()

// 表单验证规则
const rules = reactive({
  username: [
    { required: true, message: '请输入账户', trigger: ['blur', 'change'] }
    // { min: 1, max: 5, message: '长度在 1 到 5 个字符', trigger: ['blur', 'change'] }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: ['blur', 'change'] }
    // { min: 1, max: 5, message: '长度在 1 到 5 个字符', trigger: ['blur', 'change'] }
  ],
  code: [{ required: true, message: '请输入验证码', trigger: ['blur', 'change'] }]
})
// 获取验证码
const imgSrc = ref('')
const getCaptcha = async () => {
  const res = await getCaptchaApi()
  if (res && res.code === 200) {
    imgSrc.value = res.data
  }
}
onMounted(() => {
  getCaptcha()
})

// 登录
const onSubmit = () => {
  form.value.validate(async (valid: boolean) => {
    if (valid) {
      let res = await loginApi(loginModel)
      if (res && res.code === 200) {
        // 存储用户信息
        setUserId(res.data.userId)
        setNickName(res.data.nickName)
        setToken(res.data.token)
        // 跳转首页
        router.push({ path: '/' })
      }
    } else {
      console.log('error submit!!')
      return false
    }
  })
}

onMounted(() => {
  getCaptcha()
})
</script>

<style lang="scss" scoped>
.login-container {
  height: 100%;
  background-color: #fff;
  background: url('../../assets/login.jpg') no-repeat;
  background-size: 100% 100%; /* 背景大小设置为100% */
  background-attachment: fixed;
  display: flex;
  justify-content: center;
  align-items: center;
  .loginForm {
    height: 300px;
    width: 400px;
    padding: 20px 35px;
    border-radius: 10px;
    background-color: #fff;
    .loginTitle {
      display: flex;
      justify-content: center;
      color: #606266;
      width: 100%;
      font-size: 24px;
      font-weight: 600;
    }
    .images {
      height: 100%;
      width: 100%;
      cursor: pointer;
    }
    .mybtn {
      width: 100%;
    }
  }
}
</style>
