import { useEffect, useState } from "react"
import apiClient from "../services/api-client";

interface FetchResponse<T> {
    status:number;
    message:string;
    data:T[];
}

const useData = <T>(endpoint: string, deps?:any[]) => {

    const [data, setData] = useState<T[]>([]);
    const [error, setError] = useState("");
    const [isLoading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        const controller = new AbortController();
        apiClient.get<FetchResponse<T>>(endpoint).then(resp=>{
            setData(resp.data.data);
            setLoading(false);
        }).catch((err) => {
            setError(err.response.data.message);
            setLoading(false);
            setData([]);
        })
        return () => controller.abort();
    }, deps ? [...deps] : []);

    return {data, error, isLoading}
}

export default useData