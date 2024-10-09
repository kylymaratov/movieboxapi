import { Router } from 'express';
import tskgApi from '@/api/tskg/tskg-api';
import { MainService } from '@/services/main-service';

const api = Router();
const mainService = new MainService();

api.get('/menu', mainService.getMenu);

api.use(`/tskg/`, tskgApi);

export default api;
