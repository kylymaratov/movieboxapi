import axios from 'axios';

type RequestMethods = 'GET' | 'POST' | 'PUT' | 'PATCH' | 'DELETE';

const createRequest = (baseURL: string) => {
    const request = axios.create({ baseURL });

    request.interceptors.request.use(
        (request) => {
            return request;
        },
        (error) => {
            return Promise.reject(error);
        }
    );

    request.interceptors.response.use(
        (response) => {
            return response;
        },
        (error) => {
            return Promise.reject(error);
        }
    );

    return async <T>(
        url: string = '',
        method: RequestMethods = 'GET',
        data: any = {},
        headers: any = {}
    ) => {
        return await request<T>({
            url,
            method,
            data: JSON.stringify(data),
            headers,
        });
    };
};

export default createRequest;
