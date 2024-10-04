import { ServerError } from '@/server/server-error';
import { NextFunction, Request, Response } from 'express';
import { body, query, validationResult } from 'express-validator';

export class TskgMiddleware {
    getEpisodesMiddleware(): any[] {
        return [body('movie_id').notEmpty(), this.checkValidationResult];
    }

    watchEpisode(): any[] {
        return [
            body('movie_id').notEmpty(),
            body('episode_source_id').notEmpty(),
            this.checkValidationResult,
        ];
    }

    private checkValidationResult(
        req: Request,
        res: Response,
        next: NextFunction
    ) {
        const errors = validationResult(req);

        if (!errors.isEmpty()) throw new ServerError('Validation falied', 400);

        next();
    }
}
