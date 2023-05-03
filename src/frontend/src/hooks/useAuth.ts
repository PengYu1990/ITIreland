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
  const [loginState, setLoginState] = useState(getSessionUser() == null ? "no":"yes");


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
    create("/api/auth/login")
      .create(values)
      .then((resp) => {
        loginSuccess(resp.data.data);
      })
      .catch((error) => {
        loginError(error.response.data);
      });
  };

  // Request sign up api
  const signup = (values: {}) => {
    console.log(values);
    create("/api/auth/signup")
      .create(values)
      .then((resp) => {
        registerSuccess(resp.data.data);
      })
      .catch((error) => {
        registerError(error.response.data);
      });
  };

  const logout = () => {
    create("/api/auth/logout")
      .create(null)
      .then(() => {
        removeSessionUser();
        notifications.show({
          title: "Notification",
          message: "Logout Success",
          color: "blue",
        });
        setLoginState("no");
      })
      .catch(() => {
        removeSessionUser();
        notifications.show({
          title: "Notification",
          message: "Logout Success",
          color: "blue",
        });
        setLoginState("no");
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
  const loginError = (errorData:any) => {
    notifications.show({
      title: "Notification",
      message: errorData.message,
      color: "red",
    });
    setLoginState("no");
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
    setLoginState("yes");
  };
  const registerError = (errorData:any) => {
    notifications.show({
      title: "Notification",
      message: errorData.message,
      color: "red",
    });
    setLoginState("no");
  };

  
  return {opened, open, close,  isLogin, loginState,
    openLoginModal, openSignUpModal, 
    login, signup, logout}
}

export default useAuth;