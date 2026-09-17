import axios from 'axios'

const api = axios.create({
  baseURL: '/api',
  timeout: 5000
})

export function listUsers() {
  return api.get('/users')
}

export function createUser(user) {
  return api.post('/users', user)
}

export function updateUser(id, user) {
  return api.put(`/users/${id}`, user)
}

export function deleteUser(id) {
  return api.delete(`/users/${id}`)
}
