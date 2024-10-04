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
Object.defineProperty(exports, '__esModule', { value: true });
const server_env_1 = require('@/server/server-env');
const mongoose_1 = require('mongoose');
const beforeRun = () =>
    __awaiter(void 0, void 0, void 0, function* () {
        yield (0, mongoose_1.connect)(
            server_env_1.serverEnv.MONGO_URL || '',
            {}
        );
    });
const afterRun = (PORT) => {
    console.info(`Server running on port: ${PORT}`);
};
const serverRun = (app) =>
    __awaiter(void 0, void 0, void 0, function* () {
        yield beforeRun();
        const PORT = Number(server_env_1.serverEnv.PORT) || 5000;
        app.listen(PORT, () => afterRun(PORT));
    });
exports.default = serverRun;
