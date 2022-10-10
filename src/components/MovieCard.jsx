import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";
import { CardActionArea } from "@mui/material";

import { Link } from "react-router-dom";

const MovieCard = ({ movieInformation }) => {
  const { poster_path, title } = movieInformation;

  const linkStyling = {
    height: "100%",

    a: {
      height: "100%",
      textDecoration: "none",
      color: "black",
      "&:hover": {
        textDecoration: "underline",
      },
    },
  };

  return (
    <Card sx={linkStyling}>
      <CardActionArea
        component="div"
        disableRipple
        sx={{
          height: "100%",
        }}
      >
        <Link to={`/movie/${title}`} state={movieInformation}>
          <CardMedia
            component="img"
            height="auto"
            image={`https://image.tmdb.org/t/p/original${poster_path}`}
            alt={`${title} poster`}
          />
          <CardContent>
            <Typography variant="h4">{title}</Typography>
          </CardContent>
        </Link>
      </CardActionArea>
    </Card>
  );
};

export default MovieCard;
