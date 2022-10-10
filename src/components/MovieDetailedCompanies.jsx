import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Avatar from "@mui/material/Avatar";

export const MovieDetailedCompanies = ({ companies }) => {
  return (
    <>
      <Typography
        variant="h4"
        component="p"
        sx={{
          mb: 3,
          fontWeight: "bold",
        }}
      >
        Production companies:
      </Typography>
      <Box
        sx={{
          display: "flex",
          flexDirection: "column",
          mb: 3,
        }}
      >
        {companies.map((compaine) => (
          <Box
            key={compaine}
            sx={{
              display: "flex",
              flexWrap: "wrap",
              alignItems: "center",
              gap: "30px",
              mb: 4,
            }}
          >
            {compaine.logo_path ? (
              <Box>
                <img
                  style={{
                    maxHeight: "50px",
                    maxWidth: "100%",
                  }}
                  src={`https://image.tmdb.org/t/p/original${compaine.logo_path}`}
                  alt={`${compaine.logo_path} poster`}
                />
              </Box>
            ) : (
              <Avatar
                sx={{
                  height: "60px",
                  width: "60px",
                }}
              >
                {compaine.name.slice(0, 2)}
              </Avatar>
            )}
            <Typography variant="h5" component="h5">
              {compaine.name}
            </Typography>
          </Box>
        ))}
      </Box>
    </>
  );
};
