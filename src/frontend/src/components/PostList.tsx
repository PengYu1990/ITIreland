import PostItem from "./PostItem";
import { Box, Pagination, createStyles, rem } from "@mantine/core";
import Category from "./Category";
import usePosts from "../hooks/usePosts";
import { Link } from "react-router-dom";

const useStyles = createStyles((theme) => ({
  postList: {
    backgroundColor: "#ffffff",
  },
  page: {
    padding: rem(10),
    float: "right",
  },
}));

const PostList = () => {
  const { classes } = useStyles();
  const { data, error, isLoading } = usePosts();
  return (
    <Box>
      <Box className={classes.postList}>
        <Category />

        {data &&
          data.map((post) => (
            <Link to={"/post/" + post.id} key={post.id}>
              <PostItem post={post} />
            </Link>
          ))}
      </Box>
      <Box className={classes.page}>
        <Pagination total={5} />
      </Box>
    </Box>
  );
};

export default PostList;
