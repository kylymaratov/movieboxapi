'use strict';
var __importDefault =
    (this && this.__importDefault) ||
    function (mod) {
        return mod && mod.__esModule ? mod : { default: mod };
    };
Object.defineProperty(exports, '__esModule', { value: true });
const express_1 = require('express');
const tskg_api_1 = __importDefault(require('@/api/tskg/tskg-api'));
const api = (0, express_1.Router)();
api.use(`/tskg/`, tskg_api_1.default);
exports.default = api;
