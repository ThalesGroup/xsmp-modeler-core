<template>
  <div class="configure notification">
    <div class="columns">
      <div class="column is-half">
        <div class="field mb-6">
          <label for="build-automatically" class="label">Build Automatically</label>
          <div class="control">
            <label class="radio">
              <input type="radio" v-model="buildAutomatically" value="true"> Yes
            </label>
            <label class="radio">
              <input type="radio" v-model="buildAutomatically" value="false"> No
            </label>
          </div>
        </div>
        <div class="field mb-6">
          <label for="idf-board" class="label">Select Profile</label>
          <div class="control">
            <div class="select">
              <select name="idf-board" id="idf-board" v-model="profile">
                <option v-for="profil in availableProfile" :value="profil">{{ profil }}</option>
              </select>
            </div>
          </div>
        </div>
        <div class="field mb-6">
          <label for="tools" class="label">Select Tools</label>
          <div class="control">
            <ul class="menu-list">
              <li v-for="(tool, index) in availableTools" :key="index">
                <label class="checkbox">
                  <input type="checkbox" v-model="tools" :value="tool">
                  {{ tool }}
                </label>
              </li>
            </ul>
          </div>
        </div>
      </div>

      <div class="column is-half">
        <div class="field mb-6">
          <label for="source-folders" class="label">Source Folders</label>
          <div class="field has-addons">
            <div class="control">
              <input class="input" type="text" v-model="newSourceFolder" placeholder="Add a source folder">
            </div>
            <div class="control">
              <a class="button is-link" @click="addSourceFolder(newSourceFolder)">Add</a>
            </div>
          </div>
          <div class="tags mt-4">
            <span class="tag is-link" v-if="sources.length === 0">smdl</span>
            <span v-for="(folder, index) in sources" :key="index" class="tag is-link">
              {{ folder }}
              <button class="delete is-small" @click="removeSourceFolder(index)"></button>
            </span>
          </div>
        </div>
        <div class="field mb-6">
          <label for="dependencies" class="label">Manage Dependencies</label>
          <div class="field has-addons">
            <div class="control">
              <div class="select is-fullwidth">
                <select v-model="selectedDependency">
                  <option value="" disabled>Select a dependency</option>
                  <option v-for="dependency in availableDependencies" :value="dependency">{{ dependency }}</option>
                </select>
              </div>
            </div>
            <div class="control">
              <button class="button is-info" @click="addDependency(selectedDependency)">Add</button>
            </div>
          </div>
          <div class="tags mt-1">
            <span v-for="(dependency, index) in dependencies" :key="index" class="tag is-info">
              {{ dependency }}
              <button class="delete is-small" @click="removeDependency(index)"></button>
            </span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {mapActions, mapState, mapMutations} from "vuex";

export default {
  data() {
    return {
      availableProfile: ["xsmp-sdk", "esa-sdk"],
      availableTools: ["smp", "python"],

      newDependency: '',
      selectedDependency: '',

      newSourceFolder: ''
    };
  },
  computed: {
    buildAutomatically: {
      get() {
        return this.$store.state.buildAutomatically;
      },
      set(val) {
        this.$store.state.buildAutomatically = val;
      },
    },
    profile: {
      get() {
        return this.$store.state.profile;
      },
      set(val) {
        this.setProfile(val);
      },
    },
    tools: {
      get() {
        return this.$store.state.tools;
      },
      set(val) {
        this.setTools(val);
      },
    },
    sources: {
      get() {
        return this.$store.state.sources;
      }
    },
    dependencies: {
      get() {
        return this.$store.state.dependencies;
      }
    },
    availableDependencies: {
      get() {
        return this.$store.state.availableDependencies;
      }
    }
  },
  methods: {
    ...mapActions(["saveSettings"]),
    ...mapMutations([
      "setProfile",
      "setTools",
      "addSourceFolder",
      "removeSourceFolder",
      "addDependency",
      "removeDependency"
    ]),
  },
  watch: {
    buildAutomatically: "saveSettings",
    profile: "saveSettings",
    tools: "saveSettings",
    sources: {
      handler: "saveSettings",
      deep: true,
    },
    dependencies: {
      handler: "saveSettings",
      deep: true,
    },
  },
};
</script>

<style lang="scss">
.centerize {
  align-items: center;
  display: flex;
  flex-direction: column;
  width: 100%;
  justify-content: center;
}

.configure {
  display: flex;
  flex-direction: column;
  margin: 1%;
}

.column {
  padding-inline: 40px;
}

.expanded {
  width: 70%;
  align-items: center;
  display: flex;
  justify-content: center;
}

.install-btn {
  margin: 0.5em;
  align-self: flex-end;
}

.notification span {
  font-weight: bold;
}
</style>
