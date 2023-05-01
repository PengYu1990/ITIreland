import usedata from "./useData"

export interface Category {
    category:string;
}
const useCategories = () => usedata<Category>("/api/categories")
export default useCategories