<template>
  <div class="login-view">
    <div class="login-form">
      <h2>Login</h2>
      <el-form :model="form" ref="formRef" :rules="formRules" :hide-required-asterisk="true">
        <el-form-item label="账号：" prop="userId">
          <el-input v-model="form.userId" />
        </el-form-item>
        <el-form-item label="密码：" prop="password">
          <el-input v-model="form.password" type="password" />
        </el-form-item>
        <el-form-item>
          <el-button
              type="success"
              style="width: 100%;"
              @click="login()"
              :loading="loginBtn.loading"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import messageUtils from '../utils/message'

const form = reactive({
  userId: '',
  password: ''
})

const formRef = ref()

const formRules = reactive({
  userId: [
    { required: true, message: '请输入账号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
})

const loginBtn = reactive({
  loading: false
})

async function validateForm() {
  let isValid = false
  await formRef.value?.validate(valid => isValid = valid)
  return isValid
}

async function login() {
  if(!await validateForm()) {
    console.log('invalid')
    return
  }
  loginBtn.loading = true
  setTimeout(() => {
    messageUtils.success(JSON.stringify(form))
    loginBtn.loading = false
  }, 1000)
}
</script>

<style scoped>
.login-view {
  display: flex;
  margin: 0;
  justify-content: center;
}

.login-form {
  margin-top: 10vh;
  width: 20em;
}
</style>