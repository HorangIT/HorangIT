import { userApi } from "@/utils/axios";

const user: Record<string, any> = JSON.parse(localStorage.getItem('user') || '{}')
const initialState: Record<string, any> = Object.keys(user).length
  ? { status: { loggedIn: true }, user }
  : { status: { loggedIn: false }, user: null };

export const userModule: Record<string, any> = {
  namespaced: true,
  state: initialState,
  actions: {
    login ({ commit }: any, user: any) {
      return userApi.login(user)
        .then(
          (user: any) => {
          commit('LOGIN', user);
          return Promise.resolve(user);
          })
        .catch(
          (error: any) => {
            commit('LOGIN_FAIL');
            return Promise.reject(error);
          }
        );
    },
    logout ({ commit }: any) {
      localStorage.removeItem('user');
      commit('LOGOUT');
    },
    signup ({ commit }: any, user: any) {
      console.log('signup actions')
      return userApi.signup(user)
        .then(
          (user: any) => {
            commit('SIGNUP');
            return Promise.resolve(user);
          },
          (error: any) => {
            commit('SIGNUP_FAIL');
            return Promise.reject(error);
          }
        )
    }
  },
  mutations: {
    LOGIN (state: any, user: any) {
      state.status.loggedIn = true;
      state.user = user;
    },
    LOGIN_FAIL (state: any, user: any) {
      state.status.loggedIn = false;
      state.user = null;
    },
    LOGOUT (state: any) {
      state.status.loggedIn = false;
      state.user = null;
    },
    SIGNUP (state: any) {
      console.log('signup mutations')
      state.status.loggedIn = false;
      // state.status.loggedIn = true;
      // state.user = user;
    },
    SIGNUP_FAIL (state: any) {
      state.status.loggedIn = false;
    },
  }
}