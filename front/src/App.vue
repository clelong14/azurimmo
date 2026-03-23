<template>
  <!-- Login page: no layout -->
  <div v-if="!isAuthenticated" class="auth-layout">
    <router-view />
  </div>

  <!-- Authenticated layout: sidebar + topbar -->
  <div v-else class="app-shell" :class="{ 'sidebar-open': sidebarOpen }">

    <aside class="sidebar">
      <div class="sidebar-logo">
        <svg width="20" height="20" viewBox="0 0 24 24" fill="none">
          <rect x="2" y="2" width="9" height="9" rx="2" fill="var(--accent)"/>
          <rect x="13" y="2" width="9" height="9" rx="2" fill="var(--accent)" opacity=".4"/>
          <rect x="2" y="13" width="9" height="9" rx="2" fill="var(--accent)" opacity=".4"/>
          <rect x="13" y="13" width="9" height="9" rx="2" fill="var(--accent)" opacity=".7"/>
        </svg>
        <span class="logo-text">Azurimmo</span>
      </div>

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

        <!-- User info + logout -->
        <div class="user-block">
          <div class="user-avatar">{{ user?.adresseMail?.[0]?.toUpperCase() }}</div>
          <div class="user-info">
            <div class="user-name">{{ user?.adresseMail }}</div>
            <div class="user-role">{{ user?.roleLibelle === 'ADMIN' ? 'Administrateur' : 'Gestionnaire' }}</div>
          </div>
          <button class="logout-btn" @click="handleLogout" title="Se déconnecter">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><polyline points="16 17 21 12 16 7"/><line x1="21" y1="12" x2="9" y2="12"/></svg>
          </button>
        </div>
      </div>
    </aside>

    <div class="main-area">
      <header class="topbar">
        <button class="mobile-toggle" @click="sidebarOpen = !sidebarOpen">
          <span></span><span></span><span></span>
        </button>

        <div class="topbar-breadcrumb">
          <span class="breadcrumb-app">Azurimmo</span>
          <span class="breadcrumb-sep">/</span>
          <span class="breadcrumb-page">{{ currentPageLabel }}</span>
        </div>

        <div class="topbar-actions">
          <button class="theme-btn" @click="toggleTheme" :title="isDark ? 'Mode clair' : 'Mode sombre'">
            <transition name="t-icon" mode="out-in">
              <component :is="isDark ? IconSun : IconMoon" :key="isDark ? 'sun' : 'moon'" />
            </transition>
          </button>
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

    <div v-if="sidebarOpen" class="sidebar-overlay" @click="sidebarOpen = false" />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useTheme } from '@/composables/useTheme'
import { useAuth } from '@/composables/useAuth'
import {
  IconHome, IconBuilding, IconApt, IconFile, IconUsers,
  IconWrench, IconShield, IconCode, IconSun, IconMoon
} from '@/components/Icons.js'

const { isDark, toggleTheme } = useTheme()
const { user, isAuthenticated, isAdmin, logout } = useAuth()
const sidebarOpen = ref(false)
const route = useRoute()
const router = useRouter()
const BASE = import.meta.env.VITE_API_URL

const handleLogout = () => {
  logout()
  router.push('/login')
}

const navLinks = [
  { to: '/',              label: 'Tableau de bord', icon: IconHome     },
  { to: '/batiments',     label: 'Bâtiments',       icon: IconBuilding },
  { to: '/appartements',  label: 'Appartements',    icon: IconApt      },
  { to: '/contrats',      label: 'Contrats',        icon: IconFile     },
  { to: '/locataires',    label: 'Locataires',      icon: IconUsers    },
  { to: '/interventions', label: 'Interventions',   icon: IconWrench   },
]

const allLabels = [
  ...navLinks,
  { to: '/admin', label: 'Administration' }
]

const currentPageLabel = computed(() =>
    allLabels.find(l => l.to === route.path)?.label ?? ''
)
</script>

<style scoped>
.auth-layout { min-height: 100vh; }

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
.logo-text { font-size: 0.95rem; font-weight: 700; color: var(--text-primary); }

.sidebar-nav { flex: 1; padding: 1rem 0.75rem; overflow-y: auto; }
.nav-section-label {
  font-size: 0.69rem; font-weight: 600; text-transform: uppercase;
  letter-spacing: 0.08em; color: var(--text-muted);
  padding: 0 0.6rem; margin-bottom: 0.35rem;
}
.nav-item {
  display: flex; align-items: center; gap: 0.65rem;
  padding: 0.48rem 0.6rem; border-radius: 6px;
  font-size: 0.85rem; font-weight: 450; color: var(--text-secondary);
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
  display: flex; align-items: center; gap: 0.6rem;
  padding: 0.6rem;
  background: var(--bg-tertiary); border: 1px solid var(--border);
  border-radius: 8px; margin-top: 0.25rem;
}
.user-avatar {
  width: 28px; height: 28px; border-radius: 50%;
  background: var(--accent-bg); border: 1px solid var(--accent-border);
  display: flex; align-items: center; justify-content: center;
  font-size: 0.72rem; font-weight: 700; color: var(--accent); flex-shrink: 0;
}
.user-info { flex: 1; min-width: 0; }
.user-name {
  font-size: 0.8rem; font-weight: 600; color: var(--text-primary);
  white-space: nowrap; overflow: hidden; text-overflow: ellipsis;
}
.user-role { font-size: 0.7rem; color: var(--text-muted); }
.logout-btn {
  display: flex; align-items: center; justify-content: center;
  width: 26px; height: 26px; border-radius: 5px;
  color: var(--text-muted); flex-shrink: 0; background: none;
  transition: all 0.15s; cursor: pointer; border: none;
}
.logout-btn:hover { background: var(--red-bg); color: var(--red); }

.main-area { flex: 1; margin-left: 220px; display: flex; flex-direction: column; min-height: 100vh; }

.topbar {
  height: 58px; border-bottom: 1px solid var(--border);
  display: flex; align-items: center; padding: 0 1.75rem; gap: 1rem;
  background: var(--bg-nav); position: sticky; top: 0; z-index: 40;
}
.mobile-toggle { display: none; flex-direction: column; gap: 4px; padding: 6px; border-radius: 6px; background: none; cursor: pointer; border: none; }
.mobile-toggle:hover { background: var(--bg-tertiary); }
.mobile-toggle span { display: block; width: 18px; height: 1.5px; background: var(--text-secondary); border-radius: 2px; }
.topbar-breadcrumb { display: flex; align-items: center; gap: 0.5rem; font-size: 0.82rem; }
.breadcrumb-app { color: var(--text-muted); }
.breadcrumb-sep { color: var(--border-strong); }
.breadcrumb-page { color: var(--text-primary); font-weight: 500; }
.topbar-actions { margin-left: auto; display: flex; align-items: center; gap: 0.5rem; }
.theme-btn {
  display: flex; align-items: center; justify-content: center;
  width: 34px; height: 34px; border-radius: 6px;
  color: var(--text-secondary); transition: all 0.15s;
  background: none; cursor: pointer; border: none;
}
.theme-btn:hover { background: var(--bg-tertiary); color: var(--text-primary); }

.t-icon-enter-active, .t-icon-leave-active { transition: all 0.15s ease; }
.t-icon-enter-from { opacity: 0; transform: rotate(-20deg) scale(0.8); }
.t-icon-leave-to { opacity: 0; transform: rotate(20deg) scale(0.8); }

.page-main { flex: 1; overflow-y: auto; }
.page-enter-active, .page-leave-active { transition: opacity 0.15s ease; }
.page-enter-from, .page-leave-to { opacity: 0; }

.sidebar-overlay { display: none; position: fixed; inset: 0; background: rgba(0,0,0,0.4); z-index: 49; }

@media (max-width: 768px) {
  .sidebar { transform: translateX(-100%); }
  .app-shell.sidebar-open .sidebar { transform: translateX(0); }
  .app-shell.sidebar-open .sidebar-overlay { display: block; }
  .main-area { margin-left: 0; }
  .mobile-toggle { display: flex; }
  .topbar { padding: 0 1rem; }
}
</style>