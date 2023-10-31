<template>
  <div id="init">
    <div class="control">
      <h1 class="title is-spaced">New Project</h1>
    </div>
    <transition name="fade" mode="out-in">
      <router-view/>
    </transition>

    <Configuration/>
  </div>
</template>

<script>
import Configuration from './components/Configuration.vue';

export default {
  components: {
    Configuration
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
