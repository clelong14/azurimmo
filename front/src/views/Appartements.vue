<template>
  <div class="page-container">

    <div class="page-header">
      <div>
        <h1 class="page-title">Appartements</h1>
        <p class="page-subtitle">
          {{ filtered.length }} appartement{{ filtered.length !== 1 ? 's' : '' }} affiché{{ filtered.length !== 1 ? 's' : '' }}
          <span v-if="selectedBatimentId !== null">pour <strong>{{ selectedBatimentLabel }}</strong></span>
        </p>
      </div>
      <button class="btn btn-primary" @click="showModal = true">
        <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M12 5v14M5 12h14"/></svg>
        Ajouter un appartement
      </button>
    </div>

    <div class="info-callout fade-in">
      <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M12 16v-4"/><path d="M12 8h.01"/></svg>
      <span>
        Les <strong>appartements</strong> sont rattachés à un bâtiment via son ID.
        <template v-if="selectedBatimentId !== null">Vous consultez actuellement uniquement le bâtiment sélectionné.</template>
      </span>
    </div>

    <div class="toolbar fade-in" style="animation-delay:.05s">
      <div class="search-wrap">
        <svg class="search-icon" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.35-4.35"/></svg>
        <input v-model="search" type="text" class="search-input" placeholder="Rechercher par numéro ou description…" />
      </div>
      <select v-model="filterPieces" class="select-filter">
        <option value="">Toutes les pièces</option>
        <option value="1">1 pièce (studio)</option>
        <option value="2">2 pièces</option>
        <option value="3">3 pièces</option>
        <option value="4">4 pièces et +</option>
      </select>
      <button v-if="selectedBatimentId !== null" type="button" class="btn btn-ghost" @click="clearBatimentFilter">
        Retirer le filtre bâtiment
      </button>
    </div>

    <div v-if="loading" class="loading-state"><div class="spinner"></div><span>Chargement…</span></div>

    <div v-else-if="appartements.length === 0" class="empty-state">
      <svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" opacity=".3"><path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"/><polyline points="9 22 9 12 15 12 15 22"/></svg>
      <h3>Aucun appartement pour le moment</h3>
      <p>Ajoutez vos premiers logements en cliquant sur <strong>« Ajouter un appartement »</strong>.</p>
      <button class="btn btn-primary" style="margin-top:.75rem" @click="showModal = true">+ Ajouter un appartement</button>
    </div>

    <div v-else-if="filtered.length === 0" class="empty-state">
      <h3>Aucun résultat</h3>
      <p v-if="selectedBatimentId !== null">Aucun appartement trouvé pour ce bâtiment avec les filtres actuels.</p>
      <p v-else>Modifiez votre recherche ou le filtre de pièces.</p>
    </div>

    <div v-else class="table-container fade-in" style="animation-delay:.1s">
      <table class="data-table">
        <thead>
          <tr>
            <th>N° appartement</th>
            <th>Surface</th>
            <th>Pièces</th>
            <th>Bâtiment</th>
            <th>Description</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(a, i) in filtered" :key="i">
            <td><strong>Appt. n°{{ a.numero }}</strong></td>
            <td class="col-mono">{{ a.surface }} m²</td>
            <td><span class="badge badge-neutral">{{ a.nbPieces }} pièce{{ a.nbPieces !== 1 ? 's' : '' }}</span></td>
            <td class="col-muted">{{ getBatimentNom(a.batiment) }}</td>
            <td class="col-muted" style="max-width:200px;overflow:hidden;text-overflow:ellipsis;white-space:nowrap">{{ a.description || '—' }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <AppModal v-model="showModal" title="Ajouter un appartement">
      <p class="modal-hint">Renseignez les caractéristiques du logement et sélectionnez le <strong>bâtiment</strong> auquel il appartient.</p>
      <form @submit.prevent="submit" class="form-grid" style="margin-top:1rem">
        <div class="form-row">
          <AppField label="Numéro" v-model="form.numero" type="number" min="0" step="1" placeholder="Ex : 669" required />
          <AppField label="Nombre de pièces" v-model="form.nbPieces" type="number" min="0" step="1" placeholder="Ex : 3" required />
        </div>
        <AppField label="Surface (m²)" v-model="form.surface" type="number" min="0" step="0.1" placeholder="Ex : 62.5" required />
        <AppField label="Description (optionnel)" v-model="form.description" type="textarea" placeholder="Ex : Vue sur cour, 3ème étage, ascenseur…" />
        <AppField label="Bâtiment" v-model="form.batiment" type="select" placeholder="Choisissez un bâtiment" required>
          <option v-for="b in batiments" :key="b.id" :value="b.id">
            {{ b.adresse }}
          </option>
        </AppField>
        <div class="form-actions">
          <button type="button" class="btn-cancel" @click="showModal = false">Annuler</button>
          <button type="submit" class="btn-submit" :disabled="saving || batiments.length === 0">{{ saving ? 'Enregistrement…' : 'Enregistrer' }}</button>
        </div>
      </form>
    </AppModal>

  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import AppModal from '@/components/AppModal.vue'
import AppField from '@/components/AppField.vue'
import { appartementApi, batimentApi } from '@/composables/useApi'

const route = useRoute()
const router = useRouter()

const appartements = ref([])
const batiments = ref([])
const loading = ref(true)
const search = ref('')
const filterPieces = ref('')
const showModal = ref(false)
const saving = ref(false)
const form = ref({ numero: '', nbPieces: '', surface: '', description: '', batiment: '' })

const selectedBatimentId = computed(() => {
  const raw = Array.isArray(route.query.batimentId) ? route.query.batimentId[0] : route.query.batimentId
  if (raw === undefined || raw === null || raw === '') return null
  const parsed = Number(raw)
  return Number.isFinite(parsed) ? parsed : null
})

const selectedBatimentLabel = computed(() => {
  if (selectedBatimentId.value === null) return 'ce bâtiment'
  const batiment = batiments.value.find((b) => +b.id === selectedBatimentId.value)
  return batiment?.adresse || `Bâtiment #${selectedBatimentId.value}`
})

const filtered = computed(() => {
  let r = appartements.value

  if (selectedBatimentId.value !== null) {
    r = r.filter((a) => +a.batiment === selectedBatimentId.value)
  }

  if (search.value) {
    const q = search.value.toLowerCase()
    r = r.filter(a => a.numero?.toString().includes(q) || a.description?.toLowerCase().includes(q))
  }

  if (filterPieces.value) {
    const p = parseInt(filterPieces.value)
    r = r.filter(a => filterPieces.value === '4' ? a.nbPieces >= 4 : a.nbPieces === p)
  }

  return r
})

const clearBatimentFilter = async () => {
  const nextQuery = { ...route.query }
  delete nextQuery.batimentId
  await router.replace({ path: route.path, query: nextQuery })
}

const load = async () => {
  try { loading.value = true; const res = await appartementApi.getAll(); appartements.value = res.data }
  catch { appartements.value = [] } finally { loading.value = false }
}

const loadBatiments = async () => {
  try {
    const res = await batimentApi.getAll()
    batiments.value = res.data || []

    // Si on arrive depuis un bâtiment, préremplir ce bâtiment dans le formulaire d'ajout.
    if (selectedBatimentId.value !== null) {
      form.value.batiment = String(selectedBatimentId.value)
    }
  } catch {
    batiments.value = []
  }
}

const getBatimentNom = (batimentId) => {
  const batiment = batiments.value.find((b) => +b.id === +batimentId)
  return batiment?.adresse || 'Bâtiment inconnu'
}

const submit = async () => {
  if (batiments.value.length === 0) {
    alert('Aucun bâtiment disponible. Créez un bâtiment avant d\'ajouter un appartement.')
    return
  }

  const numero = Number(form.value.numero)
  const nbPieces = Number(form.value.nbPieces)
  const surface = Number(form.value.surface)

  if (!Number.isFinite(numero) || !Number.isFinite(nbPieces) || !Number.isFinite(surface)) {
    alert('Veuillez saisir des valeurs numériques valides.')
    return
  }

  if (numero < 0 || nbPieces < 0 || surface < 0) {
    alert('Les valeurs ne peuvent pas être inférieures à 0.')
    return
  }

  try {
    saving.value = true
    await appartementApi.create({ ...form.value, numero, nbPieces, surface, batiment: +form.value.batiment })
    form.value = { numero: '', nbPieces: '', surface: '', description: '', batiment: '' }
    showModal.value = false; await load()
  } catch { alert('Erreur lors de la création.') } finally { saving.value = false }
}

onMounted(async () => {
  await Promise.all([load(), loadBatiments()])
})
</script>

<style scoped>
@import '@/assets/page.css';
.modal-hint { font-size: 0.82rem; color: var(--text-secondary); line-height: 1.55; }

/* Hide number input spin buttons in the appartement modal fields */
:deep(input[type="number"])::-webkit-outer-spin-button,
:deep(input[type="number"])::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

:deep(input[type="number"]) {
  -moz-appearance: textfield;
}
</style>
