import { Box, Text, createStyles, rem } from "@mantine/core";
import { Post } from "../hooks/usePosts";
import dayjs from "dayjs";
// extend dayjs
import relativeTime from "dayjs/plugin/relativeTime";
import PostMeta from "./shared/PostMeta";
import { createShortcut } from "../utils/common";
import { Link } from "react-router-dom";
dayjs.extend(relativeTime);

const useStyles = createStyles((theme) => ({
  postItem: {
    paddingLeft: rem(10),
    paddingRight: rem(10),
    paddingTop: rem(2),
    paddingBottom: rem(5),
    borderBottom: `${rem(1)} solid ${
      theme.colorScheme === "dark" ? theme.colors.dark[5] : theme.colors.gray[1]
    }`,
  },

  heading: {
    textDecoration: "none",
    color: theme.colors.dark[4],
    fontFamily: "Pathway Extreme",
    padding: 0,
  },
  summary: {
    textDecoration: "none",
    fontFamily: "Pathway Extreme",
    color: theme.colors.dark[4],
  },
}));

interface Props {
  post: Post;
}

const PostItem = ({ post }: Props) => {
  const { classes } = useStyles();

  return (
    <Box className={classes.postItem}>
      <Link to={"/post/" + post.id} key={post.id}>
        <h3 className={classes.heading}>{post.title}</h3>
        <Text className={classes.summary}>
          {createShortcut(post.content, 100)}
        </Text>
      </Link>
      <PostMeta post={post} />
    </Box>
  );
};

export default PostItem;
