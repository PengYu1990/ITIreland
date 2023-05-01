import { Footer } from "./components/Footer";
import "./App.css";
import { NavBar } from "./components/NavBar";

import { AppShell, Container, Grid, createStyles, rem } from "@mantine/core";
import PostList from "./components/PostList";
import ToTop from "./components/ToTop";
import RegisterModal from "./components/Modal";
import { useDisclosure } from "@mantine/hooks";
import RegisterForm from "./components/RegisterForm";
import LoginForm from "./components/LoginForm";
import { useState } from "react";
import { User } from "./hooks/useUser";
import {
  getSessionUser,
  removeSessionUser,
  setSessionUser,
} from "./services/session-service";
import create from "./services/http-service";
import { notifications } from "@mantine/notifications";

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
  const [opened, { open, close }] = useDisclosure(false);
  const [user, setUser] = useState<User | null>(getSessionUser());

  const [login, setLogin] = useState(true);

  const openLoginModal = () => {
    setLogin(true);
    open();
  };

  const openSignUpModal = () => {
    setLogin(false);
    open();
  };

  const loginSuccess = (user: User) => {
    close();
    setSessionUser(user);
    setUser(getSessionUser());
    notifications.show({
      title: "Notification",
      message: "Login Success",
      color: "blue",
    });
  };
  const loginError = () => {
    notifications.show({
      title: "Notification",
      message: "Login Error",
      color: "red",
    });
  };

  const registerSuccess = (user: User) => {
    close();
    setSessionUser(user);
    setUser(getSessionUser());
    notifications.show({
      title: "Notification",
      message: "Sign Success",
      color: "blue",
    });
  };
  const registerError = () => {
    notifications.show({
      title: "Notification",
      message: "Sign Up Error",
      color: "red",
    });
  };

  const logout = () => {
    create("/api/auth/logout")
      .create(null)
      .then((resp) => {
        removeSessionUser();
        setUser(null);
        notifications.show({
          title: "Notification",
          message: "Logout Success",
          color: "blue",
        });
        // window.location.reload();
      })
      .catch((error) => {
        notifications.show({
          title: "Notification",
          message: error.error.message,
          color: "red",
        });
      });
  };

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
      >
        <Grid className={classes.content} grow>
          <Grid.Col md={9} sm={12}>
            <Container>
              <PostList />
            </Container>
          </Grid.Col>
          <Grid.Col md={3} sm={12}></Grid.Col>
        </Grid>
        <Footer />

        <ToTop />
      </AppShell>
      <RegisterModal
        opened={opened}
        close={close}
        title={login ? "Log in" : "Sign up"}
      >
        {login ? (
          <LoginForm success={loginSuccess} error={loginError} />
        ) : (
          <RegisterForm success={registerSuccess} error={registerError} />
        )}
      </RegisterModal>
    </>
  );
}
