import "bootstrap/dist/css/bootstrap.css"
import "bootstrap-icons/font/bootstrap-icons.css";
import './assets/main.css'
import "vue-select/dist/vue-select.css";
import  vSelect from "vue-select";
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

const app = createApp(App)
app.component('v-select',vSelect)
app.use(router)

app.mount('#app')
