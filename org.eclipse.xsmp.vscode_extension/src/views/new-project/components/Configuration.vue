<template>
  <div class="configure notification">
    <div class="field">
      <label for="projectName" class="label">Project Name</label>
      <div class="control expanded">
        <input
            type="text"
            name="projectName"
            id="projectName"
            class="input"
            v-model="projectName"
            placeholder="project-name"
        />
      </div>
    </div>

    <FolderOpen
        propLabel="Enter Project directory"
        :propModel.sync="containerDirectory"
        :propMutate="setContainerDirectory"
        :openMethod="openProjectDirectory"
        :staticText="projectName"
    />

    <div class="field">
      <label for="idf-board" class="label">Select Profile</label>
      <div class="control">
        <div class="select">
          <select name="idf-board" id="idf-board" v-model="profile">
            <option v-for="profil in availableProfile" :value="profil">{{ profil }}</option>
          </select>
        </div>
      </div>
    </div>

    <div class="field">
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

    <div class="field install-btn">
      <div class="control">
        <button class="button is-info" @click="createProject" v-bind:disabled="isCreateButtonDisabled">Create Project</button>
      </div>
    </div>
  </div>
</template>

<script>
import FolderOpen from "./FolderOpen.vue";
import { mapActions, mapState, mapMutations } from "vuex";

export default {
  components: {
    FolderOpen,
  },
  data() {
    return {
      availableProfile: ["xsmp-sdk", "esa-sdk"],
      availableTools: ["smp", "python"],
    };
  },
  mounted() {
    this.requestInitialValues();
  },
  computed: {
    ...mapState(["containerDirectory"]),
    isCreateButtonDisabled() {
      return !this.projectName || !this.containerDirectory || !this.profile;
    },
    projectName: {
      get() {
        return this.$store.state.projectName;
      },
      set(val) {
        this.setProjectName(val);
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
  },
  methods: {
    ...mapActions(["createProject", "openProjectDirectory", "requestInitialValues"]),
    ...mapMutations(["setProjectName", "setContainerDirectory", "setProfile", "setTools"]),
  },
};
</script>

<style lang="scss">
.centerize {
  align-items: center;
  display: flex;
  flex-direction: column;
  width: 100%;
}

.configure {
  display: flex;
  flex-direction: column;
  margin: 1%;
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