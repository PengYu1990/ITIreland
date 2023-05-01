import { Post } from "./usePosts";
import useSingleData from "./useSingleData";

const usePost = (id:string|undefined) => useSingleData<Post>("/api/posts/"+id)
export default usePost