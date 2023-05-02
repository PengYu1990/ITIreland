import { Avatar, Box, Flex, Text, createStyles, rem } from "@mantine/core";
import { Comment } from "../../hooks/useComments";

interface Props {
  comment: Comment;
}

const useStyles = createStyles((theme) => ({
  citem: {
    marginTop: rem(20),
    color: theme.colors.gray[6],
    fontSize: rem(14),
    paddingBottom: rem(10),
    borderBottom: `${rem(1)} solid ${
      theme.colorScheme === "dark" ? theme.colors.dark[5] : theme.colors.gray[2]
    }`,
  },
  content: {
    marginTop: rem(10),
  },
}));

const CommentItem = ({ comment }: Props) => {
  const { classes } = useStyles();
  return (
    <>
      <Flex
        className={classes.citem}
        gap={10}
        justify="flex-start"
        direction="row"
      >
        <Avatar color="cyan" radius="xl" size={30}>
          {comment.user.username.substring(0, 2).toUpperCase()}
        </Avatar>
        <Box>
          <Text w="100%">{comment.user.username}</Text>
          <Text w="100%" className={classes.content}>
            {comment.content}
          </Text>
        </Box>
      </Flex>
    </>
  );
};

export default CommentItem;
