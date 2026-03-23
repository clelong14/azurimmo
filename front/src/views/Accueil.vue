<template>
  <div class="page-container">

    <div class="welcome-banner fade-in">
      <div class="welcome-text">
        <h1 class="welcome-title">Bienvenue sur Azurimmo 👋</h1>
        <p class="welcome-desc">
          Cette application vous permet de gérer votre patrimoine immobilier : bâtiments, appartements, contrats de location, locataires et interventions.
          Utilisez le menu à gauche pour naviguer entre les sections.
        </p>
      </div>
    </div>

    <div class="section-block fade-in" style="animation-delay:.05s">
      <h2 class="section-heading">Comment démarrer ?</h2>
      <div class="steps-grid">
        <div class="step-card" v-for="(step, i) in steps" :key="i">
          <div class="step-number">{{ i + 1 }}</div>
          <div class="step-content">
            <div class="step-title">{{ step.title }}</div>
            <div class="step-desc">{{ step.desc }}</div>
          </div>
        </div>
      </div>
    </div>

    <div class="section-block fade-in" style="animation-delay:.1s">
      <h2 class="section-heading">État de votre parc</h2>
      <div class="stats-row">
        <router-link :to="s.to" class="stat-card stat-link-card" v-for="s in stats" :key="s.label">
          <div class="stat-icon-wrap" :style="`background:${s.color}18;color:${s.color}`">
            <component :is="s.icon" />
          </div>
          <div class="stat-value">{{ s.value ?? '…' }}</div>
          <div class="stat-label">{{ s.label }}</div>
          <div class="stat-hint">Accéder →</div>
        </router-link>
      </div>
    </div>

    <div class="section-block fade-in" style="animation-delay:.15s">
      <h2 class="section-heading">Que faire dans chaque section ?</h2>
      <p class="section-intro">Cliquez sur une section pour y accéder directement.</p>
      <div class="modules-list">
        <router-link :to="m.to" class="module-row" v-for="m in modules" :key="m.to">
          <div class="module-icon-wrap"><component :is="m.icon" /></div>
          <div class="module-info">
            <div class="module-name">{{ m.name }}</div>
            <div class="module-desc">{{ m.desc }}</div>
          </div>
          <div class="module-actions">
            <span class="module-action-tag" v-for="a in m.actions" :key="a">{{ a }}</span>
          </div>
          <svg class="module-arrow" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><path d="m9 18 6-6-6-6"/></svg>
        </router-link>
      </div>
    </div>

    <div class="section-block fade-in" style="animation-delay:.2s">
      <div class="api-card">
        <div class="api-info">
          <div class="api-title">📄 Documentation API — Swagger UI</div>
          <div class="api-desc">Testez les endpoints REST, consultez les modèles de données et intégrez Azurimmo à vos outils.</div>
        </div>
        <a :href="`${BASE}/swagger-ui.html`" target="_blank" class="btn btn-secondary">Ouvrir Swagger ↗</a>
      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { batimentApi, appartementApi, contratApi, locataireApi, interventionApi } from '@/composables/useApi'

import { IconBuilding, IconApt, IconFile, IconUsers, IconWrench } from '@/components/Icons.js'

const BASE = import.meta.env.VITE_API_URL

const steps = [
  { title: 'Créez vos bâtiments', desc: 'Commencez par enregistrer vos immeubles avec leur adresse et leur ville.' },
  { title: 'Ajoutez les appartements', desc: 'Pour chaque bâtiment, saisissez les logements : numéro, surface, pièces.' },
  { title: 'Enregistrez les locataires', desc: 'Renseignez les coordonnées de vos locataires : nom, email, téléphone.' },
  { title: 'Créez les contrats', desc: 'Associez un appartement à un contrat de location avec les dates et montants.' },
  { title: 'Suivez les interventions', desc: 'Planifiez et tracez les travaux de maintenance sur vos logements.' },
]

const stats = ref([
  { label: 'Bâtiments',      value: null, to: '/batiments',      color: '#2563eb', icon: IconBuilding },
  { label: 'Appartements',   value: null, to: '/appartements',   color: '#7c3aed', icon: IconApt     },
  { label: 'Contrats actifs',value: null, to: '/contrats',       color: '#16a34a', icon: IconFile    },
  { label: 'Locataires',     value: null, to: '/locataires',     color: '#d97706', icon: IconUsers   },
  { label: 'Interventions',  value: null, to: '/interventions',  color: '#dc2626', icon: IconWrench  },
])

const modules = [
  { to: '/batiments',     name: 'Bâtiments',     icon: IconBuilding, desc: 'Gérez vos immeubles : adresse, ville. Chaque bâtiment contient un ou plusieurs appartements.',                   actions: ['Lister', 'Ajouter'] },
  { to: '/appartements',  name: 'Appartements',  icon: IconApt,      desc: 'Consultez et gérez les logements. Filtrez par nombre de pièces, recherchez par numéro.',                         actions: ['Lister', 'Filtrer', 'Ajouter'] },
  { to: '/contrats',      name: 'Contrats',      icon: IconFile,     desc: 'Suivez vos baux : dates, loyer, charges. Les contrats en cours et expirés sont clairement identifiés.',          actions: ['Lister', 'Filtrer', 'Ajouter'] },
  { to: '/locataires',    name: 'Locataires',    icon: IconUsers,    desc: 'Retrouvez rapidement un locataire par nom, email ou téléphone.',                                                  actions: ['Rechercher', 'Ajouter'] },
  { to: '/interventions', name: 'Interventions', icon: IconWrench,   desc: 'Planifiez les travaux et leur suivi. Distinguez les interventions réalisées de celles en attente.',               actions: ['Lister', 'Filtrer', 'Ajouter'] },
]

onMounted(async () => {
  try {
    const [b, a, c, l, i] = await Promise.allSettled([
      batimentApi.getAll(), appartementApi.getAll(), contratApi.getAll(),
      locataireApi.getAll(), interventionApi.getAll(),
    ])
    const now = new Date()
    const vals = [
      b.status === 'fulfilled' ? b.value.data.length : '—',
      a.status === 'fulfilled' ? a.value.data.length : '—',
      c.status === 'fulfilled' ? c.value.data.filter(x => new Date(x.dateDebut) <= now && now <= new Date(x.dateFin)).length : '—',
      l.status === 'fulfilled' ? l.value.data.length : '—',
      i.status === 'fulfilled' ? i.value.data.length : '—',
    ]
    stats.value = stats.value.map((s, idx) => ({ ...s, value: vals[idx] }))
  } catch {}
})
</script>

<style scoped>
@import '@/assets/page.css';

.welcome-banner {
  background: var(--accent-bg);
  border: 1px solid var(--accent-border);
  border-radius: var(--radius-lg);
  padding: 1.4rem 1.75rem;
  margin-bottom: 2rem;
}
.welcome-title { font-size: 1.15rem; font-weight: 700; color: var(--text-primary); margin-bottom: 0.4rem; }
.welcome-desc { font-size: 0.875rem; color: var(--text-secondary); line-height: 1.65; max-width: 680px; }

.section-block { margin-bottom: 2.25rem; }
.section-heading { font-size: 0.875rem; font-weight: 600; color: var(--text-primary); margin-bottom: 0.75rem; }
.section-intro { font-size: 0.82rem; color: var(--text-muted); margin-bottom: 0.75rem; margin-top: -0.3rem; }

.steps-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(210px, 1fr));
  gap: 0.65rem;
}
.step-card {
  display: flex; align-items: flex-start; gap: 0.8rem;
  background: var(--bg-card); border: 1px solid var(--border);
  border-radius: var(--radius); padding: 0.9rem 1rem;
}
.step-number {
  width: 24px; height: 24px; flex-shrink: 0;
  background: var(--accent); color: #fff; border-radius: 50%;
  display: flex; align-items: center; justify-content: center;
  font-size: 0.72rem; font-weight: 700;
}
.step-title { font-size: 0.83rem; font-weight: 600; color: var(--text-primary); margin-bottom: 0.2rem; }
.step-desc  { font-size: 0.77rem; color: var(--text-secondary); line-height: 1.5; }

.stat-link-card { display: flex; flex-direction: column; text-decoration: none; transition: border-color 0.15s, box-shadow 0.15s; }
.stat-link-card:hover { border-color: var(--accent); box-shadow: var(--shadow-sm); }
.stat-icon-wrap { width: 34px; height: 34px; border-radius: 8px; display: flex; align-items: center; justify-content: center; margin-bottom: 0.55rem; }
.stat-hint { font-size: 0.72rem; color: var(--accent); margin-top: 0.25rem; }

.modules-list { display: flex; flex-direction: column; border: 1px solid var(--border); border-radius: var(--radius-lg); overflow: hidden; }
.module-row {
  display: flex; align-items: center; gap: 0.9rem;
  padding: 0.85rem 1.1rem; border-bottom: 1px solid var(--border);
  text-decoration: none; transition: background 0.1s;
}
.module-row:last-child { border-bottom: none; }
.module-row:hover { background: var(--bg-secondary); }
.module-icon-wrap {
  width: 32px; height: 32px; flex-shrink: 0;
  background: var(--bg-tertiary); border: 1px solid var(--border);
  border-radius: 7px; display: flex; align-items: center; justify-content: center;
  color: var(--text-secondary);
}
.module-info { flex: 1; min-width: 0; }
.module-name { font-size: 0.875rem; font-weight: 600; color: var(--text-primary); }
.module-desc { font-size: 0.78rem; color: var(--text-secondary); line-height: 1.5; margin-top: 1px; }
.module-actions { display: flex; gap: 0.35rem; flex-shrink: 0; }
.module-action-tag {
  font-size: 0.69rem; font-weight: 500;
  padding: 0.15rem 0.45rem; border-radius: 4px;
  background: var(--bg-tertiary); border: 1px solid var(--border);
  color: var(--text-muted); white-space: nowrap;
}
.module-arrow { color: var(--text-muted); flex-shrink: 0; }

.api-card {
  display: flex; align-items: center; gap: 1.5rem;
  background: var(--bg-card); border: 1px solid var(--border);
  border-radius: var(--radius); padding: 1.1rem 1.25rem;
}
.api-info { flex: 1; }
.api-title { font-size: 0.875rem; font-weight: 600; color: var(--text-primary); margin-bottom: 0.3rem; }
.api-desc { font-size: 0.8rem; color: var(--text-secondary); line-height: 1.5; }

@media (max-width: 768px) {
  .module-actions { display: none; }
  .api-card { flex-direction: column; align-items: flex-start; }
  .steps-grid { grid-template-columns: 1fr; }
}
</style>