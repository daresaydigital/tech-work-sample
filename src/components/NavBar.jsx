import { Container } from "@mui/material";
import { Link } from "react-router-dom";

export const NavBar = () => (
  <Container>
    <Link to={`/`}>Lint</Link>
  </Container>
);
