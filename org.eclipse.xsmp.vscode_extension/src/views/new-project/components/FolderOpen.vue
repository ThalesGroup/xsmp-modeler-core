<template>
  <div class="field text-size">
    <label class="label">{{ propLabel }}</label>
    <div class="field expanded has-addons">
      <div class="control expanded">
        <input
            type="text"
            class="input"
            v-model="dataModel"
        />
      </div>
      <div class="control" v-if="staticText">
        <a class="button is-static">{{ pathSep + staticText }}</a>
      </div>
      <div class="control">
        <div class="icon is-large is-size-4" style="text-decoration: none;">
          <Icon
              :icon="folderIcon"
              @mouseover="folderIcon = 'mingcute:folder-open-line'"
              @mouseout="folderIcon = 'mingcute:folder-line'"
              v-on:click="openMethod"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {Icon} from '@iconify/vue';

export default {
  components: {
    Icon,
  },
  props: {
    propLabel: String,
    propModel: String,
    propMutate: Function,
    openMethod: Function,
    keyEnterMethod: Function,
    staticText: String,
  },
  data() {
    return {
      folderIcon: 'mingcute:folder-line',
    };
  },
  computed: {
    dataModel: {
      get() {
        return this.propModel;
      },
      set(newValue) {
        this.propMutate(newValue);
      },
    },
    pathSep() {
      return navigator.platform.indexOf("Win") !== -1 ? "\\" : "/";
    },
  },
  methods: {
    onKeyEnter() {
      if (this.keyEnterMethod) {
        this.keyEnterMethod();
      }
    }
  },
};
</script>