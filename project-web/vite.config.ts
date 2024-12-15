import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import * as path from 'path'

import AutoImport from 'unplugin-auto-import/vite'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    AutoImport({
      imports: [
        'vue',
        'vue-router',
        // 'vue-i18n',
        // '@vueuse/core',
        'pinia'
      ],
      dts: path.resolve(__dirname, 'src/types/auto-imports.d.ts') // 使用typescript，需要指定生成对应的d.ts文件或者设置为true,生成默认导入d.ts文件
      // dirs: ['src/stores', 'src/composables', 'src/hooks'],
    })
  ],
  server: {
    host: '0.0.0.0',
    port: 8081,
    strictPort: true,
    open: false,
    proxy: {
      '/api': {
        target: 'http://localhost:8089/',
        ws: false,
        secure: false,
        changeOrigin: true,
        rewrite: path => path.replace(/^\/api/, '')
      }
    }
  },
  resolve: {
    alias: {
      '@': path.resolve(__dirname, 'src')
    }
  },
  css: {
    // css预处理器
    preprocessorOptions: {
      // 引入 mixin.scss 这样就可以在全局中使用 mixin.scss中预定义的变量了
      // 给导入的路径最后加上 ;
      scss: {
        additionalData: `@use "@/assets/styles/variables.scss" as *;`
      }
    }
  }
})
