<script setup>
import { ref, onMounted } from 'vue'
import api from '@/service/api'

const batiments = ref([])
const loading = ref(true)

onMounted(async () => {
  try {
    const response = await api.get('batiments/all')
    console.log("Données reçues par Vue :", response.data)
    batiments.value = response.data

  } catch (error) {
    console.error("Erreur de connexion :", error)
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div class="page-container">
    <header class="page-header">
      <h1>AZURIMMO</h1>
      <p v-if="!loading">Gestion des {{ batiments.length }} bâtiments</p>
    </header>

    <main class="content">
      <div v-if="loading">Chargement en cours...</div>

      <div v-else-if="batiments.length > 0" class="grid">
        <div v-for="(b, index) in batiments" :key="index" class="card">
          <h3>{{ b.ville || 'Bâtiment sans nom' }}</h3>
          <p>{{ b.adresse }}</p>
          <small>{{ b.appartements ? b.appartements.length : 0 }} appartement(s)</small>
        </div>
      </div>

      <div v-else>
        <p>Aucun bâtiment trouvé. Vérifiez la console (F12).</p>
      </div>
    </main>
  </div>
</template>

<style>
@import "@/assets/main.css";
</style>