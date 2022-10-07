import "./App.css";
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
        element: <MovieGrid />,
      },
      {
        path: "movie/:movieTitle",
        element: <MovieDetailed />,
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
