

const MovieCard = ({movieInformation, index}) => {
    const {poster_path, original_title} = movieInformation;

    return (
        <div key={`${index}-${original_title}`}>
            <img
                style={{width: "150px"}}
                src={`https://image.tmdb.org/t/p/original${poster_path}`} 
                alt={`${original_title} poster`}/>
            <p>{original_title}</p>
        </div>
    )
}

export default MovieCard;