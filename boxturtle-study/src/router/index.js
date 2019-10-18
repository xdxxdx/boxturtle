import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/Main.vue'
import TagAdd from '../views/TagAdd.vue'
import TagList from '../views/TagList.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'main',
    component: Main,
    children:[
      {
        path:'/tag/add',
        name:'tagAdd',
        component:TagAdd,
      },
      {
        path:'/tag/list',
        name:'tagList',
        component:TagList,
      },
    ]
  },
 
]

const router = new VueRouter({
  routes
})

export default router
