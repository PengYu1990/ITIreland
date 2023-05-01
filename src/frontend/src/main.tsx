import * as React from "react";
import * as ReactDOM from "react-dom/client";
import App from "./App";
import "./App.css";
import { MantineProvider } from "@mantine/core";
import { Notifications } from "@mantine/notifications";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import Index from ".";
import PostDetail from "./PostDetail";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      {
        path: "/",
        element: <Index />,
      },
      {
        path: "/post/:id",
        element: <PostDetail />,
      },
    ],
  },
]);

const rootElement = document.getElementById("root");
ReactDOM.createRoot(rootElement).render(
  <React.StrictMode>
    <MantineProvider>
      <Notifications />
      <RouterProvider router={router} />
    </MantineProvider>
  </React.StrictMode>
);
