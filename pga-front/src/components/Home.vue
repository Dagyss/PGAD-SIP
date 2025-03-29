<script lang="ts">
import PaypalButton from "./PaypalButton.vue";

export default {
  components: {
    PaypalButton
  },
  props: {
    msg: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      cursoMsg: "curso"
    };
  },
  methods: {
    async comprarCurso() {
      // tu lógica aquí
    }
  }
}
</script>

<template>
  <div class="greetings">
    <h1 class="green">{{ msg }}</h1>
    <h3>
      <PaypalButton />
    </h3>
  </div>
</template>

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
