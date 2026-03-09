<script setup>
</script>

<template>
  <div id="app-layout">
    <nav class="main-nav">
      <router-link to="/" class="nav-link">Accueil</router-link>
      <router-link to="/batiments" class="nav-link">Bâtiments</router-link>
    </nav>

    <router-view />
  </div>
</template>

<style scoped>
@import "@/assets/main.css";
</style>
