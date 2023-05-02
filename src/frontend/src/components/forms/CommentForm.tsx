import {
  Avatar,
  Button,
  Flex,
  Textarea,
  createStyles,
  rem,
} from "@mantine/core";
import { useState } from "react";
import { getSessionUser } from "../../services/session-service";
import { useForm } from "@mantine/form";
import { notifications } from "@mantine/notifications";
import create from "../../services/http-service";
import { Comment } from "../../hooks/useComments";

const useStyles = createStyles((theme) => ({
  form: {
    paddingTop: rem(10),
    paddingBottom: rem(55),
  },
  button: {
    marginTop: rem(10),
    float: "right",
  },
  editor: {
    marginLeft: rem(10),
  },
}));

interface Props {
  postId: number;
  addComment: (comment: Comment) => void;
}

const CommentSection = ({ postId, addComment }: Props) => {
  const { classes } = useStyles();
  const [htmlContent, setHtmlContent] = useState("");
  const user = getSessionUser();

  const form = useForm({
    initialValues: {
      content: "",
    },

    validate: {
      content: (value) =>
        value.length < 1 ? "Comment can not be empty" : null,
    },
  });

  const comment = (values: {}) => {
    const user = getSessionUser();
    if (user === null) {
      notifications.show({
        title: "Notification",
        message: "You need to login before comment",
        color: "red",
      });

      return;
    }

    if (postId == null) {
      return;
    }

    const comment = { ...values, postId: postId, userId: user.id };
    // return;
    create("/api/comments")
      .create(comment)
      .then((resp) => {
        console.log(resp.data);
        notifications.show({
          title: "Notification",
          message: "Comment success",
          color: "blue",
        });
        form.reset();
        addComment(resp.data.data);
      })
      .catch((error) => {
        notifications.show({
          title: "Notification",
          message: "Comment error",
          color: "red",
        });
        return;
      });
  };

  return (
    <>
      <form
        className={classes.form}
        onSubmit={form.onSubmit((values) => comment(values))}
      >
        <Flex justify="space-between" direction="row">
          <Avatar color="cyan" radius="xl" size={35}>
            {user && user.username.substring(0, 2).toUpperCase()}
          </Avatar>
          <Textarea
            className={classes.editor}
            minRows={3}
            w="100%"
            placeholder="Comment content"
            {...form.getInputProps("content")}
          ></Textarea>
        </Flex>
        <Button type="submit" className={classes.button}>
          Submit
        </Button>
      </form>
    </>
  );
};

export default CommentSection;
