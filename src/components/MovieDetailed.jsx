import { useLocation } from "react-router-dom";

const MovieDetailed = () => {
  const location = useLocation();
  const { original_title } = location.state;

  return (
    <>
      <h1>{original_title}</h1>
    </>
  );
};

export default MovieDetailed;
