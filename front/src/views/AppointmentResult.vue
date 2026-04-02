<template>
  <div class="appointment-result-container">
    <div class="header">
      <img src="@/assets/logo.png" alt="硅谷小医" width="100" height="100" />
      <h1 class="title">云上小医（医疗版）</h1>
      <p class="slogan">智能医疗助手，守护您的健康</p>
      <h2 class="page-title">挂号查询结果</h2>
    </div>
    
    <div v-if="appointmentData" class="result-content">
      <el-card class="result-card">
        <el-descriptions :column="1" border>
          <el-descriptions-item label="科室">{{ appointmentData.department }}</el-descriptions-item>
          <el-descriptions-item label="日期">{{ appointmentData.date }}</el-descriptions-item>
          <el-descriptions-item label="时间">{{ appointmentData.time }}</el-descriptions-item>
          <el-descriptions-item label="医生">{{ appointmentData.doctorName }}</el-descriptions-item>
          <el-descriptions-item label="姓名">{{ appointmentData.username }}</el-descriptions-item>
          <el-descriptions-item label="身份证号">{{ appointmentData.idCard }}</el-descriptions-item>
        </el-descriptions>
      </el-card>
      <el-button type="primary" class="back-button" @click="goBack">返回查询</el-button>
    </div>
    
    <div v-else class="no-data">
      <el-empty description="暂无挂号数据" />
      <el-button type="primary" class="back-button" @click="goBack">返回查询</el-button>
    </div>
    
    <div class="footer">
      <p>© 2026 云上小医 - 智能医疗系统</p>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import axios from 'axios'

const router = useRouter()
const route = useRoute()
const appointmentData = ref(null)

onMounted(() => {
  // 从路由参数中获取查询条件
  const { name, idNumber } = route.query
  if (name && idNumber) {
    queryAppointment(name, idNumber)
  } else {
    // 如果没有查询参数，返回登录页面
    goBack()
  }
})

const queryAppointment = (name, idNumber) => {
  axios.post('/api/xiaoyi/appointment/query', {
    name,
    idNumber
  })
  .then(response => {
    if (response.data) {
      appointmentData.value = response.data
    }
  })
  .catch(error => {
    console.error('查询失败:', error)
  })
}

const goBack = () => {
  router.push('/login')
}
</script>

<style scoped>
.appointment-result-container {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  padding: 40px 20px;
}

.header {
  text-align: center;
  margin-bottom: 40px;
  color: white;
}

.title {
  font-size: 28px;
  font-weight: bold;
  margin: 20px 0 10px;
}

.slogan {
  font-size: 16px;
  opacity: 0.9;
  margin-bottom: 20px;
}

.page-title {
  font-size: 20px;
  margin-top: 20px;
}

.result-content {
  width: 100%;
  max-width: 600px;
  background: white;
  border-radius: 10px;
  padding: 30px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
}

.result-card {
  margin-bottom: 30px;
}

.no-data {
  width: 100%;
  max-width: 600px;
  background: white;
  border-radius: 10px;
  padding: 60px 30px;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.back-button {
  display: block;
  margin: 0 auto;
  margin-top: 30px;
}

.footer {
  margin-top: 60px;
  color: white;
  opacity: 0.8;
  font-size: 14px;
}
</style>
