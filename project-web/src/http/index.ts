import axios, { AxiosInstance, InternalAxiosRequestConfig, AxiosResponse, AxiosRequestConfig } from 'axios'
import { ElMessage } from 'element-plus'
import { Result } from '@/types'
import { useUserStore } from '@/store/user'
import { useMenuStore } from '@/store/menu'

const config = {
  //   baseURL: 'http://localhost:8089', // 请求接口的地址
  baseURL: '/api', // 请求接口的地址
  timeout: 5000, // 请求超时时间
  withCredentials: true // 是否允许携带凭证
  //   headers: {
  //     'Content-Type': 'application/json;charset=UTF-8'
  //   }
}

// axios.defaults.withCredentials = true // 携带cookie

class Http {
  // 初始化axios
  private instance: AxiosInstance
  // 初始化
  constructor(configs: AxiosRequestConfig) {
    // 创建axios实例
    this.instance = axios.create(configs)
    // 配置拦截器
    this.interceptors()
  }
  // 请求发送，返回之后处理
  private interceptors() {
    // 添加请求拦截器
    this.instance.interceptors.request.use(
      (config: InternalAxiosRequestConfig) => {
        let store = useUserStore()
        let user = JSON.parse(localStorage.getItem('user') || '{}')

        // 获取token
        let token = store.getToken || user.token
        if (token) {
          //   config.headers['Authorization'] = token
          config.headers!['token'] = token
        }
        return config
      },
      (error: any) => {
        error.data = {}
        error.data.msg = '服务器异常，请联系管理员!'
        return error
      }
    )
    // 添加响应拦截器
    this.instance.interceptors.response.use(
      (response: AxiosResponse) => {
        let userStore = useUserStore()
        let menuStore = useMenuStore()
        if (response.data.code === 600) {
          // token过期，调到登录页面
          // 清空缓存
          sessionStorage.clear()
          localStorage.clear()
          userStore.setUserId('')
          userStore.setToken('')
          userStore.setCodeList([])
          menuStore.setMenu([])
          window.location.href = '/login'
        } else if (response.data.code === 200) {
          return response.data
        } else {
          ElMessage.error(response.data.msg || '服务器出错！')
          return Promise.reject(response.data.msg || '服务器出错！')
        }
      },
      (error: any) => {
        error.data = {}
        if (error & error.response) {
          switch (error.response.status) {
            case 400:
              error.data.msg = '错误请求'
              ElMessage.error(error.data.msg)
              break
            case 401:
              error.data.msg = '未授权，请重新登录'
              ElMessage.error(error.data.msg)
              break
            case 403:
              error.data.msg = '拒绝访问'
              ElMessage.error(error.data.msg)
              break
            case 404:
              error.data.msg = '请求错误，未找到接口'
              ElMessage.error(error.data.msg)
              break
            case 405:
              error.data.msg = '请求方法未允许'
              ElMessage.error(error.data.msg)
              break
            case 408:
              error.data.msg = '请求超时'
              ElMessage.error(error.data.msg)
              break
            case 500:
              //   error.data.msg = '服务器端出错'
              error.data.msg = '后台接口出错'
              ElMessage.error(error.data.msg)
              break
            case 501:
              error.data.msg = '网络未实现'
              ElMessage.error(error.data.msg)
              break
            case 502:
              error.data.msg = '网铬错误'
              ElMessage.error(error.data.msg)
              break
            case 503:
              error.data.msg = '服务不可用'
              ElMessage.error(error.data.msg)
              break
            case 504:
              error.data.msg = '网铬超时'
              ElMessage.error(error.data.msg)
              break
            case 505:
              error.data.msg = 'http版本不支持该请求'
              ElMessage.error(error.data.msg)
              break
            default:
              error.data.msg = `连接错误${error.response.status}`
              ElMessage.error(error.data.msg)
          }
        } else {
          error.data.msg = '连接到服务器失败'
          ElMessage.error(error.data.msg)
        }
        return Promise.reject(error)
      }
    )
  }
  // Post
  post<T = Result>(url: string, data?: object): Promise<T> {
    return this.instance.post(url, data)
  }
  //Put
  put<T = Result>(url: string, data?: object): Promise<T> {
    return this.instance.put(url, data)
  }
  //Get
  get<T = Result>(url: string, params?: object): Promise<T> {
    return this.instance.get(url, { params })
  }
  // Delete
  delete<T = Result>(url: string): Promise<T> {
    return this.instance.delete(url)
  }
  // 图片上传
  upload<T = Result>(url: string, params?: object): Promise<T> {
    return this.instance.post(url, params, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    })
  }
}

export default new Http(config)
