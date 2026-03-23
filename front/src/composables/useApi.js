import axios from 'axios'

// Create the instance
const api = axios.create({
    baseURL: import.meta.env.VITE_API_URL,
    headers: {
        'Content-Type': 'application/json'
    }
})

// Optional: Add a request interceptor to attach tokens
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

// Your existing exports (all now benefit from the interceptor)
export const batimentApi = {
    getAll: () => api.get('/batiments/all'),
    create: (data) => api.post('/batiments', data), // Removed trailing slash for consistency
}

// ... etc for your other exports
export const appartementApi = {
    getAll: () => api.get('/appartements/all'),
    create: (data) => api.post('/appartements', data),
}
export const contratApi = {
    getAll: () => api.get('/contrats/all'),
    create: (data) => api.post('/contrats', data),
}
export const locataireApi = {
    getAll: () => api.get('/locataires/all'),
    create: (data) => api.post('/locataires', data),
}
export const interventionApi = {
    getAll: () => api.get('/interventions/all'),
    create: (data) => api.post('/interventions', data),
}
export const loyerApi = {
    getAll: () => api.get('/loyers/all'),
    create: (data) => api.post('/loyers', data),
}