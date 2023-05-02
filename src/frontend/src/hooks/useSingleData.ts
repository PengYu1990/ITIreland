import { useEffect, useState } from "react"
import createClient from "../services/api-client";
import { Post } from "./usePosts";

interface FetchResponse<T> {
    status:number;
    message:string;
    data:T;
}

const useSingleData = <T>(endpoint: string, deps?:any[]) => {

    const [data, setData] = useState<T>();
    const [error, setError] = useState("");
    const [isLoading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        const controller = new AbortController();
        createClient().get<FetchResponse<T>>(endpoint).then(resp=>{
            setData(resp.data.data);
            setLoading(false);
        }).catch((err) => {
            setError(err.response.data.message);
            setLoading(false);
        })
        return () => controller.abort();
    }, deps ? [...deps] : []);

    return {data, error, isLoading, setData}
}

export default useSingleData