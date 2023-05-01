import {
  Box,
  Button,
  Group,
  Select,
  TextInput,
  createStyles,
  rem,
} from "@mantine/core";
import RichEditor from "./components/forms/RichEditor";
import { useEffect, useState } from "react";
import create from "./services/http-service";
import { notifications } from "@mantine/notifications";
import { useForm } from "@mantine/form";
import { getSessionUser } from "./services/session-service";
import { useLocation, useNavigate } from "react-router-dom";
import { Post } from "./hooks/usePosts";

const useStyles = createStyles((theme) => ({
  form: {
    paddingTop: rem(10),
    paddingBottom: rem(55),
  },
  mt10: {
    marginTop: rem(10),
  },
}));

const categories = ["Backend", "Frontend", "Fullstack"];

const PostEdit = () => {
  const [htmlContent, setHtmlContent] = useState("");
  const { classes } = useStyles();
  const history = useNavigate();
  const { pathname } = useLocation();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, [pathname]);

  const form = useForm({
    initialValues: {
      title: "",
      category: "",
    },

    validate: {
      title: (value) => (value.length < 1 ? "Title can not be empty" : null),
      category: (value) =>
        value.length < 1 ? "Category must be selected" : null,
    },
  });

  const submitPost = (values) => {
    const user = getSessionUser();
    if (user === null) {
      notifications.show({
        title: "Notification",
        message: "You need to login before post",
        color: "red",
      });
      return;
    }

    values = { ...values, content: htmlContent, userId: user.id };
    if (values.content == null || values.content === "") {
      notifications.show({
        title: "Notification",
        message: "Content can not be empty",
        color: "red",
      });
      return;
    }
    create("/api/posts")
      .create(values)
      .then((resp) => {
        notifications.show({
          title: "Notification",
          message: "Post success",
          color: "blue",
        });
        const postId = resp.data.data.id;
        history(`/post/${postId}`);
      })
      .catch((error) => {
        notifications.show({
          title: "Notification",
          message: error.message,
          color: "red",
        });
      });
  };

  return (
    <Box className={classes.form}>
      <form onSubmit={form.onSubmit((values) => submitPost(values))}>
        <TextInput
          withAsterisk
          placeholder="Please type title"
          {...form.getInputProps("title")}
        />
        <Select
          className={classes.mt10}
          data={categories}
          placeholder="Category"
          {...form.getInputProps("category")}
        />
        <RichEditor
          className={classes.mt10}
          setHtmlContent={(content) => setHtmlContent(content)}
        />
        <Group position="right" mt="md">
          <Button type="submit">Submit</Button>
        </Group>
      </form>
    </Box>
  );
};

export default PostEdit;
