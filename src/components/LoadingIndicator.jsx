import Grid from "@mui/material/Unstable_Grid2";
import CircularProgress from "@mui/material/CircularProgress";

export const LoadingIndicator = () => {
  return (
    <Grid container justifyContent="center">
      <CircularProgress size={50} thickness={4} value={100} disableShrink />
    </Grid>
  );
};
