import { Footer } from "./components/Footer";
import "./App.css";
import { NavBar } from "./components/NavBar";

import { Container, Grid, MediaQuery, createStyles, rem } from "@mantine/core";
import Category from "./components/Category";
import PostList from "./components/PostList";

const useStyles = createStyles((theme) => ({
  content: {
    marginTop: rem(10),
    paddingBottom: rem(20),
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
      <NavBar />
      <Grid className={classes.content} grow>
        <Grid.Col md={9} sm={12}>
          <Container>
            <Category />
            <PostList />
          </Container>
        </Grid.Col>
        <Grid.Col md={3} sm={12}></Grid.Col>
      </Grid>
      <Footer />
    </>
  );
}
