import { createRouter, createWebHistory } from 'vue-router'
import AccueilView from "@/views/Accueil.vue";

const routes = [
  { path: '/', name: 'Accueil', component: AccueilView },
  { path: '/batiments', name: 'Batiments', component: AccueilView }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
