import { Footer } from "./components/Footer";
import "./App.css";
import { NavBar } from "./components/NavBar";

import {
  AppShell,
  Container,
  Grid,
  MediaQuery,
  createStyles,
  rem,
} from "@mantine/core";
import PostList from "./components/PostList";
import ToTop from "./components/ToTop";

const useStyles = createStyles((theme) => ({
  content: {
    borderBottom: `${rem(1)} solid ${
      theme.colorScheme === "dark" ? theme.colors.dark[5] : theme.colors.gray[2]
    }`,
    backgroundColor: "#f2f2f2",
  },
}));

export default function App() {
  const { classes } = useStyles();
  return (
    <>
      <AppShell header={<NavBar />} padding={0}>
        <Grid className={classes.content} grow>
          <Grid.Col md={9} sm={12}>
            <Container>
              <PostList />
            </Container>
          </Grid.Col>
          <Grid.Col md={3} sm={12}></Grid.Col>
        </Grid>
        <Footer />

        <ToTop />
      </AppShell>
    </>
  );
}
