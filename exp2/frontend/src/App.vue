<script setup>
import UserList from './components/UserList.vue'
</script>

<template>
  <div class="app">
    <h1>实验二：前后端分离与 RESTful API</h1>
    <UserList />
  </div>
</template>

<style>
body {
  font-family: system-ui, -apple-system, sans-serif;
  margin: 0;
  background: #f5f6f8;
}
.app {
  max-width: 720px;
  margin: 0 auto;
  padding: 24px;
}
</style>
