import usedata from "./useData"

export interface Category {
    category:string;
}
const usePost = () => usedata<Category>("/api/categories")
export default usePost