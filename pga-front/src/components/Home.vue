<script setup lang="ts">
defineProps<{
  msg: string
}>()
</script>

<template>
  <div class="greetings">
    <h1 class="green">{{ msg }}</h1>
    <h3>
      <!-- <button @click="comprarCurso">Comprar curso</button> -->
      <PaypalButton />
    </h3>
  </div>
</template>

<script lang="ts">
import PaypalButton from "./PaypalButton.vue";
  export default {
    data() {
      return {
        msg: "curso",
      };
    },

    methods: {
      async comprarCurso() {
        // try {
        //   const response = await 
        // } catch (error) {
          
        // }
      }
    },
    components: {
      PaypalButton,
    }
  }
</script>

<style scoped>
h1 {
  font-weight: 500;
  font-size: 2.6rem;
  position: relative;
  top: -10px;
}

h3 {
  font-size: 1.2rem;
}

.greetings h1,
.greetings h3 {
  text-align: center;
}

@media (min-width: 1024px) {
  .greetings h1,
  .greetings h3 {
    text-align: left;
  }
}
</style>
