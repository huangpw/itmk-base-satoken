import { ElMessageBox } from 'element-plus'

export default function myconfirm(text: string, title: string) {
  return new Promise((resolve, reject) => {
    ElMessageBox.confirm(text, title || '系统提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
      .then(() => {
        // 确定
        resolve(true)
      })
      .catch(() => {
        // 取消
        reject(false)
      })
  }).catch(() => {
    return false
  })
}
