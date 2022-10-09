import client from "./client";

const BASE_URL = `https://api.themoviedb.org/3/`;

const getMovies = async (pageNumber = 1, fetchMoviePath) => {
  return await client(`${BASE_URL}movie/${fetchMoviePath}?page=${pageNumber}`);
};

export { getMovies };
