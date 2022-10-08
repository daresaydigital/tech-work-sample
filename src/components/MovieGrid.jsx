import { useEffect, useState } from "react";
import { getMovies } from "../api";

import Box from "@mui/material/Box";
import Grid from "@mui/material/Unstable_Grid2";
import Button from "@mui/material/Button";
import CircularProgress from "@mui/material/CircularProgress";

import MovieCard from "./MovieCard";

export const MovieGrid = () => {
  const [movies, setMovies] = useState();
  const [pageNumber, setPageNumber] = useState(1);
  const [maxPageNumber, setMaxPageNumber] = useState();
  const [loading, setLoading] = useState(false);

  async function requestMovies(pageNumber) {
    setLoading(true);
    const moviesResponse = await getMovies(pageNumber);
    setLoading(false);
    setMaxPageNumber(moviesResponse.total_pages);
    if (movies) {
      setMovies((movies) => [...movies, ...moviesResponse.results]);
    } else {
      setMovies(moviesResponse.results);
    }
  }

  useEffect(() => {
    requestMovies(pageNumber);
  }, [pageNumber]);

  const loadMore = () => setPageNumber((pageNumber) => pageNumber + 1);

  return (
    <Box sx={{ flexGrow: 1 }}>
      {loading && pageNumber === 1 ? (
        <CircularProgress size={40} thickness={4} value={100} disableShrink />
      ) : (
        <Grid container spacing={{ xs: 2, md: 3 }}>
          {movies &&
            movies.map((movie, index) => (
              <Grid xs={6} sm={4} md={3} key={index}>
                <MovieCard
                  key={`${index}-${movie.original_title}`}
                  movieInformation={movie}
                  index={index}
                />
              </Grid>
            ))}
        </Grid>
      )}

      {loading && (pageNumber !== 1 || pageNumber <= maxPageNumber) && (
        <CircularProgress size={40} thickness={4} value={100} disableShrink />
      )}
      {pageNumber <= maxPageNumber && !loading && (
        <Button variant="contained" onClick={loadMore}>
          Load more
        </Button>
      )}
    </Box>
  );
};
