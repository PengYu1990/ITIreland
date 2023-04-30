import React from "react";
import PostItem from "./PostItem";
import { Box, Center, Pagination, createStyles, rem } from "@mantine/core";
import Category from "./Category";
import usePost from "../hooks/usePost";

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
  const { data, error, isLoading } = usePost();
  return (
    <Box>
      <Box className={classes.postList}>
        <Category />
        {data.map((post) => {
          return <PostItem post={post} key={post.id} />;
        })}
      </Box>
      <Box className={classes.page}>
        <Pagination total={5} />
      </Box>
    </Box>
  );
};

export default PostList;
