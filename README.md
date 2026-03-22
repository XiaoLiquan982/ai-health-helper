# 云上小医（医疗版） - LangChain4j 实战项目

基于 LangChain4j + 通义千问 + Spring Boot + Vue 3 的 AI 医疗问诊与预约挂号助手

Spring Boot Vue.js LangChain4j Java

大家好，这是一套以 AI 医疗助手为主题的 LangChain4j 实战项目。相比只讲概念和 API 的入门案例，这个项目更强调“能跑、能看、能改、能扩展”，把大模型对话、会话记忆、流式输出、工具调用、RAG 检索和预约挂号业务结合到一个完整应用中。

项目最终呈现的是一个“云上小医（医疗版）”智能助手。用户可以像和真人医生助理聊天一样，进行基础医疗咨询、了解医院和科室信息，并通过 AI 发起预约挂号、查询号源、取消预约等操作。

如果你想系统练习 Java AI 应用开发，这个项目很适合作为一个完整的实战样例和项目经历。

## ✨ 项目介绍

### 定位

- AI 医疗咨询助手：提供基础医疗问答、医院信息介绍和科室推荐
- 智能预约挂号助手：支持预约挂号、取消预约、查询号源等业务能力
- 医疗知识问答助手：结合本地知识库进行更准确的回答
- LangChain4j 学习项目：覆盖会话记忆、工具调用、流式响应、RAG 等常见能力

### 项目亮点

- 流式对话体验：前端实时展示模型输出，交互更自然
- 会话记忆能力：支持基于 `memoryId` 的多轮上下文聊天
- 工具调用落地：AI 可调用预约挂号相关工具完成真实业务操作
- RAG 检索增强：结合本地 `knowledge` 目录中的医疗资料辅助回答
- 多模型接入：当前接入通义千问兼容 OpenAI 接口，也保留 Ollama 本地模型配置
- 前后端分离：后端 Spring Boot，前端 Vue 3 + Element Plus

## 🧠 技术栈

### 后端

- Java 17
- Spring Boot 3.2.6
- LangChain4j 1.0.0-beta3
- Spring WebFlux
- MyBatis-Plus
- MySQL
- MongoDB
- Knife4j / OpenAPI

### 前端

- Vue 3
- Vite
- Element Plus
- Axios

### AI 能力

- 通义千问 / 百炼平台
- OpenAI Compatible 接口接入
- DashScope 社区模型支持
- Ollama 本地模型支持
- Pinecone 向量存储（本地开发未配置时已支持内存向量库回退）

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- Node.js 16+
- MySQL 8.0
- MongoDB 6+（未启动时后端仍可启动）
- 百炼 / DashScope API Key

### 1. 克隆项目

```bash
git clone <your-repository-url>
cd ai-health-helper
```

### 2. 配置后端本地密钥

项目已经支持本地私有配置文件，公开配置中不会直接保存密钥。

后端默认会加载：

```text
back/src/main/resources/application.yml
back/src/main/resources/application-local.yml
```

其中：

- `application.yml`：公共配置
- `application-local.yml`：本地私有配置，已加入 `.gitignore`

如果你需要修改本地密钥，可编辑：

```yaml
# back/src/main/resources/application-local.yml
DASH_SCOPE_API_KEY: "你的百炼 API Key"
```

### 3. 启动后端

在项目根目录执行：

```bash
mvn -DskipTests spring-boot:run
```

默认后端端口：

```text
http://localhost:8080
```

### 4. 启动前端

```bash
cd front
npm install
npm run dev
```

Vite 默认端口通常为：

```text
http://localhost:5173
```

如果默认端口被占用，Vite 可能自动切换到 `5174` 等其他端口。

### 5. 访问项目

- 前端页面：`http://localhost:5173` 或 Vite 实际输出端口
- 后端服务：`http://localhost:8080`
- 前端通过 Vite 代理将 `/api` 请求转发到后端

## 🏗️ 技术架构

```text
┌────────────────────┐      ┌────────────────────┐
│    Vue 3 前端       │──────│   Spring Boot 后端   │
│   - 聊天界面         │      │   - REST/SSE 对话    │
│   - 消息流式渲染     │      │   - AI Agent         │
│   - 新建会话         │      │   - 工具调用         │
└────────────────────┘      └────────────────────┘
                                       │
                           ┌────────────────────┐
                           │    LangChain4j     │
                           │   - 会话记忆        │
                           │   - RAG 检索        │
                           │   - Tool Calling    │
                           └────────────────────┘
                                       │
          ┌────────────────────────────┼────────────────────────────┐
          │                            │                            │
┌────────────────────┐      ┌────────────────────┐      ┌────────────────────┐
│   通义千问 / 百炼    │      │   MongoDB 会话存储   │      │  MySQL 挂号业务库    │
│   - 对话模型         │      │   - 多轮上下文       │      │  - 预约记录管理      │
│   - 嵌入模型         │      │                      │      │                      │
└────────────────────┘      └────────────────────┘      └────────────────────┘
```

## 📦 目录结构

```text
ai-health-helper/
├─ back/                         # 后端源码与资源
│  └─ src/main/
│     ├─ java/com/atguigu/java/ai/langchain4j/
│     │  ├─ assistant/           # AI 助手与 Agent 定义
│     │  ├─ config/              # LangChain4j / 记忆 / RAG / Embedding 配置
│     │  ├─ controller/          # 对话接口
│     │  ├─ service/             # 挂号业务服务
│     │  ├─ tools/               # AI 工具调用实现
│     │  └─ store/               # Mongo 聊天记录存储
│     └─ resources/
│        ├─ application.yml
│        ├─ application-local.yml
│        └─ mapper/              # MyBatis XML
├─ front/                        # Vue 3 前端
├─ knowledge/                    # 本地医疗知识库
└─ pom.xml
```

## 🔧 核心模块

- `XiaoyiController`：提供 `/xiaoyi/chat` 对话接口，返回流式响应
- `XiaoyiAgent`：核心 AI 助手定义，负责整合模型、记忆、工具与检索能力
- `MongoChatMemoryStore`：基于 MongoDB 的会话记忆存储
- `AppointmentTools`：预约挂号、取消预约、查询号源等工具调用入口
- `AppointmentService`：挂号业务服务层
- `EmbeddingStoreConfig`：向量存储配置，支持 Pinecone 与本地内存回退
- `XiaoyiAgentConfig`：RAG 检索器与 Agent 能力组装

## 📚 知识库与业务能力

当前项目已经内置了医疗知识相关文件，可用于扩展 RAG：

- 医院信息
- 科室信息
- 神经内科
- 口腔科
- 其他测试资料

结合 LangChain4j 后，项目可进一步扩展为：

- 智能分诊
- 医生推荐
- 检查项目说明
- 就诊流程咨询
- 医疗知识问答

## 🖼️ 页面效果

项目当前前端为简洁的聊天式医疗助手界面，支持：

- 自动欢迎语
- 用户与 AI 双侧消息展示
- 流式输出
- 新建会话
- 本地 `UUID` 会话隔离

你提供的页面截图对应的就是当前项目前端运行效果。

## ⚠️ 说明

- 本项目更偏向 LangChain4j 学习与 AI 应用开发实战，并非真实医疗系统
- 医疗问答内容仅作学习演示，不能替代专业医生诊断
- 若未启动 MongoDB，后端通常仍可启动，但控制台会提示连接告警
- 若未配置 Pinecone，项目已自动回退为内存向量存储，方便本地开发

## 🙏 致谢

- LangChain4j
- Spring Boot
- Vue 3
- Element Plus
- 阿里云百炼 / 通义千问
- MyBatis-Plus
- MongoDB

如果这个项目对你有帮助，欢迎 Star，也欢迎在这个基础上继续扩展出自己的 AI 医疗助手项目。
