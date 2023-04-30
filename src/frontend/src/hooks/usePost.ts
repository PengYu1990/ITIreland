import { Category } from "./useCategory";
import usedata from "./useData"
import { User } from "./useUser";
interface Tag {
    tag:string;
}
export interface Post {
    id:number;
    title:string;
    content:string;
    ctime:Date;
    utime:Date;
    views:number;
    thumbs:number;
    category:Category;
    user:User;
    tags:Tag[]

}
const usePost = () => usedata<Post>("/api/posts")
export default usePost