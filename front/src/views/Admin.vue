<template>
  <div class="page-container">

    <div class="page-header">
      <div>
        <h1 class="page-title">Administration</h1>
        <p class="page-subtitle">Gestion des comptes et paramètres système</p>
      </div>
      <span class="badge badge-red" style="font-size:0.78rem;padding:.3rem .75rem">Accès Admin uniquement</span>
    </div>

    <div class="info-callout fade-in">
      <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M12 16v-4"/><path d="M12 8h.01"/></svg>
      <span>Cette section est réservée aux <strong>administrateurs</strong>. Elle permet de visualiser les comptes utilisateurs déclarés dans l'application.</span>
    </div>

    <!-- Users table -->
    <div class="section-block fade-in" style="animation-delay:.05s">
      <h2 class="section-heading">Comptes utilisateurs</h2>
      <div class="table-container">
        <table class="data-table">
          <thead>
          <tr>
            <th>Utilisateur</th>
            <th>Identifiant</th>
            <th>Email</th>
            <th>Rôle</th>
          </tr>
          </thead>
          <tbody>
          <tr v-for="u in users" :key="u.id">
            <td>
              <div style="display:flex;align-items:center;gap:.65rem">
                <div class="avatar">{{ u.name[0] }}</div>
                <strong>{{ u.name }}</strong>
              </div>
            </td>
            <td class="col-mono col-muted">{{ u.username }}</td>
            <td class="col-muted">{{ u.email }}</td>
            <td>
                <span :class="u.role === 'admin' ? 'badge badge-red' : 'badge badge-blue'">
                  {{ u.role === 'admin' ? '🔑 Admin' : '👤 Gestionnaire' }}
                </span>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- Role permissions -->
    <div class="section-block fade-in" style="animation-delay:.1s">
      <h2 class="section-heading">Droits par rôle</h2>
      <div class="permissions-grid">
        <div class="perm-card">
          <div class="perm-header">
            <span class="badge badge-red" style="font-size:.78rem">🔑 Admin</span>
          </div>
          <ul class="perm-list">
            <li v-for="p in adminPerms" :key="p" class="perm-item perm-allowed">
              <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="m20 6-11 11-5-5"/></svg>
              {{ p }}
            </li>
          </ul>
        </div>
        <div class="perm-card">
          <div class="perm-header">
            <span class="badge badge-blue" style="font-size:.78rem">👤 Gestionnaire</span>
          </div>
          <ul class="perm-list">
            <li v-for="p in userPerms" :key="p.label" class="perm-item" :class="p.allowed ? 'perm-allowed' : 'perm-denied'">
              <svg v-if="p.allowed" width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="m20 6-11 11-5-5"/></svg>
              <svg v-else width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="m18 6-12 12M6 6l12 12"/></svg>
              {{ p.label }}
            </li>
          </ul>
        </div>
      </div>
    </div>

    <!-- Current session -->
    <div class="section-block fade-in" style="animation-delay:.15s">
      <h2 class="section-heading">Session en cours</h2>
      <div class="session-card">
        <div class="session-row">
          <span class="session-label">Connecté en tant que</span>
          <strong class="session-value">{{ user?.name }}</strong>
        </div>
        <div class="session-row">
          <span class="session-label">Identifiant</span>
          <span class="session-value col-mono">{{ user?.username }}</span>
        </div>
        <div class="session-row">
          <span class="session-label">Rôle</span>
          <span :class="user?.role === 'admin' ? 'badge badge-red' : 'badge badge-blue'" style="font-size:.78rem">{{ user?.role }}</span>
        </div>
        <div class="session-row" style="border-bottom:none">
          <span class="session-label">Email</span>
          <span class="session-value">{{ user?.email }}</span>
        </div>
      </div>
    </div>

  </div>
</template>

<script setup>
import { useAuth } from '@/composables/useAuth.js'

const { user } = useAuth()

const users = [
  { id: 1, name: 'Administrateur', username: 'admin',        email: 'admin@azurimmo.fr',        role: 'admin' },
  { id: 2, name: 'Jean Dupont',    username: 'gestionnaire', email: 'jean.dupont@azurimmo.fr',  role: 'user'  },
]

const adminPerms = [
  'Accès au tableau de bord',
  'Consulter tous les bâtiments',
  'Ajouter / modifier des bâtiments',
  'Consulter tous les appartements',
  'Ajouter / modifier des appartements',
  'Consulter tous les contrats',
  'Créer des contrats',
  'Consulter tous les locataires',
  'Ajouter des locataires',
  'Consulter les interventions',
  'Planifier des interventions',
  'Accès à la section Administration',
]

const userPerms = [
  { label: 'Accès au tableau de bord',           allowed: true  },
  { label: 'Consulter tous les bâtiments',        allowed: true  },
  { label: 'Ajouter / modifier des bâtiments',    allowed: true  },
  { label: 'Consulter tous les appartements',     allowed: true  },
  { label: 'Ajouter / modifier des appartements', allowed: true  },
  { label: 'Consulter tous les contrats',         allowed: true  },
  { label: 'Créer des contrats',                  allowed: true  },
  { label: 'Consulter tous les locataires',       allowed: true  },
  { label: 'Ajouter des locataires',              allowed: true  },
  { label: 'Consulter les interventions',         allowed: true  },
  { label: 'Planifier des interventions',         allowed: true  },
  { label: 'Accès à la section Administration',   allowed: false },
]
</script>

<style scoped>
@import '@/assets/page.css';

.section-block { margin-bottom: 2rem; }
.section-heading { font-size: 0.875rem; font-weight: 600; color: var(--text-primary); margin-bottom: 0.75rem; }

.permissions-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 1rem;
}
.perm-card {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-lg);
  overflow: hidden;
}
.perm-header {
  padding: 0.8rem 1.1rem;
  border-bottom: 1px solid var(--border);
  background: var(--bg-secondary);
}
.perm-list { list-style: none; padding: 0.5rem 0; }
.perm-item {
  display: flex;
  align-items: center;
  gap: 0.6rem;
  padding: 0.45rem 1.1rem;
  font-size: 0.82rem;
}
.perm-allowed { color: var(--text-primary); }
.perm-allowed svg { color: var(--green); flex-shrink: 0; }
.perm-denied { color: var(--text-muted); }
.perm-denied svg { color: var(--red); flex-shrink: 0; }

.session-card {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-lg);
  overflow: hidden;
}
.session-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0.75rem 1.1rem;
  border-bottom: 1px solid var(--border);
  font-size: 0.85rem;
}
.session-label { color: var(--text-muted); }
.session-value { color: var(--text-primary); }

@media (max-width: 640px) {
  .permissions-grid { grid-template-columns: 1fr; }
}
</style>