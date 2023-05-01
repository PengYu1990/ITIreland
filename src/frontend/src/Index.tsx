import { Grid } from "@mantine/core";
import PostList from "./components/PostList";
import PublishBox from "./components/sidebar/PublishBox";

const Index = () => {
  return (
    <Grid grow>
      <Grid.Col md={9} sm={12}>
        <PostList />
      </Grid.Col>

      <Grid.Col md={3} sm={12}>
        <PublishBox />
      </Grid.Col>
    </Grid>
  );
};

export default Index;
