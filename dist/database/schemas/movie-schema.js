'use strict';
Object.defineProperty(exports, '__esModule', { value: true });
const mongoose_1 = require('mongoose');
const MovieSchema = new mongoose_1.Schema({
    movie_id: String,
    country: String,
    genre: String,
    title: String,
    year: String,
    poster_url: String,
});
