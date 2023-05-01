import { useLocation, useParams } from "react-router-dom";
import PostMeta from "./components/shared/PostMeta";
import usePost from "./hooks/usePost";
import { Box, createStyles, Grid, rem, Text } from "@mantine/core";
import PublishBox from "./components/sidebar/PublishBox";
import ToTop from "./components/shared/ToTop";
import { useEffect } from "react";

const useStyles = createStyles((theme) => ({
  detail: {
    paddingLeft: rem(20),
    paddingRight: rem(20),
    paddingTop: rem(10),
    paddingBottom: rem(10),
    backgroundColor: "white",
    marginBottom: rem(10),
  },

  heading: {
    textDecoration: "none",
    color: theme.colors.dark[4],
    fontFamily: "Pathway Extreme",
    padding: 0,
  },
  content: {
    textDecoration: "none",
    fontFamily: "Pathway Extreme",
    color: theme.colors.dark[4],
    marginTop: rem(20),
    marginBottom: rem(30),
  },
}));

const PostDetail = () => {
  const { classes } = useStyles();
  let { id } = useParams();
  const { data, error, isLoading } = usePost(id);

  const { pathname } = useLocation();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, [pathname]);

  return data ? (
    <>
      <Grid grow>
        <Grid.Col md={9} sm={12}>
          <Box className={classes.detail}>
            <h3 className={classes.heading}>{data.title}</h3>
            <PostMeta post={data} />
            <div dangerouslySetInnerHTML={{ __html: data.content }} />
          </Box>
        </Grid.Col>

        <Grid.Col md={3} sm={12}>
          <PublishBox />
        </Grid.Col>
      </Grid>
      <ToTop />
    </>
  ) : (
    <></>
  );
};

export default PostDetail;
