import { Link, useRouteError } from "react-router-dom";

import { Container } from "@mui/material";

const ErrorPage = () => {
  const error = useRouteError();
  console.error(error);

  return (
    <Container>
      <div>
        <h1>Oops!</h1>
        <p>Sorry, an unexpected error has occurred.</p>
        <p>
          <i>{error.statusText || error.message}</i>
        </p>
      </div>

      <Link to={"/"}>Go back to start page!</Link>
    </Container>
  );
};

export default ErrorPage;
