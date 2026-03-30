<template>
  <div class="page-container">

    <div class="page-header">
      <div>
        <h1 class="page-title">Contrats de location</h1>
        <p class="page-subtitle">{{ contrats.length }} contrat{{ contrats.length !== 1 ? 's' : '' }} — {{ actifs }} en cours</p>
      </div>
      <button class="btn btn-primary" @click="openCreateModal">
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
        <input v-model="search" type="text" class="search-input" placeholder="Rechercher par numéro ou adresse d'appartement…" />
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
      <button class="btn btn-primary" style="margin-top:.75rem" @click="openCreateModal">+ Nouveau contrat</button>
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
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(c, i) in filtered" :key="c.id || i">
            <td>
              <span :class="isActive(c) ? 'badge badge-green' : 'badge badge-red'">
                {{ isActive(c) ? '● En cours' : '○ Terminé' }}
              </span>
            </td>
            <td class="col-muted">{{ getAppartementLabel(c.appartement) }}</td>
            <td class="col-mono">{{ fmt(c.dateDebut) }}</td>
            <td class="col-mono">{{ fmt(c.dateFin) }}</td>
            <td class="col-mono">{{ c.montantBrut?.toFixed(2) }} €</td>
            <td class="col-mono">{{ c.montantCharges?.toFixed(2) }} €</td>
            <td><strong class="col-mono">{{ ((c.montantBrut || 0) + (c.montantCharges || 0)).toFixed(2) }} €</strong></td>
            <td>
              <button type="button" class="btn btn-ghost" @click="openEditModal(c)">Modifier</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <AppModal v-model="showModal" :title="isEditMode ? 'Modifier le contrat de location' : 'Nouveau contrat de location'">
      <p class="modal-hint">Sélectionnez l'appartement concerné, les dates du bail et les montants. Le statut « En cours » sera calculé automatiquement.</p>
      <form @submit.prevent="submit" class="form-grid" style="margin-top:1rem">
        <div class="form-row">
          <AppField label="Date de début" v-model="form.dateDebut" type="date" required />
          <AppField label="Date de fin" v-model="form.dateFin" type="date" required />
        </div>
        <div class="form-row">
          <AppField label="Loyer brut (€)" v-model="form.montantBrut" type="number" placeholder="Ex : 850.00" required />
          <AppField label="Charges mensuelles (€)" v-model="form.montantCharges" type="number" placeholder="Ex : 75.00" required />
        </div>
        <AppField label="Appartement" v-model="form.appartement" type="select" placeholder="Choisissez un appartement" required>
          <option v-for="opt in appartementOptions" :key="opt.value" :value="opt.value">
            {{ opt.label }}
          </option>
        </AppField>
        <div class="form-actions">
          <button type="button" class="btn-cancel" @click="closeModal">Annuler</button>
          <button type="submit" class="btn-submit" :disabled="saving || appartementOptions.length === 0">
            {{ saving ? 'Enregistrement…' : (isEditMode ? 'Mettre à jour' : 'Enregistrer') }}
          </button>
        </div>
      </form>
    </AppModal>

  </div>
</template>

<script setup>
import { ref, computed, onMounted, watch } from 'vue'
import AppModal from '@/components/AppModal.vue'
import AppField from '@/components/AppField.vue'
import { contratApi, appartementApi, batimentApi } from '@/composables/useApi'

const contrats = ref([])
const appartements = ref([])
const batiments = ref([])
const loading = ref(true)
const search = ref('')
const filterStatus = ref('')
const showModal = ref(false)
const saving = ref(false)
const isEditMode = ref(false)
const editingContratId = ref(null)
const form = ref({ dateDebut: '', dateFin: '', montantBrut: '', montantCharges: '', appartement: '' })

const isActive = (c) => { const now = new Date(); return new Date(c.dateDebut) <= now && now <= new Date(c.dateFin) }
const fmt = (d) => d ? new Date(d).toLocaleDateString('fr-FR') : '—'
const actifs = computed(() => contrats.value.filter(isActive).length)

const getBatimentAdresse = (batimentId) => {
  const batiment = batiments.value.find((b) => +b.id === +batimentId)
  return batiment?.adresse || 'Adresse inconnue'
}

const getAppartementLabel = (appartementId) => {
  const appartement = appartements.value.find((a) => +a.id === +appartementId)
  if (!appartement) return `Appartement #${appartementId}`
  const numero = appartement.numero ?? appartement.id
  return `Appt. n°${numero} — ${getBatimentAdresse(appartement.batiment)}`
}

const appartementOptions = computed(() => {
  return appartements.value
    .map((a) => {
      const numero = a.numero ?? a.id
      return {
        value: String(a.id),
        label: `Appt. n°${numero} — ${getBatimentAdresse(a.batiment)}`,
      }
    })
    .sort((a, b) => a.label.localeCompare(b.label, 'fr'))
})

const filtered = computed(() => {
  let r = contrats.value
  if (search.value) {
    const q = search.value.toLowerCase()
    r = r.filter((c) => {
      const label = getAppartementLabel(c.appartement).toLowerCase()
      return c.appartement?.toString().includes(q) || label.includes(q)
    })
  }
  if (filterStatus.value) r = r.filter(c => filterStatus.value === 'active' ? isActive(c) : !isActive(c))
  return r
})

const loadContrats = async () => {
  try { loading.value = true; const res = await contratApi.getAll(); contrats.value = res.data || [] }
  catch { contrats.value = [] } finally { loading.value = false }
}

const loadAppartements = async () => {
  try {
    const [appRes, batRes] = await Promise.all([appartementApi.getAll(), batimentApi.getAll()])
    appartements.value = appRes.data || []
    batiments.value = batRes.data || []
  } catch {
    appartements.value = []
    batiments.value = []
  }
}

const toInputDate = (dateValue) => {
  if (!dateValue) return ''
  const date = new Date(dateValue)
  if (Number.isNaN(date.getTime())) return ''
  return date.toISOString().slice(0, 10)
}

const resetForm = () => {
  form.value = { dateDebut: '', dateFin: '', montantBrut: '', montantCharges: '', appartement: '' }
  isEditMode.value = false
  editingContratId.value = null
}

const closeModal = () => {
  showModal.value = false
  resetForm()
}

const openCreateModal = () => {
  resetForm()
  showModal.value = true
}

const openEditModal = (contrat) => {
  if (!contrat?.id) {
    alert('Impossible de modifier ce contrat: identifiant introuvable.')
    return
  }

  isEditMode.value = true
  editingContratId.value = contrat.id
  form.value = {
    dateDebut: toInputDate(contrat.dateDebut),
    dateFin: toInputDate(contrat.dateFin),
    montantBrut: contrat.montantBrut ?? '',
    montantCharges: contrat.montantCharges ?? '',
    appartement: contrat.appartement != null ? String(contrat.appartement) : '',
  }
  showModal.value = true
}

const submit = async () => {
  if (appartementOptions.value.length === 0) {
    alert('Aucun appartement disponible. Créez un appartement avant d\'ajouter un contrat.')
    return
  }

  try {
    saving.value = true
    const payload = {
      dateDebut: new Date(form.value.dateDebut).toISOString(),
      dateFin: new Date(form.value.dateFin).toISOString(),
      montantBrut: +form.value.montantBrut,
      montantCharges: +form.value.montantCharges,
      appartement: +form.value.appartement,
    }

    if (isEditMode.value && editingContratId.value) {
      await contratApi.update(editingContratId.value, payload)
    } else {
      await contratApi.create(payload)
    }

    closeModal()
    await Promise.all([loadContrats(), loadAppartements()])
  } catch {
    alert(isEditMode.value ? 'Erreur lors de la mise à jour.' : 'Erreur lors de la création.')
  } finally { saving.value = false }
}

watch(showModal, (isOpen) => {
  if (!isOpen) resetForm()
})

onMounted(async () => {
  await Promise.all([loadContrats(), loadAppartements()])
})
</script>

<style scoped>
@import '@/assets/page.css';
.modal-hint { font-size: 0.82rem; color: var(--text-secondary); line-height: 1.55; }
</style>
