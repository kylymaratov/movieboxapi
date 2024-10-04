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
const request_1 = __importDefault(require('@/parser/request'));
const format_1 = require('@/parser/tskg/format');
class TsKgParser {
    constructor() {
        this.baseUrl = 'https://www.ts.kg/';
        this.request = (0, request_1.default)(this.baseUrl);
        this.formatter = new format_1.TsKgFormat();
    }
    getHome() {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const response = yield this.request();
                const formatedData = this.formatter.formatHome(
                    response.data,
                    this.baseUrl
                );
                return {
                    baseUrl: this.baseUrl,
                    data: formatedData,
                };
            } catch (error) {
                throw error;
            }
        });
    }
    fetchEpisodes(movie_id) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const response = yield this.request(movie_id);
                const formatedData = this.formatter.formatEpisodes(
                    response.data
                );
                return formatedData;
            } catch (error) {
                throw error;
            }
        });
    }
    fetchEpisode(movie, episode_source_id) {
        return __awaiter(this, void 0, void 0, function* () {
            try {
                const url = `/show/episode/episode.json?episode=${episode_source_id}`;
                const response = yield this.request(
                    url,
                    'GET',
                    {},
                    {
                        'x-requested-with': 'XMLHttpRequest',
                    }
                );
                return response.data;
            } catch (error) {
                throw error;
            }
        });
    }
}
exports.default = TsKgParser;
