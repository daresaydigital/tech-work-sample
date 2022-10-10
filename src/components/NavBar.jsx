import React from "react";

import MobileMenu from "./MobileMenu";

import { AppBar, Box } from "@mui/material";
import { Container } from "@mui/system";
import Typography from "@mui/material/Typography";

import { NavLink } from "react-router-dom";

export const NavBar = () => {
  const headerData = [
    {
      label: "Popular movies",
      path: "/",
    },
    {
      label: "Top rated movies",
      path: "/top_rated",
    },
  ];

  const navBarStyling = {
    flexGrow: 1,

    a: {
      textDecoration: "none",
      color: "white",
      margin: "10px",
      fontSize: { xs: "20px", md: "26px" },

      "&:hover": {
        textDecoration: "underline",
      },
    },
  };

  let activeStyle = {
    color: "black",
    textDecoration: "underline",
  };

  return (
    <Box sx={navBarStyling}>
      <AppBar position="static" color="primary">
        <Container sx={{ mt: 4, mb: 4, display: "flex" }}>
          <Typography
            variant="h5"
            noWrap
            sx={{ flexGrow: 1, color: "#032541" }}
          >
            The movie db
          </Typography>
          <Box
            sx={{
              display: { xs: "none", md: "block" },
            }}
          >
            {headerData.map(({ label, path }) => (
              <React.Fragment key={path}>
                <NavLink
                  to={path}
                  style={({ isActive }) => (isActive ? activeStyle : undefined)}
                  end
                >
                  {label}
                </NavLink>
              </React.Fragment>
            ))}
          </Box>
          <Box
            sx={{
              display: { xs: "block", md: "none" },
            }}
          >
            <MobileMenu headerListData={headerData} />
          </Box>
        </Container>
      </AppBar>
    </Box>
  );
};
