import usedata from "./useData"
import { Post } from "./usePosts";
import { User } from "./useUser";

export interface Comment {
    id:number;
    content:string;
    ctime:Date;
    utime:Date;
    post:Post;
    user:User;
}
  
const useComments = (postId:number) => usedata<Post>("/api/comments",{ 
    params : { 
      postId : postId, 
    }},[postId])
export default useComments