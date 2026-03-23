<template>
  <div class="page-container">

    <div class="page-header">
      <div>
        <h1 class="page-title">Bâtiments</h1>
        <p class="page-subtitle">{{ batiments.length }} bâtiment{{ batiments.length !== 1 ? 's' : '' }} enregistré{{ batiments.length !== 1 ? 's' : '' }}</p>
      </div>
      <button class="btn btn-primary" @click="showModal = true">
        <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M12 5v14M5 12h14"/></svg>
        Ajouter un bâtiment
      </button>
    </div>

    <!-- Contextual help -->
    <div class="info-callout fade-in">
      <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M12 16v-4"/><path d="M12 8h.01"/></svg>
      <span>Un <strong>bâtiment</strong> représente un immeuble ou une maison. Il regroupe un ou plusieurs appartements. Commencez ici avant d'ajouter des logements.</span>
    </div>

    <div class="toolbar fade-in" style="animation-delay:.05s">
      <div class="search-wrap">
        <svg class="search-icon" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.35-4.35"/></svg>
        <input v-model="search" type="text" class="search-input" placeholder="Rechercher par adresse ou ville…" />
      </div>
    </div>

    <div v-if="loading" class="loading-state"><div class="spinner"></div><span>Chargement…</span></div>

    <div v-else-if="batiments.length === 0" class="empty-state">
      <svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" opacity=".3"><rect width="16" height="20" x="4" y="2" rx="2"/><path d="M9 22v-4h6v4"/><path d="M8 6h.01"/><path d="M16 6h.01"/><path d="M12 6h.01"/></svg>
      <h3>Aucun bâtiment pour le moment</h3>
      <p>Cliquez sur <strong>« Ajouter un bâtiment »</strong> pour commencer à renseigner votre patrimoine immobilier.</p>
      <button class="btn btn-primary" style="margin-top:.75rem" @click="showModal = true">+ Ajouter un bâtiment</button>
    </div>

    <div v-else-if="filtered.length === 0" class="empty-state">
      <h3>Aucun résultat pour « {{ search }} »</h3>
      <p>Essayez un autre terme de recherche.</p>
    </div>

    <div v-else class="table-container fade-in" style="animation-delay:.1s">
      <table class="data-table">
        <thead>
          <tr>
            <th>Adresse</th>
            <th>Ville</th>
            <th>Appartements</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(b, i) in filtered" :key="i">
            <td><strong>{{ b.adresse }}</strong></td>
            <td class="col-muted">{{ b.ville }}</td>
            <td>
              <span class="badge badge-neutral">{{ b.appartements?.length ?? 0 }} logement{{ b.appartements?.length !== 1 ? 's' : '' }}</span>
            </td>
            <td>
              <router-link to="/appartements" class="btn btn-ghost" style="font-size:0.78rem;padding:0.3rem 0.65rem">
                Voir les appartements →
              </router-link>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <AppModal v-model="showModal" title="Ajouter un bâtiment">
      <p class="modal-hint">Renseignez l'adresse complète et la ville de l'immeuble. Vous pourrez ensuite y rattacher des appartements.</p>
      <form @submit.prevent="submit" class="form-grid" style="margin-top:1rem">
        <AppField label="Adresse complète" v-model="form.adresse" placeholder="Ex : 12 Rue de la Paix" required />
        <AppField label="Ville" v-model="form.ville" placeholder="Ex : Paris" required />
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
import { batimentApi } from '@/composables/useApi'

const batiments = ref([])
const loading = ref(true)
const search = ref('')
const showModal = ref(false)
const saving = ref(false)
const form = ref({ adresse: '', ville: '' })

const filtered = computed(() => {
  const q = search.value.toLowerCase()
  return batiments.value.filter(b => b.adresse?.toLowerCase().includes(q) || b.ville?.toLowerCase().includes(q))
})

const load = async () => {
  try { loading.value = true; const r = await batimentApi.getAll(); batiments.value = r.data }
  catch { batiments.value = [] } finally { loading.value = false }
}

const submit = async () => {
  try {
    saving.value = true
    await batimentApi.create(form.value)
    form.value = { adresse: '', ville: '' }
    showModal.value = false
    await load()
  } catch (error) {
    const message = error?.response?.data?.message || error?.response?.data || error?.message || 'Erreur lors de la création.'
    alert(message)
  } finally { saving.value = false }
}

onMounted(load)
</script>

<style scoped>
@import '@/assets/page.css';
.modal-hint { font-size: 0.82rem; color: var(--text-secondary); line-height: 1.55; }
</style>
