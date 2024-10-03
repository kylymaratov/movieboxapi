import { Express } from 'express';
import bodyParser from 'body-parser';
import { serverRequestTime } from '@/server/server-request-time';
import api from '@/api/api';

export const setServerMiddlewares = (app: Express) => {
    app.use(bodyParser.json());
    app.use(serverRequestTime);
    app.use('/api/', api);
};
