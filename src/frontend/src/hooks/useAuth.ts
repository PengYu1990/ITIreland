import { useDisclosure } from "@mantine/hooks";
import { useState } from "react";
import { getSessionUser, removeSessionUser, setSessionUser } from "../services/session-service";
import { User } from "./useUser";
import { notifications } from "@mantine/notifications";
import create from "../services/http-service";

// Auth Hook
const useAuth = () => {
  const [opened, { open, close }] = useDisclosure(false);
  // const [user, setUser] = useState<User | null>(getSessionUser());

  // decide display login form or register form
  const [isLogin, setLogin] = useState(true);

  // record global user login state
  const [loginState, setLoginState] = useState("no");


  const openLoginModal = () => {
    setLogin(true);
    open();
  };

  const openSignUpModal = () => {
    setLogin(false);
    open();
  };

  // Request login api
  const login = (values: {}) => {
    console.log(values);
    create("/api/auth/login")
      .create(values)
      .then((resp) => {
        console.log(resp.data);
        loginSuccess(resp.data.data);
      })
      .catch((error) => {
        console.log(error.message);
        loginError();
      });
  };

  // Request sign up api
  const signup = (values: {}) => {
    console.log(values);
    create("/api/auth/signup")
      .create(values)
      .then((resp) => {
        console.log(resp.data);
        registerSuccess(resp.data.data);
      })
      .catch((error) => {
        console.log(error.message);
        registerError();
      });
  };

  const logout = () => {
    create("/api/auth/logout")
      .create(null)
      .then((resp) => {
        removeSessionUser();
        // setUser(null);
        notifications.show({
          title: "Notification",
          message: "Logout Success",
          color: "blue",
        });
        setLoginState("no");
      })
      .catch((error) => {
        removeSessionUser();
        // setUser(null);
        // notifications.show({
        //   title: "Notification",
        //   message: error.message,
        //   color: "red",
        // });
      });
  };

  const loginSuccess = (user: User) => {
    close();
    setSessionUser(user);
    // setUser(getSessionUser());
    notifications.show({
      title: "Notification",
      message: "Login Success",
      color: "blue",
    });
    setLoginState("yes");
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
    // setUser(getSessionUser());
    notifications.show({
      title: "Notification",
      message: "Sign Up Success",
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

  
  return {opened, open, close,  isLogin, loginState,
    openLoginModal, openSignUpModal, 
    login, signup, logout}
}

export default useAuth;