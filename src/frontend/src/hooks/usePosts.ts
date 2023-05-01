import { Category } from "./useCategories";
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
const usePosts = () => usedata<Post>("/api/posts")
export default usePosts