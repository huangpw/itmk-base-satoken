<template>
  <el-dropdown>
    <span class="el-dropdown-link">
      <el-icon size="24" color="var(--el-text-color-bred)"><HelpFilled /></el-icon>
      <span>主题</span>
    </span>
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item @click="onChangeTheme('green')">green</el-dropdown-item>
        <el-dropdown-item @click="onChangeTheme('brown')">brown</el-dropdown-item>
        <el-dropdown-item @click="onChangeTheme('grape')">grape</el-dropdown-item>
        <el-dropdown-item @click="onChangeTheme('default')">default</el-dropdown-item>
        <el-dropdown-item @click="onChangeTheme('blacks')">blacks</el-dropdown-item>
        <el-dropdown-item @click="onChangeTheme('whites')">whites</el-dropdown-item>
        <el-dropdown-item @click="onChangeTheme('default1')">default1</el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script setup lang="ts">
const onChangeTheme = (theme: any) => {
  document.getElementsByTagName('html')[0].className = theme
}
</script>

<style lang="scss" scoped>
.example-showcase .el-dropdown-link {
  cursor: pointer;
  /* color: var(--el-color-logo-color); */
  display: flex;
  align-items: center;
}
.el-dropdown {
  color: var(--el-color-logo-color) !important;
  font-size: var(--el-text-bred-font-size);
  margin-right: 20px;
}
.el-dropdown-link {
  outline: none;
  padding-top: 4px;
  display: flex;
  align-items: center;
  cursor: pointer;
  span {
    color: var(--el-text-color-bred) !important;
  }
}
</style>
