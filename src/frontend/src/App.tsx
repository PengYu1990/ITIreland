import { Footer } from "./components/Footer";
import "./App.css";
import { NavBar } from "./components/NavBar";

import { Grid } from "@mantine/core";
import Category from "./components/Category";

export default function App() {
  return (
    <>
      <NavBar />
      <Grid>
        <Grid.Col span={9}>
          <Category />
        </Grid.Col>
        <Grid.Col span={3}></Grid.Col>
      </Grid>
      <Footer />
    </>
  );
}
