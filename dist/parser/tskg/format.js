'use strict';
var __createBinding =
    (this && this.__createBinding) ||
    (Object.create
        ? function (o, m, k, k2) {
              if (k2 === undefined) k2 = k;
              var desc = Object.getOwnPropertyDescriptor(m, k);
              if (
                  !desc ||
                  ('get' in desc
                      ? !m.__esModule
                      : desc.writable || desc.configurable)
              ) {
                  desc = {
                      enumerable: true,
                      get: function () {
                          return m[k];
                      },
                  };
              }
              Object.defineProperty(o, k2, desc);
          }
        : function (o, m, k, k2) {
              if (k2 === undefined) k2 = k;
              o[k2] = m[k];
          });
var __setModuleDefault =
    (this && this.__setModuleDefault) ||
    (Object.create
        ? function (o, v) {
              Object.defineProperty(o, 'default', {
                  enumerable: true,
                  value: v,
              });
          }
        : function (o, v) {
              o['default'] = v;
          });
var __importStar =
    (this && this.__importStar) ||
    function (mod) {
        if (mod && mod.__esModule) return mod;
        var result = {};
        if (mod != null)
            for (var k in mod)
                if (
                    k !== 'default' &&
                    Object.prototype.hasOwnProperty.call(mod, k)
                )
                    __createBinding(result, mod, k);
        __setModuleDefault(result, mod);
        return result;
    };
Object.defineProperty(exports, '__esModule', { value: true });
exports.TsKgFormat = void 0;
const cheerio = __importStar(require('cheerio'));
class TsKgFormat {
    formatHome(body, baseUrl) {
        const $ = cheerio.load(body);
        const container = $('.app-shows-container > .app-shows-item-full');
        const new_movies = [];
        const popular_movies = [];
        container.each((i, item) => {
            const movie_id = $(item).find('a').attr('href');
            const poster_url = baseUrl + $(item).find('a > img').attr('src');
            const title = $(item).find('.app-shows-card-title').text().trim();
            let genre = $(item).find('.app-shows-card-tooltip').text().trim();
            const country = $(item)
                .find('.app-shows-card-tooltip > .app-shows-card-flag')
                .attr('alt');
            const genres = genre.split(',');
            const year = genres[0];
            genre = genres.slice(1, genres.length).toString().trim();
            if (!movie_id || !poster_url) return;
            if (i % 2) {
                popular_movies.push({
                    movie_id,
                    poster_url,
                    title,
                    genre,
                    country,
                    year,
                });
            } else {
                new_movies.push({
                    movie_id,
                    poster_url,
                    title,
                    genre,
                    country,
                    year,
                });
            }
        });
        return { new_movies, popular_movies };
    }
    formatEpisodes(body) {
        const $ = cheerio.load(body);
        const section = $('.app-show-seasons-section-full');
        const seasons = [];
        section.each((i, item) => {
            const season_id = i + 1;
            const episodes = [];
            $(item)
                .find('.app-show-season-collapse > table > tbody > tr')
                .each((j, episode) => {
                    var _a;
                    const episode_source_id =
                        (_a = $(episode)
                            .find('td > span')
                            .eq(1)
                            .find('a')
                            .attr('id')) === null || _a === void 0
                            ? void 0
                            : _a.split('-').pop();
                    if (!episode_source_id) return;
                    const episode_id = j + 1;
                    const episode_title = $(episode)
                        .find('td > span > a')
                        .text()
                        .trim();
                    const duration = $(episode)
                        .find('td > span > small')
                        .text()
                        .replace('| ', '');
                    const quality = episode_title.split(' ')[0].trim();
                    episodes.push({
                        episode_id,
                        episode_title,
                        quality,
                        duration,
                        episode_source_id,
                    });
                });
            seasons.push({ season_id, episodes });
        });
        return seasons;
    }
}
exports.TsKgFormat = TsKgFormat;
