import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../components/LoginView.vue'
import MainView from '../components/MainView.vue'
import HttpApiTest from '../components/HttpApiTest.vue'

const routes = [
  {
    path: '/',
    name: 'main',
    component: MainView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/httpApiTest',
    name: 'httpApiTest',
    component: HttpApiTest
  }
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    ...routes,
    {
      path: '/:pathMatch(.*)',
      redirect: '/'
    }
  ]
})

export default router
