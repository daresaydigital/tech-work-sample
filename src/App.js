import { getMovies } from "./api/";

import ErrorPage from "./components/ErrorPage";
import MovieDetailed from "./components/MovieDetailed";
import { LoadingIndicator } from "./components/LoadingIndicator";
import { PageLayout } from "./components/PageLayout";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { MovieGrid } from "./components/MovieGrid";

const router = createBrowserRouter([
  {
    path: "/",
    element: <PageLayout />,
    errorElement: <ErrorPage />,
    children: [
      {
        path: "/",
        element: <MovieGrid fetchMovies={getMovies} fetchMoviePath="popular" />,
      },
      {
        path: "movie/:movieTitle",
        element: <MovieDetailed />,
      },
      {
        path: "/top_rated",
        element: (
          <MovieGrid fetchMovies={getMovies} fetchMoviePath="top_rated" />
        ),
      },
    ],
  },
]);

function App() {
  return (
    <RouterProvider router={router} fallbackElement={<LoadingIndicator />} />
  );
}

export default App;
