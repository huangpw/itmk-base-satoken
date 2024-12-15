import { defineStore } from 'pinia'

// 定义Store
export const useTestStore = defineStore('testStore', {
    state: () => ({
        count: 0,
        name: 'Eduardo'
    }),
    getters: {
        getCount(state) { 
            return state.count
        }
    },
    actions: {
        setCount(count: number) {
            console.log(count)
            this.count = count
        }
    }
})