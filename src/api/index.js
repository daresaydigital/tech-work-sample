import client from "./client";

const BASE_URL = `https://api.themoviedb.org/3/movie/popular`;

const getImages = async () => {
  return await client(BASE_URL);
};

export { getImages };
