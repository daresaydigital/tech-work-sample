import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import CircularProgress from "@mui/material/CircularProgress";

export const MovieDetailedInformation = ({ movieInfromation }) => {
  const { vote_average, release_date, genres, runtime } = movieInfromation;

  const movieRunTimeInHours = (runtime) => {
    const hours = runtime / 60;
    const remainingHours = Math.floor(hours);
    const minutes = (hours - remainingHours) * 60;
    const remainingMinutes = Math.round(minutes);

    return `${remainingHours}h ${remainingMinutes} m`;
  };

  const CircularProgressTextStyling = {
    top: 0,
    left: 0,
    bottom: 0,
    right: 0,
    position: "absolute",
    display: "flex",
    alignItems: "center",
    justifyContent: "center",
  };

  const typographyStyling = {
    display: "flex",
    flexDirection: { xs: "column", md: "row" },
    whiteSpace: "pre",
    span: {
      fontWeight: "bold",
    },
  };

  return (
    <>
      <Box
        sx={{
          display: "flex",
          flexWrap: "wrap",
          flexShrink: 0,
          alignItems: "flex-start",
          gap: { xs: "30px", lg: "40px" },
          mb: 5,
        }}
      >
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          <Typography
            variant="h5"
            component="p"
            gutterBottom
            sx={typographyStyling}
          >
            <span>Vote average: </span>
          </Typography>

          <Box sx={{ position: "relative", display: "inline-flex" }}>
            <CircularProgress
              size={110}
              variant="determinate"
              value={vote_average * 10}
            />
            <Box sx={CircularProgressTextStyling}>
              <Typography variant="h5" component="p">
                {`${(vote_average * 10).toFixed(2)}%`}
              </Typography>
            </Box>
          </Box>
        </Box>
        <Box
          sx={{
            display: "flex",
            flexDirection: "column",
            flexWrap: "wrap",
            flexShrink: 0,
            gap: { xs: 0, lg: "10px" },
          }}
        >
          <Typography
            variant="h5"
            component="p"
            gutterBottom
            sx={typographyStyling}
          >
            Release date:
            <span> {release_date}</span>
          </Typography>
          <Typography
            variant="h5"
            component="p"
            gutterBottom
            sx={typographyStyling}
          >
            Genres:
            <span> {genres.map((item) => item.name).join(", ")}</span>
          </Typography>
          <Typography
            variant="h5"
            component="p"
            gutterBottom
            sx={typographyStyling}
          >
            Duration:
            <span> {movieRunTimeInHours(runtime)}</span>
          </Typography>
        </Box>
      </Box>
    </>
  );
};
