<template>
  <div class="login-shell">

    <div class="login-left">
      <div class="login-brand">
        <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
          <rect x="2" y="2" width="9" height="9" rx="2" fill="#2563eb"/>
          <rect x="13" y="2" width="9" height="9" rx="2" fill="#2563eb" opacity=".4"/>
          <rect x="2" y="13" width="9" height="9" rx="2" fill="#2563eb" opacity=".4"/>
          <rect x="13" y="13" width="9" height="9" rx="2" fill="#2563eb" opacity=".7"/>
        </svg>
        <span>Azurimmo</span>
      </div>

      <div class="login-card">
        <div class="login-header">
          <h1 class="login-title">Connexion</h1>
          <p class="login-subtitle">Accédez à votre espace de gestion immobilière.</p>
        </div>

        <form @submit.prevent="submit" class="login-form">

          <div class="field">
            <label class="field-label">Adresse email</label>
            <div class="input-wrap" :class="{ focused: focusedField === 'email' }">
              <svg class="input-icon" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/><polyline points="22,6 12,13 2,6"/></svg>
              <input
                  v-model="form.adresseMail"
                  type="email"
                  class="login-input"
                  placeholder="Ex : jean@azurimmo.fr"
                  autocomplete="email"
                  required
                  @focus="focusedField = 'email'"
                  @blur="focusedField = null"
              />
            </div>
          </div>

          <div class="field">
            <label class="field-label">Mot de passe</label>
            <div class="input-wrap" :class="{ focused: focusedField === 'password' }">
              <svg class="input-icon" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="11" width="18" height="11" rx="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg>
              <input
                  v-model="form.motDePasse"
                  :type="showPwd ? 'text' : 'password'"
                  class="login-input"
                  placeholder="••••••••"
                  autocomplete="current-password"
                  required
                  @focus="focusedField = 'password'"
                  @blur="focusedField = null"
              />
              <button type="button" class="toggle-pwd" @click="showPwd = !showPwd" tabindex="-1">
                <svg v-if="!showPwd" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
                <svg v-else width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/><line x1="1" y1="1" x2="23" y2="23"/></svg>
              </button>
            </div>
          </div>

          <transition name="shake">
            <div v-if="error" class="error-msg">
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
              {{ error }}
            </div>
          </transition>

          <button type="submit" class="login-btn" :disabled="loading">
            <span v-if="loading" class="btn-spinner"></span>
            <span v-else>Se connecter</span>
          </button>

        </form>

        <div class="login-footer">
          Pas encore de compte ?
          <router-link to="/register" class="login-link">S'inscrire</router-link>
        </div>
      </div>
    </div>

    <div class="login-right" aria-hidden="true">
      <div class="login-right-content">
        <div class="feature-list">
          <div class="feature-item" v-for="f in features" :key="f.title">
            <div class="feature-icon"><component :is="f.icon" /></div>
            <div>
              <div class="feature-title">{{ f.title }}</div>
              <div class="feature-desc">{{ f.desc }}</div>
            </div>
          </div>
        </div>
        <div class="login-right-tagline">
          Gérez votre patrimoine<br>immobilier en toute simplicité.
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuth } from '@/composables/useAuth'

const router = useRouter()
const { login } = useAuth()

const form = ref({ adresseMail: '', motDePasse: '' })
const error = ref('')
const loading = ref(false)
const showPwd = ref(false)
const focusedField = ref(null)

import { IconBuilding, IconFile, IconWrench } from '@/components/Icons.js'

const features = [
  { icon: IconBuilding, title: 'Gestion des bâtiments', desc: 'Centralisez tous vos immeubles et appartements.' },
  { icon: IconFile,     title: 'Suivi des contrats',    desc: 'Visualisez les baux actifs, les loyers et les échéances.' },
  { icon: IconWrench,   title: 'Interventions',         desc: 'Planifiez et suivez les travaux de maintenance.' },
]

const submit = async () => {
  error.value = ''
  loading.value = true
  try {
    await login(form.value.adresseMail, form.value.motDePasse)
    router.push('/')
  } catch (e) {
    error.value = e.message
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
@import '@/assets/login.css';
</style>