import { NavBar } from "./NavBar";
import { Outlet } from "react-router-dom";

export const PageLayout = () => {
  return (
    <>
      <NavBar />
      <Outlet />
    </>
  );
};
