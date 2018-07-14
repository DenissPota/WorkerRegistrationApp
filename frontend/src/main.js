import Vue from 'vue'
import App from './App'
import router from './router'
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'

Vue.use(Vuetify, {
    theme: {
        primary: '#1976D2',
        secondary: '#009688',
        accent: '#82B1FF',
        error: '#FF5252',
        info: '#2196F3',
        success: '#4CAF50',
        warning: '#FFC107'
    }
});


let vm = new Vue({
    el: '#app',
    router,
    components: {App},
    template: '<App/>'
});

