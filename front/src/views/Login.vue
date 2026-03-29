<template>
  <div class="login-container">
    <div class="login-form-wrapper">
      <div class="logo-section">
        <img src="@/assets/logo.png" alt="硅谷小医" width="120" height="120" />
        <h1 class="logo-text">云上小医（医疗版）</h1>
        <p class="slogan">智能医疗助手，守护您的健康</p>
      </div>
      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" class="login-form">
        <el-form-item prop="username">
          <el-input v-model="loginForm.username" placeholder="用户名" prefix-icon="UserFilled" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input v-model="loginForm.password" type="password" placeholder="密码" prefix-icon="LockFilled" show-password />
        </el-form-item>
        <el-form-item prop="captcha" class="captcha-item">
          <el-input v-model="loginForm.captcha" placeholder="验证码" prefix-icon="GridFilled" class="captcha-input" />
          <canvas ref="captchaCanvas" width="120" height="40" class="captcha-canvas" @click="generateCaptcha"></canvas>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="rememberMe">记住我</el-checkbox>
          <el-link type="primary" :underline="false" style="float: right">忘记密码？</el-link>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="login-button" @click="handleLogin" :loading="isLoading" round>
            登录
          </el-button>
        </el-form-item>
      </el-form>
      <div class="footer">
        <p>© 2026 云上小医 - 智能医疗系统</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const loginFormRef = ref()
const isLoading = ref(false)
const rememberMe = ref(false)
const captchaCanvas = ref(null)
const currentCaptcha = ref('')

const loginForm = reactive({
  username: '',
  password: '',
  captcha: ''
})

// 验证码校验规则
const validateCaptcha = (rule, value, callback) => {
  if (!value) {
    callback(new Error('请输入验证码'))
  } else if (value.toLowerCase() !== currentCaptcha.value.toLowerCase()) {
    callback(new Error('验证码错误'))
  } else {
    callback()
  }
}

const rules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 2, max: 20, message: '用户名长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于 6 个字符', trigger: 'blur' }
  ],
  captcha: [
    { required: true, validator: validateCaptcha, trigger: 'blur' }
  ]
}

// 生成随机验证码
const generateCaptcha = () => {
  const canvas = captchaCanvas.value
  const ctx = canvas.getContext('2d')
  const width = canvas.width
  const height = canvas.height

  // 清空画布
  ctx.fillStyle = '#f5f5f5'
  ctx.fillRect(0, 0, width, height)

  // 生成随机字符
  const chars = 'ABCDEFGHJKLMNPQRSTUVWXYZ23456789'
  let captcha = ''
  for (let i = 0; i < 4; i++) {
    captcha += chars.charAt(Math.floor(Math.random() * chars.length))
  }
  currentCaptcha.value = captcha

  // 绘制干扰线
  for (let i = 0; i < 5; i++) {
    ctx.strokeStyle = `rgb(${Math.random() * 255}, ${Math.random() * 255}, ${Math.random() * 255})`
    ctx.beginPath()
    ctx.moveTo(Math.random() * width, Math.random() * height)
    ctx.lineTo(Math.random() * width, Math.random() * height)
    ctx.stroke()
  }

  // 绘制验证码文字
  for (let i = 0; i < captcha.length; i++) {
    ctx.font = `bold ${Math.random() * 10 + 20}px Arial`
    ctx.fillStyle = `rgb(${Math.random() * 100 + 50}, ${Math.random() * 100 + 50}, ${Math.random() * 100 + 50})`
    ctx.textBaseline = 'middle'
    const x = 20 + i * 25
    const y = height / 2 + Math.random() * 10 - 5
    const angle = Math.random() * 0.4 - 0.2
    ctx.save()
    ctx.translate(x, y)
    ctx.rotate(angle)
    ctx.fillText(captcha[i], 0, 0)
    ctx.restore()
  }

  // 绘制干扰点
  for (let i = 0; i < 30; i++) {
    ctx.fillStyle = `rgb(${Math.random() * 255}, ${Math.random() * 255}, ${Math.random() * 255})`
    ctx.beginPath()
    ctx.arc(Math.random() * width, Math.random() * height, 1, 0, 2 * Math.PI)
    ctx.fill()
  }
}

const handleLogin = () => {
  loginFormRef.value.validate((valid) => {
    if (valid) {
      isLoading.value = true
      // 模拟登录请求
      setTimeout(() => {
        isLoading.value = false
        // 登录成功后跳转到聊天页面
        router.push('/chat')
      }, 1000)
    } else {
      console.log('表单验证失败')
      return false
    }
  })
}

onMounted(() => {
  generateCaptcha()
})
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.login-form-wrapper {
  width: 400px;
  padding: 40px;
  background: white;
  border-radius: 12px;
  box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px);
}

.logo-section {
  text-align: center;
  margin-bottom: 30px;
}

.logo-text {
  font-size: 24px;
  font-weight: bold;
  margin: 15px 0 10px 0;
  color: #2c3e50;
}

.slogan {
  font-size: 14px;
  color: #7f8c8d;
  margin: 0;
}

.login-form {
  width: 100%;
}

.captcha-item :deep(.el-form-item__content) {
  display: flex;
  gap: 10px;
}

.captcha-input {
  flex: 1;
}

.captcha-canvas {
  width: 120px;
  height: 40px;
  border-radius: 4px;
  cursor: pointer;
  border: 1px solid #dcdfe6;
}

.login-button {
  width: 100%;
  height: 48px;
  font-size: 16px;
  margin-top: 10px;
}

.footer {
  text-align: center;
  margin-top: 30px;
  font-size: 12px;
  color: #95a5a6;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .login-form-wrapper {
    width: 90%;
    padding: 30px;
  }
}
</style>
