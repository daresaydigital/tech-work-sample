import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import CardMedia from '@mui/material/CardMedia';
import Typography from '@mui/material/Typography';
import { CardActionArea } from '@mui/material';

const MovieCard = ({movieInformation, index}) => {
    const {poster_path, original_title, overview} = movieInformation;

    return (        
        <Card>
            <CardActionArea>
            <CardMedia
                component="img"
                height="auto"
                image={`https://image.tmdb.org/t/p/original${poster_path}`}
                alt={`${original_title} poster`}
            />
            <CardContent>
                <Typography gutterBottom variant="h3" component="div">
                { original_title }
                </Typography>
                <Typography variant="body2" color="text.secondary">
                { overview }
                </Typography>
            </CardContent>
            </CardActionArea>
        </Card>
    )
}

export default MovieCard;