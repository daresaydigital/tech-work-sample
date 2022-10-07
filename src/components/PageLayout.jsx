import { Container, Box } from "@mui/material";
import Grid from "@mui/material/Unstable_Grid2";
import { NavBar } from "./NavBar";
import { Outlet } from "react-router-dom";

export const PageLayout = () => {
  return (
    <>
      <NavBar />
      <Container>
        <Outlet />
      </Container>
    </>
  );
};
