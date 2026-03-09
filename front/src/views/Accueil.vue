<script setup>
import { ref, onMounted, computed } from 'vue'
import api from '@/service/api'

const batiments = ref([])
const loading = ref(true)

// Statistiques calculées
const totalAppartements = computed(() => {
  return batiments.value.reduce((acc, b) => acc + (b.appartements?.length || 0), 0)
})

onMounted(async () => {
  try {
    const response = await api.get('batiments/all')
    batiments.value = response.data
  } catch (error) {
    console.error("Erreur :", error)
  } finally {
    loading.value = false
  }
})
</script>

<template>
  <div class="home-container">
    <header class="hero">
      <div class="hero-text">
        <h1>Bienvenue sur <span class="brand">AzurImmo</span></h1>
        <p>Votre assistant de gestion immobilière intelligent.</p>
      </div>
      <div class="hero-actions">
        <button class="btn-primary">Ajouter un bâtiment</button>
        <button class="btn-outline">Voir les rapports</button>
      </div>
    </header>

    <section class="stats-row">
      <div class="stat-card">
        <span class="stat-icon">🏢</span>
        <div class="stat-info">
          <span class="stat-value">{{ batiments.length }}</span>
          <span class="stat-label">Bâtiments</span>
        </div>
      </div>
      <div class="stat-card">
        <span class="stat-icon">🔑</span>
        <div class="stat-info">
          <span class="stat-value">{{ totalAppartements }}</span>
          <span class="stat-label">Appartements</span>
        </div>
      </div>
      <div class="stat-card">
        <span class="stat-icon">📈</span>
        <div class="stat-info">
          <span class="stat-value">98%</span>
          <span class="stat-label">Occupation</span>
        </div>
      </div>
    </section>

    <main class="main-content">
      <div class="section-header">
        <h2>Vos Propriétés Récentes</h2>
        <router-link to="/batiments" class="link">Voir tout →</router-link>
      </div>

      <div v-if="loading" class="loader">Chargement...</div>

      <div v-else-if="batiments.length > 0" class="recent-grid">
        <div v-for="b in batiments.slice(0, 3)" :key="b.id" class="building-item">
          <div class="building-img">🏢</div>
          <div class="building-details">
            <h3>{{ b.ville }}</h3>
            <p>{{ b.adresse }}</p>
            <span class="badge">{{ b.appartements?.length || 0 }} unités</span>
          </div>
        </div>
      </div>

      <div v-else class="empty-state">
        <p>Commencez par ajouter votre premier bâtiment !</p>
      </div>
    </main>
  </div>
</template>

<style>
@import "@/assets/main.css";
</style>