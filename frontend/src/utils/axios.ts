import axios, { AxiosInstance, AxiosResponse } from "axios";

const authHeader = function(): Record<string, string> {
  const user: Record<string, any> = JSON.parse(localStorage.getItem("user") || "{}");
  if (Object.keys(user).length) {
    const token = user.object.token;
    return { Authorization: "Bearer " + token };
  }
  return {};
};

const request: AxiosInstance = axios.create({
  //baseURL: "http://i4a101.p.ssafy.io:8000/api",
  baseURL: "http://localhost:8000/api",
  headers: authHeader()
});

export const userApi: Record<string, any> = {
  async login(user: Record<string, any>): Promise<void | AxiosResponse<any>> {
    const loginAddress = "account/login";
    // get 방식
    // const config = {
    //   params: user,
    // };
    // const response = await request.get(loginAddress, config);
    // post 방식
    const userData = user;
    const response = await request.post(loginAddress, userData);
    if (response.status === 200) {
      localStorage.setItem("user", JSON.stringify(response.data));
    }
    return response.data;
  },
  async signup(user: Record<string, any>): Promise<void | AxiosResponse<any>> {
    const signupAddress = "account/signup";
    const userData = user;
    const response = await request.post(signupAddress, userData);
    if (response.status === 201) {
      localStorage.setItem("user", JSON.stringify(response.data));
    }
    return response.data;
  },
  logout(): void {
    localStorage.removeItem("user");
  }
};

export const itemApi = {
  item: (formData: any) => {
    return request.post("/item", formData, { // item 등록
      headers: {
        "content-Type": "multipart/form-data"
      }
    });
  },
  getItem(id: number): any {
    return request.get(`/item/${id}`);
  },
  // 지역 필터
  search(data: any): any {
    if (data) {
      return request.get("/item/district", {
        params: {
          districtName: data
        }  
      })
    } else {
      return request.get("/item/district", {
        params: {
          districtName: null
        }  
      })
    }
  },
  // 아이템 필터링 + 페이지네이션
  getItemPage(page: number, data: any): any {
    const searchParams = {
      status: data.status,
      category: data.category.length ? data.category.join(",") : null,
      grade: data.grade.length ? data.grade.join(",") : null ,
      si: data.si,
      gu: data.gu,
      name: data.name
    }
    return request.get(`/item/page/${page}`, {
      params: searchParams
    });
  },
  getChatLog (page: number): any {
    return request.get(`/item/${page}/chat`)
  }
};

export const auctionApi = {
  // bidding(itemId: number, data: any): any {
  //   return request.post("/auction", data);
  // },
  // flex(data: any): any {
  //   return request.post("/auction/flex", data);
  // },
  log (itemId: number): any {
    return request.get(`/auction/log/${itemId}`);
  }
};

export const myAuctionApi = {
  buyer(userId: any): any {
    return request.get(`/auction/buyer/${userId}`);
  },
  seller(userId: any): any {
    return request.get(`/auction/seller/${userId}`);
  },
  pay (data: any): any {
    return request.post('/payment', data);
  },
  item(itemId: any): any {
    return request.get(`/auction/item/${itemId}`);
  },
  success (token: any):any {
    const params = {
      pg_token: token
    }
    return request.get('/payment/success',{
      params: params
    });
  }
};
