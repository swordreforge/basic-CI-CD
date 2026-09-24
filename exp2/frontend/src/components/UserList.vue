<script setup>
import { onMounted, ref } from 'vue'
import { listUsers, createUser, updateUser, deleteUser } from '../api/userApi'

const users = ref([])
const form = ref({ id: null, username: '', password: '', email: '' })
const error = ref('')

async function load() {
  try {
    const { data } = await listUsers()
    users.value = data
    error.value = ''
  } catch (e) {
    error.value = e.message
  }
}

async function save() {
  try {
    // AI-assisted: 更新且密码留空时不传 password 字段，后端保留原哈希，人工已复核。
    const payload = {
      username: form.value.username,
      email: form.value.email
    }
    if (!form.value.id || form.value.password) payload.password = form.value.password
    if (form.value.id) {
      await updateUser(form.value.id, payload)
    } else {
      await createUser(payload)
    }
    form.value = { id: null, username: '', password: '', email: '' }
    await load()
  } catch (e) {
    error.value = e.message
  }
}

function edit(user) {
  // 回显时不带出密码哈希（避免二次加密），留空即保留原密码
  form.value = { id: user.id, username: user.username, password: '', email: user.email || '' }
}

async function remove(id) {
  await deleteUser(id)
  await load()
}

onMounted(load)
</script>

<template>
  <div>
    <p v-if="error" class="error">{{ error }}</p>

    <form class="form" @submit.prevent="save">
      <input v-model="form.username" placeholder="用户名" required />
      <input v-model="form.password" :placeholder="form.id ? '留空则不修改密码' : '密码'" :required="!form.id" />
      <input v-model="form.email" placeholder="邮箱" />
      <button type="submit">{{ form.id ? '更新' : '新增' }}</button>
    </form>

    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>用户名</th>
          <th>邮箱</th>
          <th>操作</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="user in users" :key="user.id">
          <td>{{ user.id }}</td>
          <td>{{ user.username }}</td>
          <td>{{ user.email }}</td>
          <td>
            <button @click="edit(user)">编辑</button>
            <button @click="remove(user.id)">删除</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.form {
  display: flex;
  gap: 8px;
  margin-bottom: 16px;
}
input {
  padding: 6px 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
}
button {
  padding: 6px 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  background: #fff;
  cursor: pointer;
}
table {
  width: 100%;
  border-collapse: collapse;
  background: #fff;
}
th, td {
  border: 1px solid #e3e5e8;
  padding: 8px;
  text-align: left;
}
.error {
  color: #c0392b;
}
</style>
