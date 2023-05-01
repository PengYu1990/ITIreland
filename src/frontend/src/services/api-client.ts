import axios from "axios";
import { getSessionUser } from "./session-service";

const user = getSessionUser();
let sessionId = null;
if(user != null){
    sessionId = user.sessionId;
}

export default axios.create({
    // baseURL:"https://worrisome-gold-bikini.cyclic.app",
    baseURL:"http://localhost:8080",
    params: {
        sessionId:sessionId
    },
    headers:{
        sessionId:sessionId 
    }
})