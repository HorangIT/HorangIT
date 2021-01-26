import axios, { AxiosInstance, AxiosResponse } from "axios";

const request: AxiosInstance = axios.create({
  baseURL:"http://localhost:8000"
});

function axiosGET (address: string, config: any, success: any, fail: any): void {
  request.get(address, config)
    .then(success)
    .catch(fail)
}

function axiosPOST (address: string, data: any, config: any, success: any, fail: any): void {
  request.post(address, data, config)
    .then(success)
    .catch(fail)
}

export const userApi = {
}

export const itemApi = {
  item:(formData: any) => {
    return request.post("/item", formData, { // item 등록
      headers: {
        "content-Type" : "multipart/form-data"
      }
    })
  },
}

export { axiosPOST, axiosGET }
