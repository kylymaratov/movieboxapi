import { NextFunction, Request, Response } from 'express';

export const serverRequestTime = (
    req: Request,
    res: Response,
    next: NextFunction
) => {
    const start = Date.now();
    const path = req.url;

    res.on('finish', () => {
        const duration = Date.now() - start;
        const contentType = res.getHeaders()['content-type'];
        const contentLength = res.getHeaders()['content-length'];

        console.info(
            `${req.method} ${path} - ${contentType} | ${contentLength} bytes - ${duration}ms | ${res.statusCode}`
        );
    });

    next();
};
