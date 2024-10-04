'use strict';
Object.defineProperty(exports, '__esModule', { value: true });
const express_1 = require('express');
const tskg_serivce_1 = require('@/services/tskg-serivce');
const tskg_middleware_1 = require('@/middlewares/tskg-middleware');
const tskgApi = (0, express_1.Router)();
const tsKgService = new tskg_serivce_1.TskgSerivce();
const tsKgMiddleware = new tskg_middleware_1.TskgMiddleware();
tskgApi.get('/home', tsKgService.getHome);
tskgApi.post(
    '/episodes',
    tsKgMiddleware.getEpisodesMiddleware(),
    tsKgService.getEpisodes
);
tskgApi.post('/watch', tsKgMiddleware.watchEpisode(), tsKgService.watchEpisode);
exports.default = tskgApi;
