import {
  Avatar,
  Box,
  Flex,
  Group,
  MediaQuery,
  Text,
  createStyles,
  rem,
} from "@mantine/core";
import { IconCategory } from "@tabler/icons-react";
import { IconTags } from "@tabler/icons-react";
import { IconCalendarTime, IconEye } from "@tabler/icons-react";
import { Post } from "../hooks/usePost";
import dayjs from "dayjs";
// extend dayjs
import relativeTime from "dayjs/plugin/relativeTime";
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
    color: theme.colors.dark[4],
    fontFamily: "Pathway Extreme",
    padding: 0,
  },
  postItemBottom: {
    marginTop: rem(20),
    color: theme.colors.gray[6],
    fontSize: rem(14),
  },
}));

interface Props {
  post: Post;
}

const PostItem = ({ post }: Props) => {
  const { classes } = useStyles();
  return (
    <Box className={classes.postItem}>
      <h3 className={classes.heading}>{post.title}</h3>
      <Text>{post.content}</Text>

      <Flex
        className={classes.postItemBottom}
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
      </Flex>
    </Box>
  );
};

export default PostItem;
