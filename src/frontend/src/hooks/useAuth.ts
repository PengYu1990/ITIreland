import { useDisclosure } from "@mantine/hooks";
import { useState } from "react";
import { getSessionUser, removeSessionUser, setSessionUser } from "../services/session-service";
import { User } from "./useUser";
import { notifications } from "@mantine/notifications";
import create from "../services/http-service";

const useAuth = () => {
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
          message: error.message,
          color: "red",
        });
      });
  };
  return {opened, open, close, login, user,
    openLoginModal, openSignUpModal, loginSuccess, 
    loginError, registerSuccess, registerError,
    logout}
}

export default useAuth;