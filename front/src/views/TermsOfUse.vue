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
          <h1>Conditions d'Utilisation</h1>
          <p class="last-updated">Dernière mise à jour: 23 mars 2026</p>

          <section class="content-section">
            <h2>1. Acceptation des conditions</h2>
            <p>
              En accédant et en utilisant Azurimmo, vous acceptez d'être lié par ces conditions d'utilisation. Si vous n'acceptez pas ces conditions, veuillez ne pas utiliser notre plateforme.
            </p>
          </section>

          <section class="content-section">
            <h2>2. Utilisation de la plateforme</h2>
            <p>Vous acceptez d'utiliser Azurimmo uniquement à des fins légales et de ne pas:</p>
            <ul>
              <li>Violer les lois ou réglementations applicables</li>
              <li>Utiliser la plateforme pour des activités frauduleuses ou trompeuses</li>
              <li>Accéder à des comptes d'autres utilisateurs sans autorisation</li>
              <li>Héberger, afficher, télécharger ou transmettre du contenu offensant ou illégal</li>
              <li>Interférer avec le fonctionnement de la plateforme</li>
            </ul>
          </section>

          <section class="content-section">
            <h2>3. Comptes d'utilisateur</h2>
            <p>
              Vous êtes responsable de maintenir la confidentialité de votre mot de passe et de votre compte. Vous acceptez d'être responsable de toutes les activités qui se produisent sur votre compte. Vous devez immédiatement nous notifier de toute utilisation non autorisée de votre compte.
            </p>
          </section>

          <section class="content-section">
            <h2>4. Propriété intellectuelle</h2>
            <p>
              Le contenu, les fonctionnalités et les fonctions de Azurimmo sont la propriété exclusive d'Azurimmo. Vous ne pouvez pas reproduire, distribuer ou transmettre le contenu sans notre permission écrite préalable.
            </p>
          </section>

          <section class="content-section">
            <h2>5. Limitation de responsabilité</h2>
            <p>
              Azurimmo est fourni « tel quel » sans garanties d'aucune sorte. Nous ne serons pas responsables des dommages indirects, accidentels, spéciaux ou consécutifs résultant de votre utilisation de la plateforme.
            </p>
          </section>

          <section class="content-section">
            <h2>6. Modification des conditions</h2>
            <p>
              Nous nous réservons le droit de modifier ces conditions à tout moment. Les modifications prendront effet immédiatement après leur publication. Votre utilisation continue de la plateforme constitue votre acceptation des conditions modifiées.
            </p>
          </section>

          <section class="content-section">
            <h2>7. Résiliation</h2>
            <p>
              Nous nous réservons le droit de résilier ou de suspendre votre compte et votre accès à la plateforme à tout moment, sans préavis, pour violation de ces conditions ou pour toute autre raison.
            </p>
          </section>

          <section class="content-section">
            <h2>8. Contact</h2>
            <p>
              Si vous avez des questions concernant ces Conditions d'Utilisation, veuillez nous contacter via notre <router-link to="/contact">page de contact</router-link>.
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
  router.push('/login')
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

