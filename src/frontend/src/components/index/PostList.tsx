import PostItem from "./PostItem";
import { Box, Pagination, createStyles, rem } from "@mantine/core";
import Category from "./Category";
import usePosts, { PostQuery } from "../../hooks/usePosts";
import { useState } from "react";

const useStyles = createStyles(() => ({
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
  const defaultPageSize = 5;
  const [postQuery, setPostQuery] = useState<PostQuery>({
    size: defaultPageSize,
  } as PostQuery);
  const { data /*error, isLoading*/ } = usePosts(postQuery);

  const changePage = (page: number) => {
    setPostQuery({ ...postQuery, page: page });
    window.scrollTo(0, 0);
  };

  return (
    <Box>
      <Box className={classes.postList}>
        <Category
          setCategory={(cate) =>
            setPostQuery({ ...postQuery, category: cate, page: 0 })
          }
          currentCategory={postQuery.category}
        />

        {data.data &&
          data.data.map((post, key) => <PostItem post={post} key={key} />)}
      </Box>
      {data && data.data && data.totalElements > defaultPageSize && (
        <Box className={classes.page}>
          <Pagination
            total={data.totalPages}
            onFirstPage={() => changePage(0)}
            onLastPage={() => changePage(data.totalPages - 1)}
            onPreviousPage={() => changePage(data.page - 1)}
            onNextPage={() => changePage(data.page + 1)}
            onChange={(page) => changePage(page - 1)}
          />
        </Box>
      )}
    </Box>
  );
};

export default PostList;
