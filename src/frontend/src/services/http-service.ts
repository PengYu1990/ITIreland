import createClient from "./api-client";

export interface Entity {
    id: number;
  }

class HttpService<T>{
    endpoint : string;

    constructor(endpoint:string){
        this.endpoint = endpoint;
    }
    
    create(entity : T){
        return createClient().post(this.endpoint, entity)

    }

    get(){
        return createClient().get(this.endpoint)
    }

    delete<T extends Entity>(entity: T){
        return createClient()
        .delete(this.endpoint+entity.id)

    }

    update<T extends Entity>(entity : T){
        return createClient().patch(this.endpoint + "/" +entity.id, entity)
    }
}

 const create =  (endpoint : string) => new HttpService(endpoint); 
 export default create;
