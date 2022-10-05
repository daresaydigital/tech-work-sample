import { useLocation } from 'react-router-dom';

const MovieCard = () => {
    const location = useLocation();
    const { original_title } = location.state
    
    return (
        <>
            <h1>{original_title}</h1>
        </>
    );
};

export default MovieCard;