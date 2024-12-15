<template>
  <el-main :style="{ height: mainHeight + 'px' }">
    <div style="display: flex">
      <el-card style="flex: 1">
        <template #header>
          <div class="card-header">订单统计</div>
        </template>
        <div ref="myChart" :style="{ width: '100%', height: '300px' }"></div>
      </el-card>
      <el-card style="flex: 1; margin-left: 20px">
        <template #header>
          <div class="card-header">热门商品</div>
        </template>
        <div ref="myChart1" :style="{ width: '100%', height: '300px' }"></div>
      </el-card>
      <el-card style="flex: 1; margin-left: 20px">
        <template #header>
          <div class="card-header">最受欢迎</div>
        </template>
        <div ref="myChart2" :style="{ width: '100%', height: '300px' }"></div>
      </el-card>
    </div>
  </el-main>
</template>

<script setup lang="ts">
import useInstance from '@/hooks/useInstance'

const { global } = useInstance()
const mainHeight = ref(0)
const myChart = ref(null)
const myChart1 = ref(null)
const myChart2 = ref(null)

// 柱状图
const charts1 = () => {
  const chartInstance = global.$echarts.init(myChart.value)
  let option = reactive({
    xAxis: {
      type: 'category',
      data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        data: [120, 200, 150, 80, 70, 110, 130],
        type: 'bar'
      }
    ]
  })
  chartInstance.setOption(option)
}
// 饼图
const charts2 = () => {
  const myChart = global.$echarts.init(myChart1.value)
  let option = reactive({
    title: {
      // text: 'Referer of a Website',
      subtext: 'Fake Data',
      left: 'center'
    },
    tooltip: {
      trigger: 'item'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: '50%',
        data: [
          { value: 1048, name: 'Search Engine' },
          { value: 735, name: 'Direct' },
          { value: 580, name: 'Email' },
          { value: 484, name: 'Union Ads' },
          { value: 300, name: 'Video Ads' }
        ],
        emphasis: {
          itemStyle: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }
    ]
  })
  myChart.setOption(option)
}
// 环形图
const charts3 = () => {
  const myChart = global.$echarts.init(myChart2.value)
  let option = reactive({
    tooltip: {
      trigger: 'item'
    },
    legend: {
      top: '5%',
      left: 'center'
    },
    series: [
      {
        name: 'Access From',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: 40,
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: [
          { value: 1048, name: 'Search Engine' },
          { value: 735, name: 'Direct' },
          { value: 580, name: 'Email' },
          { value: 484, name: 'Union Ads' },
          { value: 300, name: 'Video Ads' }
        ]
      }
    ]
  })
  myChart.setOption(option)
}
onMounted(() => {
  charts1()
  charts2()
  charts3()
  nextTick(() => {
    mainHeight.value = window.innerHeight - 102
  })
})
</script>

<style scoped></style>
