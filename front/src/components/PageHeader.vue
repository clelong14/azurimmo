<template>
  <div class="page-header fade-in">
    <div class="header-left">
      <div class="header-badge">{{ icon }}</div>
      <div>
        <h1 class="header-title">{{ title }}</h1>
        <p v-if="subtitle" class="header-subtitle">{{ subtitle }}</p>
      </div>
    </div>
    <slot />
  </div>
</template>

<script setup>
defineProps({ title: String, subtitle: String, icon: String })
</script>

<style scoped>
.page-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
  margin-bottom: 2rem;
  flex-wrap: wrap;
}

.header-left {
  display: flex;
  align-items: center;
  gap: 1rem;
}

.header-badge {
  width: 52px;
  height: 52px;
  background: var(--accent-bg);
  border: 1px solid var(--accent);
  border-radius: 14px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1.4rem;
  flex-shrink: 0;
}

.header-title {
  font-size: 1.9rem;
  font-weight: 800;
  color: var(--text-primary);
  letter-spacing: -0.03em;
  line-height: 1.1;
}

.header-subtitle {
  font-size: 0.875rem;
  color: var(--text-muted);
  margin-top: 0.2rem;
}
</style>