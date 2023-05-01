import { Footer } from "./components/shared/Footer";
import "./App.css";
import { NavBar } from "./components/shared/NavBar";

import {
  AppShell,
  Button,
  Container,
  Flex,
  Grid,
  MantineProvider,
  createStyles,
  rem,
} from "@mantine/core";
import RegisterModal from "./components/shared/Modal";
import RegisterForm from "./components/forms/RegisterForm";
import LoginForm from "./components/forms/LoginForm";
import useAuth from "./hooks/useAuth";
import ToTop from "./components/shared/ToTop";
import { Outlet } from "react-router-dom";
import PublishBox from "./components/sidebar/PublishBox";

const useStyles = createStyles((theme) => ({
  content: {
    borderBottom: `${rem(1)} solid ${
      theme.colorScheme === "dark" ? theme.colors.dark[5] : theme.colors.gray[2]
    }`,
    backgroundColor: "#f2f2f2",
  },
}));

export default function App() {
  const { classes } = useStyles();
  const {
    opened,
    close,
    isLogin,
    openLoginModal,
    openSignUpModal,
    login,
    signup,
    logout,
  } = useAuth();

  return (
    <>
      <AppShell
        header={
          <NavBar
            onLoginClicked={openLoginModal}
            onSignUpClicked={openSignUpModal}
            onLogoutClicked={logout}
          />
        }
        padding={0}
        footer={<Footer />}
      >
        <Container className={classes.content} size={1280}>
          <Outlet />
          <ToTop />
        </Container>
      </AppShell>
      <RegisterModal
        opened={opened}
        close={close}
        title={isLogin ? "Log in" : "Sign up"}
      >
        {isLogin ? (
          <LoginForm login={login} />
        ) : (
          <RegisterForm signup={signup} />
        )}
      </RegisterModal>
    </>
  );
}
