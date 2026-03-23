<template>
  <div class="page-container">

    <div class="page-header">
      <div>
        <h1 class="page-title">Locataires</h1>
        <p class="page-subtitle">{{ locataires.length }} locataire{{ locataires.length !== 1 ? 's' : '' }} enregistré{{ locataires.length !== 1 ? 's' : '' }}</p>
      </div>
      <button class="btn btn-primary" @click="showModal = true">
        <svg width="13" height="13" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5"><path d="M12 5v14M5 12h14"/></svg>
        Ajouter un locataire
      </button>
    </div>

    <div class="info-callout fade-in">
      <svg width="15" height="15" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="12" cy="12" r="10"/><path d="M12 16v-4"/><path d="M12 8h.01"/></svg>
      <span>Les <strong>locataires</strong> sont les personnes qui occupent vos logements. Enregistrez-les ici, puis associez-les à des contrats pour un suivi complet.</span>
    </div>

    <div class="toolbar fade-in" style="animation-delay:.05s">
      <div class="search-wrap">
        <svg class="search-icon" width="14" height="14" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"><circle cx="11" cy="11" r="8"/><path d="m21 21-4.35-4.35"/></svg>
        <input v-model="search" type="text" class="search-input" placeholder="Rechercher par nom, prénom, email ou téléphone…" />
      </div>
    </div>

    <div v-if="loading" class="loading-state"><div class="spinner"></div><span>Chargement…</span></div>

    <div v-else-if="locataires.length === 0" class="empty-state">
      <svg width="36" height="36" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.5" opacity=".3"><path d="M17 21v-2a4 4 0 0 0-4-4H5a4 4 0 0 0-4 4v2"/><circle cx="9" cy="7" r="4"/></svg>
      <h3>Aucun locataire pour le moment</h3>
      <p>Ajoutez vos locataires en cliquant sur <strong>« Ajouter un locataire »</strong>.</p>
      <button class="btn btn-primary" style="margin-top:.75rem" @click="showModal = true">+ Ajouter un locataire</button>
    </div>

    <div v-else-if="filtered.length === 0" class="empty-state">
      <h3>Aucun résultat pour « {{ search }} »</h3>
      <p>Essayez un autre terme de recherche.</p>
    </div>

    <div v-else class="table-container fade-in" style="animation-delay:.1s">
      <table class="data-table">
        <thead>
          <tr>
            <th>Locataire</th>
            <th>Téléphone</th>
            <th>Email</th>
            <th>Date de naissance</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(l, i) in filtered" :key="i">
            <td>
              <div style="display:flex;align-items:center;gap:0.65rem">
                <div class="avatar">{{ initials(l) }}</div>
                <strong>{{ l.prenom }} {{ l.nom }}</strong>
              </div>
            </td>
            <td class="col-mono col-muted">{{ l.telephone }}</td>
            <td class="col-muted">{{ l.adresseMail }}</td>
            <td class="col-mono col-muted">{{ fmt(l.dateNaissance) }}</td>
          </tr>
        </tbody>
      </table>
    </div>

    <AppModal v-model="showModal" title="Ajouter un locataire">
      <p class="modal-hint">Renseignez les informations de contact du locataire. Ces données pourront être associées à un contrat de location.</p>
      <form @submit.prevent="submit" class="form-grid" style="margin-top:1rem">
        <div class="form-row">
          <AppField label="Nom" v-model="form.nom" placeholder="Ex : Dupont" required />
          <AppField label="Prénom" v-model="form.prenom" placeholder="Ex : Marie" required />
        </div>
        <AppField label="Numéro de téléphone" v-model="form.telephone" type="tel" placeholder="Ex : 06 12 34 56 78" required />
        <AppField label="Adresse email" v-model="form.adresseMail" type="email" placeholder="Ex : marie.dupont@email.com" required />
        <AppField label="Date de naissance" v-model="form.dateNaissance" type="date" required />
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
import { locataireApi } from '@/composables/useApi'

const locataires = ref([])
const loading = ref(true)
const search = ref('')
const showModal = ref(false)
const saving = ref(false)
const form = ref({ nom: '', prenom: '', telephone: '', adresseMail: '', dateNaissance: '' })

const initials = (l) => ((l.prenom?.[0] || '') + (l.nom?.[0] || '')).toUpperCase()
const fmt = (d) => d ? new Date(d).toLocaleDateString('fr-FR') : '—'

const filtered = computed(() => {
  const q = search.value.toLowerCase()
  return locataires.value.filter(l =>
    l.nom?.toLowerCase().includes(q) || l.prenom?.toLowerCase().includes(q) ||
    l.adresseMail?.toLowerCase().includes(q) || l.telephone?.includes(q)
  )
})

const load = async () => {
  try { loading.value = true; const r = await locataireApi.getAll(); locataires.value = r.data }
  catch { locataires.value = [] } finally { loading.value = false }
}

const submit = async () => {
  try {
    saving.value = true
    await locataireApi.create({ ...form.value, dateNaissance: new Date(form.value.dateNaissance).toISOString() })
    form.value = { nom: '', prenom: '', telephone: '', adresseMail: '', dateNaissance: '' }
    showModal.value = false; await load()
  } catch { alert('Erreur lors de la création.') } finally { saving.value = false }
}

onMounted(load)
</script>

<style scoped>
@import '@/assets/page.css';
.modal-hint { font-size: 0.82rem; color: var(--text-secondary); line-height: 1.55; }
</style>
