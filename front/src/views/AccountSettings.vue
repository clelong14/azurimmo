<template>
  <div class="page-container">

    <div class="page-header">
      <div>
        <h1 class="page-title">Paramètres de Compte</h1>
        <p class="page-subtitle">Gérez votre compte et vos préférences</p>
      </div>
    </div>

    <div class="settings-grid">
      <!-- Informations du compte -->
      <section class="settings-card">
        <div class="card-header">
          <h2 class="card-title">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
              <circle cx="12" cy="7" r="4"/>
            </svg>
            Informations du Compte
          </h2>
        </div>
        <div class="card-content">
          <div class="info-block">
            <label class="info-label">Email</label>
            <p class="info-value">{{ user?.adresseMail }}</p>
          </div>
        </div>
      </section>

      <!-- Sécurité et Déconnexion -->
      <section class="settings-card">
        <div class="card-header">
          <h2 class="card-title">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M12 22s8-4 8-10V5l-8-3-8 3v7c0 6 8 10 8 10z"/>
            </svg>
            Sécurité
          </h2>
        </div>
        <div class="card-content">
          <p class="card-description">Gérez votre session et vos paramètres de sécurité</p>
          <button class="btn btn-secondary" @click="handleLogout" style="margin-top: 1rem;">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/>
              <polyline points="16 17 21 12 16 7"/>
              <line x1="21" y1="12" x2="9" y2="12"/>
            </svg>
            Se Déconnecter
          </button>
        </div>
      </section>

      <!-- Zone Dangereuse - Supprimer le compte -->
      <section class="settings-card danger-zone">
        <div class="card-header">
          <h2 class="card-title" style="color: var(--danger);">
            <svg width="18" height="18" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <polyline points="3 6 5 6 21 6"/>
              <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/>
              <line x1="10" y1="11" x2="10" y2="17"/>
              <line x1="14" y1="11" x2="14" y2="17"/>
            </svg>
            Zone Dangereuse
          </h2>
        </div>
        <div class="card-content">
          <div class="danger-warning">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <circle cx="12" cy="12" r="10"/>
              <line x1="12" y1="8" x2="12" y2="12"/>
              <line x1="12" y1="16" x2="12.01" y2="16"/>
            </svg>
            <div>
              <p class="warning-title">Supprimer votre compte définitivement</p>
              <p class="warning-text">Cette action est irréversible. Votre compte et toutes vos données seront supprimés.</p>
            </div>
          </div>
          <button class="btn btn-danger" @click="openDeleteConfirmation" :disabled="isDeleting">
            <svg width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
              <polyline points="3 6 5 6 21 6"/>
              <path d="M19 6v14a2 2 0 0 1-2 2H7a2 2 0 0 1-2-2V6m3 0V4a2 2 0 0 1 2-2h4a2 2 0 0 1 2 2v2"/>
            </svg>
            {{ isDeleting ? 'Suppression en cours…' : 'Supprimer mon compte' }}
          </button>
        </div>
      </section>
    </div>

    <!-- Modal de confirmation de suppression -->
    <AppModal v-model="showDeleteModal" title="Supprimer définitivement votre compte ?">
      <div class="confirmation-content">
        <div class="warning-box">
          <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
            <circle cx="12" cy="12" r="10"/>
            <line x1="12" y1="8" x2="12" y2="12"/>
            <line x1="12" y1="16" x2="12.01" y2="16"/>
          </svg>
          <p><strong>Attention!</strong> Cette action est irréversible et définitive.</p>
        </div>

        <p class="confirmation-text">
          Une fois votre compte supprimé:
        </p>
        <ul class="confirmation-list">
          <li>Tous vos données seront effacées</li>
          <li>Vous ne pourrez plus accéder à votre compte</li>
          <li>Cette action ne peut pas être annulée</li>
        </ul>

        <div class="confirmation-footer">
          <button type="button" class="btn-cancel" @click="showDeleteModal = false" :disabled="isDeleting">
            Annuler
          </button>
          <button type="button" class="btn-danger" @click="confirmDeleteAccount" :disabled="isDeleting">
            <span v-if="isDeleting" class="btn-spinner"></span>
            {{ isDeleting ? 'Suppression en cours…' : 'Oui, supprimer mon compte' }}
          </button>
        </div>
      </div>
    </AppModal>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuth } from '@/composables/useAuth'
import AppModal from '@/components/AppModal.vue'

const router = useRouter()
const { user, logout } = useAuth()

const showDeleteModal = ref(false)
const isDeleting = ref(false)

const handleLogout = () => {
  logout()
  router.push('/accueil')
}

const openDeleteConfirmation = () => {
  showDeleteModal.value = true
}

const confirmDeleteAccount = async () => {
  isDeleting.value = true
  try {
    const userId = user.value?.id

    if (!userId) {
      throw new Error('ID utilisateur introuvable')
    }

    let apiUrl = import.meta.env.VITE_API_URL || 'http://localhost:9008'
    // Nettoyer le trailing slash
    apiUrl = apiUrl.replace(/\/$/, '')

    const response = await fetch(
      `${apiUrl}/users/${userId}`,
      {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json'
        }
      }
    )

    if (!response.ok) {
      const errorMsg = await response.text()
      throw new Error(errorMsg || 'Erreur lors de la suppression du compte')
    }

    // Déconnecter l'utilisateur et rediriger
    logout()
    showDeleteModal.value = false

    // Attendre un peu pour que le logout soit traité
    setTimeout(() => {
      router.push('/accueil')
    }, 500)

  } catch (error) {
    console.error('Erreur lors de la suppression:', error)
    alert('Erreur: ' + error.message)
    isDeleting.value = false
  }
}

</script>

<style scoped>

.page-container {
  padding: 2rem;
  max-width: 900px;
  margin: 0 auto;
}

.page-header {
  margin-bottom: 2rem;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.page-title {
  font-size: 1.8rem;
  font-weight: 600;
  margin: 0;
  color: var(--text-primary);
}

.page-subtitle {
  font-size: 0.9rem;
  color: var(--text-secondary);
  margin: 0.5rem 0 0 0;
}

.settings-grid {
  display: grid;
  gap: 2rem;
  animation: fadeIn 0.3s ease-out;
}

.settings-card {
  background: var(--surface);
  border-radius: 0.75rem;
  border: 1px solid var(--border);
  overflow: hidden;
  transition: all 0.2s ease;
}

.settings-card:hover {
  border-color: var(--accent);
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
}

.settings-card.danger-zone {
  border-color: var(--danger);
  background: linear-gradient(135deg, var(--surface) 0%, rgba(220, 38, 38, 0.02) 100%);
}

.card-header {
  padding: 1.5rem;
  border-bottom: 1px solid var(--border);
  background: var(--bg);
}

.card-title {
  display: flex;
  align-items: center;
  gap: 0.75rem;
  font-size: 1.1rem;
  font-weight: 600;
  margin: 0;
  color: var(--text-primary);
}

.card-content {
  padding: 1.5rem;
}

.card-description {
  color: var(--text-secondary);
  margin: 0;
  font-size: 0.9rem;
}

/* Info Block */
.info-block {
  margin-bottom: 1.5rem;
}

.info-block:last-child {
  margin-bottom: 0;
}

.info-label {
  display: block;
  font-size: 0.8rem;
  font-weight: 600;
  color: var(--text-secondary);
  text-transform: uppercase;
  letter-spacing: 0.05em;
  margin-bottom: 0.5rem;
}

.info-value {
  font-size: 1rem;
  color: var(--text-primary);
  margin: 0;
  word-break: break-all;
}

/* Danger Zone */
.danger-warning {
  display: flex;
  gap: 1rem;
  padding: 1rem;
  background: rgba(220, 38, 38, 0.05);
  border-radius: 0.5rem;
  border-left: 3px solid var(--danger);
  margin-bottom: 1.5rem;
  color: var(--danger);
}

.danger-warning svg {
  flex-shrink: 0;
  margin-top: 0.2rem;
}

.warning-title {
  font-weight: 600;
  margin: 0 0 0.25rem 0;
}

.warning-text {
  font-size: 0.9rem;
  margin: 0;
  opacity: 0.9;
}

/* Buttons */
.btn {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  padding: 0.625rem 1rem;
  border: none;
  border-radius: 0.5rem;
  font-weight: 500;
  font-size: 0.9rem;
  cursor: pointer;
  transition: all 0.2s ease;
  white-space: nowrap;
}

.btn:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-secondary {
  background: var(--bg);
  color: var(--text-primary);
  border: 1px solid var(--border);
}

.btn-secondary:hover:not(:disabled) {
  background: var(--border);
}

.btn-danger {
  background: var(--danger);
  color: white;
}

.btn-danger:hover:not(:disabled) {
  background: #b91c1c;
  transform: translateY(-1px);
  box-shadow: 0 4px 12px rgba(220, 38, 38, 0.3);
}

.btn-spinner {
  display: inline-block;
  width: 0.75rem;
  height: 0.75rem;
  border: 2px solid rgba(255, 255, 255, 0.3);
  border-top-color: white;
  border-radius: 50%;
  animation: spin 0.8s linear infinite;
}

@keyframes spin {
  to { transform: rotate(360deg); }
}

/* Modal Confirmation */
.confirmation-content {
  padding: 1rem 0;
}

.warning-box {
  display: flex;
  gap: 1rem;
  padding: 1rem;
  background: rgba(220, 38, 38, 0.05);
  border-radius: 0.5rem;
  border-left: 3px solid var(--danger);
  margin-bottom: 1.5rem;
  color: var(--danger);
}

.warning-box svg {
  flex-shrink: 0;
}

.warning-box p {
  margin: 0;
  font-size: 0.9rem;
}

.confirmation-text {
  margin: 1.5rem 0 0.75rem 0;
  color: var(--text-primary);
  font-weight: 500;
}

.confirmation-list {
  margin: 0 0 1.5rem 0;
  padding-left: 1.5rem;
  color: var(--text-secondary);
}

.confirmation-list li {
  margin-bottom: 0.5rem;
}

.confirmation-footer {
  display: flex;
  gap: 1rem;
  margin-top: 1.5rem;
  justify-content: flex-end;
}

.btn-cancel {
  flex: 1;
  padding: 0.625rem 1rem;
  border: 1px solid var(--border);
  background: var(--surface);
  color: var(--text-primary);
  border-radius: 0.5rem;
  font-weight: 500;
  cursor: pointer;
  transition: all 0.2s ease;
  white-space: nowrap;
}

.btn-cancel:hover:not(:disabled) {
  background: var(--bg);
  border-color: var(--text-secondary);
}

.btn-cancel:disabled {
  opacity: 0.6;
  cursor: not-allowed;
}

.btn-danger {
  flex: 1;
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
  .page-container {
    padding: 1rem;
  }

  .page-title {
    font-size: 1.5rem;
  }

  .confirmation-footer {
    flex-direction: column-reverse;
  }

  .settings-card {
    border-radius: 0.5rem;
  }
}
</style>

