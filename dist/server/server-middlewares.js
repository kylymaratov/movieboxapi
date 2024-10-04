'use strict';
var __importDefault =
    (this && this.__importDefault) ||
    function (mod) {
        return mod && mod.__esModule ? mod : { default: mod };
    };
Object.defineProperty(exports, '__esModule', { value: true });
exports.setServerMiddlewares = void 0;
const body_parser_1 = __importDefault(require('body-parser'));
const server_request_time_1 = require('@/server/server-request-time');
const api_1 = __importDefault(require('@/api/api'));
const setServerMiddlewares = (app) => {
    app.use(body_parser_1.default.json());
    app.use(server_request_time_1.serverRequestTime);
    app.use('/api/', api_1.default);
};
exports.setServerMiddlewares = setServerMiddlewares;
