import { config } from 'dotenv';

config();

interface ServerEnv {}

export const serverEnv = process.env;
