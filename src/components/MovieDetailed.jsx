import { useEffect, useState } from "react";

import { getSpecificMovie } from "../api";
import { MovieDetailedCompanies } from "./MovieDetailedCompanies";
import { MovieDetailedInformation } from "./MovieDetailedInformation";

import { Container } from "@mui/material";
import Box from "@mui/material/Box";
import Grid from "@mui/material/Unstable_Grid2";
import Typography from "@mui/material/Typography";
import CircularProgress from "@mui/material/CircularProgress";

import { useLocation } from "react-router-dom";

const MovieDetailed = () => {
  const {
    state: { id },
  } = useLocation();

  console.log(id);

  const [movie, setMovie] = useState();
  const [loading, setLoading] = useState(false);

  const requestMovies = async () => {
    setLoading(true);
    const movieResponse = await getSpecificMovie(id);
    setLoading(false);
    setMovie(movieResponse);
  };

  useEffect(() => {
    requestMovies();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return (
    <Box
      sx={{
        pt: { xs: 3, md: 2 },
        display: "flex",
        background: {
          md: `url(
          https://image.tmdb.org/t/p/original${movie?.poster_path})`,
        },
        backgroundRepeat: "no-repeat",
        backgroundSize: "100%",
        backgroundPosition: "center top",
        boxShadow: "inset 0 0 0 1000px rgba(255,255,255,.87)",
      }}
    >
      <Container>
        {loading ? (
          <Grid container justifyContent="center" sx={{ mt: 7 }}>
            <CircularProgress
              size={50}
              thickness={4}
              value={100}
              disableShrink
            />
          </Grid>
        ) : (
          <Grid
            container
            spacing={{ xs: 2, sm: 3, md: 6 }}
            sx={{
              pt: { xs: 1, sm: 3 },
            }}
          >
            <Grid item xs={12} sm={4}>
              <img
                style={{ width: "100%" }}
                src={`https://image.tmdb.org/t/p/original${movie?.poster_path}`}
                alt={`${movie?.original_title} poster`}
              />
            </Grid>

            <Grid item xs={12} sm={8}>
              <Typography
                variant="h1"
                component="h1"
                sx={{
                  fontWeight: "bold",
                  mb: 2,
                }}
              >
                {movie?.title}
              </Typography>

              <Typography
                variant="h2"
                component="p"
                sx={{
                  mb: 4,
                  fontSize: "2rem",
                  fontStyle: "italic",
                  fontWeight: "400",
                }}
              >
                {movie?.tagline}
              </Typography>

              {movie && <MovieDetailedInformation movieInfromation={movie} />}

              <Typography
                variant="h4"
                component="p"
                sx={{
                  mb: 3,
                  fontWeight: "bold",
                }}
              >
                Overview:
              </Typography>

              <Typography
                variant="h5"
                component="h3"
                sx={{
                  mb: 6,
                }}
              >
                {movie?.overview}
              </Typography>

              {movie?.production_companies && (
                <MovieDetailedCompanies
                  companies={movie?.production_companies}
                />
              )}
            </Grid>
          </Grid>
        )}
      </Container>
    </Box>
  );
};

export default MovieDetailed;
