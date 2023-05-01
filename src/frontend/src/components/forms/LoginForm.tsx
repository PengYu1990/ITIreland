import { Group, Button, TextInput, Checkbox, Flex } from "@mantine/core";
import { useForm } from "@mantine/form";

interface Props {
  login: (values: {}) => void;
}

const LoginForm = ({ login }: Props) => {
  const form = useForm({
    initialValues: {
      username: "",
      password: "",
    },

    validate: {
      username: (value) =>
        value.length < 6 ? "Username must have at least 6 letters" : null,
    },
  });

  return (
    <form onSubmit={form.onSubmit((values) => login(values))}>
      <TextInput
        withAsterisk
        label="Username"
        placeholder=""
        {...form.getInputProps("username")}
      />
      <TextInput
        withAsterisk
        label="Password"
        placeholder=""
        type="password"
        {...form.getInputProps("password")}
      />
      <Group position="right" mt="md">
        <Button type="submit">Log In</Button>
      </Group>
    </form>
  );
};

export default LoginForm;
