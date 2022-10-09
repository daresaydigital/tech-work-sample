import Card from "@mui/material/Card";
import CardContent from "@mui/material/CardContent";
import CardMedia from "@mui/material/CardMedia";
import Typography from "@mui/material/Typography";

import { CardActionArea } from "@mui/material";

import { Link } from "react-router-dom";

const MovieCard = ({ movieInformation }) => {
  const { poster_path, original_title } = movieInformation;

  const linkStyling = {
    height: "100%",

    a: {
      textDecoration: "none",
      "&:hover": {
        textDecoration: "underline",
      },
    },
  };

  return (
    <Card sx={linkStyling}>
      <Link to={`/movie/${original_title}`} state={movieInformation}>
        <CardActionArea>
          <CardMedia
            component="img"
            height="auto"
            image={`https://image.tmdb.org/t/p/original${poster_path}`}
            alt={`${original_title} poster`}
          />
          <CardContent>
            <Typography variant="h4">{original_title}</Typography>
          </CardContent>
        </CardActionArea>
      </Link>
    </Card>
  );
};

export default MovieCard;
