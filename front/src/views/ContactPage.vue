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
          <h1>Nous Contacter</h1>
          <p class="subtitle">Nous serions heureux d'entendre parler de vous. Remplissez le formulaire ci-dessous.</p>

          <div class="contact-grid">
            <!-- Contact Form -->
            <div class="contact-form-section">
              <form @submit.prevent="submitForm" class="contact-form">
                <div class="form-group">
                  <label for="name">Nom *</label>
                  <input
                    id="name"
                    v-model="form.name"
                    type="text"
                    required
                    placeholder="Votre nom"
                  />
                </div>

                <div class="form-group">
                  <label for="email">Email *</label>
                  <input
                    id="email"
                    v-model="form.email"
                    type="email"
                    required
                    placeholder="votre@email.com"
                  />
                </div>

                <div class="form-group">
                  <label for="subject">Sujet *</label>
                  <input
                    id="subject"
                    v-model="form.subject"
                    type="text"
                    required
                    placeholder="Objet de votre message"
                  />
                </div>

                <div class="form-group">
                  <label for="message">Message *</label>
                  <textarea
                    id="message"
                    v-model="form.message"
                    required
                    rows="6"
                    placeholder="Décrivez votre demande..."
                  ></textarea>
                </div>

                <div v-if="successMessage" class="success-message">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="m20 6-11 11-5-5"/>
                  </svg>
                  {{ successMessage }}
                </div>

                <div v-if="errorMessage" class="error-message">
                  <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <circle cx="12" cy="12" r="10"/>
                    <line x1="12" y1="8" x2="12" y2="12"/>
                    <line x1="12" y1="16" x2="12.01" y2="16"/>
                  </svg>
                  {{ errorMessage }}
                </div>

                <button type="submit" class="submit-btn" :disabled="isSubmitting">
                  {{ isSubmitting ? 'Envoi en cours...' : 'Envoyer le message' }}
                </button>
              </form>
            </div>

            <!-- Contact Info -->
            <div class="contact-info-section">
              <div class="info-card">
                <div class="info-icon">
                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/>
                    <polyline points="9 22 9 12 15 12 15 22"/>
                  </svg>
                </div>
                <h3>Adresse</h3>
                <p>
                  Azurimmo<br/>
                  123 rue de l'Immobilier<br/>
                  75000 Paris, France
                </p>
              </div>

              <div class="info-card">
                <div class="info-icon">
                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <path d="M22 16.92v3a2 2 0 0 1-2.18 2 19.79 19.79 0 0 1-8.63-3.07 19.5 19.5 0 0 1-6-6 19.79 19.79 0 0 1-3.07-8.67A2 2 0 0 1 4.11 2h3a2 2 0 0 1 2 1.72 12.84 12.84 0 0 0 .7 2.81 2 2 0 0 1-.45 2.11L8.09 9.91a16 16 0 0 0 6 6l1.27-1.27a2 2 0 0 1 2.11-.45 12.84 12.84 0 0 0 2.81.7A2 2 0 0 1 22 16.92z"/>
                  </svg>
                </div>
                <h3>Téléphone</h3>
                <p>
                  <a href="tel:+33123456789">+33 1 23 45 67 89</a><br/>
                  Lun-Ven: 09:00 - 18:00
                </p>
              </div>

              <div class="info-card">
                <div class="info-icon">
                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                    <rect x="2" y="4" width="20" height="16" rx="2"/>
                    <path d="m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7"/>
                  </svg>
                </div>
                <h3>Email</h3>
                <p>
                  <a href="mailto:contact@azurimmo.fr">contact@azurimmo.fr</a><br/>
                  <a href="mailto:support@azurimmo.fr">support@azurimmo.fr</a>
                </p>
              </div>
            </div>
          </div>
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

const form = ref({
  name: '',
  email: '',
  subject: '',
  message: ''
})

const isSubmitting = ref(false)
const successMessage = ref('')
const errorMessage = ref('')

const handleLogout = () => {
  logout()
  router.push('/login')
}

const submitForm = async () => {
  successMessage.value = ''
  errorMessage.value = ''
  isSubmitting.value = true

  try {
    // Simuler l'envoi du formulaire
    await new Promise(resolve => setTimeout(resolve, 1500))

    successMessage.value = 'Merci! Votre message a été envoyé avec succès. Nous vous répondrons bientôt.'

    // Réinitialiser le formulaire
    form.value = {
      name: '',
      email: '',
      subject: '',
      message: ''
    }

    // Effacer le message après 5 secondes
    setTimeout(() => {
      successMessage.value = ''
    }, 5000)
  } catch (error) {
    errorMessage.value = 'Une erreur est survenue. Veuillez réessayer.'
  } finally {
    isSubmitting.value = false
  }
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
  max-width: 1000px;
  margin: 0 auto;
}

h1 {
  font-size: 2.5rem;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.subtitle {
  color: var(--text-secondary);
  font-size: 1.1rem;
  margin-bottom: 3rem;
}

.contact-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 3rem;
  margin-bottom: 2rem;
}

/* Contact Form */
.contact-form {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-group label {
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
  font-size: 0.95rem;
}

.form-group input,
.form-group textarea {
  padding: 0.75rem 1rem;
  border: 1px solid var(--border);
  border-radius: 0.5rem;
  background: var(--surface);
  color: var(--text-primary);
  font-family: inherit;
  font-size: 0.95rem;
  transition: all 0.2s;
}

.form-group input:focus,
.form-group textarea:focus {
  outline: none;
  border-color: var(--accent);
  box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.1);
}

.success-message {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 1rem;
  background: rgba(34, 197, 94, 0.1);
  border: 1px solid rgba(34, 197, 94, 0.3);
  border-radius: 0.5rem;
  color: #22c55e;
  font-size: 0.95rem;
}

.error-message {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  padding: 1rem;
  background: rgba(220, 38, 38, 0.1);
  border: 1px solid rgba(220, 38, 38, 0.3);
  border-radius: 0.5rem;
  color: #dc2626;
  font-size: 0.95rem;
}

.submit-btn {
  padding: 0.75rem 1.5rem;
  background: var(--accent);
  color: white;
  border: none;
  border-radius: 0.5rem;
  font-weight: 600;
  font-size: 0.95rem;
  cursor: pointer;
  transition: all 0.2s;
}

.submit-btn:hover:not(:disabled) {
  background: #2563eb;
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.3);
}

.submit-btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

/* Contact Info */
.contact-info-section {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.info-card {
  padding: 1.5rem;
  background: var(--surface);
  border: 1px solid var(--border);
  border-radius: 0.75rem;
  transition: all 0.2s;
}

.info-card:hover {
  border-color: var(--accent);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
}

.info-icon {
  width: 50px;
  height: 50px;
  background: var(--accent-bg);
  border-radius: 0.5rem;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--accent);
  margin-bottom: 1rem;
}

.info-card h3 {
  font-size: 1.1rem;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 0.5rem;
}

.info-card p {
  color: var(--text-secondary);
  line-height: 1.6;
  font-size: 0.95rem;
}

.info-card a {
  color: var(--accent);
  text-decoration: none;
  transition: color 0.2s;
}

.info-card a:hover {
  text-decoration: underline;
}

/* Responsive */
@media (max-width: 768px) {
  .contact-grid {
    grid-template-columns: 1fr;
    gap: 2rem;
  }

  h1 {
    font-size: 2rem;
  }
}
</style>

