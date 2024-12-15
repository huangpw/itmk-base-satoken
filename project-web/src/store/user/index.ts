import { defineStore } from 'pinia'
import { getUserInfoApi } from '@/api/user'

// 定义UserState类型
interface UserState {
  userId: string
  nickName: string
  token: string
  codeList: any
}

// 定义Store
export const useUserStore = defineStore('userStore', {
  state: (): UserState => ({
    userId: '',
    nickName: '',
    token: '',
    codeList: JSON.parse(localStorage.getItem('codeList') || '[]')
  }),
  getters: {
    getUserId(state) {
      return state.userId
    },
    getNickName(state) {
      return state.nickName
    },
    getToken(state) {
      return state.token
    },
    getCodeList(state) {
      return state.codeList
    }
  },
  actions: {
    setUserId(userId: string) {
      this.userId = userId
    },
    setNickName(nickName: string) {
      this.nickName = nickName
    },
    setToken(token: string) {
      this.token = token
    },
    setCodeList(codeList: any) {
      this.codeList = codeList
    },
    getUserInfo() {
      return new Promise<void>((resolve, reject) => {
        getUserInfoApi(this.userId)
          .then(res => {
            if (res && res.code === 200) {
              this.codeList = res.data.permissons
              localStorage.setItem('codeList', JSON.stringify(res.data.permissons))
            }
            resolve(this.codeList)
          })
          .catch(error => {
            reject(error)
          })
      })
    }
  },
  persist: [
    {
      key: 'user',
      //   pick: ['userId', 'nickName', 'token'],
      storage: localStorage
    }
  ]
})
