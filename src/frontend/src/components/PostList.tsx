import React from "react";
import PostItem from "./PostItem";
import { Box, createStyles, rem } from "@mantine/core";

const useStyles = createStyles((theme) => ({
  postList: {
    backgroundColor: "#ffffff",
  },
}));

const PostList = () => {
  const { classes } = useStyles();
  return (
    <Box className={classes.postList}>
      <PostItem />
    </Box>
  );
};

export default PostList;
