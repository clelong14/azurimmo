<template>
  <div class="public-shell">
    <!-- Header Navigation -->
    <header class="public-header">
      <div class="header-content">
        <div class="header-brand">
          <img src="/azurimmo_logo.png" alt="Azurimmo Logo" class="logo-img" />
          <span>Azurimmo</span>
        </div>
        <nav class="header-nav">
          <router-link to="/accueil" class="nav-link">Accueil</router-link>
          <router-link to="services" class="nav-link active">Services</router-link>
        </nav>
        <div class="header-actions">
          <button class="theme-btn" @click="toggleTheme" :title="isDark ? 'Mode clair' : 'Mode sombre'">
            <component :is="isDark ? IconSun : IconMoon" />
          </button>
          <!-- User Avatar Menu -->
          <div class="user-menu">
            <button class="avatar-btn" @click="showUserMenu = !showUserMenu" :title="user?.adresseMail">
              <div class="avatar">{{ user?.adresseMail?.[0]?.toUpperCase() }}</div>
            </button>
            <div v-if="showUserMenu" class="user-dropdown">
              <router-link to="/account" class="dropdown-item" @click="showUserMenu = false">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                  <circle cx="12" cy="7" r="4"/>
                </svg>
                Paramètres
              </router-link>
              <button @click="handleLogout" class="dropdown-item logout">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
                  <polyline points="16 17 21 12 16 7"/>
                  <line x1="21" y1="12" x2="9" y2="12"/>
                </svg>
                Se déconnecter
              </button>
            </div>
          </div>
        </div>
      </div>
    </header>

    <!-- Main Content -->
    <main class="public-main">
      <div class="page-container">
        <!-- Hero Section -->
        <section class="hero">
          <div class="hero-content">
            <h1 class="hero-title">Nos Services</h1>
            <p class="hero-subtitle">Découvrez toutes les fonctionnalités d'Azurimmo</p>
            <p class="hero-text">Une plateforme complète pour gérer votre patrimoine immobilier avec tous les outils nécessaires.</p>
          </div>
        </section>

        <!-- Services Grid -->
        <section class="services-section">
          <div class="services-grid">
            <!-- Service 1: Bâtiments -->
            <div class="service-card">
              <div class="service-icon">
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <rect width="16" height="20" x="4" y="2" rx="2"/>
                  <path d="M9 22v-4h6v4"/>
                  <path d="M8 6h.01"/>
                  <path d="M16 6h.01"/>
                  <path d="M12 6h.01"/>
                  <path d="M8 10h.01"/>
                  <path d="M16 10h.01"/>
                  <path d="M12 10h.01"/>
                  <path d="M8 14h.01"/>
                  <path d="M16 14h.01"/>
                  <path d="M12 14h.01"/>
                </svg>
              </div>
              <h3 class="service-title">Gestion des Bâtiments</h3>
              <p class="service-desc">Centralisez et gérez tous vos bâtiments et immeubles en un seul endroit. Visualisez rapidement les informations clés de chaque propriété.</p>
            </div>

            <!-- Service 2: Appartements -->
            <div class="service-card">
              <div class="service-icon">
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <rect x="3" y="3" width="18" height="18" rx="2"/>
                  <path d="M9 3v18"/>
                  <path d="M15 3v18"/>
                  <path d="M3 9h18"/>
                  <path d="M3 15h18"/>
                </svg>
              </div>
              <h3 class="service-title">Gestion des Appartements</h3>
              <p class="service-desc">Suivez et gérez l'ensemble de votre portefeuille d'appartements. Accédez rapidement aux détails, photos et documents de chaque logement.</p>
            </div>

            <!-- Service 3: Contrats -->
            <div class="service-card">
              <div class="service-icon">
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M13 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V9z"/>
                  <polyline points="13 2 13 9 20 9"/>
                </svg>
              </div>
              <h3 class="service-title">Suivi des Contrats</h3>
              <p class="service-desc">Visualisez tous vos contrats de bail actifs et passés. Recevez des notifications sur les échéances importantes et les renouvellements.</p>
            </div>

            <!-- Service 4: Interventions -->
            <div class="service-card">
              <div class="service-icon">
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 1 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"/>
                </svg>
              </div>
              <h3 class="service-title">Planification d'Interventions</h3>
              <p class="service-desc">Planifiez et suivez les interventions de maintenance et de réparation. Gardez une trace complète des travaux effectués.</p>
            </div>

            <!-- Service 5: Locataires -->
            <div class="service-card">
              <div class="service-icon">
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                  <circle cx="12" cy="7" r="4"/>
                </svg>
              </div>
              <h3 class="service-title">Gestion des Locataires</h3>
              <p class="service-desc">Accédez aux informations de vos locataires et à l'historique des contacts. Gérez efficacement vos relations avec les occupants.</p>
            </div>

            <!-- Service 6: Loyers -->
            <div class="service-card">
              <div class="service-icon">
                <svg width="32" height="32" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                  <line x1="12" y1="1" x2="12" y2="23"/>
                  <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/>
                </svg>
              </div>
              <h3 class="service-title">Suivi des Loyers</h3>
              <p class="service-desc">Suivez les paiements de loyers et les arriérés. Visualisez rapidement la situation financière de vos locations.</p>
            </div>
          </div>
        </section>

        <!-- Features Section -->
        <section class="features-section">
          <h2 class="section-title">Pourquoi choisir Azurimmo ?</h2>
          <div class="features-grid">
            <div class="feature-item">
              <div class="feature-number">01</div>
              <h3>Interface Intuitive</h3>
              <p>Une plateforme facile à utiliser, conçue pour tous les niveaux d'expérience.</p>
            </div>
            <div class="feature-item">
              <div class="feature-number">02</div>
              <h3>Données Sécurisées</h3>
              <p>Vos données immobilières sont protégées avec les derniers standards de sécurité.</p>
            </div>
            <div class="feature-item">
              <div class="feature-number">03</div>
              <h3>Support Réactif</h3>
              <p>Une équipe dédié prête à vous aider pour optimiser votre utilisation d'Azurimmo.</p>
            </div>
            <div class="feature-item">
              <div class="feature-number">04</div>
              <h3>Mise à Jour Régulière</h3>
              <p>De nouvelles fonctionnalités régulièrement pour améliorer votre expérience.</p>
            </div>
          </div>
        </section>
      </div>
    </main>

    <!-- Footer -->
    <footer class="public-footer">
      <div class="footer-content">
        <p>&copy; 2026 Azurimmo. Tous droits réservés.</p>
        <div class="footer-links">
          <router-link to="/confidentialite">Confidentialité</router-link>
          <router-link to="/conditions">Conditions d'utilisation</router-link>
          <router-link to="/contact">Contact</router-link>
        </div>
      </div>
    </footer>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useTheme } from '@/composables/useTheme'
import { useAuth } from '@/composables/useAuth'
import { IconSun, IconMoon } from '@/components/Icons.js'

const router = useRouter()
const { isDark, toggleTheme } = useTheme()
const { user, logout } = useAuth()
const showUserMenu = ref(false)

const handleLogout = () => {
  logout()
  router.push('/accueil')
}

// Fermer le menu quand on clique ailleurs
onMounted(() => {
  document.addEventListener('click', (e) => {
    if (!e.target.closest('.user-menu')) {
      showUserMenu.value = false
    }
  })
})
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.public-shell {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  background: var(--bg);
}

/* Header */
.public-header {
  background: var(--bg-secondary);
  border-bottom: 1px solid var(--border);
  position: sticky;
  top: 0;
  z-index: 50;
}

.header-content {
  max-width: 1400px;
  margin: 0 auto;
  padding: 1rem 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 2rem;
}

.header-brand {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  font-weight: 700;
  font-size: 1.1rem;
  color: var(--text-primary);
  text-decoration: none;
}

.logo-img {
  width: 28px;
  height: 28px;
  object-fit: contain;
}

.header-nav {
  display: flex;
  gap: 2rem;
  flex: 1;
}

.nav-link {
  color: var(--text-secondary);
  text-decoration: none;
  font-weight: 500;
  transition: color 0.2s;
}

.nav-link:hover,
.nav-link.active {
  color: var(--accent);
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.theme-btn {
  background: var(--bg-tertiary);
  border: 1px solid var(--border);
  width: 40px;
  height: 40px;
  border-radius: 0.5rem;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--text-secondary);
  transition: all 0.2s;
}

.theme-btn:hover {
  background: var(--bg);
}

/* User Menu */
.user-menu {
  position: relative;
}

.avatar-btn {
  background: none;
  border: none;
  cursor: pointer;
  padding: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: transform 0.2s;
}

.avatar-btn:hover {
  transform: scale(1.05);
}

.avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background: var(--accent-bg);
  border: 2px solid var(--accent);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 0.9rem;
  font-weight: 700;
  color: var(--accent);
}

.user-dropdown {
  position: absolute;
  top: 100%;
  right: 0;
  background: var(--surface);
  border: 1px solid var(--border);
  border-radius: 0.5rem;
  min-width: 200px;
  margin-top: 0.5rem;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
  z-index: 100;
  animation: slideDown 0.2s ease-out;
}

.dropdown-item {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  width: 100%;
  padding: 0.75rem 1rem;
  background: none;
  border: none;
  color: var(--text-primary);
  text-decoration: none;
  cursor: pointer;
  transition: all 0.2s;
  border-bottom: 1px solid var(--border);
  font-size: 0.95rem;
}

.dropdown-item:last-child {
  border-bottom: none;
}

.dropdown-item:hover {
  background: var(--bg-tertiary);
}

.dropdown-item.logout {
  color: var(--red);
}

.dropdown-item.logout:hover {
  background: rgba(220, 38, 38, 0.1);
}

.dropdown-item svg {
  flex-shrink: 0;
}

@keyframes slideDown {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Main */
.public-main {
  flex: 1;
  overflow-y: auto;
}

.page-container {
  max-width: 1400px;
  margin: 0 auto;
  padding: 4rem 2rem;
}

/* Hero Section */
.hero {
  text-align: center;
  margin-bottom: 5rem;
  animation: fadeIn 0.6s ease-out;
}

.hero-title {
  font-size: 2.5rem;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 1rem;
}

.hero-subtitle {
  font-size: 1.3rem;
  color: var(--accent);
  margin-bottom: 0.75rem;
}

.hero-text {
  font-size: 1rem;
  color: var(--text-secondary);
  max-width: 600px;
  margin: 0 auto;
}

/* Services Grid */
.services-section {
  margin-bottom: 5rem;
}

.section-title {
  font-size: 1.8rem;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 2rem;
  text-align: center;
}

.services-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  margin-bottom: 3rem;
}

.service-card {
  background: var(--surface);
  border: 1px solid var(--border);
  border-radius: 0.75rem;
  padding: 2rem;
  transition: all 0.3s ease;
}

.service-card:hover {
  border-color: var(--accent);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
  transform: translateY(-4px);
}

.service-icon {
  width: 60px;
  height: 60px;
  background: var(--accent-bg);
  border-radius: 0.75rem;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--accent);
  margin-bottom: 1.5rem;
}

.service-title {
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 0.75rem;
}

.service-desc {
  font-size: 0.95rem;
  color: var(--text-secondary);
  line-height: 1.6;
}

/* Features Section */
.features-section {
  margin-bottom: 5rem;
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
}

.feature-item {
  padding: 2rem;
  background: var(--bg-tertiary);
  border-radius: 0.75rem;
  text-align: center;
}

.feature-number {
  font-size: 2.5rem;
  font-weight: 700;
  color: var(--accent);
  margin-bottom: 0.5rem;
}

.feature-item h3 {
  font-size: 1.1rem;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.feature-item p {
  font-size: 0.9rem;
  color: var(--text-secondary);
}

/* Footer */
.public-footer {
  background: var(--bg-secondary);
  border-top: 1px solid var(--border);
  margin-top: auto;
}

.footer-content {
  max-width: 1400px;
  margin: 0 auto;
  padding: 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 2rem;
  color: var(--text-secondary);
  font-size: 0.9rem;
}

.footer-links {
  display: flex;
  gap: 2rem;
}

.footer-links a {
  color: var(--text-secondary);
  text-decoration: none;
  transition: color 0.2s;
}

.footer-links a:hover {
  color: var(--accent);
}

/* Animations */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* Responsive */
@media (max-width: 768px) {
  .header-content {
    flex-direction: column;
    gap: 1rem;
  }

  .header-nav {
    flex: none;
  }

  .hero-title {
    font-size: 2rem;
  }

  .services-grid {
    grid-template-columns: 1fr;
  }

  .page-container {
    padding: 2rem 1rem;
  }

  .footer-content {
    flex-direction: column;
    text-align: center;
  }
}
</style>

