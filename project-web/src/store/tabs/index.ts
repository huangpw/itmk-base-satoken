import { defineStore } from 'pinia'
import { Tab, TabState } from '@/types'

// 定义Store
export const useTabsStore = defineStore('tabsStore', {
  state: (): TabState => ({
    tabList: []
  }),
  getters: {
    getTabList(state) {
      return state.tabList
    }
  },
  actions: {
    addTabList(tab: Tab) {
      if (!this.tabList.some(item => item.path === tab.path)) {
        if (tab.path === '/login') {
          return
        }
        if (tab.path === '/dashboard') {
          this.tabList.unshift(tab)
        } else {
          this.tabList.push(tab)
        }
      }
    }
  },
  persist: [
    {
      key: 'tabList',
      storage: localStorage
    }
  ]
})
