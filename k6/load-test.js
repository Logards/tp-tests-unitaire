import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 5,              // 5 virtual users
  duration: '5s',      // run for 5 seconds
};

export default function () {
  const url = 'http://localhost:8080/books';
  
  // 1. Add a new book (POST /books)
  const payload = JSON.stringify({
    author: `Author ${__VU}-${__ITER}`,
    name: `Book ${__VU}-${__ITER}`,
  });
  
  const params = {
    headers: {
      'Content-Type': 'application/json',
    },
  };
  
  const postRes = http.post(url, payload, params);
  check(postRes, {
    'POST status is 201': (r) => r.status === 201,
    'POST returns correct author': (r) => JSON.parse(r.body).author.startsWith('Author'),
  });
  
  sleep(1);
  
  // 2. Retrieve books (GET /books)
  const getRes = http.get(url);
  check(getRes, {
    'GET status is 200': (r) => r.status === 200,
    'GET returns a list of books': (r) => Array.isArray(JSON.parse(r.body)),
  });
  
  sleep(1);
}
