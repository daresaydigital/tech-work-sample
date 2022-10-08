import client from "./client";

const BASE_URL = `https://api.themoviedb.org/3/`;
const URL_PATH = `movie/popular`;

const getMovies = async (pageNumber = 1) => {
  return await client(`${BASE_URL}${URL_PATH}?page=${pageNumber}`);
};

export { getMovies };
