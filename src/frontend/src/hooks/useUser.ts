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


}
const usePost = () => usedata<User>("/api/users")
export default usePost