'use strict';
var __awaiter =
    (this && this.__awaiter) ||
    function (thisArg, _arguments, P, generator) {
        function adopt(value) {
            return value instanceof P
                ? value
                : new P(function (resolve) {
                      resolve(value);
                  });
        }
        return new (P || (P = Promise))(function (resolve, reject) {
            function fulfilled(value) {
                try {
                    step(generator.next(value));
                } catch (e) {
                    reject(e);
                }
            }
            function rejected(value) {
                try {
                    step(generator['throw'](value));
                } catch (e) {
                    reject(e);
                }
            }
            function step(result) {
                result.done
                    ? resolve(result.value)
                    : adopt(result.value).then(fulfilled, rejected);
            }
            step(
                (generator = generator.apply(thisArg, _arguments || [])).next()
            );
        });
    };
var __importDefault =
    (this && this.__importDefault) ||
    function (mod) {
        return mod && mod.__esModule ? mod : { default: mod };
    };
Object.defineProperty(exports, '__esModule', { value: true });
require('./path-register');
const express_1 = __importDefault(require('express'));
const server_cors_1 = require('@/server/server-cors');
const server_middlewares_1 = require('@/server/server-middlewares');
const server_runner_1 = __importDefault(require('@/server/server-runner'));
function bootstrap() {
    return __awaiter(this, void 0, void 0, function* () {
        try {
            const app = (0, express_1.default)();
            (0, server_cors_1.setServerCors)(app);
            (0, server_middlewares_1.setServerMiddlewares)(app);
            yield (0, server_runner_1.default)(app);
        } catch (error) {
            console.log(`Server failed with error: ${error}`);
        }
    });
}
bootstrap();
