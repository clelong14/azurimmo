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
          <router-link to="/accueil/services" class="nav-link">Services</router-link>
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
        <div class="content-wrapper">
          <h1>Politique de Confidentialité</h1>
          <p class="last-updated">Dernière mise à jour: 23 mars 2026</p>

          <section class="content-section">
            <h2>1. Introduction</h2>
            <p>
              Azurimmo (« nous », « notre » ou « nos ») s'engage à protéger votre vie privée. Cette Politique de Confidentialité explique comment nous collectons, utilisons, divulguons et sauvegardons vos informations lorsque vous utilisez notre plateforme.
            </p>
          </section>

          <section class="content-section">
            <h2>2. Informations que nous collectons</h2>
            <p>Nous collectons les informations que vous nous fournissez directement:</p>
            <ul>
              <li><strong>Informations de compte:</strong> Email, mot de passe, nom</li>
              <li><strong>Informations immobilières:</strong> Détails sur vos bâtiments, appartements et contrats</li>
              <li><strong>Informations de contact:</strong> Téléphone, adresse</li>
              <li><strong>Données d'utilisation:</strong> Pages visitées, actions effectuées, temps d'utilisation</li>
            </ul>
          </section>

          <section class="content-section">
            <h2>3. Comment nous utilisons vos informations</h2>
            <p>Nous utilisons vos informations pour:</p>
            <ul>
              <li>Fournir et maintenir nos services</li>
              <li>Traiter vos demandes et transactions</li>
              <li>Améliorer et personnaliser votre expérience</li>
              <li>Communiquer avec vous au sujet de votre compte</li>
              <li>Assurer la sécurité et la prévention de la fraude</li>
              <li>Respecter nos obligations légales</li>
            </ul>
          </section>

          <section class="content-section">
            <h2>4. Partage de vos informations</h2>
            <p>
              Nous ne vendons, échangeons ou louons pas vos informations personnelles à des tiers. Nous pouvons partager vos informations avec:
            </p>
            <ul>
              <li>Nos prestataires de services qui nous aident à exploiter notre plateforme</li>
              <li>Les autorités légales si requis par la loi</li>
              <li>Les autres utilisateurs avec lesquels vous choisissez de partager des informations</li>
            </ul>
          </section>

          <section class="content-section">
            <h2>5. Sécurité de vos données</h2>
            <p>
              Nous avons implémenté des mesures de sécurité appropriées pour protéger vos informations contre l'accès non autorisé, l'altération, la divulgation ou la destruction.
            </p>
          </section>

          <section class="content-section">
            <h2>6. Vos droits</h2>
            <p>
              Vous avez le droit d'accéder à vos données personnelles, de les corriger ou de demander leur suppression. Vous pouvez exercer ces droits en nous contactant directement.
            </p>
          </section>

          <section class="content-section">
            <h2>7. Contact</h2>
            <p>
              Si vous avez des questions concernant cette Politique de Confidentialité, veuillez nous contacter via notre <router-link to="/contact">page de contact</router-link>.
            </p>
          </section>
        </div>
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

onMounted(() => {
  document.addEventListener('click', (e) => {
    if (!e.target.closest('.user-menu')) {
      showUserMenu.value = false
    }
  })
})
</script>

<style scoped>
@import '@/assets/public-layout.css';

.content-wrapper {
  max-width: 800px;
  margin: 0 auto;
}

h1 {
  font-size: 2.5rem;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.last-updated {
  color: var(--text-secondary);
  font-size: 0.9rem;
  margin-bottom: 2rem;
}

.content-section {
  margin-bottom: 2rem;
  padding-bottom: 2rem;
  border-bottom: 1px solid var(--border);
}

.content-section:last-child {
  border-bottom: none;
}

.content-section h2 {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 1rem;
}

.content-section p {
  color: var(--text-secondary);
  line-height: 1.8;
  margin-bottom: 1rem;
}

.content-section ul {
  list-style-position: inside;
  color: var(--text-secondary);
  line-height: 1.8;
}

.content-section li {
  margin-bottom: 0.5rem;
}

.content-section a {
  color: var(--accent);
  text-decoration: none;
}

.content-section a:hover {
  text-decoration: underline;
}

strong {
  color: var(--text-primary);
  font-weight: 600;
}
</style>

