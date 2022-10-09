import { AppBar, Box } from "@mui/material";
import { Container } from "@mui/system";

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
      padding: "10px",
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
        <Container sx={{ mt: 4, mb: 4 }}>
          {headerData.map(({ label, path, index }) => (
            <NavLink
              key={index}
              to={path}
              style={({ isActive }) => (isActive ? activeStyle : undefined)}
              end
            >
              {label}
            </NavLink>
          ))}
        </Container>
      </AppBar>
    </Box>
  );
};
