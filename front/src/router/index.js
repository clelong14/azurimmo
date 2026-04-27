import { createRouter, createWebHistory } from 'vue-router'
import LoginView         from '@/views/Login.vue'
import AccueilView       from '@/views/Accueil.vue'
import BatimentsView     from '@/views/Batiments.vue'
import AppartementsView  from '@/views/Appartements.vue'
import ContratsView      from '@/views/Contrats.vue'
import LocatairesView    from '@/views/Locataires.vue'
import InterventionsView from '@/views/Interventions.vue'
import AdminView         from '@/views/Admin.vue'
import RegisterView      from '@/views/Register.vue'
import AccountSettingsView from '@/views/AccountSettings.vue'
import PublicHomeView    from '@/views/PublicHome.vue'
import ServicesView      from '@/views/ServicesView.vue'
import PrivacyPolicy     from '@/views/PrivacyPolicy.vue'
import TermsOfUse        from '@/views/TermsOfUse.vue'
import ContactPage       from '@/views/ContactPage.vue'

const routes = [
  { path: '/login', component: LoginView, meta: { public: true } },
  { path: '/register', component: RegisterView, meta: { public: true } },

  // 1. La page d'atterrissage pour les visiteurs (Non connectés)
  { path: '/accueil', component: PublicHomeView, meta: { requiresAuth: false, isPublic: true } },

  // 2. Les pages d'infos (Rendues publiques : requiresAuth à false)
  // Dans router/index.js
  {
    path: '/services',
    component: ServicesView,
    meta: { requiresAuth: false, isPublic: true } // Doit être à false
  },
  { path: '/confidentialite', component: PrivacyPolicy, meta: { requiresAuth: false, isPublic: true } },
  { path: '/conditions', component: TermsOfUse, meta: { requiresAuth: false, isPublic: true } },
  { path: '/contact', component: ContactPage, meta: { requiresAuth: false, isPublic: true } },

  { path: '/account', component: AccountSettingsView, meta: { requiresAuth: true } },
  { path: '/', redirect: '/accueil' },

  // 3. Le vrai tableau de bord (Une fois connecté)
  { path: '/tableau-de-bord', component: AccueilView, meta: { requiresAuth: true, requiresAdminOrGestionnaire: true } },

  // 4. Les modules de gestion
  { path: '/batiments',     component: BatimentsView,     meta: { requiresAuth: true, requiresAdminOrGestionnaire: true } },
  { path: '/appartements',  component: AppartementsView,  meta: { requiresAuth: true, requiresAdminOrGestionnaire: true, allowedRoles: ['ADMIN', 'GESTIONNAIRE'] } },
  { path: '/contrats',      component: ContratsView,      meta: { requiresAuth: true, requiresAdminOrGestionnaire: true } },
  { path: '/locataires',    component: LocatairesView,    meta: { requiresAuth: true, requiresAdminOrGestionnaire: true } },
  { path: '/interventions', component: InterventionsView, meta: { requiresAuth: true, requiresAdminOrGestionnaire: true } },
  { path: '/admin',         component: AdminView,         meta: { requiresAuth: true, requiresAdmin: true } },
]

const router = createRouter({ history: createWebHistory(), routes })

router.beforeEach((to, _from) => {
  const user = JSON.parse(localStorage.getItem('azurimmo-user') || 'null')
  
  // Si pas de user et route protégée
  if (to.meta.requiresAuth && !user) {
    return '/accueil'
  }
  
  // Si user existe, bloquer uniquement les pages d'authentification
  if (user && (to.path === '/login' || to.path === '/register')) {
    if (user?.roleLibelle === 'USER') {
      return '/accueil'
    }
    return '/tableau-de-bord'
  }
  
  // Protéger les routes admin uniquement
  if (to.meta.requiresAdmin && user?.roleLibelle !== 'ADMIN') {
    return '/accueil'
  }
  
  // Protéger les routes admin/gestionnaire
  if (to.meta.requiresAdminOrGestionnaire && user?.roleLibelle === 'USER') {
    return '/accueil'
  }
  
  // Restriction explicite par rôle pour certaines routes (ex: Appartements).
  if (to.meta.allowedRoles && !to.meta.allowedRoles.includes(user?.roleLibelle)) {
    return '/accueil'
  }

  // Redirection par défaut selon le rôle pour les routes publiques
  if (user && (to.path === '/' || to.path === '' || to.path === '/tableau-de-bord')) {
    if (user.roleLibelle === 'USER') {
      return '/accueil'
    }
  }
  
  return true
})

export default router