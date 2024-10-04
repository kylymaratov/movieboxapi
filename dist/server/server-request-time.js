'use strict';
Object.defineProperty(exports, '__esModule', { value: true });
exports.serverRequestTime = void 0;
const serverRequestTime = (req, res, next) => {
    const start = Date.now();
    const path = req.url;
    res.on('finish', () => {
        const duration = Date.now() - start;
        const contentType = res.getHeaders()['content-type'];
        const contentLength = res.getHeaders()['content-length'];
        console.info(
            `${req.method} ${path} - ${contentType} | ${contentLength} bytes - ${duration}ms | ${res.statusCode}`
        );
    });
    next();
};
exports.serverRequestTime = serverRequestTime;
