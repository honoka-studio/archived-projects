<template>
  <div>
    <h2>HTTP API Test</h2>
    <div>URL: https://api.bilibili.com/x/web-interface/wbi/index/top/feed/rcmd</div>
    <div class="content">
      <pre>{{ content }}</pre>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref } from 'vue'
import bilibiliApi from '../http-api/bilibili'

const content = ref('{"a": "123"}')

onMounted(() => {
  bilibiliApi.rcmd().then(res => {
    content.value = JSON.stringify(res, null, 2)
  })
})
</script>

<style scoped lang="scss">
.content {
  border: 1px solid white;
  padding: 0.5em;
  margin-top: 0.5em;
  margin-bottom: 1em;
}

pre {
  font-size: 1em;
  font-family: Consolas, serif;
  white-space: pre-wrap;
  word-wrap: break-word;
}
</style>