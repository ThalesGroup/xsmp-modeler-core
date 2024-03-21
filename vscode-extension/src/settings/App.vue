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
/*******************************************************************************
* Copyright (C) 2023 THALES ALENIA SPACE FRANCE.
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License 2.0
* which accompanies this distribution, and is available at
* https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/

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
