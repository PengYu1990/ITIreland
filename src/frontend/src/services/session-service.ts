import { User } from "../hooks/useUser";

export const setSessionUser = (user : User) =>{
    if(user != null){
        sessionStorage.setItem("user",JSON.stringify(user));
    }
    
}

export const getSessionUser = () =>{
    const u = sessionStorage.getItem("user");
    if(u != null){
        const user: User = JSON.parse(u);
        return user;
    }
   return null;
}

export const removeSessionUser = () =>{
    sessionStorage.removeItem("user");
}

// get from session (if the value expired it is destroyed)
function sessionGet(key:string) {
    let stringValue = window.sessionStorage.getItem(key)
      if (stringValue !== null) {
        let value = JSON.parse(stringValue)
          let expirationDate = new Date(value.expirationDate)
          if (expirationDate > new Date()) {
            return value.value
          } else {
            window.sessionStorage.removeItem(key)
          }
      }
      return null
  }
  
  // add into session
  function sessionSet(key:string, value:string, expirationInMin = 10) {
    let expirationDate = new Date(new Date().getTime() + (60000 * expirationInMin))
      let newValue = {
      value: value,
      expirationDate: expirationDate.toISOString()
    }
    window.sessionStorage.setItem(key, JSON.stringify(newValue))
  }