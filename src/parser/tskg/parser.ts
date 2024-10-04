import createRequest from '@/parser/request';
import { FormatHomeResult, TsKgFormat } from '@/parser/tskg/format';
import { Movie, MovieSeason, TsKgMovieData } from '@/parser/types';

interface ParseHomeResult {
    baseUrl: string;
    data: FormatHomeResult;
}

class TsKgParser {
    private baseUrl = 'https://www.ts.kg/';
    private request = createRequest(this.baseUrl);
    private formatter = new TsKgFormat();

    constructor() {}

    async getHome(): Promise<ParseHomeResult> {
        try {
            const response = await this.request();

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
    }

    async fetchEpisodes(movie_id: string): Promise<MovieSeason[]> {
        try {
            const response = await this.request(movie_id);

            const formatedData = this.formatter.formatEpisodes(response.data);

            return formatedData;
        } catch (error) {
            throw error;
        }
    }

    async fetchEpisode(
        movie: Movie,
        episode_source_id: string
    ): Promise<TsKgMovieData> {
        try {
            const url = `/show/episode/episode.json?episode=${episode_source_id}`;
            const response = await this.request<TsKgMovieData>(
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
    }
}

export default TsKgParser;
