<template>
  <div v-if="!isAuthenticated" class="auth-layout">
    <router-view />
  </div>

  <div v-else-if="isPublicTopbarRoute" class="public-layout">
    <header class="public-topbar">
      <div class="public-topbar-content">
        <router-link to="/accueil" class="public-brand">
          <img src="/azurimmo_logo.png" alt="Azurimmo Logo" class="public-logo" />
          <span>Azurimmo</span>
        </router-link>

        <nav class="public-nav">
          <router-link
            v-for="link in publicLinks"
            :key="link.to"
            :to="link.to"
            class="public-nav-link"
            :class="{ active: route.path === link.to }"
          >
            {{ link.label }}
          </router-link>
        </nav>

        <div class="public-actions">
          <router-link
            v-if="showBackToAccueil"
            to="/accueil"
            class="public-back-btn"
          >
            Retour à l'accueil
          </router-link>

          <button
            type="button"
            class="public-theme-btn"
            @click="toggleTheme"
            :title="isDark ? 'Mode clair' : 'Mode sombre'"
            :aria-label="isDark ? 'Activer le mode clair' : 'Activer le mode sombre'"
          >
            <component :is="isDark ? IconSun : IconMoon" />
          </button>

          <div class="public-user-menu">
            <button
              type="button"
              class="public-avatar-btn"
              @click="togglePublicMenu"
              :title="user?.adresseMail"
              aria-label="Ouvrir le menu profil"
            >
              <div class="public-avatar">{{ user?.adresseMail?.[0]?.toUpperCase() }}</div>
            </button>

            <div v-if="showPublicMenu" class="public-dropdown">
              <router-link to="/account" class="public-dropdown-item" @click="showPublicMenu = false">
                Paramètres
              </router-link>
              <button type="button" class="public-dropdown-item logout" @click="handleLogout">
                Se déconnecter
              </button>
            </div>
          </div>
        </div>
      </div>
    </header>

    <router-view />
  </div>

  <!-- Authenticated layout: sidebar + topbar (admin/gestionnaire) -->
  <div v-else class="app-shell" :class="{ 'sidebar-open': sidebarOpen }">

    <aside class="sidebar">
      <router-link to="/accueil" class="sidebar-logo">
        <img src="/azurimmo_logo.png" alt="Azurimmo Logo" class="logo-img" />
        <span class="logo-text">Azurimmo</span>
      </router-link>

      <nav class="sidebar-nav">
        <div class="nav-section-label">Navigation</div>
        <router-link
            v-for="link in navLinks"
            :key="link.to"
            :to="link.to"
            class="nav-item"
            :class="{ active: $route.path === link.to }"
            @click="sidebarOpen = false"
        >
          <component :is="link.icon" class="nav-icon" />
          <span>{{ link.label }}</span>
        </router-link>

        <!-- Admin link: only visible for admin role -->
        <template v-if="isAdmin">
          <div class="nav-section-label" style="margin-top:1rem">Administration</div>
          <router-link
              to="/admin"
              class="nav-item nav-item-admin"
              :class="{ active: $route.path === '/admin' }"
              @click="sidebarOpen = false"
          >
            <component :is="IconShield" class="nav-icon" />
            <span>Admin</span>
            <span class="admin-tag">Admin</span>
          </router-link>
        </template>
      </nav>

      <div class="sidebar-footer">
        <a :href="`${BASE}swagger-ui.html`" target="_blank" class="nav-item">
          <component :is="IconCode" class="nav-icon" />
          <span>API Swagger</span>
          <span class="external-badge">↗</span>
        </a>
      </div>
    </aside>

    <div class="main-area">
      <header class="dashboard-topbar">
        <div class="dashboard-topbar-content">
          <button class="mobile-toggle" @click="sidebarOpen = !sidebarOpen" title="Menu">
            <span></span><span></span><span></span>
          </button>

          <nav class="dashboard-nav">
            <router-link
              v-for="link in dashboardTopLinks"
              :key="link.to"
              :to="link.to"
              class="dashboard-nav-link"
              :class="{ active: route.path === link.to }"
            >
              {{ link.label }}
            </router-link>
          </nav>

          <div class="dashboard-actions">
            <button
              type="button"
              class="dashboard-avatar-btn"
              @click="openDashboardProfileModal"
              :title="displayName"
              aria-label="Ouvrir les paramètres du compte"
            >
              <img v-if="profilePhoto" :src="profilePhoto" alt="Photo de profil" class="avatar-img" />
              <span v-else>{{ avatarInitial }}</span>
            </button>
          </div>
        </div>
      </header>

      <main class="page-main">
        <router-view v-slot="{ Component }">
          <transition name="page" mode="out-in">
            <component :is="Component" />
          </transition>
        </router-view>
      </main>
    </div>

    <AppModal v-model="showDashboardProfileModal" title="Paramètres du compte">
      <div class="profile-modal-content">
        <div class="profile-header-block">
          <div class="profile-photo-wrap">
            <img v-if="profilePhoto" :src="profilePhoto" alt="Photo de profil" class="profile-photo" />
            <div v-else class="profile-photo-fallback">{{ avatarInitial }}</div>
          </div>
          <div class="profile-main-info">
            <strong class="profile-username-preview">{{ profileDraft.username || displayName }}</strong>
            <span class="profile-email-preview">{{ user?.adresseMail }}</span>
          </div>
        </div>

        <div class="profile-section">
          <h4 class="section-title">Profil</h4>
          <label class="field-label" for="usernameInput">Nom d'utilisateur</label>
          <input id="usernameInput" v-model="profileDraft.username" class="profile-input" type="text" placeholder="Votre nom d'utilisateur" />

          <label class="field-label" for="photoInput">Photo de profil</label>
          <input id="photoInput" class="profile-input" type="file" accept="image/*" @change="onProfilePhotoChange" />

          <button type="button" class="profile-btn" @click="saveProfileSettings">Enregistrer le profil</button>
        </div>

        <div class="profile-section">
          <h4 class="section-title">Sécurité</h4>
          <label class="field-label" for="newPwd">Nouveau mot de passe</label>
          <input id="newPwd" v-model="passwordDraft.newPassword" class="profile-input" type="password" placeholder="Nouveau mot de passe" />

          <label class="field-label" for="confirmPwd">Confirmer le mot de passe</label>
          <input id="confirmPwd" v-model="passwordDraft.confirmPassword" class="profile-input" type="password" placeholder="Confirmer le mot de passe" />

          <button type="button" class="profile-btn" @click="savePasswordSettings">Modifier le mot de passe</button>
        </div>

        <div class="profile-section">
          <h4 class="section-title">Préférences</h4>
          <button type="button" class="profile-btn" @click="toggleTheme">
            {{ isDark ? 'Passer en mode clair' : 'Passer en mode sombre' }}
          </button>
        </div>

        <div class="profile-section danger">
          <h4 class="section-title">Zone dangereuse</h4>
          <button type="button" class="profile-btn delete" :disabled="isDeletingAccount" @click="deleteAccountFromModal">
            {{ isDeletingAccount ? 'Suppression...' : 'Supprimer le compte' }}
          </button>
        </div>

        <div class="profile-actions">
          <button type="button" class="profile-btn" @click="openAccountPage">Voir la page complète</button>
          <button type="button" class="profile-btn logout" @click="handleLogout">Se déconnecter</button>
        </div>
      </div>
    </AppModal>

    <div v-if="sidebarOpen" class="sidebar-overlay" @click="sidebarOpen = false" />
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onBeforeUnmount } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useTheme } from '@/composables/useTheme'
import { useAuth } from '@/composables/useAuth'
import AppModal from '@/components/AppModal.vue'
import {
  IconHome, IconBuilding, IconApt, IconFile, IconUsers,
  IconWrench, IconShield, IconCode, IconSun, IconMoon
} from '@/components/Icons.js'

const { isDark, toggleTheme } = useTheme()
const { user, isAuthenticated, isAdmin, logout } = useAuth()
const sidebarOpen = ref(false)
const showPublicMenu = ref(false)
const showDashboardProfileModal = ref(false)
const profilePhoto = ref('')
const isDeletingAccount = ref(false)
const profileDraft = ref({ username: '' })
const passwordDraft = ref({ newPassword: '', confirmPassword: '' })
const route = useRoute()
const router = useRouter()
const BASE = import.meta.env.VITE_API_URL

const profileStorageKey = computed(() => {
  const idPart = user.value?.id ? `id-${user.value.id}` : user.value?.adresseMail || 'guest'
  return `azurimmo-profile-${idPart}`
})

const displayName = computed(() => {
  if (profileDraft.value.username?.trim()) return profileDraft.value.username.trim()
  const email = user.value?.adresseMail || ''
  return email.includes('@') ? email.split('@')[0] : (email || 'Utilisateur')
})

const avatarInitial = computed(() => (displayName.value?.[0] || 'U').toUpperCase())

const isPublicTopbarRoute = computed(() => {
  return [
    '/accueil',
    '/services',
    '/confidentialite',
    '/conditions',
    '/contact',
    '/account',
  ].includes(route.path)
})

const showBackToAccueil = computed(() =>
  isPublicTopbarRoute.value && route.path !== '/accueil'
)

const publicLinks = computed(() => {
  const links = [
    { to: '/accueil', label: 'Accueil' },
    { to: '/services', label: 'Services' },
  ]

  if (user.value?.roleLibelle === 'ADMIN' || user.value?.roleLibelle === 'GESTIONNAIRE') {
    links.push({ to: '/tableau-de-bord', label: 'Tableau de bord' })
  }

  return links
})

const togglePublicMenu = () => {
  showPublicMenu.value = !showPublicMenu.value
}

const closePublicMenuOnOutsideClick = (event) => {
  if (!event.target.closest('.public-user-menu')) {
    showPublicMenu.value = false
  }
}

onMounted(() => {
  document.addEventListener('click', closePublicMenuOnOutsideClick)
  loadProfileSettings()
})

onBeforeUnmount(() => {
  document.removeEventListener('click', closePublicMenuOnOutsideClick)
})

const handleLogout = () => {
  logout()
  showPublicMenu.value = false
  showDashboardProfileModal.value = false
  router.push('/accueil')
}

const openDashboardProfileModal = () => {
  loadProfileSettings()
  showDashboardProfileModal.value = true
}

const openAccountPage = () => {
  showDashboardProfileModal.value = false
  router.push('/account')
}

const loadProfileSettings = () => {
  try {
    const raw = localStorage.getItem(profileStorageKey.value)
    if (!raw) {
      profileDraft.value = { username: '' }
      profilePhoto.value = ''
      return
    }
    const parsed = JSON.parse(raw)
    profileDraft.value = { username: parsed.username || '' }
    profilePhoto.value = parsed.photo || ''
  } catch {
    profileDraft.value = { username: '' }
    profilePhoto.value = ''
  }
}

const saveProfileSettings = () => {
  const payload = {
    username: profileDraft.value.username?.trim() || '',
    photo: profilePhoto.value || ''
  }
  localStorage.setItem(profileStorageKey.value, JSON.stringify(payload))
  alert('Profil mis à jour.')
}

const onProfilePhotoChange = (event) => {
  const file = event.target.files?.[0]
  if (!file) return
  const reader = new FileReader()
  reader.onload = () => {
    profilePhoto.value = String(reader.result || '')
  }
  reader.readAsDataURL(file)
}

const savePasswordSettings = () => {
  const { newPassword, confirmPassword } = passwordDraft.value
  if (!newPassword || !confirmPassword) {
    alert('Veuillez renseigner les deux champs mot de passe.')
    return
  }
  if (newPassword.length < 6) {
    alert('Le mot de passe doit contenir au moins 6 caractères.')
    return
  }
  if (newPassword !== confirmPassword) {
    alert('Les mots de passe ne correspondent pas.')
    return
  }

  passwordDraft.value = { newPassword: '', confirmPassword: '' }
  alert('Mot de passe mis à jour.')
}

const deleteAccountFromModal = async () => {
  if (!confirm('Confirmer la suppression définitive de votre compte ?')) return

  isDeletingAccount.value = true
  try {
    const userId = user.value?.id
    if (!userId) throw new Error('ID utilisateur introuvable')

    const apiUrl = (import.meta.env.VITE_API_URL || 'http://localhost:9008').replace(/\/$/, '')
    const response = await fetch(`${apiUrl}/users/${userId}`, { method: 'DELETE' })
    if (!response.ok) {
      const errorText = await response.text()
      throw new Error(errorText || 'Erreur lors de la suppression du compte')
    }

    localStorage.removeItem(profileStorageKey.value)
    handleLogout()
  } catch (error) {
    alert(error.message || 'Erreur lors de la suppression du compte')
  } finally {
    isDeletingAccount.value = false
  }
}

const navLinks = [
  { to: '/tableau-de-bord', label: 'Tableau de bord', icon: IconHome     },
  { to: '/batiments',       label: 'Bâtiments',       icon: IconBuilding },
  { to: '/appartements',    label: 'Appartements',    icon: IconApt      },
  { to: '/contrats',        label: 'Contrats',        icon: IconFile     },
  { to: '/locataires',      label: 'Locataires',      icon: IconUsers    },
  { to: '/interventions',   label: 'Interventions',   icon: IconWrench   },
]

const dashboardTopLinks = computed(() => {
  return [
    { to: '/accueil', label: 'Accueil' },
    { to: '/tableau-de-bord', label: 'Tableau de bord' },
  ]
})

const allLabels = [
  ...navLinks,
  { to: '/admin', label: 'Administration' },
  { to: '/account', label: 'Paramètres de Compte' }
]

const currentPageLabel = computed(() =>
    allLabels.find(l => l.to === route.path)?.label ?? ''
)
</script>

<style scoped>
.auth-layout { min-height: 100vh; }

/* Masque les headers dupliques dans les vues publiques */
.public-layout :deep(.public-header) {
  display: none;
}

.public-topbar {
  position: sticky;
  top: 0;
  z-index: 60;
  border-bottom: 1px solid var(--border);
  background: var(--bg-secondary);
}

.public-topbar-content {
  max-width: 1400px;
  margin: 0 auto;
  padding: 0.85rem 1.25rem;
  display: flex;
  align-items: center;
  gap: 1rem;
}

.public-brand {
  display: flex;
  align-items: center;
  gap: 0.6rem;
  font-weight: 700;
  color: var(--text-primary);
}

.public-logo {
  width: 24px;
  height: 24px;
  object-fit: contain;
}

.public-nav {
  display: flex;
  gap: 1.25rem;
  margin-left: 1rem;
}

.public-nav-link {
  text-decoration: none;
  color: var(--text-secondary);
  font-size: 0.98rem;
  padding: 0.3rem 0.15rem;
  border-bottom: 2px solid transparent;
  transition: color 0.15s ease, border-color 0.15s ease;
}

.public-nav-link.active,
.public-nav-link:hover {
  color: var(--accent);
  border-bottom-color: var(--accent);
}

.public-nav-link:focus-visible,
.public-theme-btn:focus-visible,
.public-avatar-btn:focus-visible,
.public-back-btn:focus-visible,
.dashboard-nav-link:focus-visible,
.dashboard-theme-btn:focus-visible,
.dashboard-profile-btn:focus-visible,
.user-info-btn:focus-visible,
.logout-btn:focus-visible {
  outline: 2px solid var(--accent);
  outline-offset: 2px;
}

.public-actions {
  margin-left: auto;
  display: flex;
  align-items: center;
  gap: 0.6rem;
}

.public-back-btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 0.45rem 0.75rem;
  border-radius: 6px;
  border: 1px solid var(--border);
  background: var(--bg-tertiary);
  color: var(--text-primary);
  text-decoration: none;
  font-size: 0.82rem;
  transition: all 0.15s ease;
}

.public-back-btn:hover {
  background: var(--bg);
  color: var(--accent);
}

.public-theme-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  width: 34px;
  height: 34px;
  border-radius: 6px;
  color: var(--text-secondary);
  background: none;
  cursor: pointer;
  border: none;
}

.public-theme-btn:hover {
  background: var(--bg-tertiary);
  color: var(--text-primary);
}

.public-user-menu {
  position: relative;
}

.public-avatar-btn {
  background: none;
  border: none;
  padding: 0;
  cursor: pointer;
}

.public-avatar {
  width: 34px;
  height: 34px;
  border-radius: 50%;
  background: var(--accent-bg);
  border: 1px solid var(--accent-border);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.78rem;
  font-weight: 700;
  color: var(--accent);
}

.public-dropdown {
  position: absolute;
  right: 0;
  top: calc(100% + 0.4rem);
  min-width: 180px;
  border: 1px solid var(--border);
  border-radius: 8px;
  background: var(--surface);
  overflow: hidden;
  z-index: 80;
}

.public-dropdown-item {
  width: 100%;
  text-align: left;
  padding: 0.65rem 0.8rem;
  border: none;
  background: none;
  color: var(--text-primary);
  cursor: pointer;
  text-decoration: none;
  display: block;
}

.public-dropdown-item:hover {
  background: var(--bg-tertiary);
}

.public-dropdown-item.logout {
  color: var(--red);
}

.app-shell { display: flex; min-height: 100vh; }

.sidebar {
  width: 220px; flex-shrink: 0;
  background: var(--bg-secondary);
  border-right: 1px solid var(--border);
  display: flex; flex-direction: column;
  position: fixed; top: 0; left: 0; bottom: 0; z-index: 50;
  transition: transform 0.2s ease;
}

.sidebar-logo {
  display: flex; align-items: center; gap: 0.6rem;
  padding: 0 1.25rem; height: 58px;
  border-bottom: 1px solid var(--border); flex-shrink: 0;
}
.logo-img {
  width: 24px;
  height: 24px;
  object-fit: contain;
}
.logo-text { font-size: 1.02rem; font-weight: 700; color: var(--text-primary); }

.sidebar-nav { flex: 1; padding: 1rem 0.75rem; overflow-y: auto; }
.nav-section-label {
  font-size: 0.69rem; font-weight: 600; text-transform: uppercase;
  letter-spacing: 0.08em; color: var(--text-muted);
  padding: 0 0.6rem; margin-bottom: 0.35rem;
}
.nav-item {
  display: flex; align-items: center; gap: 0.65rem;
  padding: 0.48rem 0.6rem; border-radius: 6px;
  font-size: 0.94rem; font-weight: 500; color: var(--text-secondary);
  transition: all 0.15s; cursor: pointer; margin-bottom: 1px;
  text-decoration: none;
}
.nav-item:hover { background: var(--bg-tertiary); color: var(--text-primary); }
.nav-item.active { background: var(--accent-bg); color: var(--accent); font-weight: 500; }
.nav-icon { flex-shrink: 0; opacity: 0.8; }
.nav-item.active .nav-icon { opacity: 1; }
.external-badge { margin-left: auto; font-size: 0.7rem; color: var(--text-muted); }

.nav-item-admin { position: relative; }
.admin-tag {
  margin-left: auto; font-size: 0.65rem; font-weight: 600;
  padding: 0.1rem 0.4rem; border-radius: 3px;
  background: var(--red-bg); color: var(--red); border: 1px solid var(--red-border);
}

.sidebar-footer { padding: 0.75rem; border-top: 1px solid var(--border); display: flex; flex-direction: column; gap: 0.25rem; }

.user-block {
  display: flex; align-items: center; justify-content: space-between; gap: 0.6rem;
  padding: 0.6rem;
  background: var(--bg-tertiary); border: 1px solid var(--border);
  border-radius: 8px; margin-top: 0.25rem;
}

.user-avatar {
  width: 28px; height: 28px; border-radius: 50%;
  background: var(--accent-bg); border: 1px solid var(--accent-border);
  display: flex; align-items: center; justify-content: center;
  font-size: 0.72rem; font-weight: 700; color: var(--accent); flex-shrink: 0;
  overflow: hidden;
}

.logout-btn {
  display: flex; align-items: center; justify-content: center;
  width: 26px; height: 26px; border-radius: 5px;
  color: var(--text-muted); flex-shrink: 0; background: none;
  transition: all 0.15s; cursor: pointer; border: none;
}
.logout-btn:hover { background: var(--red-bg); color: var(--red); }

.profile-modal-content {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.profile-header-block {
  display: flex;
  align-items: center;
  gap: 0.8rem;
}

.profile-photo-wrap {
  width: 56px;
  height: 56px;
  flex-shrink: 0;
}

.profile-photo,
.profile-photo-fallback {
  width: 100%;
  height: 100%;
  border-radius: 50%;
}

.profile-photo {
  object-fit: cover;
  border: 1px solid var(--accent-border);
}

.profile-photo-fallback {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background: var(--accent-bg);
  color: var(--accent);
  border: 1px solid var(--accent-border);
  font-weight: 700;
}

.profile-main-info {
  display: flex;
  flex-direction: column;
  min-width: 0;
}

.profile-username-preview {
  font-size: 1rem;
  color: var(--text-primary);
}

.profile-email-preview {
  font-size: 0.82rem;
  color: var(--text-muted);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.profile-section {
  border: 1px solid var(--border);
  border-radius: 8px;
  padding: 0.75rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.profile-section.danger {
  border-color: var(--red-border);
  background: var(--red-bg);
}

.section-title {
  font-size: 0.88rem;
  color: var(--text-primary);
  margin: 0;
}

.field-label {
  font-size: 0.8rem;
  color: var(--text-muted);
}

.profile-input {
  width: 100%;
  padding: 0.55rem 0.65rem;
  border: 1px solid var(--border);
  border-radius: 6px;
  background: var(--surface);
  color: var(--text-primary);
}

.profile-input:focus {
  outline: none;
  border-color: var(--accent);
}

.profile-btn {
  flex: 1;
  padding: 0.55rem 0.75rem;
  border: 1px solid var(--border);
  border-radius: 6px;
  background: var(--bg-secondary);
  color: var(--text-primary);
  font-size: 0.85rem;
}

.profile-btn:hover {
  background: var(--bg-tertiary);
}

.profile-btn.logout {
  color: var(--red);
}

.profile-btn.delete {
  border-color: var(--red-border);
  color: var(--red);
}

.main-area { flex: 1; margin-left: 220px; display: flex; flex-direction: column; min-height: 100vh; }

.dashboard-topbar {
  height: 58px;
  border-bottom: 1px solid var(--border);
  background: var(--bg-secondary);
  position: sticky;
  top: 0;
  z-index: 40;
}

.dashboard-topbar-content {
  height: 100%;
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 0 1rem;
}

.dashboard-nav {
  display: flex;
  align-items: center;
  gap: 0.75rem;
}

.dashboard-nav-link {
  text-decoration: none;
  color: var(--text-secondary);
  font-size: 0.98rem;
  padding: 0.35rem 0.15rem;
  border-bottom: 2px solid transparent;
  transition: color 0.15s ease, border-color 0.15s ease;
}

.dashboard-nav-link.active,
.dashboard-nav-link:hover {
  color: var(--accent);
  border-bottom-color: var(--accent);
}

.dashboard-actions {
  margin-left: auto;
  display: flex;
  align-items: center;
  gap: 0.55rem;
}

.dashboard-avatar-btn {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  border: 1px solid var(--accent-border);
  background: var(--accent-bg);
  color: var(--accent);
  font-weight: 700;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
}

.dashboard-avatar-btn:hover {
  border-color: var(--accent);
}

.avatar-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

@media (max-width: 768px) {
  .sidebar { transform: translateX(-100%); }
  .app-shell.sidebar-open .sidebar { transform: translateX(0); }
  .app-shell.sidebar-open .sidebar-overlay { display: block; }
  .main-area { margin-left: 0; }
  .mobile-toggle { display: flex; }
  .topbar { padding: 0 1rem; }

  .dashboard-topbar-content {
    padding: 0 1rem;
    gap: 0.6rem;
  }

  .dashboard-nav {
    overflow-x: auto;
    white-space: nowrap;
    scrollbar-width: thin;
  }
}
</style>