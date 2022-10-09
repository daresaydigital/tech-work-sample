import { Container } from "@mui/material";
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
