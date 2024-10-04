import { Express } from 'express';
import { serverEnv } from '@/server/server-env';
import { connect } from 'mongoose';

const beforeRun = async () => {
    await connect(serverEnv.MONGO_URL || '', {});
};

const afterRun = (PORT: number) => {
    console.info(`Server running on port: ${PORT}`);
};

const serverRun = async (app: Express) => {
    await beforeRun();

    const PORT: number = Number(serverEnv.PORT);

    app.listen(PORT, () => afterRun(PORT));
};

export default serverRun;
