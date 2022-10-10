import { useEffect, useState } from "react";

import MovieCard from "./MovieCard";

import { Container } from "@mui/material";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Unstable_Grid2";
import Button from "@mui/material/Button";
import CircularProgress from "@mui/material/CircularProgress";

export const MovieGrid = ({ fetchMovies, fetchMoviePath }) => {
  const [movies, setMovies] = useState();
  const [pageNumber, setPageNumber] = useState(1);
  const [maxPageNumber, setMaxPageNumber] = useState();
  const [loading, setLoading] = useState(false);
  const [fetchPrevPath, setfetchPrevPath] = useState();

  const requestMovies = async (pageNumber) => {
    setLoading(true);
    const moviesResponse = await fetchMovies(pageNumber, fetchMoviePath);
    setLoading(false);
    setMaxPageNumber(moviesResponse.total_pages);
    if (movies && fetchMoviePath === fetchPrevPath) {
      setMovies((movies) => [...movies, ...moviesResponse.results]);
    } else {
      setMovies(moviesResponse.results);
      setfetchPrevPath(fetchMoviePath);
    }
  };

  useEffect(() => {
    requestMovies(pageNumber);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [pageNumber, fetchMoviePath]);

  const loadMore = () => setPageNumber((pageNumber) => pageNumber + 1);

  return (
    <Container>
      <Box sx={{ flexGrow: 1, mt: 7 }}>
        {loading && pageNumber === 1 ? (
          <Grid container justifyContent="center">
            <CircularProgress
              size={50}
              thickness={4}
              value={100}
              disableShrink
            />
          </Grid>
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
          <Grid container justifyContent="center" sx={{ m: 5, mb: 7 }}>
            <CircularProgress
              size={50}
              thickness={4}
              value={100}
              disableShrink
            />
          </Grid>
        )}
        {pageNumber <= maxPageNumber && !loading && (
          <Grid container justifyContent="center" sx={{ m: 5, mb: 7 }}>
            <Button
              variant="contained"
              onClick={loadMore}
              sx={{ fontSize: "20px" }}
            >
              Load more
            </Button>
          </Grid>
        )}
      </Box>
    </Container>
  );
};
