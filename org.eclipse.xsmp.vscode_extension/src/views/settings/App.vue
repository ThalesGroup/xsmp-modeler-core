<template>
  <div id="init">
    <div class="control">
      <h1 class="title is-spaced">XSMP settings</h1>
      <hr/>
    </div>

    <Settings/>
  </div>
</template>

<script>
import Settings from './components/Settings.vue';

export default {
  components: {
    Settings
  },
};
</script>

<style lang="scss">
#init {
  cursor: default;
  margin: auto;
  padding-top: 1em;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 1s;
}

.title {
  text-align: center;
}
</style>
