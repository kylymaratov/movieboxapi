import * as cheerio from 'cheerio';
import { Movie, MovieEpisode, MovieQuality, MovieSeason } from '@/parser/types';

export interface FormatHomeResult {
    result: {
        title: string;
        details: Movie[];
    }[];
}

export interface FromatSeasonsResult {
    seasons: MovieSeason[];
    description: string;
}

export class TsKgFormat {
    formatHome(body: any, baseUrl: string): FormatHomeResult {
        const $ = cheerio.load(body);

        const container = $('.app-shows-container > .app-shows-item-full');

        const response: FormatHomeResult = {
            result: [
                {
                    title: 'Новинки',
                    details: [],
                },
                {
                    title: 'Популярные',
                    details: [],
                },
            ],
        };

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
                response.result[0].details.push({
                    movie_id,
                    poster_url,
                    title,
                    genre,
                    country,
                    year,
                });
            } else {
                response.result[1].details.push({
                    movie_id,
                    poster_url,
                    title,
                    genre,
                    country,
                    year,
                });
            }
        });

        return response;
    }

    formatEpisodes(body: any): FromatSeasonsResult {
        const $ = cheerio.load(body);

        const section = $('.app-show-seasons-section-full');

        const seasons: MovieSeason[] = [];

        const description = $('.app-show-description').text();

        section.each((i, item) => {
            const season_id = i + 1;

            const episodes: MovieEpisode[] = [];

            $(item)
                .find('.app-show-season-collapse > table > tbody > tr')
                .each((j, episode) => {
                    const episode_source_id = $(episode)
                        .find('td > span')
                        .eq(1)
                        .find('a')
                        .attr('id')
                        ?.split('-')
                        .pop();

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

                    const quality = episode_title
                        .split(' ')[0]
                        .trim() as MovieQuality;

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
        return { seasons, description };
    }
}
