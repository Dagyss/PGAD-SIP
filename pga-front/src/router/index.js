"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var vue_router_1 = require("vue-router");
var Home_vue_1 = require("../components/Home.vue");
var routes = [
    {
        path: '/',
        name: 'home',
        component: Home_vue_1.default
    }
];
var router = (0, vue_router_1.createRouter)({
    //   history: createWebHistory(import.meta.env.BASE_URL),
    routes: routes
});
exports.default = router;
