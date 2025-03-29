import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createAuth0 } from '@auth0/auth0-vue'

createApp(App)
.use(createAuth0({
    domain: "dev-p568hqvco8r5dv2g.us.auth0.com0",
    clientId: "b7NB5g7Q0eNbyVwCBPBwZWQnebRogh0u",
    authorizationParams: {
        redirect_uri: window.location.origin,
    }
}))
.use(router)
.mount('#app')
