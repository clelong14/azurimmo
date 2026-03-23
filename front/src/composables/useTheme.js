import { ref, onMounted } from 'vue'

const isDark = ref(false)

export function useTheme() {
  const apply = (dark) => {
    document.documentElement.setAttribute('data-theme', dark ? 'dark' : 'light')
  }

  const init = () => {
    const saved = localStorage.getItem('azurimmo-theme')
    isDark.value = saved ? saved === 'dark' : window.matchMedia('(prefers-color-scheme: dark)').matches
    apply(isDark.value)
  }

  const toggleTheme = () => {
    isDark.value = !isDark.value
    localStorage.setItem('azurimmo-theme', isDark.value ? 'dark' : 'light')
    apply(isDark.value)
  }

  onMounted(init)

  return { isDark, toggleTheme }
}