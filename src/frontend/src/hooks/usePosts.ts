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
    userId:number;
    user:User;
    tags:Tag[]

}

export interface PostQuery {
    category: string | null;
    order: string;
    searchText: string;
  }
  
const usePosts = (postQuery:PostQuery) => usedata<Post>("/api/posts",{ 
    params : { 
      category : postQuery?.category, 
      ordering:postQuery?.order,
    }},[postQuery])
export default usePosts