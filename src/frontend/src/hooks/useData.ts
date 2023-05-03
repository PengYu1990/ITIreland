import { useEffect, useState } from "react"
import createClient from "../services/api-client";
import { AxiosRequestConfig, CanceledError } from "axios";
import { notifications } from "@mantine/notifications";

interface FetchResponse<T> {
    status:number;
    message:string;
    data:T[];
    totalPages:number;
    totalElements:number;
    page:number;
}

const useData = <T>(endpoint: string, requestConfig?:AxiosRequestConfig, deps?:any[]) => {

    const [data, setData] = useState<FetchResponse<T>>({status:400,message:"",data:[],totalElements:0,totalPages:0,page:0});
    const [error, setError] = useState("");
    const [isLoading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);
        const controller = new AbortController();
        createClient().get<FetchResponse<T>>(endpoint, {signal:controller.signal,...requestConfig}).then(resp=>{
            setData(resp.data);
            setLoading(false);
        }).catch((err) => {
            if(err instanceof CanceledError) return;
            setError(err.response.data.message);
            notifications.show({
                title: "Notification",
                message: err.response.data.message,
                color: "red",
              });
            setLoading(false);
            // setData();
        })
        return () => controller.abort();
    }, deps ? [...deps] : []);

    return {data, error, isLoading}
}

export default useData