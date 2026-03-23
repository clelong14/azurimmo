import { createRouter, createWebHistory } from 'vue-router'
import LoginView         from '@/views/Login.vue'
import AccueilView       from '@/views/Accueil.vue'
import BatimentsView     from '@/views/Batiments.vue'
import AppartementsView  from '@/views/Appartements.vue'
import ContratsView      from '@/views/Contrats.vue'
import LocatairesView    from '@/views/Locataires.vue'
import InterventionsView from '@/views/Interventions.vue'
import AdminView         from '@/views/Admin.vue'
import RegisterView from '@/views/Register.vue'

const routes = [
  { path: '/login', component: LoginView, meta: { public: true } },
  { path: '/',              component: AccueilView,       meta: { requiresAuth: true } },
  { path: '/batiments',     component: BatimentsView,     meta: { requiresAuth: true } },
  { path: '/appartements',  component: AppartementsView,  meta: { requiresAuth: true } },
  { path: '/contrats',      component: ContratsView,      meta: { requiresAuth: true } },
  { path: '/locataires',    component: LocatairesView,    meta: { requiresAuth: true } },
  { path: '/interventions', component: InterventionsView, meta: { requiresAuth: true } },
  { path: '/admin',         component: AdminView,         meta: { requiresAuth: true, requiresAdmin: true } },
  { path: '/register', component: RegisterView, meta: { public: true } },
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, _from) => {
  const user = JSON.parse(localStorage.getItem('azurimmo-user') || 'null')
  if (to.meta.public && user)                              return '/'
  if (to.meta.requiresAuth && !user)                       return '/login'
  if (to.meta.requiresAdmin && user?.role !== 'admin')     return '/'
  return true
})

export default router