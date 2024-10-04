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
exports.TskgSerivce = void 0;
const parser_1 = __importDefault(require('@/parser/tskg/parser'));
class TskgSerivce {
    constructor() {
        this.tsKgParser = new parser_1.default();
        this.getHome = (req, res, next) =>
            __awaiter(this, void 0, void 0, function* () {
                try {
                    const home = yield this.tsKgParser.getHome();
                    res.status(200).json(home.data);
                } catch (error) {
                    next(error);
                }
            });
        this.getEpisodes = (req, res, next) =>
            __awaiter(this, void 0, void 0, function* () {
                try {
                    const { movie_id } = req.body;
                    const seasons = yield this.tsKgParser.fetchEpisodes(
                        String(movie_id)
                    );
                    res.status(200).json(seasons);
                } catch (error) {
                    next(error);
                }
            });
        this.watchEpisode = (req, res, next) =>
            __awaiter(this, void 0, void 0, function* () {
                try {
                    const { movie_id, episode_source_id } = req.body;
                    const episode = yield this.tsKgParser.fetchEpisode(
                        movie_id,
                        episode_source_id
                    );
                    res.status(200).json(episode);
                } catch (error) {
                    next(error);
                }
            });
    }
}
exports.TskgSerivce = TskgSerivce;
