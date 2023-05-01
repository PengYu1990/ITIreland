import { useParams } from "react-router-dom";
import PostMeta from "./components/shared/PostMeta";
import usePost from "./hooks/usePost";
import { Box, createStyles, rem, Text } from "@mantine/core";

const useStyles = createStyles((theme) => ({
  postItem: {
    paddingLeft: rem(10),
    paddingRight: rem(10),
    paddingTop: rem(2),
    paddingBottom: rem(5),
    borderBottom: `${rem(1)} solid ${
      theme.colorScheme === "dark" ? theme.colors.dark[5] : theme.colors.gray[1]
    }`,
    backgroundColor: "white",
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
  return (
    data && (
      <Box className={classes.postItem}>
        <h3 className={classes.heading}>{data.title}</h3>
        <PostMeta post={data} />
        <Text className={classes.content}>{data.content}</Text>
      </Box>
    )
  );
};

export default PostDetail;
