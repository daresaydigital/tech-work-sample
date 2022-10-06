import { useEffect, useState } from "react";
import { getMovies } from "../api";

import Box from "@mui/material/Box";
import Grid from "@mui/material/Unstable_Grid2";

import MovieCard from "./MovieCard";

export const MovieGrid = () => {
  const [movies, setMovies] = useState();

  async function requestMovies() {
    const moviesResponse = await getMovies();
    setMovies(moviesResponse);
  }

  useEffect(() => {
    requestMovies();
  }, []);

  return (
    <Box sx={{ flexGrow: 1 }}>
      <Grid container spacing={{ xs: 2, md: 3 }}>
        {movies &&
          movies.results.map((movie, index) => (
            <Grid xs={6} sm={4} md={3} key={index}>
              <MovieCard
                key={`${index}-${movie.original_title}`}
                movieInformation={movie}
                index={index}
              />
            </Grid>
          ))}
      </Grid>
    </Box>
  );
};
