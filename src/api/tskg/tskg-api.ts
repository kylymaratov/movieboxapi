import { Router } from 'express';
import { TskgSerivce } from '@/services/tskg-serivce';
import { TskgMiddleware } from '@/middlewares/tskg-middleware';

const tskgApi = Router();
const tsKgService = new TskgSerivce();
const tsKgMiddleware = new TskgMiddleware();

tskgApi.get('/home', tsKgService.getHome);
tskgApi.get('/search', tsKgMiddleware.search(), tsKgService.search);
tskgApi.post(
    '/episodes',
    tsKgMiddleware.getEpisodesMiddleware(),
    tsKgService.getEpisodes
);
tskgApi.post('/watch', tsKgMiddleware.watchEpisode(), tsKgService.watchEpisode);

export default tskgApi;
