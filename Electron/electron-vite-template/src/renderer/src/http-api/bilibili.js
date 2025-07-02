import request from '../utils/request'

const bilibiliApi = {
  rcmd: () => request({
    url: 'https://api.bilibili.com/x/web-interface/wbi/index/top/feed/rcmd',
    method: 'get'
  })
}

export default bilibiliApi