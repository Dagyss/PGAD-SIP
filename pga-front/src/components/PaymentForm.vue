<template>
    <div>
      <div id="cardPaymentBrick_container"></div>
    </div>
  </template>
  
  <script>
  export default {
    mounted() {
      this.$nextTick(() => {
        this.loadMercadoPagoBrick();
      });
    }
    ,
    methods: {
      loadMercadoPagoBrick() {
        if (!window.MercadoPago) {
          const script = document.createElement("script");
          script.src = "https://sdk.mercadopago.com/js/v2";
          script.async = true;
          script.onload = this.initializeBrick;
          document.body.appendChild(script);
        } else {
          this.initializeBrick();
        }
      },
      initializeBrick() {
        const mp = new window.MercadoPago("YOUR_PUBLIC_KEY", { locale: "es-AR" });
        const bricksBuilder = mp.bricks();
  
        const settings = {
          initialization: {
            amount: 100,
            payer: { email: "test@example.com" },
          },
          customization: {
            visual: { style: { theme: "default" } },
            paymentMethods: { maxInstallments: 1 },
          },
          callbacks: {
            onSubmit: (cardFormData) => {
              return fetch("http://localhost:8080/payment/process", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(cardFormData),
              })
                .then((response) => response.json())
                .then((result) => {
                  console.log("Payment result:", result);
                })
                .catch((error) => console.error("Payment error:", error));
            },
            onError: (error) => console.error("Brick error:", error),
          },
        };
  
        bricksBuilder.create("cardPayment", "cardPaymentBrick_container", settings);
      },
    },
  };
  </script>
  