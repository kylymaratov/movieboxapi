import { ServerError } from '@/server/server-error';
import { logDirPath } from '@/server/server-logger';
import { NextFunction, Request, Response } from 'express';
import * as fs from 'fs';

const menu = [
    {
        id: 0,
        title: 'TS.KG',
        icon: '',
        fragment: 'TS.KG',
    },
];

export class CoreService {
    constructor() {}

    getMenu = (req: Request, res: Response, next: NextFunction) => {
        res.status(200).json(menu);
    };

    getLogs = async (req: Request, res: Response, next: NextFunction) => {
        try {
            const { type } = req.query;

            const logs: string = await new Promise((resolve, reject) => {
                fs.readFile(logDirPath + `/${type}.log`, (err, data) => {
                    if (err)
                        return reject(new ServerError('Failed read file', 500));
                    resolve(data.toString());
                });
            });

            const logsArr = logs.split('\n');

            res.status(200).json({ type, logs: logsArr });
        } catch (error) {
            next(error);
        }
    };
}
