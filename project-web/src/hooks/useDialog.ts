import { DialogModel } from '@/types'
export default function useDialog() {
  // 定义弹框属性
  const dialog = reactive<DialogModel>({
    title: '标题',
    visible: false,
    width: 630,
    height: 280
  })

  const onShow = () => {
    dialog.visible = true
  }
  const onClose = () => {
    dialog.visible = false
  }
  const onConfirm = () => {
    dialog.visible = false
  }
  return {
    dialog,
    onShow,
    onClose,
    onConfirm
  }
}
