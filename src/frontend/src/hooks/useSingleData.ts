import { useEffect, useState } from "react"
import createClient from "../services/api-client";
import { Post } from "./usePosts";
import { CanceledError } from "axios";
import { notifications } from "@mantine/notifications";

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
        createClient().get<FetchResponse<T>>(endpoint,{signal:controller.signal}).then(resp=>{
            setData(resp.data.data);
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
        })
        return () => controller.abort();
    }, deps ? [...deps] : []);

    return {data, error, isLoading, setData}
}

export default useSingleData