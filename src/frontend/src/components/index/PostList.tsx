import PostItem from "./PostItem";
import { Box, Pagination, createStyles, rem } from "@mantine/core";
import Category from "./Category";
import usePosts, { PostQuery } from "../../hooks/usePosts";
import { useState } from "react";

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
  const [postQuery, setPostQuery] = useState<PostQuery>({} as PostQuery);
  const { data, error, isLoading } = usePosts(postQuery);
  return (
    <Box>
      <Box className={classes.postList}>
        <Category
          setCategory={(cate) => setPostQuery({ ...postQuery, category: cate })}
          currentCategory={postQuery.category}
        />

        {data && data.map((post, key) => <PostItem post={post} key={key} />)}
      </Box>
      <Box className={classes.page}>
        <Pagination total={5} />
      </Box>
    </Box>
  );
};

export default PostList;
