<template>
  <div class="page-container">

    <div class="page-header">
      <div>
        <h1 class="page-title">Contrats de location</h1>
        <p class="page-subtitle">{{ contrats.length }} contrat{{ contrats.length !== 1 ? 's' : '' }} — {{ actifs }} en cours</p>
      </div>
      <button class="btn btn-primary" @click="showModal = true">
        <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M12 5v14M5 12h14"/></svg>
        Nouveau contrat
      </button>
    </div>

    <div class="info-callout fade-in">
      <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M12 16v-4"/><path d="M12 8h.01"/></svg>
      <span>Un <strong>contrat</strong> lie un appartement à une période de location. Le statut <span class="badge badge-green" style="display:inline;padding:.1rem .4rem;font-size:.72rem">En cours</span> est automatique selon les dates saisies.</span>
    </div>

    <div class="toolbar fade-in" style="animation-delay:.05s">
      <div class="search-wrap">
        <svg class="search-icon" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.35-4.35"/></svg>
        <input v-model="search" type="text" class="search-input" placeholder="Rechercher par ID d'appartement…" />
      </div>
      <select v-model="filterStatus" class="select-filter">
        <option value="">Tous les statuts</option>
        <option value="active">En cours uniquement</option>
        <option value="ended">Terminés uniquement</option>
      </select>
    </div>

    <div v-if="loading" class="loading-state"><div class="spinner"></div><span>Chargement…</span></div>

    <div v-else-if="contrats.length === 0" class="empty-state">
      <svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" opacity=".3"><path d="M14 2H6a2 2 0 0 0-2 2v16a2 2 0 0 0 2 2h12a2 2 0 0 0 2-2V8z"/><polyline points="14 2 14 8 20 8"/></svg>
      <h3>Aucun contrat pour le moment</h3>
      <p>Créez votre premier contrat en cliquant sur <strong>« Nouveau contrat »</strong>.</p>
      <button class="btn btn-primary" style="margin-top:.75rem" @click="showModal = true">+ Nouveau contrat</button>
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
            <th>Appartement</th>
            <th>Date de début</th>
            <th>Date de fin</th>
            <th>Loyer brut</th>
            <th>Charges</th>
            <th>Total / mois</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(c, i) in filtered" :key="i">
            <td>
              <span :class="isActive(c) ? 'badge badge-green' : 'badge badge-red'">
                {{ isActive(c) ? '● En cours' : '○ Terminé' }}
              </span>
            </td>
            <td class="col-muted">Appt. #{{ c.appartement }}</td>
            <td class="col-mono">{{ fmt(c.dateDebut) }}</td>
            <td class="col-mono">{{ fmt(c.dateFin) }}</td>
            <td class="col-mono">{{ c.montantBrut?.toFixed(2) }} €</td>
            <td class="col-mono">{{ c.montantCharges?.toFixed(2) }} €</td>
            <td><strong class="col-mono">{{ ((c.montantBrut || 0) + (c.montantCharges || 0)).toFixed(2) }} €</strong></td>
          </tr>
        </tbody>
      </table>
    </div>

    <AppModal v-model="showModal" title="Nouveau contrat de location">
      <p class="modal-hint">Indiquez l'appartement concerné (son ID), les dates du bail et les montants. Le statut « En cours » sera calculé automatiquement.</p>
      <form @submit.prevent="submit" class="form-grid" style="margin-top:1rem">
        <div class="form-row">
          <AppField label="Date de début" v-model="form.dateDebut" type="date" required />
          <AppField label="Date de fin" v-model="form.dateFin" type="date" required />
        </div>
        <div class="form-row">
          <AppField label="Loyer brut (€)" v-model="form.montantBrut" type="number" placeholder="Ex : 850.00" required />
          <AppField label="Charges mensuelles (€)" v-model="form.montantCharges" type="number" placeholder="Ex : 75.00" required />
        </div>
        <AppField label="ID de l'appartement" v-model="form.appartement" type="number" placeholder="Ex : 3 — visible dans la section Appartements" required />
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
import { contratApi } from '@/composables/useApi'

const contrats = ref([])
const loading = ref(true)
const search = ref('')
const filterStatus = ref('')
const showModal = ref(false)
const saving = ref(false)
const form = ref({ dateDebut: '', dateFin: '', montantBrut: '', montantCharges: '', appartement: '' })

const isActive = (c) => { const now = new Date(); return new Date(c.dateDebut) <= now && now <= new Date(c.dateFin) }
const fmt = (d) => d ? new Date(d).toLocaleDateString('fr-FR') : '—'
const actifs = computed(() => contrats.value.filter(isActive).length)

const filtered = computed(() => {
  let r = contrats.value
  if (search.value) { const q = search.value.toLowerCase(); r = r.filter(c => c.appartement?.toString().includes(q)) }
  if (filterStatus.value) r = r.filter(c => filterStatus.value === 'active' ? isActive(c) : !isActive(c))
  return r
})

const load = async () => {
  try { loading.value = true; const res = await contratApi.getAll(); contrats.value = res.data }
  catch { contrats.value = [] } finally { loading.value = false }
}

const submit = async () => {
  try {
    saving.value = true
    await contratApi.create({ dateDebut: new Date(form.value.dateDebut).toISOString(), dateFin: new Date(form.value.dateFin).toISOString(), montantBrut: +form.value.montantBrut, montantCharges: +form.value.montantCharges, appartement: +form.value.appartement })
    form.value = { dateDebut: '', dateFin: '', montantBrut: '', montantCharges: '', appartement: '' }
    showModal.value = false; await load()
  } catch { alert('Erreur lors de la création.') } finally { saving.value = false }
}

onMounted(load)
</script>

<style scoped>
@import '@/assets/page.css';
.modal-hint { font-size: 0.82rem; color: var(--text-secondary); line-height: 1.55; }
</style>
