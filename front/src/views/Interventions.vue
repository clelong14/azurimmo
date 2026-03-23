<template>
  <div class="page-container">

    <div class="page-header">
      <div>
        <h1 class="page-title">Interventions</h1>
        <p class="page-subtitle">{{ interventions.length }} intervention{{ interventions.length !== 1 ? 's' : '' }} — {{ enAttente }} en attente</p>
      </div>
      <button class="btn btn-primary" @click="showModal = true">
        <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M12 5v14M5 12h14"/></svg>
        Planifier une intervention
      </button>
    </div>

    <div class="info-callout fade-in">
      <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M12 16v-4"/><path d="M12 8h.01"/></svg>
      <span>Une <strong>intervention</strong> est un travail planifié sur un appartement (plomberie, électricité, etc.). Elle est <span class="badge badge-amber" style="display:inline;padding:.1rem .4rem;font-size:.72rem">En attente</span> jusqu'à ce qu'une date de réalisation soit renseignée.</span>
    </div>

    <div class="toolbar fade-in" style="animation-delay:.05s">
      <div class="search-wrap">
        <svg class="search-icon" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.35-4.35"/></svg>
        <input v-model="search" type="text" class="search-input" placeholder="Rechercher par type ou description…" />
      </div>
      <select v-model="filterStatus" class="select-filter">
        <option value="">Tous les statuts</option>
        <option value="pending">En attente uniquement</option>
        <option value="done">Réalisées uniquement</option>
      </select>
    </div>

    <div v-if="loading" class="loading-state"><div class="spinner"></div><span>Chargement…</span></div>

    <div v-else-if="interventions.length === 0" class="empty-state">
      <svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" opacity=".3"><path d="M14.7 6.3a1 1 0 0 0 0 1.4l1.6 1.6a1 1 0 0 0 1.4 0l3.77-3.77a6 6 0 0 1-7.94 7.94l-6.91 6.91a2.12 2.12 0 0 1-3-3l6.91-6.91a6 6 0 0 1 7.94-7.94l-3.76 3.76z"/></svg>
      <h3>Aucune intervention pour le moment</h3>
      <p>Planifiez votre première intervention en cliquant sur <strong>« Planifier une intervention »</strong>.</p>
      <button class="btn btn-primary" style="margin-top:.75rem" @click="showModal = true">+ Planifier une intervention</button>
    </div>

    <div v-else-if="filtered.length === 0" class="empty-state">
      <h3>Aucun résultat</h3>
      <p>Modifiez votre recherche ou le filtre de statut.</p>
    </div>

    <div v-else class="table-container fade-in" style="animation-delay:.1s">
      <table class="data-table">
        <thead>
          <tr>
            <th>Statut</th>
            <th>Type</th>
            <th>Appartement</th>
            <th>Date prévue</th>
            <th>Date réalisée</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(inv, i) in filtered" :key="i">
            <td>
              <span :class="inv.dateRealisee ? 'badge badge-green' : 'badge badge-amber'">
                {{ inv.dateRealisee ? '✓ Réalisée' : '⏳ En attente' }}
              </span>
            </td>
            <td><strong>{{ inv.type }}</strong></td>
            <td class="col-muted">Appt. #{{ inv.appartement }}</td>
            <td class="col-mono">{{ fmt(inv.datePrevue) }}</td>
            <td class="col-mono col-muted">{{ inv.dateRealisee ? fmt(inv.dateRealisee) : '—' }}</td>
            <td class="col-muted" style="max-width:200px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap">{{ inv.description || '—' }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <AppModal v-model="showModal" title="Planifier une intervention">
      <p class="modal-hint">Choisissez le type de travaux, la date prévue et l'appartement concerné. Laissez la <strong>date réalisée vide</strong> si l'intervention n'est pas encore effectuée.</p>
      <form @submit.prevent="submit" class="form-grid" style="margin-top:1rem">
        <AppField label="Type d'intervention" v-model="form.type" type="select" required>
          <option value="Plomberie">🚰 Plomberie</option>
          <option value="Électricité">⚡ Électricité</option>
          <option value="Chauffage">🔥 Chauffage</option>
          <option value="Peinture">🎨 Peinture</option>
          <option value="Serrurerie">🔑 Serrurerie</option>
          <option value="Nettoyage">🧹 Nettoyage</option>
          <option value="Autre">🔧 Autre</option>
        </AppField>
        <div class="form-row">
          <AppField label="Date prévue" v-model="form.datePrevue" type="date" required />
          <AppField label="Date réalisée (optionnel)" v-model="form.dateRealisee" type="date" />
        </div>
        <AppField label="Description" v-model="form.description" type="textarea" placeholder="Décrivez le problème ou les travaux à effectuer…" required />
        <AppField label="ID de l'appartement" v-model="form.appartement" type="number" placeholder="Ex : 2 — visible dans la section Appartements" required />
        <div class="form-actions">
          <button type="button" class="btn-cancel" @click="showModal = false">Annuler</button>
          <button type="submit" class="btn-submit" :disabled="saving">{{ saving ? 'Enregistrement…' : 'Enregistrer' }}</button>
        </div>
      </form>
    </AppModal>

  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import AppModal from '@/components/AppModal.vue'
import AppField from '@/components/AppField.vue'
import { interventionApi } from '@/composables/useApi'

const interventions = ref([])
const loading = ref(true)
const search = ref('')
const filterStatus = ref('')
const showModal = ref(false)
const saving = ref(false)
const form = ref({ type: '', datePrevue: '', dateRealisee: '', description: '', appartement: '' })

const fmt = (d) => d ? new Date(d).toLocaleDateString('fr-FR') : '—'
const enAttente = computed(() => interventions.value.filter(i => !i.dateRealisee).length)

const filtered = computed(() => {
  let r = interventions.value
  if (search.value) { const q = search.value.toLowerCase(); r = r.filter(i => i.type?.toLowerCase().includes(q) || i.description?.toLowerCase().includes(q)) }
  if (filterStatus.value) r = r.filter(i => filterStatus.value === 'done' ? !!i.dateRealisee : !i.dateRealisee)
  return r
})

const load = async () => {
  try { loading.value = true; const res = await interventionApi.getAll(); interventions.value = res.data }
  catch { interventions.value = [] } finally { loading.value = false }
}

const submit = async () => {
  try {
    saving.value = true
    await interventionApi.create({ type: form.value.type, datePrevue: new Date(form.value.datePrevue).toISOString(), dateRealisee: form.value.dateRealisee ? new Date(form.value.dateRealisee).toISOString() : null, description: form.value.description, appartement: +form.value.appartement })
    form.value = { type: '', datePrevue: '', dateRealisee: '', description: '', appartement: '' }
    showModal.value = false; await load()
  } catch { alert('Erreur lors de la création.') } finally { saving.value = false }
}

onMounted(load)
</script>

<style scoped>
@import '@/assets/page.css';
.modal-hint { font-size: 0.82rem; color: var(--text-secondary); line-height: 1.55; }
</style>
