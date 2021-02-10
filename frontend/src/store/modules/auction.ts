

export const auctionModule: Record<string, any> = {
  namespaced: true,
  state: {
    page: parseInt(sessionStorage.getItem("page") || "1"),
    filters: JSON.parse(sessionStorage.getItem("filters") || "{}"),
  },
  mutations: {
    SET_PAGE (state: any, page: number) {
      state.page = page;
      sessionStorage.setItem("page", String(page));
    },
    SET_FILTER (state: any, filters: any) {
      state.filters = filters;
      sessionStorage.setItem("filters", JSON.stringify(filters));
    }
  },
  actions: {
    setPage ({commit}: any, page: number) {
      commit('SET_PAGE', page)
    },
    setFilter ({commit}: any, filters: any) {
      commit('SET_FILTER', filters)
    }
  }
}