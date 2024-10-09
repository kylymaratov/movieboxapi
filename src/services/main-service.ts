import { NextFunction, Request, Response } from 'express';

const menu = [
    {
        id: 0,
        title: 'TS.KG',
        fragment: 'TS.KG',
    },
];

export class MainService {
    constructor() {}

    getMenu = (req: Request, res: Response, next: NextFunction) => {
        res.status(200).json(menu);
    };
}
