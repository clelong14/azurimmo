import { createRouter, createWebHistory } from 'vue-router'
import AccueilView from "@/views/Accueil.vue";
import BatimentView from "@/views/Batiments.vue";

const routes = [
  { path: '/', redirect: '/accueil' },
  { path: '/accueil', component: AccueilView },
  { path: '/batiments', name: 'Batiments', component: BatimentView }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
