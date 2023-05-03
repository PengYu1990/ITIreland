import { Group, Button, TextInput, Checkbox } from "@mantine/core";
import { useForm } from "@mantine/form";

interface Props {
  signup: (values: {}) => void;
}

const RegisterForm = ({ signup }: Props) => {
  const form = useForm({
    initialValues: {
      email: "",
      username: "",
      password: "",
      password2: "",
      termsOfService: false,
    },

    validate: {
      email: (value) => (/^\S+@\S+$/.test(value) ? null : "Invalid email"),
      username: (value) =>
        value.length < 6 ? "Username must have at least 6 letters" : null,
      password: (value) => (value.length < 1 ? "Password can't be null" : null),
      password2: (value, values) =>
        value !== values.password ? "Passwords did not match" : null,
    },
  });

  return (
    <form onSubmit={form.onSubmit((values) => signup(values))}>
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
      <TextInput
        withAsterisk
        label="Confirm Password"
        placeholder=""
        type="password"
        {...form.getInputProps("password2")}
      />
      <TextInput
        withAsterisk
        label="Email"
        placeholder="your@email.com"
        type="email"
        {...form.getInputProps("email")}
      />

      <Checkbox
        mt="md"
        label="I agree to sell my privacy"
        {...form.getInputProps("termsOfService", { type: "checkbox" })}
      />

      <Group position="right" mt="md">
        <Button type="submit">Sign Up</Button>
      </Group>
    </form>
  );
};

export default RegisterForm;
