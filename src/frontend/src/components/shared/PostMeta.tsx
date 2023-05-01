import {
  Avatar,
  Flex,
  Group,
  MediaQuery,
  Text,
  createStyles,
  rem,
} from "@mantine/core";
import {
  IconCalendarTime,
  IconCategory,
  IconEye,
  IconTags,
} from "@tabler/icons-react";
import { Post } from "../../hooks/usePosts";
import dayjs from "dayjs";

const useStyles = createStyles((theme) => ({
  meta: {
    marginTop: rem(20),
    color: theme.colors.gray[6],
    fontSize: rem(14),
  },
}));

interface Props {
  post: Post;
}

const PostMeta = ({ post }: Props) => {
  const { classes } = useStyles();
  return (
    <Flex
      className={classes.meta}
      gap={25}
      justify="flex-start"
      direction="row"
    >
      <Group spacing="xs">
        <Avatar color="cyan" radius="xl" size={23}>
          {post.user.username.substring(0, 2).toUpperCase()}
        </Avatar>
        <Text>{post.user.username}</Text>
      </Group>

      <Group spacing="xs" position="left">
        <IconCalendarTime size={20} />
        <Text>{dayjs(post.ctime).fromNow()}</Text>
      </Group>
      <MediaQuery smallerThan="md" styles={{ display: "none" }}>
        <Group spacing="xs" position="left">
          <IconEye size={20} />
          <Text>{post.views}</Text>
        </Group>
      </MediaQuery>

      <MediaQuery smallerThan="sm" styles={{ display: "none" }}>
        <Group spacing="xs" position="left">
          <IconCategory size={20} />
          <Text>{post.category.category}</Text>
        </Group>
      </MediaQuery>

      {post.tags.length > 0 && (
        <MediaQuery smallerThan="md" styles={{ display: "none" }}>
          <Group spacing="xs" position="left">
            <IconTags size={25} />
            <Flex gap={10} justify="flex-start" direction="row">
              {post.tags.map((tag, key) => (
                <Text key={key}>{tag.tag}</Text>
              ))}
            </Flex>
          </Group>
        </MediaQuery>
      )}
    </Flex>
  );
};

export default PostMeta;
