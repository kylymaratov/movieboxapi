'use strict';
Object.defineProperty(exports, '__esModule', { value: true });
exports.TskgMiddleware = void 0;
const express_validator_1 = require('express-validator');
class TskgMiddleware {
    getEpisodesMiddleware() {
        return [
            (0, express_validator_1.body)('movie_id').notEmpty(),
            this.checkValidationResult,
        ];
    }
    watchEpisode() {
        return [
            (0, express_validator_1.body)('movie_id').notEmpty(),
            (0, express_validator_1.body)('episode_source_id').notEmpty(),
            this.checkValidationResult,
        ];
    }
    checkValidationResult(req, res, next) {
        const errors = (0, express_validator_1.validationResult)(req);
        if (!errors.isEmpty()) {
            return res.status(400).json({ errors: errors.array() });
        }
        next();
    }
}
exports.TskgMiddleware = TskgMiddleware;
