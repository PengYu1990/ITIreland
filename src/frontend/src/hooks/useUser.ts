import usedata from "./useData"
export interface User {
    id:number;
    username:string;
    password:string;
    email:string;
    profile:string;
    state:number;
    credits:number;
    level:number;
    headShotUrl:string;
    ctime:Date;
    sessionId:string;


}
const useUser = () => usedata<User>("/api/users")
export default useUser