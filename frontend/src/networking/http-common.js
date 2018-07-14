import axios from 'axios'
// For local setup
export const AXIOS = axios.create({
  baseURL: `http://localhost:8088`,
  headers: {
    'Access-Control-Allow-Origin': 'http://localhost:8080',
  }
});

// export const AXIOS = axios.create({
//   baseURL: `http://193.40.11.213`,
// });
