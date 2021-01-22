import axios from "axios";

const request: any = axios.create({
    baseURL:"http://localhost:8000"
});

export const userApi = {
}

export const postApi = {
    post:(formData: any) => {
        return request.post("/post", formData, { // item 등록
            headers: {
                "content-Type" : "multipart/form-data"
            }
        })
    },
}
