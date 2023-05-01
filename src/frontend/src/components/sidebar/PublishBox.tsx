import { Button, Flex, createStyles, rem } from "@mantine/core";
import { AiOutlineEdit, AiOutlineQuestionCircle } from "react-icons/ai";

const useStyles = createStyles((theme) => ({
  publish_box: {
    backgroundColor: "white",
    padding: rem(20),
  },
}));

const PublishBox = () => {
  const { classes } = useStyles();
  return (
    <Flex
      className={classes.publish_box}
      gap={30}
      justify="center"
      direction="row"
    >
      <Button variant="gradient" leftIcon={<AiOutlineEdit size={rem(18)} />}>
        Post
      </Button>
      <Button
        leftIcon={<AiOutlineQuestionCircle size={rem(18)} />}
        variant="default"
      >
        Ask
      </Button>
    </Flex>
  );
};

export default PublishBox;
