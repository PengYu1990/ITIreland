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

const useStyles = createStyles((theme) => ({
  postItem: {
    paddingLeft: rem(10),
    paddingRight: rem(10),
    paddingTop: rem(2),
    paddingBottom: rem(2),
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
  },
}));

const PostItem = () => {
  const { classes } = useStyles();
  return (
    <Box className={classes.postItem}>
      <h3 className={classes.heading}>Python is my favourate language</h3>
      <Text>Sit optio et ut suscipit nesciunt quis sint.</Text>

      <Flex
        className={classes.postItemBottom}
        gap={25}
        justify="flex-start"
        direction="row"
      >
        <Group spacing="xs">
          <Avatar color="cyan" radius="xl" size={28}>
            MK
          </Avatar>
          <Text>Eoin</Text>
        </Group>

        <Group spacing="xs" position="left">
          <IconCalendarTime size={25} />
          <Text>7:06PM, May 2nd, 2023</Text>
        </Group>
        <MediaQuery smallerThan="md" styles={{ display: "none" }}>
          <Group spacing="xs" position="left">
            <IconEye size={25} />
            <Text>5</Text>
          </Group>
        </MediaQuery>

        <MediaQuery smallerThan="sm" styles={{ display: "none" }}>
          <Group spacing="xs" position="left">
            <IconCategory size={25} />
            <Text>Frontend</Text>
          </Group>
        </MediaQuery>

        <MediaQuery smallerThan="md" styles={{ display: "none" }}>
          <Group spacing="xs" position="left">
            <IconTags size={25} />
            <Flex gap={10} justify="flex-start" direction="row">
              <Text>Java</Text>
              <Text>Python</Text>
              <Text>React</Text>
            </Flex>
          </Group>
        </MediaQuery>
      </Flex>
    </Box>
  );
};

export default PostItem;
