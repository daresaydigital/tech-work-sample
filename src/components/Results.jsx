import { useEffect, useState } from 'react';
import { getImages } from '../api';

import MovieCard from './MovieCard';

const Results = () => {

    const [movies, setMovies] = useState();

    async function requestMovies() {
        const moviesResponse = await getImages();
        setMovies(moviesResponse);
    }

    useEffect(() => {
        requestMovies();
    }, []);

    return (
        <div>
            {movies && movies.results.map((movie, index) => {
                return (
                    <MovieCard key={`${index}-${movie.original_title}`} movieInformation={movie} index={index} />
                )
            })}
        </div>
    )
}

export default Results;
