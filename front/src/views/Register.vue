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
          <h1 class="login-title">Créer un compte</h1>
          <p class="login-subtitle">Renseignez vos informations pour accéder à Azurimmo.</p>
        </div>

        <form @submit.prevent="submit" class="login-form">

          <div class="field">
            <label class="field-label">Adresse email <span class="req">*</span></label>
            <div class="input-wrap" :class="{ focused: focusedField === 'email' }">
              <svg class="input-icon" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M4 4h16c1.1 0 2 .9 2 2v12c0 1.1-.9 2-2 2H4c-1.1 0-2-.9-2-2V6c0-1.1.9-2 2-2z"/><polyline points="22,6 12,13 2,6"/></svg>
              <input
                  v-model="form.adresseMail"
                  type="email"
                  class="login-input"
                  placeholder="Ex : jean@azurimmo.fr"
                  required
                  @focus="focusedField = 'email'"
                  @blur="focusedField = null"
              />
            </div>
          </div>

          <div class="field">
            <label class="field-label">Mot de passe <span class="req">*</span></label>
            <div class="input-wrap" :class="{ focused: focusedField === 'password' }">
              <svg class="input-icon" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="11" width="18" height="11" rx="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg>
              <input
                  v-model="form.motDePasse"
                  :type="showPwd ? 'text' : 'password'"
                  class="login-input"
                  placeholder="Minimum 6 caractères"
                  required
                  minlength="6"
                  @focus="focusedField = 'password'"
                  @blur="focusedField = null"
              />
              <button type="button" class="toggle-pwd" @click="showPwd = !showPwd" tabindex="-1">
                <svg v-if="!showPwd" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
                <svg v-else width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/><line x1="1" y1="1" x2="23" y2="23"/></svg>
              </button>
            </div>
          </div>

          <div class="field">
            <label class="field-label">Confirmer le mot de passe <span class="req">*</span></label>
            <div class="input-wrap" :class="{ focused: focusedField === 'confirm', error: confirmError }">
              <svg class="input-icon" width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><rect x="3" y="11" width="18" height="11" rx="2"/><path d="M7 11V7a5 5 0 0 1 10 0v4"/></svg>
              <input
                  v-model="form.confirm"
                  :type="showConfirm ? 'text' : 'password'"
                  class="login-input"
                  placeholder="Répétez votre mot de passe"
                  required
                  @focus="focusedField = 'confirm'"
                  @blur="focusedField = null"
              />
              <button type="button" class="toggle-pwd" @click="showConfirm = !showConfirm" tabindex="-1">
                <svg v-if="!showConfirm" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M1 12s4-8 11-8 11 8 11 8-4 8-11 8-11-8-11-8z"/><circle cx="12" cy="12" r="3"/></svg>
                <svg v-else width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M17.94 17.94A10.07 10.07 0 0 1 12 20c-7 0-11-8-11-8a18.45 18.45 0 0 1 5.06-5.94M9.9 4.24A9.12 9.12 0 0 1 12 4c7 0 11 8 11 8a18.5 18.5 0 0 1-2.16 3.19m-6.72-1.07a3 3 0 1 1-4.24-4.24"/><line x1="1" y1="1" x2="23" y2="23"/></svg>
              </button>
            </div>
            <span v-if="confirmError" class="field-error">Les mots de passe ne correspondent pas</span>
          </div>

          <!-- Indicateur de force du mot de passe -->
          <div v-if="form.motDePasse" class="pwd-strength">
            <div class="pwd-strength-label">Force du mot de passe :
              <span :class="`strength-${pwdStrength.level}`">{{ pwdStrength.label }}</span>
            </div>
            <div class="pwd-strength-bar">
              <div class="pwd-strength-fill" :class="`fill-${pwdStrength.level}`" :style="`width:${pwdStrength.percent}%`"></div>
            </div>
          </div>

          <transition name="shake">
            <div v-if="error" class="error-msg">
              <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><line x1="12" y1="8" x2="12" y2="12"/><line x1="12" y1="16" x2="12.01" y2="16"/></svg>
              {{ error }}
            </div>
          </transition>

          <div v-if="success" class="success-msg">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="m20 6-11 11-5-5"/></svg>
            Compte créé avec succès ! Redirection en cours…
          </div>

          <button type="submit" class="login-btn" :disabled="loading || !!confirmError">
            <span v-if="loading" class="btn-spinner"></span>
            <span v-else>Créer mon compte</span>
          </button>
        </form>

        <div class="login-footer">
          Vous avez déjà un compte ?
          <router-link to="/login" class="login-link">Se connecter</router-link>
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
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { IconBuilding, IconApt, IconFile, IconUsers, IconWrench } from '@/components/Icons.js'

const router = useRouter()
const BASE = (import.meta.env.VITE_API_URL || 'http://localhost:9008').replace(/\/$/, '')

const form = ref({ adresseMail: '', motDePasse: '', confirm: '' })
const error = ref('')
const success = ref(false)
const loading = ref(false)
const showPwd = ref(false)
const showConfirm = ref(false)
const focusedField = ref(null)

const confirmError = computed(() =>
    form.value.confirm && form.value.motDePasse !== form.value.confirm
        ? 'Les mots de passe ne correspondent pas'
        : ''
)

const pwdStrength = computed(() => {
  const pwd = form.value.motDePasse
  if (!pwd) return { level: 'empty', label: '', percent: 0 }
  let score = 0
  if (pwd.length >= 6)           score++
  if (pwd.length >= 10)          score++
  if (/[A-Z]/.test(pwd))         score++
  if (/[0-9]/.test(pwd))         score++
  if (/[^A-Za-z0-9]/.test(pwd))  score++
  if (score <= 1) return { level: 'weak',   label: 'Faible', percent: 25  }
  if (score <= 2) return { level: 'medium', label: 'Moyen',  percent: 50  }
  if (score <= 3) return { level: 'good',   label: 'Bon',    percent: 75  }
  return                 { level: 'strong', label: 'Fort',   percent: 100 }
})

const features = [
  { icon: IconBuilding, title: 'Gestion des bâtiments', desc: 'Centralisez tous vos immeubles et appartements.' },
  { icon: IconFile,     title: 'Suivi des contrats',    desc: 'Visualisez les baux actifs et les échéances.' },
  { icon: IconWrench,   title: 'Interventions',         desc: 'Planifiez et suivez les travaux de maintenance.' },
]

const submit = async () => {
  error.value = ''
  if (confirmError.value) return

  loading.value = true
  try {
    const response = await fetch(`${BASE}/login/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        adresseMail: form.value.adresseMail,
        motDePasse: form.value.motDePasse,
      })
    })

    if (!response.ok) {
      let msg = 'Erreur lors de la creation du compte.'
      try {
        const errorData = await response.json()
        msg = errorData?.message || msg
      } catch {
        const text = await response.text()
        if (text) msg = text
      }
      throw new Error(msg)
    }

    success.value = true
    setTimeout(() => router.push('/login'), 2000)

  } catch (e) {
    error.value = e.message
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
@import '@/assets/login.css';

.req { color: var(--red); margin-left: 2px; }
.field-error { font-size: 0.75rem; color: var(--red); margin-top: 0.25rem; }
.input-wrap.error { border-color: var(--red); }

.pwd-strength { display: flex; flex-direction: column; gap: 0.35rem; }
.pwd-strength-label { font-size: 0.77rem; color: var(--text-muted); }
.strength-weak   { color: var(--red);    font-weight: 600; }
.strength-medium { color: var(--amber);  font-weight: 600; }
.strength-good   { color: var(--accent); font-weight: 600; }
.strength-strong { color: var(--green);  font-weight: 600; }

.pwd-strength-bar { height: 4px; background: var(--border); border-radius: 2px; overflow: hidden; }
.pwd-strength-fill { height: 100%; border-radius: 2px; transition: width 0.3s ease, background 0.3s ease; }
.fill-weak   { background: var(--red);    }
.fill-medium { background: var(--amber);  }
.fill-good   { background: var(--accent); }
.fill-strong { background: var(--green);  }

.success-msg {
  display: flex; align-items: center; gap: 0.5rem;
  padding: 0.6rem 0.85rem;
  background: var(--green-bg); border: 1px solid var(--green-border);
  border-radius: var(--radius); font-size: 0.82rem; color: var(--green);
}

.login-footer { text-align: center; font-size: 0.82rem; color: var(--text-muted); margin-top: 1.25rem; }
.login-link { color: var(--accent); font-weight: 500; margin-left: 0.3rem; }
.login-link:hover { text-decoration: underline; }
</style>