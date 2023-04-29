import React from "react";
import PostItem from "./PostItem";
import { Box, Center, Pagination, createStyles, rem } from "@mantine/core";
import Category from "./Category";

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
  return (
    <Box>
      <Box className={classes.postList}>
        <Category />
        <PostItem />
        <PostItem />
        <PostItem />
        <PostItem />
        <PostItem />
        <PostItem />
        <PostItem />
      </Box>
      <Box className={classes.page}>
        <Pagination total={5} />
      </Box>
    </Box>
  );
};

export default PostList;
