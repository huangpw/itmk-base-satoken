<template>
  <el-select
    v-model="selectedOptions"
    multiple
    placeholder="请选择"
    :popper-append-to-body="false"
    :style="selectWidth"
    @remove-tag="removeTag"
  >
    <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
      <el-checkbox v-model="item.check" @change="isChecked(item)">{{ item.label }}</el-checkbox>
    </el-option>
    <div class="is-all">
      <div @click="selectAll(true)">全选</div>
      <div @click="selectAll(false)">反选</div>
    </div>
  </el-select>
</template>

<script setup lang="ts">
// 定义下拉的数据类型
type SelectItem = {
  value: string | number
  label: string
  check: boolean
}

// 接收父组件的参数
let props = defineProps({
  options: {
    type: Array<SelectItem>,
    required: true
  },
  width: {
    type: Number,
    default() {
      return 220
    }
  },
  bindValue: {
    type: Array<string | number>,
    default() {
      return []
    }
  }
})
// 选择器宽度
let selectWidth = 'width:' + props.width + 'px'
// 定义事件
const emit = defineEmits(['selected'])
// 下拉的数据
let selectedOptions = ref<Array<string | number>>([])
// 下拉的点击事件
const isChecked = (item: SelectItem) => {
  if (item.check && selectedOptions.value.indexOf(item.value) === -1) {
    selectedOptions.value.push(item.value)
  } else if (!item.check) {
    selectedOptions.value.forEach((elm, idx) => {
      if (elm == item.value) {
        selectedOptions.value.splice(idx, 1)
      }
    })
  }
  emit('selected', selectedOptions.value)
}
// 删除的图标事件
const removeTag = (value: any) => {
  props.options.forEach(elm => {
    if (elm.value == value) {
      elm.check = false
    }
  })
  emit('selected', selectedOptions.value)
}
// 全选和反选
const selectAll = (isAll: boolean) => {
  if (isAll) {
    selectedOptions.value = []
    props.options.forEach((item: SelectItem) => {
      item.check = true
      selectedOptions.value.push(item.value)
    })
    console.log(selectedOptions.value)
  } else {
    let arr: Array<string | number> = []
    props.options.forEach((item: SelectItem) => {
      item.check = false
      if (!selectedOptions.value.includes(item.value)) {
        arr.push(item.value)
      }
    })
    selectedOptions.value = arr
  }
  emit('selected', selectedOptions.value)
}
// 清空下拉数据
const clear = () => {
  selectedOptions.value = []
}
// 暴露出去，给外部组件使用
defineExpose({
  clear
})
// 监听父组件的值
watch(
  () => props.bindValue,
  () => {
    selectedOptions.value = props.bindValue
    // 设置checkbox的选中状态
    props.bindValue.forEach(item => {
      props.options.find(dom => {
        if (dom.value === item) {
          dom.check = true
        }
      })
    })
  },
  { immediate: true, deep: true }
)
</script>

<style lang="scss">
.is-all {
  display: flex;
  padding-left: 10px;
  div {
    cursor: pointer;
    margin: 6px 10px;
    transition: 0.2s;
    &:hover {
      opacity: 0.7;
    }
  }
}
</style>
