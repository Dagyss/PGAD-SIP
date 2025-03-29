<template>
    <div>
      <div id="paypal-button-container"></div>
      <p id="result-message"></p>
    </div>
  </template>
  
  <script>
  export default {
    mounted() {
      this.loadPaypalScript();
    },
    methods: {
      async loadPaypalScript() {
        if (document.getElementById("paypal-sdk")) return;
  
        const script = document.createElement("script");
        script.id = "paypal-sdk";
        script.src = "https://www.paypal.com/sdk/js?client-id=TU_CLIENT_ID&currency=USD";
        script.onload = this.renderPaypalButton;
        document.body.appendChild(script);
      },
      renderPaypalButton() {
        window.paypal.Buttons({
          style: {
            shape: "rect",
            layout: "vertical",
            color: "gold",
            label: "paypal",
          },
          async createOrder() {
            const response = await fetch("/api/paypal/orders", {
              method: "POST",
              headers: { "Content-Type": "application/json" },
              body: JSON.stringify({
                cart: [{ id: "PRODUCT_ID", quantity: 1 }],
              }),
            });
  
            const orderData = await response.json();
            return orderData.id;
          },
          async onApprove(data, actions) {
            const response = await fetch(`/api/paypal/orders/${data.orderID}/capture`, {
              method: "POST",
              headers: { "Content-Type": "application/json" },
            });
  
            const orderData = await response.json();
            const transaction = orderData?.purchase_units?.[0]?.payments?.captures?.[0];
            document.querySelector("#result-message").innerHTML = `Transacción ${transaction.status}: ${transaction.id}`;
          },
        }).render("#paypal-button-container");
      },
    },
  };
  </script>
  