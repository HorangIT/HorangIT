export const auctionModule: Record<string, any> = {
  namespaced: true,
  state: {
    page: sessionStorage.getItem("page") ? sessionStorage.getItem("page") : 1,
    filters: sessionStorage.getItem("filters") ? sessionStorage.getItem("filters") : '{}'
  },
  mutations: {
    SET_PAGE (state: any, page: any) {
      state.page = page;
    },
    SET_FILTER (state: any, filters: any) {
      state.filters = filters;
    }
  }
}