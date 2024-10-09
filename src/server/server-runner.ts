import { Express } from 'express';
import { serverEnv } from '@/server/server-env';
import { connect } from 'mongoose';

const beforeRun = async () => {
    await connect(serverEnv.env.MONGO_URL || '', {});
};

const afterRun = (PORT: number) => {
    console.info(`Server running on port: ${PORT}`);
};

const serverRun = async (app: Express) => {
    await beforeRun();

    const PORT: number = serverEnv.isProd ? 5001 : 5002;

    app.listen(PORT, () => afterRun(PORT));
};

export default serverRun;
