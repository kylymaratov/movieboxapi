'use strict';
var __importDefault =
    (this && this.__importDefault) ||
    function (mod) {
        return mod && mod.__esModule ? mod : { default: mod };
    };
Object.defineProperty(exports, '__esModule', { value: true });
exports.setServerCors = void 0;
const cors_1 = __importDefault(require('cors'));
const setServerCors = (app) => {
    app.use(
        (0, cors_1.default)({
            origin: '*',
            methods: '*',
            credentials: true,
        })
    );
};
exports.setServerCors = setServerCors;
