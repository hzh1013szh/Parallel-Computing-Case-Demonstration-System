import axios from "axios";

// 创建axios实例
const request = axios.create({
  baseURL: "http://localhost:8080/api",
  timeout: 5000 // 请求超时时间
});

// request拦截器
// 可以自请求发送前对请求做一些处理
// 比如统一加token，对请求参数统一加密
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';
    // config.headers['token'] = user.token;  // 设置请求头
    // 前台把token获取到，下次请求时带到header中
    const user = localStorage.getItem('user');
    if(user){
      config.headers['token'] = JSON.parse(user).token;
    }
    return config;
}, error => {
  return Promise.reject(error);
});

//response拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
  response => {
      // response.data为服务端返回的数据
      let res = response.data;
      // 兼容服务端返回的字符串数据
      if (typeof res === 'string') {
        res = res ? JSON.parse(res) : res
      }
      return res;
  },
    error => {
        console.log('err' + error)
        return Promise.reject(error);
    }
);
export default request;