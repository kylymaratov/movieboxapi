import { Router } from 'express';
import tskgApi from '@/api/tskg/tskg-api';

const api = Router();

api.use(`/tskg/`, tskgApi);

export default api;
