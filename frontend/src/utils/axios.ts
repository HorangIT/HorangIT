import axios, { AxiosInstance, AxiosResponse } from "axios";

const authHeader = function (): Record<string, string> {
  const user = JSON.parse(localStorage.getItem('user') || '{}');
  const token: string = user.object.token;
  if (user && token) {
    return { Authorization: 'Bearer ' + token };
  }
  return {};
};

const request: AxiosInstance = axios.create({
  baseURL:"http://localhost:8000",
  headers: authHeader(),
});

export const userApi: Record<string, any> = {
  async login (user: Record<string, any>): Promise<void | AxiosResponse<any>> {
    const loginAddress = 'account/login';
    // get 방식
    // const config = {
    //   params: user,
    // };
    // const response = await request.get(loginAddress, config);
    // post 방식
    const userData = user;
    const response = await request.post(loginAddress, userData);
    if (response.status === 200) {
      localStorage.setItem('user', JSON.stringify(response.data));
    }
    return response.data;
  },
  async signup (user: Record<string, any>): Promise<void | AxiosResponse<any>> {
    const signupAddress = 'account/signup';
    const userData = user;
    const response = await request.post(signupAddress, userData);
    if (response.status === 201) {
      localStorage.setItem('user', JSON.stringify(response.data));
    }
    return response.data;
  },
  logout (): void {
    localStorage.removeItem('user')
  },

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
