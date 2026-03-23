import { ref, computed } from 'vue'

const BASE = (import.meta.env.VITE_API_URL || 'http://localhost:9008').replace(/\/$/, '')

const currentUser = ref(JSON.parse(localStorage.getItem('azurimmo-user') || 'null'))

export function useAuth() {
  const isAuthenticated = computed(() => !!currentUser.value)
  const isAdmin = computed(() => currentUser.value?.roleLibelle === 'ADMIN')
  const user = computed(() => currentUser.value)

  const login = async (adresseMail, motDePasse) => {
    const response = await fetch(`${BASE}/login/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ adresseMail, motDePasse }),
    })

    if (!response.ok) {
      let message = 'Identifiants incorrects'
      try {
        const errorData = await response.json()
        message = errorData?.message || message
      } catch {
        const text = await response.text()
        if (text) message = text
      }
      throw new Error(message)
    }

    const userData = await response.json()
    currentUser.value = userData
    localStorage.setItem('azurimmo-user', JSON.stringify(userData))
    return userData
  }

  const logout = () => {
    currentUser.value = null
    localStorage.removeItem('azurimmo-user')
  }

  return { user, isAuthenticated, isAdmin, login, logout }
}