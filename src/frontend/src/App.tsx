import { Footer } from "./components/shared/Footer";
import "./App.css";
import { NavBar } from "./components/shared/NavBar";

import { AppShell, Container, Grid, createStyles, rem } from "@mantine/core";
import RegisterModal from "./components/shared/Modal";
import RegisterForm from "./components/forms/RegisterForm";
import LoginForm from "./components/forms/LoginForm";
import useAuth from "./hooks/useAuth";
import ToTop from "./components/shared/ToTop";
import { Outlet } from "react-router-dom";

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
      >
        <Grid className={classes.content} grow>
          <Grid.Col md={9} sm={12}>
            <Container>
              <Outlet />
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
