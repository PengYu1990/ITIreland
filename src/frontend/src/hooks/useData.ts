import { useEffect, useState } from "react"
import createClient from "../services/api-client";
import { AxiosRequestConfig, CanceledError } from "axios";

interface FetchResponse<T> {
    status:number;
    message:string;
    data:T[];
}

const useData = <T>(endpoint: string, requestConfig?:AxiosRequestConfig, deps?:any[]) => {

    const [data, setData] = useState<T[]>([]);
    const [error, setError] = useState("");
    const [isLoading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        const controller = new AbortController();
        createClient().get<FetchResponse<T>>(endpoint, {signal:controller.signal,...requestConfig}).then(resp=>{
            setData(resp.data.data);
            setLoading(false);
        }).catch((err) => {
            if(err instanceof CanceledError) return;
            setError(err.response.data.message);
            setLoading(false);
            setData([]);
        })
        return () => controller.abort();
    }, deps ? [...deps] : []);

    return {data, error, isLoading}
}

export default useData