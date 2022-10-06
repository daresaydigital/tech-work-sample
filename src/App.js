import logo from "./logo.svg";
import "./App.css";

import ErrorPage from "./components/ErrorPage";
import MovieDetailed from "./components/MovieDetailed";
import { MovieGrid } from "./components/MovieGrid";

import Container from "@mui/material/Container";

import { createBrowserRouter, RouterProvider } from "react-router-dom";

const router = createBrowserRouter([
  {
    path: "/",
    element: <MovieGrid />,
    errorElement: <ErrorPage />,
  },
  {
    path: "movie/:movieTitle",
    element: <MovieDetailed />,
  },
]);

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
      <Container>
        <RouterProvider router={router} />
      </Container>
    </div>
  );
}

export default App;
